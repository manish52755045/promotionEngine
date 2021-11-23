package com.app.pengine.dto;

import java.io.Serializable;
import java.util.List;

import com.app.pengine.model.CheckOut;

public class CheckOutResponseDto implements Serializable {
	
	private List<CheckOut> checkOut;
	private int FinalPrice;
	public List<CheckOut> getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(List<CheckOut> checkOut) {
		this.checkOut = checkOut;
	}
	public int getFinalPrice() {
		return FinalPrice;
	} 
	public void setFinalPrice(int finalPrice) {
		FinalPrice = finalPrice;
	}
	public CheckOutResponseDto(List<CheckOut> checkOut, int finalPrice) {
		this.checkOut = checkOut;
		FinalPrice = finalPrice;
	}
	public CheckOutResponseDto() {
		 
	}
	
	
	

}
