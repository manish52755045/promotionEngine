package com.app.pengine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.StockStoreModel;

@Service
public class PrmotionEngineService {

	@Autowired
	ActivePromotionService activePromotionService;

	@Autowired
	StockStoreService stockStoreImpl;

	public PrmotionEngineService(ActivePromotionService activePromotionService, StockStoreService stockStoreImpl) {
		this.activePromotionService = activePromotionService;
		this.stockStoreImpl = stockStoreImpl;
	}

	 

	 

	public List<CheckOut> executeFixedPricePromotion(List<Cart> cartList,
			Map<String, ActivePromotionModel> promotionMap, List<StockStoreModel> stockList) {

		List<CheckOut> returnList = new ArrayList<>();
		for (Cart cart : cartList) {
			CheckOut checkOut = null;
			ActivePromotionModel prmos = promotionMap.get(cart.getSkuUnit());
			if (prmos != null && cart.getQunatity() >= prmos.getPromotionQuantity()) {
				checkOut = priceForFixedPromotion(cart.getQunatity(), prmos, stockList);

			} else {
				checkOut = new CheckOut(false, cart.getPrice(), cart.getQunatity(), cart.getSkuUnit());
			}
			returnList.add(checkOut);
		}

		return returnList;
	}

	CheckOut priceForFixedPromotion(int quantity, ActivePromotionModel prmos, List<StockStoreModel> stockList) {

		List<String> itemUnitList = new ArrayList<>();
		CheckOut checkOut = null;
		if (quantity % prmos.getPromotionQuantity() == 0) {

			checkOut = new CheckOut(true, prmos.getPromotionPrice() * (quantity / prmos.getPromotionQuantity()),
					quantity, prmos.getPromotionSKU());

		} else {

			itemUnitList.add(String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()));
			int tempQuantity = quantity;
			while (prmos.getPromotionQuantity() < tempQuantity) {
				tempQuantity = tempQuantity - prmos.getPromotionQuantity();

				if (prmos.getPromotionQuantity() < tempQuantity) {
					itemUnitList.add(
							String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()));
				} else {

					itemUnitList.add(String.valueOf(tempQuantity).concat("-").concat(prmos.getPromotionSKU()));
				}
			}

