# promotionEngine

Problem Statement 1: Promotion Engine
We need you to implement a simple promotion engine for a checkout process. Our Cart contains a list of single character SKU ids (A, B, C....) over which the promotion engine will need to run.
The promotion engine will need to calculate the total order value after applying the 2 promotion types
	buy 'n' items of a SKU for a fixed price (3 A's for 130)
	buy SKU 1 & SKU 2 for a fixed price ( C + D = 30 )
The promotion engine should be modular to allow for more promotion types to be added at a later date (e.g. a future promotion could be x% of a SKU unit price). For this coding exercise you can assume that the promotions will be mutually exclusive; in other words if one is applied the other promotions will not apply
Test Setup
Unit price for SKU IDs
A      50
B      30
C      20
D      15

Active Promotions
3 of A's for 130
2 of B's for 45
C & D for 30

Scenario A
1 * A     50
1 * B     30
1 * C     20
======
Total     100

Scenario B
5 * A     130 + 2*50
5 * B     45 + 45 + 30
1 * C     20
======
Total     370

Scenario C
3 * A     130
5 * B     45 + 45 + 1 * 30
1 * C     -
1 * D     30
======
Total     280


===================================================================================================================================================

code is designed to full fill the probelm mention above

# Technical Specification
  SpringBoot
  H2 Database 
# Running the application 
  project is using the Maven build tool. jar as the target file, run the file as spring boot application 
  
  run Application by using below url and parameter
  http://127.0.0.1:8080/pengine/checkout
  Request parameter
  =========================================================================================================
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
==================================================================================================
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
================================================================================
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
=====================================================================================================

http://127.0.0.1:8080/pengine/addstock


{

"valueObj":{
    "skuId":"4",
     "skuName" :"d1",
     "skuPrice": 10
}
}
 ================================================================================
 http://127.0.0.1:8080/pengine/addactivepromotion
 {

"valueObj":{
    "promotionId":"5",
     "promotionSKU" :"d1",
     "promotionQuantity": 10,
     "promotionPrice": 23,
     "description" : "demo"
}

}
