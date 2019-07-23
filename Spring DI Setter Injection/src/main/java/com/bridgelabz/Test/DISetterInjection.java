package com.bridgelabz.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.Employee;

public class DISetterInjection {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/beansSetterInjection.xml");
		Employee employee = context.getBean("employee", Employee.class);
		System.out.println("Setter Injection");
		employee.display();
	}

}
