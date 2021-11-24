package com.app.pengine.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.StockStoreModel;
import com.app.pengine.repository.ActivePromotionRepository;
import com.app.pengine.repository.StockStoreRepository;
import com.app.pengine.service.ActivePromotionService;
import com.app.pengine.service.PrmotionEngineService;
import com.app.pengine.service.StockStoreService;

@RunWith(MockitoJUnitRunner.class)
public class PromotionEngineServiceTest {
	
 
	private PrmotionEngineService promotionEngineService;
	
	
	@InjectMocks
	ActivePromotionService activePromotionImpl;
	
	@Mock
	ActivePromotionRepository activePromotionRepository;
	
	@InjectMocks
	StockStoreService stockStoreService;
	
	@Mock
	private StockStoreRepository stockStoreRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
		 promotionEngineService=new PrmotionEngineService(activePromotionImpl,stockStoreService);
		 activePromotionImpl=new ActivePromotionService(activePromotionRepository);
		 stockStoreService= new StockStoreService(stockStoreRepository);
		
	}
	
	
	
	
	@Test
	public void  promotion_engine_single_item_rule() {
		
		List<StockStoreModel>  stockStoremodel= new ArrayList<StockStoreModel>();
		 stockStoremodel.add(new StockStoreModel(1,"A",50f));
		 stockStoremodel.add(new StockStoreModel(2,"B",30f));
		 stockStoremodel.add(new StockStoreModel(3,"C",20f));
		 stockStoremodel.add(new StockStoreModel(4,"C",15f));
		
		List<Cart> cartList=new ArrayList<Cart>();
		
		cartList.add(new Cart(5,"A",50f));
		cartList.add(new Cart(5,"B",30f));
		cartList.add(new Cart(1,"C ",20f));
		
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList.add(new CheckOut(true,250f, 1, "A"));
		checkOutList.add(new CheckOut(true,150f, 1, "B"));
		checkOutList.add(new CheckOut(true,20f, 1, "C"));
		
		List<ActivePromotionModel> activePromotionList = new ArrayList<ActivePromotionModel>();
		activePromotionList.add(new ActivePromotionModel(1, "A", 3, 130f, "3 of A for 130"));
		activePromotionList.add(new ActivePromotionModel(2, "B", 2, 45f, "2 of B for 45"));
		activePromotionList.add(new ActivePromotionModel(3, "C&D", 1, 30f, "C &  D for 30"));
		
		
		Map<String, ActivePromotionModel> promotionMap = new HashMap<String, ActivePromotionModel>();
		for (ActivePromotionModel prmos : activePromotionList) {
			promotionMap.put(prmos.getPromotionSKU(), prmos);
		}
		
		List<CheckOut> checkList=  promotionEngineService.executeFixedPricePromotion(cartList,promotionMap,stockStoremodel);
		 
	 
		// act and assert
	     
	     given(promotionEngineService.executeFixedPricePromotion(cartList,promotionMap,stockStoremodel)).willReturn(checkOutList);
	     
	     assertThat(checkOutList.stream().mapToInt(e ->e.getPrice().intValue()).sum()).isEqualTo(checkList.stream().mapToInt(e ->e.getPrice().intValue()).sum());
	      
		
	}

}
