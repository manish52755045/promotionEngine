package com.app.pengine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.repository.ActivePromotionRepository;
import com.app.pengine.repository.StockStoreRepository;

@Service
public class ActivePromotionService {
	
	 
	ActivePromotionRepository activePromotionRepository;
	@Autowired
	public ActivePromotionService(ActivePromotionRepository activePromotionRepository) throws Exception {
		this.activePromotionRepository=activePromotionRepository;
	}

	public List<ActivePromotionModel> getAllActivePromotions() throws Exception{
		return activePromotionRepository.findAll();
	}
	
	public ActivePromotionModel addActivePromotion(ActivePromotionModel activePromotions) throws Exception {
		return activePromotionRepository.save(activePromotions);
	}

}
 