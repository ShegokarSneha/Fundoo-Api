package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.Customer;

public class AutowiringAnnotation {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/BeanAutowiringAnnotation.xml");
		Customer customer = context.getBean("customer", Customer.class);
		System.out.println("**** Constructor Annotation ****");
		customer.show();

	}

}
