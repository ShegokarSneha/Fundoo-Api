package com.bridgelabz.additional.rabbitmq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.additional.model.Image;
import com.bridgelabz.additional.service.AmazonS3ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RabbitmqConsumer {
	
	@Autowired
	private AmazonS3ServiceImpl amazonS3ServiceImpl;
	
	public void receiveMessage(String message) {
		processMessage(message);
	}

	public void receiveMessage(byte[] message) {
		String strMessage = new String(message);
		processMessage(strMessage);
	}

	private void processMessage(String message) {
		try {
			Image image = new ObjectMapper().readValue(message, Image.class);
			System.out.println("\nAfter Rabbitmq");
			System.out.println("File : "+image.getFile());
			System.out.println("File name : "+image.getFilename());
			System.out.println("Image : "+image.toString());
			amazonS3ServiceImpl.uploadFileTos3bucket(image.getFilename(), image.getFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
