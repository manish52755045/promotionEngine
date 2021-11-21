package com.app.pengine.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.pengine.common.CommonUtil;
import com.app.pengine.dto.CheckOutResponseDto;
import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.StockStoreModel;

@Service
public class CheckOutServiceImpl {

	@Autowired
	CommonUtil commUtil;

	public  CheckOutResponseDto proceedCheckOut(List<Cart> cart) {

		List<CheckOut> checkOutFixedRule = executeFixedPromotion(cart, commUtil.getAllActivePromotionsMap());
		 return  executeMergeCheckOut(checkOutFixedRule,
				executeCombinedRule(checkOutFixedRule, commUtil.getAllActivePromotionsMap()));
		
		 
	}

	private CheckOutResponseDto executeMergeCheckOut(List<CheckOut> checkOutFixedRule, List<CheckOut> executeCombinedRule) {

		List<CheckOut> returnList = new ArrayList<>(checkOutFixedRule);
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

	private List<CheckOut> executeFixedPromotion(List<Cart> cartList, Map<String, ActivePromotionModel> promotionMap) {

		List<CheckOut> returnList = new ArrayList<>();
		for (Cart cart : cartList) {
			CheckOut checkOut = null;
			ActivePromotionModel prmos = promotionMap.get(cart.getSkuUnit());
			if (prmos != null && cart.getQunatity() >= prmos.getPromotionQuantity()) {
				checkOut = priceForFixedPromotion(cart.getQunatity(), prmos);

			} else {
				checkOut = new CheckOut(false, cart.getPrice(), cart.getQunatity(), cart.getSkuUnit());
			}
			returnList.add(checkOut);
		}

		return returnList;
	}

	private CheckOut priceForFixedPromotion(int quantity, ActivePromotionModel prmos) {

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

			checkOut = new CheckOut(true, getPromotionWiseMap(itemUnitList).floatValue(), quantity, prmos.getPromotionSKU());

		}

		return checkOut;
	}

	public Integer getPromotionWiseMap(List<String> list) {

		Map<String, Float> originialPrice = new HashMap<String, Float>();
		List<Float> priceList = new ArrayList<>();

		Map<String, Float> promotionMap = new HashMap<String, Float>();
		for (ActivePromotionModel prmos : commUtil.getAllActivePromotions()) {
			promotionMap.put(String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()),
					prmos.getPromotionPrice());
		}

		for (StockStoreModel prmos : commUtil.getAllStockItems()) {
			originialPrice.put(prmos.getSkuName(), prmos.getSkuPrice());
		}

		for (String str : list) {
			Float temp_str = promotionMap.get(str);

			if (temp_str != null) {
				priceList.add(temp_str);
			} else {
				priceList.add(originialPrice.get(str.split("-")[1]) * Integer.parseInt(str.split("-")[0]));
			}

		}
		return priceList.stream().mapToInt(e -> e.intValue()).sum();

	}

	private static List<CheckOut> executeCombinedRule(List<CheckOut> cart, Map<String, ActivePromotionModel> promotionMap) {

		List<CheckOut> chekcOutList = new ArrayList<>();
		CheckOut checkOut = null;
		for (Entry<String, ActivePromotionModel> mapObj : promotionMap.entrySet()) {

			Optional<List<CheckOut>> chek = getCombineRule(cart).entrySet().stream()
					.filter(e -> e.getKey().equalsIgnoreCase(String.valueOf(mapObj.getKey()))).map(Map.Entry::getValue)
					.findFirst();

			if (chek.isPresent()) {
				if (chek.get().get(0).getTotalQuantity() == chek.get().get(1).getTotalQuantity()) {

					checkOut = new CheckOut(true,
							mapObj.getValue().getPromotionPrice() * chek.get().get(1).getTotalQuantity(),
							mapObj.getValue().getPromotionQuantity(), mapObj.getValue().getPromotionSKU());
				} else {
					Map<String, Integer> item1Map = getMapForCombinedRules(mapObj,
							chek.get().get(0).getTotalQuantity());
					Map<String, Integer> item2Map = getMapForCombinedRules(mapObj,
							chek.get().get(1).getTotalQuantity());

					if (item1Map.size() > item2Map.size()) {

						checkOut = new CheckOut(true, getCombinedPrice(item1Map, item2Map, mapObj, chek.get().get(0)).floatValue(),
								chek.get().get(0).getTotalQuantity() + chek.get().get(1).getTotalQuantity(),
								mapObj.getValue().getPromotionSKU());
					} else {

						checkOut = new CheckOut(true, getCombinedPrice(item2Map, item1Map, mapObj, chek.get().get(1)).floatValue(),
								chek.get().get(0).getTotalQuantity() + chek.get().get(1).getTotalQuantity(),
								mapObj.getValue().getPromotionSKU());
					}

				}
				chekcOutList.add(checkOut);
			}

		}
		return chekcOutList;
	}

	public static Map<String, List<CheckOut>> getCombineRule(List<CheckOut> cart) {

		Map<String, List<CheckOut>> combileRuleCheck = new HashMap<String, List<CheckOut>>();
		for (int i = 0; i <= cart.size() - 1; i++) {

			List<CheckOut> innerList = null;
			String mapKey = null;
			for (int j = 0; j <= cart.size() - 1; j++) {
				innerList = new ArrayList<>();
				innerList.add(cart.get(i));
				innerList.add(cart.get(j));
				mapKey = cart.get(i).getSKUunit().concat("&").concat(cart.get(j).getSKUunit());
			}
			combileRuleCheck.put(mapKey, innerList);

		}
		return combileRuleCheck;
	}

	private static Map<String, Integer> getMapForCombinedRules(Entry<String, ActivePromotionModel> mapObj, int quantity) {

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

	public static Integer getCombinedPrice(Map<String, Integer> item1Map, Map<String, Integer> item2Map,
			Entry<String, ActivePromotionModel> promotionMap, CheckOut cart) {
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
}
