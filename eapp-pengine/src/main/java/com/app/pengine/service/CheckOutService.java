 package com.app.pengine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pengine.controller.ExceptionResponseHandler;
import com.app.pengine.dto.CheckOutResponseDto;
import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.StockStoreModel;
 
@Service
 public class CheckOutService {
 	
 	@Autowired 
	ActivePromotionService activePromotionImpl;
 	
 	@Autowired
 	PromotionEngineFiexedRuleService prmotionEngineService;

	 
 	@Autowired
	ActivePromotionService activePromotionService;
 	
 	
 	@Autowired
	StockStoreService stockStoreImpl;
 	
 	
	public   CheckOutResponseDto proceedCheckOut(List<Cart> cart) throws Exception {
		 return  executeMergeCheckOut(prmotionEngineService.executeFixedPricePromotion(cart, getAllActivePromotionsMap(), getAllStockItems()), prmotionEngineService.executeCombinedRule(cart, getAllActivePromotionsMap()));
	} 
	
	private CheckOutResponseDto executeMergeCheckOut(List<CheckOut> checkOutFixedRule, List<CheckOut> executeCombinedRule) {

		List<CheckOut> returnList = new ArrayList<CheckOut>(checkOutFixedRule);
		if (executeCombinedRule != null && executeCombinedRule.size() > 0) {

			for (CheckOut chObject : checkOutFixedRule) {

				Optional<CheckOut> Object = executeCombinedRule.stream()
						.filter(e -> e.getSKUunit().contains(chObject.getSKUunit())).findFirst();

				if (Object.isPresent()) {
					returnList.remove(chObject);
				}

			}
			returnList.addAll(executeCombinedRule);
		}
		return new CheckOutResponseDto(checkOutFixedRule,returnList.stream().mapToInt(e->e.getPrice().intValue()).sum());
	}
	
	
	public Map<String, ActivePromotionModel> getAllActivePromotionsMap() throws Exception {
		Map<String, ActivePromotionModel> promotionMap = new HashMap<String, ActivePromotionModel>();
		for (ActivePromotionModel prmos : activePromotionService.getAllActivePromotions()) {
			promotionMap.put(prmos.getPromotionSKU(), prmos);
		}
		return promotionMap;
	}
	
	
	public List<ActivePromotionModel> getAllActivePromotions() throws Exception{
		return activePromotionService.getAllActivePromotions();
	}

	public List<StockStoreModel> getAllStockItems() throws Exception {
		return stockStoreImpl.getAllStockItems();
	}

	
	 
}
