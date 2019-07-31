package com.bridgelabz.Fundoo.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitmqConfiguration {
	
	private static final String LISTENER_METHOD = "receiveMessage";


	@Value("${fanout.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.template.queue}")
	private String queue;

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
	
	@Bean
	 SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
	  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	  container.setConnectionFactory(connectionFactory);
	  container.setQueueNames(queue);
	  container.setMessageListener(listenerAdapter);
	  return container;
	 }
	 @Bean
	 MessageListenerAdapter listenerAdapter(RabbitmqProducer consumer) {
	  return new MessageListenerAdapter(consumer, LISTENER_METHOD);
	 }

}
