package com.app.pengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Stock_Store")
public class StockStoreModel {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sku_id")
	private Integer skuId;
	
	@Column(name = "sku_name")
	private String skuName;
	 
	@Column(name = "sku_price")
	private float skuPrice; 

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public float getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(float skuPrice) {
		this.skuPrice = skuPrice;
	}

	public StockStoreModel(Integer skuId, String skuName, float skuPrice) {
		 
		this.skuId = skuId;
		this.skuName = skuName;
		this.skuPrice = skuPrice;
	}
	 
	public StockStoreModel() {
		 
	}

	@Override
	public String toString() {
		return "StockStoreModel [skuId=" + skuId + ", skuName=" + skuName + ", skuPrice=" + skuPrice + "]";
	}
	
	
}
