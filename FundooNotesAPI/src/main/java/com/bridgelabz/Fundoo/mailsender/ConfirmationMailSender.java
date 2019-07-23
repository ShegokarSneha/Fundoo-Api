package com.bridgelabz.Fundoo.mailsender;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String email, String subject, String body) {

		Properties properties = System.getProperties();

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		try {

			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(body);

			// Send message
			javaMailSender.send(message);
			System.out.println("\nMail Sent Successfully....");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
