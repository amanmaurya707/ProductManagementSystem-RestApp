package com.pms;

import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.controller.ProductRestController;
import com.pms.model.ProductDTO;
import com.pms.service.IProductService;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.pms")
@TestMethodOrder(OrderAnnotation.class)//executing test cases order
public class ControllerMockMvcTest {
	@Autowired
	MockMvc mockMvc;//here globally instanciate
	//this time we are not calling controller methods directly .here implicitly called based on endpoints or request types
	@Mock
	IProductService service;//for mocking service layer
	
	@InjectMocks
	ProductRestController productRestController;
	
	@Mock
	ProductDTO pDTO;
	
	List<ProductDTO> productDTOList;
	//Note:before executing each test cases we need to do some setup i.e nothing but standlone setup
	@BeforeEach
	public void setUp()
	{
	mockMvc=MockMvcBuilders.standaloneSetup(productRestController).build();	
		
	}
	
	@Test
	@Order(1)
	public void test_findAllProducts() throws Exception
	{
	//creating mock data
	productDTOList=new ArrayList<ProductDTO>();
	//for pDTO1
	ProductDTO pDTO1=new ProductDTO();
	pDTO1.setProductId(401L);
	pDTO1.setProductName("earphone");
	pDTO1.setPrice(400D);
	pDTO1.setManufacturer("boat");
	productDTOList.add(pDTO1);
	//for pDTO2
	ProductDTO pDTO2=new ProductDTO();
	pDTO2.setProductId(402L);
	pDTO2.setProductName("earbud");
	pDTO2.setPrice(2000D);
	pDTO2.setManufacturer("oppo");
	productDTOList.add(pDTO2);
	//for pDTO3
	ProductDTO pDTO3=new ProductDTO();
	pDTO3.setProductId(403L);
	pDTO3.setProductName("headphone");
	pDTO3.setPrice(1300D);
	pDTO3.setManufacturer("boat");
	productDTOList.add(pDTO3);
	//mocking external dependency and returning mock data
	when(service.searchAllProducts()).thenReturn(productDTOList);	
	//here we are not calling controller methods directly, called based on request types.	
	this.mockMvc.perform(get("/allProducts")) //explicitly not called controller method ,called based on  endpoints by using MockMvc framework without starting spring boot server.
	    .andExpect(status().isOk())//validating response 
	    .andDo(print()) //print the response
	    ;
	
	;
	}
	@Test
	@Order(2)
	public void test_findProductById() throws Exception
	{
		//creating mock data
		ProductDTO pDTO=new ProductDTO();
		pDTO.setProductId(501L);
		pDTO.setProductName("bluetooth");
		pDTO.setPrice(1200D);
		pDTO.setManufacturer("oppo");
		Long id=501L;
		when(service.searchByProductId(id)).thenReturn(pDTO);
		//calling controller method based on MockMvc by calling endpoints ,spring boot automatically callled controller handler or methods based on endpoints
	
	this.mockMvc.perform(get("/product/{id}",id))//here pass path parameter by using parameter 
	    //mock mvc accept request in json format only and also it return json response only
	    .andExpect(MockMvcResultMatchers.jsonPath(".productId").value(501))//expected value
	    .andExpect(MockMvcResultMatchers.jsonPath(".productName").value("bluetooth"))//expected value
	    .andExpect(MockMvcResultMatchers.jsonPath(".price").value(1200D))//expected value
	    .andExpect(MockMvcResultMatchers.jsonPath(".manufacturer").value("oppo"))//expected value
	    .andDo(print())
	
	; //
	}
	
@Test
@Order(3)
public void test_findProductByManufacturer() throws Exception
{
	//creating mock data
	productDTOList=new ArrayList<ProductDTO>();
	//for pDTO1
	ProductDTO pDTO1=new ProductDTO();
	pDTO1.setProductId(401L);
	pDTO1.setProductName("earphone");
	pDTO1.setPrice(400D);
	pDTO1.setManufacturer("boat");
	productDTOList.add(pDTO1);
	//for pDTO2
	ProductDTO pDTO2=new ProductDTO();
	pDTO2.setProductId(402L);
	pDTO2.setProductName("earbud");
	pDTO2.setPrice(2400D);
	pDTO2.setManufacturer("boat");
	productDTOList.add(pDTO2);
	//for pDTO3
	ProductDTO pDTO3=new ProductDTO();
	pDTO3.setProductId(403L);
	pDTO3.setProductName("headphone");
	pDTO3.setPrice(1700D);
	pDTO3.setManufacturer("boat");
	productDTOList.add(pDTO3);
	//mocking service dependency
	String manufacturer="boat";
	when(service.searchByManufacturer(manufacturer)).thenReturn(productDTOList);
	//invoking url/endpoint of controller
	//here we are not called controller methods directly/explicitly ,called based on endpoints by using MockMvc framework
	
	//this.mockMvc.perform(get("/products/manufacturer").param("manufacturer", "boat"))
	//this is for query parameter

	this.mockMvc.perform(get("/products/{manufacturer}",manufacturer))
	   //.andExpect(MockMvcResultMatchers.jsonPath("[*].manufacturer").value(new Object[] {"boat","boat","boat"}))
	    .andExpect(jsonPath("$[0].manufacturer", is(manufacturer)))
	    .andExpect(jsonPath("$[1].manufacturer", is(manufacturer)))
	    .andExpect(jsonPath("$[2].manufacturer", is(manufacturer)));
        //.andExpect(jsonPath("$[2].manufacturer", is("boat")));
}
@Test
@Order(4)
public void test_addProduct() throws Exception
{
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(401L);
	pDTO.setProductName("earphone");
	pDTO.setPrice(400D);
	pDTO.setManufacturer("boat");
	ObjectMapper mapper=new ObjectMapper();
	String jsonBody=mapper.writeValueAsString(pDTO);
	//Mocking external dependency
	//String msg="product is saved successfully!!";
	//when(service.addProduct(pDTO)).thenReturn(msg);
	when(service.addProduct(pDTO)).thenReturn("product is saved successfully!!");
	String expectedMSG="product is saved successfully!!";
	//assertEquals(expectedMSG,productRestController.addProduct(pDTO));

   // MvcResult result =(MvcResult)
   //ResultActions actions= (ResultActions) 
	this.mockMvc.perform(post("/product/add")
			            .content(jsonBody)
			           .contentType(MediaType.APPLICATION_JSON)
			           .accept(MediaType.TEXT_PLAIN_VALUE))
	                    //note:add equals() and hashcode() method in your dto class ohterwise return empty resonpse
	//Or add  @EqualsAndHashcode //add after from project lombook dependency                  
	.andExpect(content().string(expectedMSG))
	                   .andDo(print())
	                   ;
	           			
			            //.andReturn();
    //String actualJson = result.getResponse().getContentAsString();

   // assertEquals("product is saved successfully!!", actualJson);
		
//actions.andExpect(content().string("product is saved successfully!!"));
	 
	
	
}
@Test
@Order(5)
public void test_updateProduct() throws Exception
{
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(401L);
	pDTO.setProductName("earbud");
	pDTO.setPrice(700D);
	pDTO.setManufacturer("REALME");
	//convert into json format
	ObjectMapper mapper=new ObjectMapper();
	String jsonData=mapper.writeValueAsString(pDTO);
	
	//creating mock data
	when(service.updateProduct(pDTO)).thenReturn(pDTO);
	//mockmvc based called
	this.mockMvc.perform(put("/product/update")
			            .content(jsonData)
			            .contentType(MediaType.APPLICATION_JSON)
			            .accept(MediaType.APPLICATION_JSON))
	                    .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("401"))
	                    .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("earbud"))
	                    .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("700.0"))
	                    .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value("REALME"));

}
@Test
@Order(6)
public void test_deleteProductById() throws Exception
{
//mocking external dependency
Long id=401L;
when(service.deleteProductById(id)).thenReturn("product is deleted successfully!!");
//mockmvc
String expectedMSG="product is deleted successfully!!";
this.mockMvc.perform(delete("/product/{id}",id)) //here passing path variable as parameter
             .andExpect(content().string(expectedMSG));
;
	
}


}
