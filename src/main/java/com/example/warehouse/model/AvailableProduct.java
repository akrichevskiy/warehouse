package com.example.warehouse.model;

public class AvailableProduct {
	private int productId;
	private String name;
	private int quantity;
	
	public AvailableProduct(int productId, String name, int quantity) {
		this.productId = productId;
		this.name = name;
		this.quantity = quantity;
	}
	
	public AvailableProduct() {
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		AvailableProduct that = (AvailableProduct) o;
		
		if (quantity != that.quantity) {
			return false;
		}
		return name != null ? name.equals(that.name) : that.name == null;
	}
	
	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + quantity;
		return result;
	}
	
	@Override
	public String toString() {
		return "AvailableProduct{" +
				"name='" + name + '\'' +
				", quantity=" + quantity +
				'}';
	}
}
