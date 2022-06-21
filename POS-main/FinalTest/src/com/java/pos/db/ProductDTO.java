package com.java.pos.db;

public class ProductDTO {
	private int id;
	private String name;
	private int price;

	public ProductDTO(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public ProductDTO(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
