package com.bridgelabz.Bean;

public class Person {
	private String name;
	private int age;
	private Address address;

	public Person(String name, int age, Address address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}
	public Person() {
		
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void show() {
		System.out.println("\nPerson Name: " + name + "\nPerson Age: " + age);
		System.out.println(address.toString());
	}

}
