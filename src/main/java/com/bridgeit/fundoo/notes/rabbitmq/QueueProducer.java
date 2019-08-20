package com.bridgeit.fundoo.notes.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgeit.fundoo.notes.dto.MailDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueProducer {

	@Value("${fanout.exchange}")
	private String fanoutExchange;
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public QueueProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

	public void produce(MailDto mailDto) throws Exception {
		rabbitTemplate.setExchange(fanoutExchange);
		rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(mailDto));
	}

}
