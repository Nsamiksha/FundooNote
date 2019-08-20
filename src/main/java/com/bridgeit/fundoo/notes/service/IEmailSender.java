package com.bridgeit.fundoo.notes.service;

import com.bridgeit.fundoo.notes.dto.MailDto;

public interface IEmailSender {

	void sendEmail(MailDto mailDTO);

}
