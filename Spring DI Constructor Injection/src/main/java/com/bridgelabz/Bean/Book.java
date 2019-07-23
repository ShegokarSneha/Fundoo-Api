package com.bridgelabz.Bean;

public class Book {
	private int id;
	private String bookname;
	private String author;
	BookInformation info;

	public Book(int id, String bookname, String author, BookInformation info) {
		this.id = id;
		this.bookname = bookname;
		this.author = author;
		this.info = info;
	}
	
	public void Display() {
		System.out.println("\nBook ID: "+ id +"\nBook Name : " + bookname +
				"\nBook Author : " + author );
		System.out.println("\nBook Information Class :"); 
		info.InformationDisplay();
	}

}
