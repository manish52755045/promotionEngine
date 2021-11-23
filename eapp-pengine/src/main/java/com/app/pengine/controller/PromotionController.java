package com.app.pengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pengine.dto.CheckOutResponseDto;
import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.Request;
import com.app.pengine.model.Response;
import com.app.pengine.model.StockStoreModel;
import com.app.pengine.service.ActivePromotionService;
import com.app.pengine.service.CheckOutService;
import com.app.pengine.service.StockStoreService;

  
@RestController
@RequestMapping(path = "pengine/")
public class PromotionController {
	
	@Autowired
	CheckOutService checkOutServiceImpl;
	
	
	@Autowired
	StockStoreService stockStoreModel;
	
	
	
	@Autowired
	ActivePromotionService activePromotion;
	
	@PostMapping("checkout")
	public Response<?> checkOutCart(@RequestBody Request<Cart> request) {
		Response<CheckOutResponseDto> returnResponse = new Response<>();
		returnResponse.setReponseObj(checkOutServiceImpl.proceedCheckOut(request.getValueObjList()));
		return returnResponse;
	}
	 
	
	@PostMapping("addstock")
	public Response<?> addStock(@RequestBody Request<StockStoreModel> request) {
		Response<StockStoreModel> returnResponse = new Response<>();
		returnResponse.setReponseObj(stockStoreModel.addStockItem(request.getValueObj()));
		return returnResponse;
	}
	
	@PostMapping("addactivepromotion")
	public Response<?> addActivePromotion(@RequestBody Request<ActivePromotionModel> request) {
		Response<ActivePromotionModel> returnResponse = new Response<>();
		returnResponse.setReponseObj(activePromotion.addActivePromotion(request.getValueObj()));
		return returnResponse;
	}

}
