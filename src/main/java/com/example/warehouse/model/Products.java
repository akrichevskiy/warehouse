package com.example.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Products {
	@JsonProperty(value = "products")
	private List<Product> productList;
	
	public Products() {
	}
	
	public List<Product> getProductList() {
		return productList;
	}
	
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
