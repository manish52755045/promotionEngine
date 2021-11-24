package com.app.pengine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pengine.exception.ExceptionResponseHandler;
import com.app.pengine.model.StockStoreModel;
import com.app.pengine.repository.StockStoreRepository;

@Service
public class StockStoreService {
	
	 
	StockStoreRepository stockStoreRepository;
	
	@Autowired
	public StockStoreService(StockStoreRepository stockStoreRepository) {
	this.stockStoreRepository=stockStoreRepository;
	}

	public List<StockStoreModel> getAllStockItems(){
		return stockStoreRepository.findAll();
	}
	
	public StockStoreModel addStockItem(StockStoreModel stockStoreModel)  {
		
	Optional<StockStoreModel> obj=	stockStoreRepository.findAll().stream().filter(e->e.getSkuName().equals(stockStoreModel.getSkuName())).findFirst();
	 
	  if(!obj.isPresent()) {
		  return stockStoreRepository.save(stockStoreModel); 
	  } 
	return null;
		
		
		
    }
	
	public  Optional<StockStoreModel>  getStockItemById(Integer id) {
		return stockStoreRepository.findById(id);
	}
}
 