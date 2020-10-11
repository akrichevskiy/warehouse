package com.example.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int productId;
	
	private String name;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@JsonProperty(value = "contain_articles")
	private List<ProductPart> productPartList;
	
	public Product() {
	}
	
	public Product(int productId, String name,
			List<ProductPart> productPartList) {
		this.productId = productId;
		this.name = name;
		this.productPartList = productPartList;
	}
	
	public Product(String name, List<ProductPart> productPartList) {
		this.name = name;
		this.productPartList = productPartList;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<ProductPart> getProductPartList() {
		return productPartList;
	}
	
	public void setProductPartList(List<ProductPart> productPartList) {
		this.productPartList = productPartList;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Product product = (Product) o;
		
		if (name != null ? !name.equals(product.name) : product.name != null) {
			return false;
		}
		return productPartList != null ? productPartList.equals(product.productPartList)
				: product.productPartList == null;
	}
	
	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (productPartList != null ? productPartList.hashCode() : 0);
		return result;
	}
	
	@Override
	public String toString() {
		return "Product{" +
				"name='" + name + '\'' +
				", productPartList=" + productPartList +
				'}';
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
