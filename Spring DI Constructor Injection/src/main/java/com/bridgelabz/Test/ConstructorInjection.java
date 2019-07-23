package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.Book;

public class ConstructorInjection {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/beanConstructorInjection.xml");
		Book book = context.getBean("book", Book.class);
		System.out.println("***** Constructor Injection *****");
		book.Display();
		

	}

}
