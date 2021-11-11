package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import com.pms.model.ProductDTO;
import com.pms.service.IProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin("*")
public class ProductRestController {
@Autowired
IProductService service;
@GetMapping(value="/allProducts",produces = "application/json")//GET is for fetch/retrive
@ApiOperation("return all product from database")//showing endpoints descriptions
@ApiResponses({@ApiResponse(code = 200,message = "Fine") ,@ApiResponse(code=404,message = "try with right url")})
public ResponseEntity<List<ProductDTO>> findAllProducts()
{
	List<ProductDTO> productDTOList=service.searchAllProducts();
	return new ResponseEntity<List<ProductDTO>>(productDTOList,HttpStatus.OK);
	
}

@GetMapping("/product/{id}")//here default produces MIME TYPE is application/json
@ApiOperation("return specific product based on given id")
public ProductDTO findProductById(@PathVariable Long id)
{
	ProductDTO productDTO=service.searchByProductId(id);
	if(productDTO==null)
	{
		return null;
	}
	else
	{
		return productDTO;
	}
	
}
@GetMapping(value="/products/{manufacturer}",produces = "application/json")//this endpoint is access by http get
@ApiOperation("return products based on manufacturer")
public List<ProductDTO> findProductByManufacturer(@PathVariable String manufacturer)
{
	List<ProductDTO> productDTOList=service.searchByManufacturer(manufacturer);
	return productDTOList;
	
}
@PostMapping(value="/product/add",produces = "text/plain",consumes = "application/json")//this endpoint is access by http post
@ApiOperation("add product details to the database")
public String addProduct(@RequestBody ProductDTO productDTO)//here deserialized
{
	//System.out.println("called here....");
	return service.addProduct(productDTO);
	
}
@PutMapping(value="/product/update",produces = "application/json",consumes = "application/json")//this endpoint is access by http put
@ApiOperation("update product details from the database")
public ProductDTO updateProduct(@RequestBody ProductDTO productDTO)
{
ProductDTO updatedProductDTO=service.updateProduct(productDTO);//pDTO
	if(updatedProductDTO==null)
	{
		return null;
	}
	else
	{
		return updatedProductDTO;
	}

	
}
@DeleteMapping("/product/{id}")//this endpoint is access by http delete
@ApiOperation("delete specific product based on id")
//@CrossOrigin("*")//allowed with any origin/domain for either consuming/producing appln
public String deleteProductById(@PathVariable Long id)
{
	return service.deleteProductById(id);
	
	
}


}
