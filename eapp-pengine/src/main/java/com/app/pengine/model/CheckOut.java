package com.app.pengine.model;

public class CheckOut {
	
	
	private boolean isPapplicable;
	private Float price;
	private int totalQuantity;
	private String SKUunit;
	public boolean isPapplicable() {
		return isPapplicable;
	}
	public void setPapplicable(boolean isPapplicable) {
		this.isPapplicable = isPapplicable;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getSKUunit() {
		return SKUunit;
	}
	public void setSKUunit(String sKUunit) {
		SKUunit = sKUunit;
	}
	public CheckOut(boolean isPapplicable, Float price, int totalQuantity, String sKUunit) {
		 
		this.isPapplicable = isPapplicable;
		this.price = price;
		this.totalQuantity = totalQuantity;
		SKUunit = sKUunit;
	}
	public CheckOut() {
		 
	}
	
	 
	
	
	

}
