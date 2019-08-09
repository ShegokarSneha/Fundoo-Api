package com.bridgelabz.fondooNotes.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fondooNotes.dto.MailDto;
import com.bridgelabz.fondooNotes.uitility.MailDataSender;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueConsumer {
	@Autowired
	private MailDataSender mailSender;

	public void receiveMessage(String message) {

		processMessage(message);
	}

	public void receiveMessage(byte[] message) {
		String strMessage = new String(message);

		processMessage(strMessage);
	}

	private void processMessage(String message) {
		try {
			MailDto maildto = new ObjectMapper().readValue(message, MailDto.class);
			mailSender.sendEmail(maildto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}