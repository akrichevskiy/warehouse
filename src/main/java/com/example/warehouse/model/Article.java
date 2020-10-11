package com.example.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Article {
	@Id
	@JsonProperty(value = "art_id")
	private int id;
	private String name;
	private int stock;
	
	public Article() {
	}
	
	public Article(int id, String name, int stock) {
		this.id = id;
		this.name = name;
		this.stock = stock;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "Article{" +
				"id=" + id +
				", name='" + name + '\'' +
				", stock=" + stock +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Article article = (Article) o;
		
		if (id != article.id) {
			return false;
		}
		if (stock != article.stock) {
			return false;
		}
		return name != null ? name.equals(article.name) : article.name == null;
	}
	
	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + stock;
		return result;
	}
}
