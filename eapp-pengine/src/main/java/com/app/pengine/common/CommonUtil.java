package com.app.pengine.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.StockStoreModel;
import com.app.pengine.serviceImpl.ActivePromotionImpl;
import com.app.pengine.serviceImpl.StockStoreImpl;

@Component
public class CommonUtil {
	
	@Autowired
	ActivePromotionImpl activePromotionImpl;
	
	@Autowired
	StockStoreImpl stockStoreImpl;
	
	public  Map<String, ActivePromotionModel> getAllActivePromotionsMap(){
		Map<String, ActivePromotionModel>promotionMap = new HashMap<String, ActivePromotionModel>();
			for (ActivePromotionModel prmos : activePromotionImpl.getAllActivePromotions()) {
				promotionMap.put(prmos.getPromotionSKU(), prmos);
			}
		return promotionMap;
	}
	
	
	public List<ActivePromotionModel> getAllActivePromotions(){
		return activePromotionImpl.getAllActivePromotions();
	}
	 
	
	
	public List<StockStoreModel> getAllStockItems(){
		return stockStoreImpl.getAllStockItems();
	}

}