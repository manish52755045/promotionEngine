

<h1> Promotion Engine</h1>
We need you to implement a simple promotion engine for a checkout process. Our Cart contains a list of single character SKU ids (A, B, C....) over which the promotion engine will need to run.

The promotion engine will need to calculate the total order value after applying the 2 promotion types
	<li>buy 'n' items of a SKU for a fixed price (3 A's for 130)</li>
	<li>buy SKU 1 & SKU 2 for a fixed price ( C + D = 30 )</li>
	
	
The promotion engine should be modular to allow for more promotion types to be added at a later date (e.g. a future promotion could be x% of a SKU unit price). For this coding exercise you can assume that the promotions will be mutually exclusive; in other words if one is applied the other promotions will not apply</p>
<h4>Test Setup</h4>
Unit price for SKU IDs
<li>A      50</li>
<li>B      30</li>
<li>C      20</li>
<li>D      15</li>

<h4>Active Promotions</h4>
<li>3 of A's for 130</li>
<li>2 of B's for 45</li>
<li>C & D for 30</li>

<h4>Scenario A</h4>
<li>1 * A     50</li>
<li>1 * B     30</li>
<li>1 * C     20</li>
 ======</br>
Total     100

<h4>Scenario B</h4>
<li>5 * A     130 + 2*50</li>
<li>5 * B     45 + 45 + 30</li>
<li>1 * C     20</li>
======</br>
Total     370

<h4>Scenario C</h4>
<li>3 * A     130</li>
<li>5 * B     45 + 45 + 1 * 30</li>
<li>1 * C     -</li>
<li>1 * D     30</li>
======</br>
Total     280


-------------------------------------

<h4>code is designed to full fill the user case mention above.By using the Spring Boot API</h4>

	# Technical Specification
 	  SpringBoot 
 	  H2 Database
	  Mockito 
	  Junit
# Application Details

  Aplication Consist of 2 H2 DB table 
  
  <li>ACTIVE_PROMOTIONS </li>
  <li>STOCK_STORE </li>
  
  # Buisness Logic Class
  
   <li>Cart</li>
   <li>CheckOut</li>
   <li>ActivePromotion</li>
   <li>PromotionEngineController</li>
   <li>PromotionEngineFixedRuleService</li>
   <li>PromotionEngineCombinedRule</li>
  
  
  <h4>project is using the Maven build tool. jar as the target file, run the file as spring boot application, execute the api by using the postman,
   passing the request parameter </h4>
  
 <h4> run Application by using below url and parameter</h4>
  <p>http://127.0.0.1:8080/pengine/checkout</p><br/>
  -------------------------------------------------------
 <h4> Request parameter</h4>
 <h3> Scenario A</h3>
	  {
    
		    "valueObjList":[

		    {
			"qunatity": 1,
			"skuUnit" :"A",
			"price" :50
		    },
		     {
			"qunatity": 1,
			"skuUnit" :"B",
			"price" :30
		    },
		     {
			"qunatity": 1,
			"skuUnit" :"C",
			"price" :20
		    }
		]
	 }
-----------------------------------------------------------------------
 <h3> Scenario B</h3>
            {
		
		"valueObjList":[

				{
					"qunatity": 5,
					"skuUnit" :"A",
					"price" :50
				},
				 {
					"qunatity": 5,
					"skuUnit" :"B",
					"price" :30
				},
				 {
					"qunatity": 1,
					"skuUnit" :"C",
					"price" :20
				}
			 ]
		}
---------------------------------------------------------------
 <h3> Scenario c</h3>
	{

	    "valueObjList":[

	    {
		"qunatity": 3,
		"skuUnit" :"A",
		"price" :50
	    },
	     {
		"qunatity": 5,
		"skuUnit" :"B",
		"price" :30
	    },
	     {
		"qunatity": 1,
		"skuUnit" :"C",
		"price" :20
	    },
	    {
		"qunatity": 1,
		"skuUnit" :"D",
		"price" : 15
	    }
			]
		}
------------------------------------------------------------ 

http://127.0.0.1:8080/pengine/addstock

	    {
	   "valueObj":{
	    "skuId":"4",
	     "skuName" :"A",
	     "skuPrice": 10
	     }
	   }
 -----------------------------------------------------------
 http://127.0.0.1:8080/pengine/addactivepromotion
	    {

	   "valueObj":{
	    "promotionId":"5",
	     "promotionSKU" :"F",
	     "promotionQuantity": 2,
	     "promotionPrice": 23,
	     "description" : "2F's 30"
	     }

	  }
