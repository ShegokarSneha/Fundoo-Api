package com.bridgelabz.Fundoo.mailsender;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MailProducer {

	@Autowired
	private RabbitTemplate rabbittemplate;

	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.template.queue}")
	private String queue;

/*	public void generateMsg(String msg) {
		rabbittemplate.convertAndSend(exchange, routingkey, msg);
		System.out.println("Message :" + msg);
	}*/

	@Bean
	Queue queue() {
		return new Queue(queue, false);
	}

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

}
