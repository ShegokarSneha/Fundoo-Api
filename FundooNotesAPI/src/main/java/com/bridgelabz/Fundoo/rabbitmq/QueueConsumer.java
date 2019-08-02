package com.bridgelabz.Fundoo.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.Fundoo.dto.MailDto;
import com.bridgelabz.Fundoo.mailsender.MailSender;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueConsumer {
	@Autowired
	private MailSender mailSender;
	// protected Logger logger = LoggerFactory.getLogger(getClass());
	public void receiveMessage(String message) {
		// logger.info("Received (String) " + message);
		processMessage(message);
	}

	public void receiveMessage(byte[] message) {
		String strMessage = new String(message);
		// logger.info("Received (No String) " + strMessage);
		processMessage(strMessage);
	}

	private void processMessage(String message) {
		try {
			MailDto maildto = new ObjectMapper().readValue(message, MailDto.class);
			// ValidationUtil.validateMailDTO(mailDTO);
			// mailServiceImpl.sendMail(mailDTO, null);
			mailSender.sendEmail(maildto);
		} catch (Exception e) {
			// logger.error(e.getMessage());
		}
	}
}