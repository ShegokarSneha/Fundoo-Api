package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.Person;

public class Autowiring {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/BeanAutowiringXml.xml");
		Person person = context.getBean("person", Person.class);
		System.out.println("**** Autowiring In Xml File ****");
		System.out.println("\n====== Autowiring Using ByName Property ======");
		person.show();
		ApplicationContext bytype = new ClassPathXmlApplicationContext("com/bridgelabz/Config/ByType.xml");
		Person person1 = bytype.getBean("person1", Person.class);
		System.out.println("\n====== Autowiring Using ByType Property ======");
		person1.show();
		Person person2 = context.getBean("person2", Person.class);
		System.out.println("\n====== Autowiring Using ByConstructor Property ======");
		person2.show();

	}

}
