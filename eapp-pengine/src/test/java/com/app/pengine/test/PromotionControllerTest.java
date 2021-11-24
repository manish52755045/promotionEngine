package com.app.pengine.test;

 
 
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.app.pengine.controller.PromotionEngineController;
import com.app.pengine.dto.CheckOutResponseDto;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.Request;
import com.app.pengine.service.ActivePromotionService;
import com.app.pengine.service.CheckOutService;
import com.app.pengine.service.StockStoreService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(PromotionEngineController.class)
public class PromotionControllerTest {
	
	
	 
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private CheckOutService checkOutService;
	
	@MockBean
	StockStoreService stockStoreModel;
	
	@MockBean
	ActivePromotionService activePromotion;
	
	@Test
	public void fixed_price_promotion_scenarioA() throws Exception {
		
		List<Cart> cartList=new ArrayList<Cart>();
		
		cartList.add(new Cart(1,"A",50f));
		cartList.add(new Cart(1,"B",30f));
		cartList.add(new Cart(1,"c",20f));
		
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList.add(new CheckOut(true,50f, 1, "A"));
		checkOutList.add(new CheckOut(true,30f, 1, "B"));
		checkOutList.add(new CheckOut(true,20f, 1, "C"));
		
		 
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto(checkOutList,100));
		
		Request<Cart> reqObj=new Request<Cart>();
		reqObj.setValueObjList(cartList);
		
		// act & assert
		 mockMvc.perform(post("/pengine/checkout")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(asJsonString(reqObj))
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
		 
	}
	
	@Test
	public void fixed_price_promotion_scenarioB() throws Exception {
		
		List<Cart> cartList=new ArrayList<Cart>();
		
		cartList.add(new Cart(5,"A",50f));
		cartList.add(new Cart(5,"B",30f));
		cartList.add(new Cart(1,"C",20f));
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList.add(new CheckOut(true,50f, 5, "A"));
		checkOutList.add(new CheckOut(true,30f, 5, "B"));
		checkOutList.add(new CheckOut(true,20f, 1, "C"));
		
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto(checkOutList,370));
		
		Request<Cart> reqObj=new Request<Cart>();
		reqObj.setValueObjList(cartList);
		
		// act & assert
		 mockMvc.perform(post("/pengine/checkout")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(asJsonString(reqObj))
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
		 
	}
	
	@Test
	public void fixed_price_promotion_scenarioC() throws Exception {
		
		List<Cart> cartList=new ArrayList<Cart>();
		
		cartList.add(new Cart(3,"A",50f));
		cartList.add(new Cart(5,"B",30f));
		cartList.add(new Cart(1,"C",20f));
		cartList.add(new Cart(1,"D",20f));
		
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList.add(new CheckOut(true,50f, 3, "A"));
		checkOutList.add(new CheckOut(true,30f, 5, "B"));
		checkOutList.add(new CheckOut(true,20f, 1, "C"));
		checkOutList.add(new CheckOut(true,15f, 1, "D"));
		
		 
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto(checkOutList,280));
		
		Request<Cart> reqObj=new Request<Cart>();
		reqObj.setValueObjList(cartList);
		
		// act & assert
		 mockMvc.perform(post("/pengine/checkout")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(asJsonString(reqObj))
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
		 
	}
	
  
	
	public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
