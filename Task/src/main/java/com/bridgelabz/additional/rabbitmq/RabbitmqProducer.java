package com.bridgelabz.additional.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.additional.model.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RabbitmqProducer {

	@Value("${fanout.exchange}")
	private String fanoutExchange;
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public RabbitmqProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

	public void produce(Image maildto) throws Exception {
		rabbitTemplate.setExchange(fanoutExchange);
		System.out.println(maildto.toString());
		rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(maildto));
	}
}

//	{
//
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//
//	@Value("${jsa.rabbitmq.exchange}")
//	private String exchange;
//
//	@Value("${jsa.rabbitmq.routingkey}")
//	private String routingKey;
//
//	public void produceMsg(Image image) {
//		try {
//			amqpTemplate.convertAndSend(exchange, routingKey, new ObjectMapper().writeValueAsString(image));
//		} catch (AmqpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Send msg = " + image);
//	}
//
//}
