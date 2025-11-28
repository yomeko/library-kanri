package model;

import java.io.Serializable;

public class Mutter implements Serializable{
	private int id;
	private int number;
	private String book;
	public Mutter() {}
	public Mutter(int number, String book) {
		this.number = number;
		this.book = book;
	}
	public Mutter(int id,int number, String book) {
		this.number = number;
		this.book = book;
		this.id = id;
	}
	
	public int getId() {return id;}
	public int getNumber() { return number;}
	public String getBook() { return book;}
}
