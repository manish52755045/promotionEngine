package com.app.pengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

 
@Entity
@Table(name = "Active_Promotions")
public class ActivePromotionModel {
	 

	@Id
	 
	@Column(name = "promotion_id")
	private Integer promotionId;
	
	@Column(name = "promotion_sku")
	private String promotionSKU;
	
	
	@Column(name = "promotion_quantity")
	private int promotionQuantity;
	
	@Column(name = "promotion_price")
	private Float promotionPrice;
	
	@Column(name = "description")
	private String Description;

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionSKU() {
		return promotionSKU;
	}

	public void setPromotionSKU(String promotionSKU) {
		this.promotionSKU = promotionSKU;
	}

	public int getPromotionQuantity() {
		return promotionQuantity;
	}

	public void setPromotionQuantity(int promotionQuantity) {
		this.promotionQuantity = promotionQuantity;
	}

	public Float getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Float promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
 
	public ActivePromotionModel(Integer promotionId, String promotionSKU, int promotionQuantity, Float promotionPrice,
			String description) {
		 
		this.promotionId = promotionId;
		this.promotionSKU = promotionSKU;
		this.promotionQuantity = promotionQuantity;
		this.promotionPrice = promotionPrice;
		Description = description;
	}

	public ActivePromotionModel() {
	 
	}

	 
	 

}
