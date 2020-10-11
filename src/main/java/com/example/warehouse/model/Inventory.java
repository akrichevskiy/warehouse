package com.example.warehouse.model;

import java.util.List;

public class Inventory {
	private List<Article> inventory;
	
	public Inventory() {
	}
	
	public Inventory(List<Article> inventory) {
		this.inventory = inventory;
	}
	
	public List<Article> getInventory() {
		return inventory;
	}
	
	public void setInventory(List<Article> inventory) {
		this.inventory = inventory;
	}
}
