package com.app.pengine.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pengine.model.StockStoreModel;
import com.app.pengine.repository.StockStoreRepository;

@Service
public class StockStoreImpl {
	
	@Autowired
	StockStoreRepository stockStoreRepository;
	
	
	public List<StockStoreModel> getAllStockItems(){
		return stockStoreRepository.findAll();
	}
	
	public StockStoreModel addStock(StockStoreModel stockStoreModel) {
		return stockStoreRepository.save(stockStoreModel);
	}
}
