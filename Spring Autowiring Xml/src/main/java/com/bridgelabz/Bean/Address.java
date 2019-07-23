package com.bridgelabz.Bean;

public class Address {
	private String city;
	private String state;
	private String zipcode;

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", state=" + state + ", zipcode=" + zipcode + "]";
	}
	
	

}
