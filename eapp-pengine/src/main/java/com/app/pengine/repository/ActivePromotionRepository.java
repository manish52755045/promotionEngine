package com.app.pengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.pengine.model.ActivePromotionModel;

@Repository
public interface ActivePromotionRepository extends JpaRepository <ActivePromotionModel, Integer>{

}
