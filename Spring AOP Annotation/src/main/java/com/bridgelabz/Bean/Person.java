package com.bridgelabz.Bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {

	private String name;
	@Autowired
	Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void display() {
		System.out.println("In Display Method");
		System.out.println("Person Name : " + name + "\nAddress : " + address);
	}
	
	public void show() {
		System.out.println("In Show Method");
		System.out.println("Person Name : " + name + "\nAddress : " + address);
		
	}

}
