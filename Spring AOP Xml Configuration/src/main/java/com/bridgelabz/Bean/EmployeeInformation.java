package com.bridgelabz.Bean;

import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeInformation {

	@Autowired
	Employee employee;

	public void display() {
		System.out.println("\nIn Display Method Of Employee Information");
		System.out.println(
				"Employee Name : " + employee.getEmployeename() + "\nEmployee Company : " + employee.getCompanyName());
	}

	public void show() {
		System.out.println("\nIn Show Method Of Employee Information");
		System.out.println(
				"Employee Name : " + employee.getEmployeename() + "\nEmployee Company : " + employee.getCompanyName());
	}

}
