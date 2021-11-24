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
public class PromotionController {

	@Autowired
	CheckOutService checkOutServiceImpl;

	@Autowired
	StockStoreService stockStoreModel;

	@Autowired
	ActivePromotionService activePromotion;

	@PostMapping("checkout")
	public ResponseEntity<Response<CheckOutResponseDto>> checkOutCart(@RequestBody Request<Cart> request) {
		Response<CheckOutResponseDto> returnResponse = new Response<>();
		try {
			returnResponse.setReponseObj(checkOutServiceImpl.proceedCheckOut(request.getValueObjList()));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(returnResponse, HttpStatus.OK);
	}

	@PostMapping("addstock")
	public ResponseEntity<Response<?>> addStock(@RequestBody Request<StockStoreModel> request) {
		Response<StockStoreModel> returnResponse = new Response<>();
		try {
			returnResponse.setReponseObj(stockStoreModel.addStockItem(request.getValueObj()));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(returnResponse, HttpStatus.OK);
	}

	@PostMapping("addactivepromotion")
	public ResponseEntity<Response<?>> addActivePromotion(@RequestBody Request<ActivePromotionModel> request) {
		Response<ActivePromotionModel> returnResponse = new Response<>();
		try {
			returnResponse.setReponseObj(activePromotion.addActivePromotion(request.getValueObj()));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(returnResponse, HttpStatus.OK);
	}

}
