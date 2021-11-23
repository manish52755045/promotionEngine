package com.app.pengine.model;

public class Cart {
	
	private int id;
	private int qunatity;
	private String skuUnit;
	private Float price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQunatity() {
		return qunatity;
	}
	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}
	public String getSkuUnit() {
		return skuUnit;
	}
	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Cart() {
		 
		 
	}
	public Cart(int id, int qunatity, String skuUnit, Float price) {
		this.id = id;
		this.qunatity = qunatity;
		this.skuUnit = skuUnit;
		this.price = price;
	}
	public Cart(int qunatity, String skuUnit, Float price) {
		this.qunatity = qunatity;
		this.skuUnit = skuUnit;
		this.price = price;
	}
	
	 
	 
	

}
