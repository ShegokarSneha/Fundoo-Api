package com.bridgelabz.microservices.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bridgelabz.microservices.Utility.MailSender;
import com.bridgelabz.microservices.dto.MailDto;

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