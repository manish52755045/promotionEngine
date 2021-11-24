package com.app.pengine.test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.app.pengine.common.CommonUtil;
import com.app.pengine.dto.CheckOutResponseDto;
import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.repository.ActivePromotionRepository;
import com.app.pengine.repository.StockStoreRepository;
import com.app.pengine.service.ActivePromotionService;
import com.app.pengine.service.CheckOutService;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {
	
	 
	private CheckOutService checkOutService;
	 
	@MockBean
	private StockStoreRepository stockStoreRepository;
	 
	@InjectMocks
	CommonUtil commonUtil;
	 
	@InjectMocks
	ActivePromotionService activePromotionImpl;
	
	@Mock
	ActivePromotionRepository activePromotionRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
		 checkOutService=new CheckOutService(activePromotionImpl);
		 activePromotionImpl=new ActivePromotionService(activePromotionRepository);
	}
	@Test
	public void  checkOutService_validatePromotion_rules() {
		
		List<Cart> cartList=new ArrayList<Cart>();
		
		cartList.add(new Cart(1,"A",50f));
		cartList.add(new Cart(1,"B",30f));
		cartList.add(new Cart(1,"c ",20f));
		
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList.add(new CheckOut(true,50f, 1, "A"));
		checkOutList.add(new CheckOut(true,30f, 1, "B"));
		checkOutList.add(new CheckOut(true,20f, 1, "C"));
		
		List<ActivePromotionModel> activePromotionList = new ArrayList<ActivePromotionModel>();
		activePromotionList.add(new ActivePromotionModel(1, "A", 3, 130f, "3 of A for 130"));
		activePromotionList.add(new ActivePromotionModel(2, "B", 2, 45f, "2 of B for 45"));
		activePromotionList.add(new ActivePromotionModel(3, "C&D", 1, 30f, "C &  D for 30"));
		
		//when(commonUtil.getAllActivePromotions()).thenReturn(activePromotionList);
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto());
		
		//given(checkOutService.executeFixedPromotion(cartList,checkOutService.getAllActivePromotionsMap())).willReturn(new ArrayList<CheckOut>());
				
		
		 
		
	}
	 

}
