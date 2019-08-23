package com.bridgelabz.additional.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.additional.rabbitmq.RabbitmqConsumer;

@Configuration
public class RabbitmqConsumerConfiguration {

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
	MessageListenerAdapter listenerAdapter(RabbitmqConsumer consumer) {
		return new MessageListenerAdapter(consumer, LISTENER_METHOD);
	}

}

//	{
//	
//	@Value("${jsa.rabbitmq.queue}")
//	   private String queueName;
//	   
//	   @Value("${jsa.rabbitmq.exchange}")
//	   private String exchange;
//	   
//	   @Value("${jsa.rabbitmq.routingkey}")
//	   private String routingKey;
//	   
//	   @Bean
//	   Queue queue() {
//	      return new Queue(queueName, false);
//	   }
//
//	   @Bean
//	   DirectExchange exchange() {
//	      return new DirectExchange(exchange);
//	   }
//
//	   @Bean
//	   Binding binding(Queue queue, DirectExchange exchange) {
//	      return BindingBuilder.bind(queue).to(exchange).with(routingKey);
//	   }
//	   @Bean
//	   public MessageConverter jsonMessageConverter() {
//	      return new Jackson2JsonMessageConverter();
//	   }
//
//	   
//	   @Bean
//	   public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//	      final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//	      rabbitTemplate.setMessageConverter(jsonMessageConverter());
//	      return rabbitTemplate;
//	   }
//
//	}
