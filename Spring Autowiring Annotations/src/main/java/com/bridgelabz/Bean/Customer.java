package com.bridgelabz.Bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Customer {
	private Person person;
	private String action;
	
	public Customer() {
		
	}
	@Autowired
	public Customer(Person person, String action) {
		this.person = person;
		this.action = action;
		
	}

	public void setPerson(Person person) {
		this.person = person;
	}


	public void setAction(String action) {
		this.action = action;
	}
	
	public void show() {
		System.out.println(person.toString());
		System.out.println("Customer is "+action);
	}

}
