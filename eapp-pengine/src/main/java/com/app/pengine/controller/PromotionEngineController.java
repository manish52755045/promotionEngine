package com.app.pengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PromotionEngineController {

	@Autowired
	CheckOutService checkOutServiceImpl;

	@Autowired
	StockStoreService stockStoreService;

	@Autowired
	ActivePromotionService activePromotion;

	@PostMapping("checkout")
	public  Response<CheckOutResponseDto>  checkOutCart(@RequestBody Request<Cart> request) {
		Response<CheckOutResponseDto> returnResponse = new Response<>();
		returnResponse.setReponseObj(checkOutServiceImpl.proceedCheckOut(request.getValueObjList()));
		return returnResponse;
		  
	}

	@PostMapping("addstock")
	public  Response<StockStoreModel> addStock(@RequestBody Request<StockStoreModel> request) {
		Response<StockStoreModel> returnResponse = new Response<>();
			returnResponse.setReponseObj(stockStoreService.addStockItem(request.getValueObj()));
			return returnResponse;
		 
			 
	}

	@PostMapping("addactivepromotion")
	public  Response<?> addActivePromotion(@RequestBody Request<ActivePromotionModel> request) {
		Response<ActivePromotionModel> returnResponse = new Response<>();
		 
			returnResponse.setReponseObj(activePromotion.addActivePromotion(request.getValueObj()));
			return returnResponse;
		 
	}

}
