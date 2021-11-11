package com.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pms.controller.ProductRestController;
import com.pms.model.ProductDTO;
import com.pms.service.IProductService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ControllerMockitoTest {
@Mock
IProductService service;//mocking external or has dependency from controller layer

@InjectMocks
ProductRestController  productRestController;
 
List<ProductDTO> productDTOList;  
@Test//executing test cases
@Order(1)
public void test_findAllProducts()
{
	//creating mock data
	productDTOList=new ArrayList<ProductDTO>();
	//for pDTO1
	ProductDTO pDTO1=new ProductDTO();
	pDTO1.setProductId(301L);
	pDTO1.setProductName("earphone");
	pDTO1.setPrice(800D);
	pDTO1.setManufacturer("boat"); 
	productDTOList.add(pDTO1);
	//for pDTO2
	ProductDTO pDTO2=new ProductDTO();
	pDTO2.setProductId(302L);
	pDTO2.setProductName("earbud");
	pDTO2.setPrice(2500D);
	pDTO2.setManufacturer("boat");
	productDTOList.add(pDTO2);
	//for pDTO3
	ProductDTO pDTO3=new ProductDTO();
	pDTO3.setProductId(303L);
	pDTO3.setProductName("macbook");
	pDTO3.setPrice(75000D);
	pDTO3.setManufacturer("apple");
	productDTOList.add(pDTO3);
	//for pDTO4
	ProductDTO pDTO4=new ProductDTO();
	pDTO4.setProductId(304L);
	pDTO4.setProductName("speaker");
	pDTO4.setPrice(1500D);
	pDTO4.setManufacturer("iboll");
	productDTOList.add(pDTO4);
	
 when(service.searchAllProducts()).thenReturn(productDTOList);
 ResponseEntity<List<ProductDTO>> res=productRestController.findAllProducts();
 //assertion
 assertEquals(HttpStatus.OK,res.getStatusCode());//validating response code or http status
 assertEquals(4,res.getBody().size());//validating data based on size or records
	
}
@Test
@Order(2)
public void test_findProductById()
{
	//creating mock data 
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(301L);
	pDTO.setProductName("earphone");
	pDTO.setPrice(800D);
	pDTO.setManufacturer("boat"); 
	Long id=301L;//expected id/ or o/p or result
	when(service.searchByProductId(id)).thenReturn(pDTO);
	assertEquals(id,productRestController.findProductById(id).getProductId());
}
@Test
@Order(3)
public void test_findProductByManufacturer()
{
	   //creating mock data
		productDTOList=new ArrayList<ProductDTO>();
		//for pDTO1
		ProductDTO pDTO1=new ProductDTO();
		pDTO1.setProductId(301L);
		pDTO1.setProductName("earphone");
		pDTO1.setPrice(800D);
		pDTO1.setManufacturer("boat"); 
		productDTOList.add(pDTO1);
		//for pDTO2
		ProductDTO pDTO2=new ProductDTO();
		pDTO2.setProductId(302L);
		pDTO2.setProductName("earbud");
		pDTO2.setPrice(2500D);
		pDTO2.setManufacturer("boat");
		productDTOList.add(pDTO2);
	    String manufacturer="boat";
	    //mocking external dependency
	   when(service.searchByManufacturer(manufacturer)).thenReturn(productDTOList);
       //assertion 
	   //iterating & checking each product's manufacturer 
	   List<ProductDTO> resProductDTOList=productRestController.findProductByManufacturer(manufacturer);
	   resProductDTOList.forEach(product->{
		  assertEquals(manufacturer,product.getManufacturer());
		    });
}
@Test
@Order(4)
public void test_addProduct() {
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(301L);
	pDTO.setProductName("earphone");
	pDTO.setPrice(800D);
	pDTO.setManufacturer("boat"); 
	when(service.addProduct(pDTO)).thenReturn("product is saved successfully!!");
	String expectedMSG="product is saved successfully!!";
	assertEquals(expectedMSG,productRestController.addProduct(pDTO));
}
@Test
@Order(5)
public void test_updateProduct()
{   
	//creating mock data
	//for pDTO
	ProductDTO pDTO=new ProductDTO();
	pDTO.setProductId(301L);
	pDTO.setProductName("earbuds");
	pDTO.setPrice(800D);
	pDTO.setManufacturer("boat");
	//mocking external dependency
	when(service.updateProduct(pDTO)).thenReturn(pDTO);
	//assertion
	assertEquals(pDTO, productRestController.updateProduct(pDTO));
}
@Test
@Order(6)
public void test_deleteProductById()
{
Long id=5L;
when(service.deleteProductById(id)).thenReturn("product is deleted successfully!!");	
String expectedMSG="product is deleted successfully!!";
assertEquals(expectedMSG,productRestController.deleteProductById(id));
}

}
