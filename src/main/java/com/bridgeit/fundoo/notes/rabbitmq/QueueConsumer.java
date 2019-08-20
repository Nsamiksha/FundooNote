package com.bridgeit.fundoo.notes.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.fundoo.notes.dto.MailDto;
import com.bridgeit.fundoo.notes.service.IEmailSender;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueConsumer {

	@Autowired
	private IEmailSender iEmailSender;

	public void receiveMessage(String message) {
		processMessage(message);
	}

	public void receiveMessage(byte[] message) {
		String strMessage = new String(message);
		processMessage(strMessage);
	}

	private void processMessage(String message) {
		try {
			MailDto mailDTO = new ObjectMapper().readValue(message, MailDto.class);
			iEmailSender.sendEmail(mailDTO);
		} catch (JsonParseException e) {
			System.out.println("Bad JSON in message: " + message);
		} catch (JsonMappingException e) {
			System.out.println("cannot map JSON to NotificationRequest: " + message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
