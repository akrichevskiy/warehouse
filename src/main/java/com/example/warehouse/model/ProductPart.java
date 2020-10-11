package com.example.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductPart {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	@JsonProperty(value = "art_id")
	private int itemid;
	
	@JsonProperty(value = "amount_of")
	private int amount;
	
	public ProductPart() {
	}
	
	public ProductPart(int itemid, int amount) {
		this.itemid = itemid;
		this.amount = amount;
	}
	
	public ProductPart(int id, int itemid, int amount) {
		this.id = id;
		this.itemid = itemid;
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		ProductPart that = (ProductPart) o;
		
		if (id != that.id) {
			return false;
		}
		if (itemid != that.itemid) {
			return false;
		}
		return amount == that.amount;
	}
	
	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + itemid;
		result = 31 * result + amount;
		return result;
	}
	
	public int getId() {
		return id;
	}
	
	public int getItemid() {
		return itemid;
	}
	
	@Override
	public String toString() {
		return "ProductPart{" +
				"id=" + id +
				", itemid=" + itemid +
				", amount=" + amount +
				'}';
	}
}
