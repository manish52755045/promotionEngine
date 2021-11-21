package com.app.pengine.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.repository.ActivePromotionRepository;

@Service
public class ActivePromotionImpl {
	
	@Autowired
	ActivePromotionRepository activePromotionRepository;
	
	public List<ActivePromotionModel> getAllActivePromotions(){
		return activePromotionRepository.findAll();
	}
	
	public ActivePromotionModel addActivePromotion(ActivePromotionModel activePromotions) {
		return activePromotionRepository.saveAndFlush(activePromotions);
	}

}
