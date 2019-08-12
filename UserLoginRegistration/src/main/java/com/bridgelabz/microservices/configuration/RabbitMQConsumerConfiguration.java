package com.bridgelabz.microservices.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.microservices.rabbitmq.QueueConsumer;

@Configuration
public class RabbitMQConsumerConfiguration {
	
	private static final String LISTENER_METHOD = "receiveMessage";
	
	@Value("${queue.name}")
	private String queueName;
	
	@Value("${fanout.exchange}")
	private String fanoutExchange;

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(QueueConsumer consumer) {
		return new MessageListenerAdapter(consumer, LISTENER_METHOD);
	}
}