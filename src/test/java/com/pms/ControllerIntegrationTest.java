package com.pms;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.pms.model.ProductDTO;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ControllerIntegrationTest {
@Test//executing test cases
@Order(1)
public void test_findAllProducts() throws JSONException
{
String expected="[\r\n" + 
		"    {\r\n" + 
		"        \"productId\": 901,\r\n" + 
		"        \"productName\": \"speaker\",\r\n" + 
		"        \"price\": 700.0,\r\n" + 
		"        \"manufacturer\": \"iBall\"\r\n" + 
		"    },\r\n" + 
		"    {\r\n" + 
		"        \"productId\": 902,\r\n" + 
		"        \"productName\": \"earbud\",\r\n" + 
		"        \"price\": 1700.0,\r\n" + 
		"        \"manufacturer\": \"boat\"\r\n" + 
		"    },\r\n" + 
		"    {\r\n" + 
		"        \"productId\": 903,\r\n" + 
		"        \"productName\": \"earphone\",\r\n" + 
		"        \"price\": 800.0,\r\n" + 
		"        \"manufacturer\": \"boat\"\r\n" + 
		"    }\r\n" + 
		"]";
TestRestTemplate restTemplate=new TestRestTemplate();
ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/PMS/allProducts",String.class);//json data is also String type.
System.out.println(response.getStatusCode());//return status code
System.out.println(response.getBody());//return data
assertEquals(HttpStatus.OK, response.getStatusCode());
//for validating json data
JSONAssert.assertEquals(expected, response.getBody(), false);//strict paramter is use for exact matching ignoring space
}
@Test//executing test cases
@Order(2)
public void test_findProductById() throws JSONException
{
String expected="{\r\n" + 
		"    \"productId\": 901,\r\n" + 
		"    \"productName\": \"speaker\",\r\n" + 
		"    \"price\": 700.0,\r\n" + 
		"    \"manufacturer\": \"iBall\"\r\n" + 
		"}";
TestRestTemplate restTemplate=new TestRestTemplate();
ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/PMS/product/901",String.class);//json data is also String type.
System.out.println(response.getStatusCode());//return status code
System.out.println(response.getBody());//return data
JSONAssert.assertEquals(expected, response.getBody(), false);//strict paramter is use for exact matching ignoring space

}
@Test//executing test cases
@Order(3)
public void test_findProductByManufacturer() throws JSONException
{
String expected="[\r\n" + 
		"    {\r\n" + 
		"        \"productId\": 902,\r\n" + 
		"        \"productName\": \"earbud\",\r\n" + 
		"        \"price\": 1700.0,\r\n" + 
		"        \"manufacturer\": \"boat\"\r\n" + 
		"    },\r\n" + 
		"    {\r\n" + 
		"        \"productId\": 903,\r\n" + 
		"        \"productName\": \"earphone\",\r\n" + 
		"        \"price\": 800.0,\r\n" + 
		"        \"manufacturer\": \"boat\"\r\n" + 
		"    }\r\n" + 
		"]";
TestRestTemplate restTemplate=new TestRestTemplate();
ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/PMS/products/boat",String.class);//json data is also String type.
System.out.println("find by manufacturer");
System.out.println(response.getStatusCode());//return status code
System.out.println(response.getBody());//return data
JSONAssert.assertEquals(expected, response.getBody(), false);//strict paramter is use for exact matching ignoring space

}
@Test//executing test cases
@Order(4)
public void test_addProduct() throws JSONException
{
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(904L);
	pDTO.setProductName("headphone");
	pDTO.setPrice(800D);
	pDTO.setManufacturer("zebronics");
	
/*String expected="{\r\n" + 
		"    \"productId\": 904,\r\n" + 
		"    \"productName\": \"headphone\",\r\n" + 
		"    \"price\": 800.0,\r\n" + 
		"    \"manufacturer\": \"zebronics\"\r\n" + 
		"}";*/
String expectedResponse="product is saved successfully!!";
TestRestTemplate restTemplate=new TestRestTemplate();
//setting content type inside header 
HttpHeaders headers=new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
HttpEntity<ProductDTO> request=new HttpEntity<ProductDTO>(pDTO,headers);
//here we can also use for postForEntity() method
String actualResponse=restTemplate.postForObject("http://localhost:8080/PMS/product/add", request,String.class );
System.out.println(actualResponse);
//System.out.println(response.getStatusCode());//return status code
//System.out.println(response.getBody());//return data
//JSONAssert.assertEquals(expectedResponse, actualResponse, false);//strict paramter is use for exact matching ignoring space
assertEquals(expectedResponse, actualResponse);
}
@Test//executing test cases
@Order(5)
public void test_updateProduct() throws JSONException
{
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(904L);
	pDTO.setProductName("mic");
	pDTO.setPrice(950D);
	pDTO.setManufacturer("zebronics");
	
String expected="{\r\n" + 
		"    \"productId\": 904,\r\n" + 
		"    \"productName\": \"mic\",\r\n" + 
		"    \"price\": 950.0,\r\n" + 
		"    \"manufacturer\": \"zebronics\"\r\n" + 
		"}";

TestRestTemplate restTemplate=new TestRestTemplate();
//setting content type inside header 
HttpHeaders headers=new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
HttpEntity<ProductDTO> request=new HttpEntity<ProductDTO>(pDTO,headers);
//here we can also use for postForEntity() method
//here put return void type i.e validation is not possible.
ResponseEntity<String> response=restTemplate.exchange("http://localhost:8080/PMS/product/update", HttpMethod.PUT, request, String.class);
System.out.println(response.getStatusCode());//by default OK i.e 200
System.out.println(response.getBody());
JSONAssert.assertEquals(expected, response.getBody(), false);
}
@Test//executing test cases
@Order(6)
public void test_deleteProductById() throws JSONException
{
	
String expectedResponse="product is deleted successfully!!";
TestRestTemplate restTemplate=new TestRestTemplate();

ResponseEntity<String> actualResponse=restTemplate.exchange("http://localhost:8080/PMS/product/904", HttpMethod.DELETE, null, String.class);
System.out.println(actualResponse.getBody());
assertEquals(expectedResponse, actualResponse.getBody());
}

}