			checkOut = new CheckOut(true, getFixedPrmotionPrice(itemUnitList, stockList).floatValue(), quantity,
					prmos.getPromotionSKU());

		}

		return checkOut;
	}

	public Integer getFixedPrmotionPrice(List<String> list, List<StockStoreModel> stockList) {

		Map<String, Float> originialPrice = new HashMap<String, Float>();
		List<Float> priceList = new ArrayList<>();

		Map<String, Float> promotionMap = new HashMap<String, Float>();
		for (ActivePromotionModel prmos : getAllActivePromotions()) {
			promotionMap.put(String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()),
					prmos.getPromotionPrice());
		}

		for (StockStoreModel prmos : stockList) {
			originialPrice.put(prmos.getSkuName(), prmos.getSkuPrice());
		}

		if (originialPrice != null && !originialPrice.isEmpty()) {
			for (String str : list) {
				Float temp_str = promotionMap.get(str);

				if (temp_str != null) {
					priceList.add(temp_str);
				} else {
					priceList.add(originialPrice.get(str.split("-")[1]) * Integer.parseInt(str.split("-")[0]));
				}

			}
		}

		return priceList.stream().mapToInt(e -> e.intValue()).sum();

	}

	public List<CheckOut> executeCombinedRule(List<Cart> cart, Map<String, ActivePromotionModel> promotionMap) {

		List<CheckOut> chekcOutList = new ArrayList<>();

		CheckOut checkOut = null;
		for (Entry<String, ActivePromotionModel> mapObj : promotionMap.entrySet()) {
		 

			Optional<List<Cart>> chek = getCombineRule(cart).entrySet().stream()
					.filter(e -> e.getKey().equalsIgnoreCase(String.valueOf(mapObj.getKey()))).map(Map.Entry::getValue)
					.findFirst();

			if (chek.isPresent()) {
				if (chek.get().get(0).getQunatity() == chek.get().get(1).getQunatity()) {

					checkOut = new CheckOut(true,
							mapObj.getValue().getPromotionPrice() * chek.get().get(1).getQunatity(),
							mapObj.getValue().getPromotionQuantity(), mapObj.getValue().getPromotionSKU());
				} else {
					Map<String, Integer> item1Map = getMapForCombinedRules(mapObj, chek.get().get(0).getQunatity());
					Map<String, Integer> item2Map = getMapForCombinedRules(mapObj, chek.get().get(1).getQunatity());

					if (item1Map.size() > item2Map.size()) {

						checkOut = new CheckOut(true,
								getCombinedPrice(item1Map, item2Map, mapObj, chek.get().get(0)).floatValue(),
								chek.get().get(0).getQunatity() + chek.get().get(1).getQunatity(),
								mapObj.getValue().getPromotionSKU());
					} else {

						checkOut = new CheckOut(true,
								getCombinedPrice(item2Map, item1Map, mapObj, chek.get().get(1)).floatValue(),
								chek.get().get(0).getQunatity() + chek.get().get(1).getQunatity(),
								mapObj.getValue().getPromotionSKU());
					}

				}
				chekcOutList.add(checkOut);
			}

		}
		return chekcOutList;
	}

	Map<String, Integer> getMapForCombinedRules(Entry<String, ActivePromotionModel> mapObj, int quantity) {

		List<Integer> itemUnitList = new ArrayList<>();
		int tempQuantity = quantity;
		itemUnitList.add(mapObj.getValue().getPromotionQuantity());
		while (mapObj.getValue().getPromotionQuantity() < tempQuantity) {
			tempQuantity = tempQuantity - mapObj.getValue().getPromotionQuantity();
			if (mapObj.getValue().getPromotionQuantity() <= tempQuantity) {
				itemUnitList.add(mapObj.getValue().getPromotionQuantity());

			}
		}
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		int flag = 1;
		for (Integer i : itemUnitList) {
			returnMap.put(flag + "#" + i, i);
			flag++;
		}
		return returnMap;

	}

	public Map<String, List<Cart>> getCombineRule(List<Cart> cart) {

		Map<String, List<Cart>> combileRuleCheck = new HashMap<String, List<Cart>>();
		for (int i = 0; i <= cart.size() - 1; i++) {

			List<Cart> innerList = null;
			String mapKey = null;
			for (int j = 0; j <= cart.size() - 1; j++) {
				innerList = new ArrayList<>();
				innerList.add(cart.get(i));
				innerList.add(cart.get(j));
				mapKey = cart.get(i).getSkuUnit().concat("&").concat(cart.get(j).getSkuUnit());
			}
			combileRuleCheck.put(mapKey, innerList);

		}
		return combileRuleCheck;
	}

	public Integer getCombinedPrice(Map<String, Integer> item1Map, Map<String, Integer> item2Map,
			Entry<String, ActivePromotionModel> promotionMap, Cart cart) {
		List<Float> priceList = new ArrayList<>();
		for (Entry<String, Integer> mapObjs : item1Map.entrySet()) {

			Integer x = item2Map.get(mapObjs.getKey());

			if (x != null) {

				priceList.add(x * promotionMap.getValue().getPromotionPrice());
			} else {

				priceList.add((float) (mapObjs.getValue().intValue() * cart.getPrice()));
			}
		}

		return priceList.stream().mapToInt(e -> e.intValue()).sum();
	}

	public Map<String, ActivePromotionModel> getAllActivePromotionsMap() {
		Map<String, ActivePromotionModel> promotionMap = new HashMap<String, ActivePromotionModel>();
		for (ActivePromotionModel prmos : activePromotionService.getAllActivePromotions()) {
			promotionMap.put(prmos.getPromotionSKU(), prmos);
		}
		return promotionMap;
	}

	public List<ActivePromotionModel> getAllActivePromotions() {
		return activePromotionService.getAllActivePromotions();
	}

	public List<StockStoreModel> getAllStockItems() {
		return stockStoreImpl.getAllStockItems();
	}

}
