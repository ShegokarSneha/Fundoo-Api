package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.Person;

public class AopAnnotation {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/Beanaopannotation.xml");
		Person person = context.getBean("person", Person.class);
		System.out.println("\n**** AOP Using Xml Configuration ****");
		System.out.println("\nBefore Advice\n");
		person.display();
		
		System.out.println("\nAfter Advice\n");
		person.show();

	}

}
