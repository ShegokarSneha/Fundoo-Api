package com.bridgelabz.Bean;

public class Employee {
	private int id;
	private String name;
	private int salary;

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public void display() {
		System.out.println("\nEmployee ID : " + id +"\nEmployee Name: "+ name +
				"\nEmployee Salary : "+ salary);
	}

}
