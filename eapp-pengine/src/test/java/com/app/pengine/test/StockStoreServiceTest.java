package com.app.pengine.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.pengine.model.StockStoreModel;
import com.app.pengine.repository.StockStoreRepository;
import com.app.pengine.service.StockStoreService;




@RunWith(MockitoJUnitRunner.class)
 
public class StockStoreServiceTest {
	
	
	private StockStoreService stockStoreService;

	 
	@Mock
	private StockStoreRepository stockStoreRepository;
	 
	 @Mock
	 StockStoreModel stockStoreModel;
	       
	   

	@BeforeEach
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
		stockStoreService = new StockStoreService(stockStoreRepository);
	}
	
	
	   @Test
	    public void getStocktItem_should_return_all_Stock_detils() throws Exception {

		 
		 
		 List<StockStoreModel>  stockStoremodel= new ArrayList<StockStoreModel>();
		 stockStoremodel.add(new StockStoreModel(1,"A",50f));
		 stockStoremodel.add(new StockStoreModel(2,"B",30f));
		 stockStoremodel.add(new StockStoreModel(3,"C",20f));
		 stockStoremodel.add(new StockStoreModel(4,"C",15f));
		 
	      // arrange
	      given(stockStoreRepository.findAll()).willReturn(stockStoremodel);

	     
	      // act and assert
	      List<StockStoreModel> allitems = stockStoreService.getAllStockItems();
	       
         assertThat(allitems.size()).isEqualTo(4);
	        
	        
	    }
	 
	    @Test
	    public void addStocktItem_should_return_added_stock_item() throws Exception {
	    	
	    	StockStoreModel model= new StockStoreModel(5,"E",150f);
	    	// arrange
		     given(stockStoreRepository.save(model)).willReturn(new StockStoreModel(5,"E",150f));
		     
		  // act and assert
		     StockStoreModel modelObject=  stockStoreService.addStockItem(model);
		     
		     assertThat(modelObject.getSkuId()).isEqualTo(model.getSkuId());
		     assertThat(modelObject.getSkuName()).isEqualTo(model.getSkuName());
		     assertThat(modelObject.getSkuPrice()).isEqualTo(model.getSkuPrice());
	 }
	  
}
