package com.bridgelabz.Bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Product {
	private Mobile[] arrayofmobile;
	private List<Mobile> listofmobile;

	public Mobile[] getArrayofmobile() {
		return arrayofmobile;
	}

	@Autowired
	public void setArrayofmobile(Mobile[] arrayofmobile) {
		this.arrayofmobile = arrayofmobile;
	}

	public List<Mobile> getListofmobile() {
		return listofmobile;
	}

	@Autowired
	public void setListofmobile(List<Mobile> listofmobile) {
		this.listofmobile = listofmobile;
	}

}
