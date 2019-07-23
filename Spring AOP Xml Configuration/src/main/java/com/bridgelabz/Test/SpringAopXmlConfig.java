package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.EmployeeInformation;

public class SpringAopXmlConfig {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/Beanaop.xml");
		EmployeeInformation employee = context.getBean("info", EmployeeInformation.class);
		System.out.println("\n**** AOP Using Xml Configuration ****");
		System.out.println("\nBefore Advice\n");
		employee.display();
		
		System.out.println("\nAfter Advice\n");
		employee.show();
		

	}

}
