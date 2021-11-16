package com.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.pms.entity.Product;
import com.pms.model.ProductDTO;
import com.pms.repository.ProductRepository;
import com.pms.service.impl.ProductServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ServiceImplMockitoTest {
	@Mock
	ProductRepository productRepository;
	//for creating mock repo
	
    @InjectMocks
    ProductServiceImpl productService;
    //for injecting service layer with mock
	
  
    
   
    
    @Mock
    Product product;//=new Product();
    
    @Mock
    ProductDTO pDTO;
    
  // List<Product> pList;
	
    @Test //for writing test cases
	@Order(1)
	public void test_searchAllProducts()
	{
		//creating mock data
		List<Product> pList=new ArrayList<>();
    	//pList=new ArrayList<>();
    	//p1
        Product p1=new Product();
        p1.setProductId(1L);
        p1.setProductName("Laptop");
        p1.setPrice(45000D);
        p1.setManufacturer("Lenovo");
		pList.add(p1);
		//p2
		Product p2=new Product();
        p2.setProductId(2L);
        p2.setProductName("earbud");
        p2.setPrice(45000D);
        p2.setManufacturer("boat");
		pList.add(p2);
		//p3
		Product p3=new Product();
        p3.setProductId(3L);
        p3.setProductName("keyboard");
        p3.setPrice(45000D);
        p3.setManufacturer("intex");
		pList.add(p3);
		
		//mocking external dependencies
		when(productRepository.findAll()).thenReturn(pList);
		//assertion: assertEquals(expected o/p,actual o/p);
		assertEquals(3,productService.searchAllProducts().size());
		System.out.println(productService.searchAllProducts());
		
		
		//System.out.println("println");
	}
	@Test
	@Order(2)
	public void test_searchByProductId01()
	{
		Product product=new Product();
		product.setProductId(7L);
		product.setProductName("mouse");
		product.setPrice(600D);
		product.setManufacturer("dell");
		
		Optional<Product> opt=Optional.of(product);
		//Optional.of() //not allow null value
		//Optional.ofNullable() //can be null value i.e null
		Long pid=7L;
		//mocking external  dependencies for returning mock data
		when(productRepository.findById(pid)).thenReturn(opt); 
		//assertEquals(expected value,actual value);
		assertEquals(pid,productService.searchByProductId(pid).getProductId());
		//System.out.println(pid);
		
	}
	@Test
	@Order(3)
	public void test_searchByProductId02()
	{
		Product product=new Product();
		product.setProductId(7L);
		product.setProductName("mouse");
		product.setPrice(600D);
		product.setManufacturer("dell");
		
		Optional<Product> opt=Optional.ofNullable(null);
		//Optional<Product> opt=Optional.of(product);
		
		//Optional.of() //not allow null value
		//Optional.ofNullable() //can be null value i.e null
		Long pid=7L;
		//mocking external  dependencies for returning mock data

		when(productRepository.findById(pid)).thenReturn(opt); 
		//when(productService.searchByProductId(pid)).thenThrow(NullPointerException.class);
		//assertEquals(expected value,actual value);
		//assertEquals(new NullPointerException().getStackTrace(),productService.searchByProductId(pid));
		assertEquals(null, productService.searchByProductId(pid));//if not wrapping null value inside optional i.e 		Optional<Product> opt=Optional.ofNullable(null); then throws null pointer exception
		//System.out.println(pid);
		
	}
	
	@Test
	@Order(4)
	public void test_searchByManufacturer()
	{
		//creating mock data
		List<Product> pList=new ArrayList<>();
		 //p1
        Product p1=new Product();
        p1.setProductId(1L);
        p1.setProductName("earphone");
        p1.setPrice(45000D);
        p1.setManufacturer("boat");
		pList.add(p1);
		//p2
		Product p2=new Product();
        p2.setProductId(2L);
        p2.setProductName("earbud");
        p2.setPrice(45000D);
        p2.setManufacturer("boat");
		pList.add(p2);
		//p3
		Product p3=new Product();
        p3.setProductId(3L);
        p3.setProductName("headphone");
        p3.setPrice(45000D);
        p3.setManufacturer("boat");
		pList.add(p3);
		
		String manufacturer="boat";
		
		//mocking external dependency
		when(productRepository.findByManufacturer(manufacturer)).thenReturn(pList);
		//assertion:just check expected or actual o/p(reuslt) matches or not.
		List<ProductDTO> pDTOList=productService.searchByManufacturer(manufacturer);
		pDTOList.forEach(product->{
			//if(product.getManufacturer().equals(manufacturer))
			assertEquals(manufacturer,product.getManufacturer());
		});
		System.out.println(pDTOList);
		
		//assertEquals(3,productService.searchByManufacturer(manufacturer).size());
		
	}
	@Test
	@Order(5)
	public void test_addProduct01()
	{
		//creating mock data for ProductDTO
		ProductDTO pDTO=new ProductDTO();
		pDTO.setProductId(108L); 
		pDTO.setProductName("Speaker");
		pDTO.setPrice(800D);
		pDTO.setManufacturer("iBoll");
		
		//set either through constructor by directly 
		//when(productRepository.existsById(product.getProductId()).thenReturn("product is already exist");
		//String msg=new String("product is already exist");
		when(productRepository.existsById(product.getProductId())).thenReturn(false);
		when(productRepository.save(product)).thenReturn(product);//
		
		//Mockito.when(productRepository.save(product)).thenReturn(product);//static method
		//assertion
	 String expectedMSG="product is saved successfully!!";
		assertEquals(expectedMSG, productService.addProduct(pDTO));
		
		}
	@Test
	@Order(6)
	public void test_addProduct02()
	{
		//creating mock for Product
		Product product=new Product();
		product.setProductId(108L); 
		product.setProductName("Headphone");
		product.setPrice(800D);
	    product.setManufacturer("iBoll");
		
		//creating mock data for ProductDTO
		ProductDTO pDTO=new ProductDTO();
		pDTO.setProductId(108L); 
		pDTO.setProductName("Speaker");
		pDTO.setPrice(800D);
		pDTO.setManufacturer("iBoll");
		
		//set either through constructor by directly 
		when(productRepository.existsById(product.getProductId())).thenReturn(true);//if not create product obj then it will not work i.e existById return false.
		//assertion
	 String expectedMSG="product is already exist";
		assertEquals(expectedMSG, productService.addProduct(pDTO)); 
		}
	@Test
	@Order(7)
	public void test_updateProduct01()
	{
		//creating mock for Product
		Product product=new Product();
		product.setProductId(109L); 
		product.setProductName("Headphone");
		product.setPrice(800D);
	    product.setManufacturer("iBoll");
		
		//creating mock data for ProductDTO
		ProductDTO pDTO=new ProductDTO();
		pDTO.setProductId(109L); 
		pDTO.setProductName("earbud");
		pDTO.setPrice(800D);
	    pDTO.setManufacturer("oneplus");
	    System.out.println("***************");
	    //System.out.println(product.toString());
        when(productRepository.existsById(product.getProductId())).thenReturn(true);
	    when(productRepository.saveAndFlush(product)).thenReturn(product);	
		assertEquals(pDTO,productService.updateProduct(pDTO));
		System.out.println(pDTO);
		System.out.println(productService.updateProduct(pDTO));
	}
	@Test
	@Order(8)
	public void test_updateProduct02()
	{
		//creating mock for Product
		Product product=new Product();
		product.setProductId(109L); 
		product.setProductName("Headphone");
		product.setPrice(800D);
	    product.setManufacturer("iBoll");
		
		//creating mock data for ProductDTO
		ProductDTO pDTO=new ProductDTO();
		pDTO.setProductId(109L); 
		pDTO.setProductName("earbud");
		pDTO.setPrice(800D);
	    pDTO.setManufacturer("oneplus");
	    System.out.println("fine....");
	    //System.out.println(product.toString());
        //when(productRepository.existsById(product.getProductId())).thenReturn(false);
	    when(productRepository.saveAndFlush(product)).thenReturn(null);	
		assertEquals(null,productService.updateProduct(pDTO));
		////System.out.println(pDTO);
		//System.out.println(productService.updateProduct(pDTO));
	}
	@Test
	@Order(9)
	public void test_deleteProductById01()
	{
		Long pid=7L;
		//String expectedMSG="product is deleted successfully!!";
		when(productRepository.existsById(pid)).thenReturn(true);
		productService.deleteProductById(pid);//call other wise error:zero interaction with this mock.
         verify(productRepository,times(1)).deleteById(pid);
	   
	}
	@Test
	@Order(10)
	public void test_deleteProductById02()
	{
		Long pid=7L;
		when(productRepository.existsById(pid)).thenReturn(false);
		assertEquals("product doesn't exist",productService.deleteProductById(pid) );
	   
	}
	
	
	

}
