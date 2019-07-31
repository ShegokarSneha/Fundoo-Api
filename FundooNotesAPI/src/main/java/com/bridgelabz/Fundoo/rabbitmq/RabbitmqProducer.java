package com.bridgelabz.Fundoo.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;


public class RabbitmqProducer {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${fanout.exchange}")
	private String fanoutExchange;
	
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	 public RabbitmqProducer(RabbitTemplate rabbitTemplate) {
	  this.rabbitTemplate = rabbitTemplate;
	 }

	public void produce(NotificationRequestDTO notificationDTO) throws Exception {
		logger.info("Storing notification...");
		rabbitTemplate.setExchange(fanoutExchange);
		rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(notificationDTO));
		logger.info("Notification stored in queue sucessfully");
	}

}
