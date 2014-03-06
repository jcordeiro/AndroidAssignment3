package com.example.jonathan_cordeiro_3;

import java.util.ArrayList;

public class Pizza {
	
	private double price;
	private ArrayList<String> toppings;
	private Size size;
	
	public enum Size {
		SMALL,
		MEDIUM,
		LARGE
	}
	
	// Constructor
	public Pizza(double price, ArrayList<String> toppings, Size size) {
		this.price = price;
		this.toppings = toppings;
		this.size = size;
	}

	// Getter and setter methods
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArrayList<String> getToppings() {
		return toppings;
	}

	public void setToppings(ArrayList<String> toppings) {
		this.toppings = toppings;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	

}
