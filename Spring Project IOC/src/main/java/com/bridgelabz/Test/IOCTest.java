package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.bridgelabz.Bean.Mobile;
import com.bridgelabz.Bean.Refrigirator;
import com.bridgelabz.Bean.Television;

public class IOCTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		System.out.println("****** Inversion Of Control ********");
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/beanIOC.xml");
		Mobile mobile = context.getBean("Mobile", Mobile.class);
		System.out.println("\nCreating Mobile Object:\n");
		mobile.productName();
		mobile.productPrice();

		System.out.println("\nCreating Television Object:\n");
		Television television = context.getBean("television", Television.class);
		television.productName();
		television.productPrice();

		System.out.println("\nCreating Refrigirator Object:\n");
		Refrigirator refrigirator = context.getBean("refrigirator", Refrigirator.class);
		refrigirator.productName();
		refrigirator.productPrice();

	}

}
