package com.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pms.entity.Product;
import com.pms.model.ProductDTO;
import com.pms.repository.ProductRepository;
import com.pms.service.IProductService;



@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	ProductRepository productRepository;

	@Override
	@Cacheable(value = "AllProductsCache",key = "#root.methodName")//use for creating/defining cache root indicate current class/obj
	public List<ProductDTO> searchAllProducts() {
		List<Product> productList=productRepository.findAll();
		List<ProductDTO> productDTOList=new ArrayList<>();
		productList.forEach(product->{
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product,productDTO);
			//1 Product obj is copy into 1 ProductDTO obj. //...copy
			productDTOList.add(productDTO);				  //....then  add until list is finish to iterate
			//Each ProductDTO obj is added to productDTOList
			
		});
		//System.out.println("Called here");//by test method
		return productDTOList;
	}

	@Override
	@Cacheable(value = "ProductById",key = "#pid",unless = "#result==null")//if result is null then don't store data in cache
	public ProductDTO searchByProductId(Long pid) {
		Optional<Product> opt=productRepository.findById(pid);
		//System.out.println(opt);
		if(opt.isPresent())
		{
			Product product=opt.get();
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product,productDTO);
			return productDTO;
		}
		return null;//pid doesn't exist
	
	}

	@Override
	public List<ProductDTO> searchByManufacturer(String manufacturer) {
		List<Product> productList=productRepository.findByManufacturer(manufacturer);
		List<ProductDTO> productDTOList=new ArrayList<>();	
		productList.forEach(product->{
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			productDTOList.add(productDTO);
				
		});	
		return productDTOList;
	}

	@Override
	//i don't want to string data in cache here.
	public String addProduct(ProductDTO productDTO) {//client sends a request in data format(json/xml) converted or deserialized into java obj Here data store in DTO obj
		Product product=new Product();
		BeanUtils.copyProperties(productDTO, product);
		//copy DTO obj to Entity obj which has persistent obj. DTO obj doesn't contain persistent obj
		if(productRepository.existsById(product.getProductId()))//isExist check
		{
			return "product is already exist";
		}
		else
		{
			productRepository.save(product);
			return "product is saved successfully!!";
		}
		
		}

	@Override
	@CachePut(value="ProductById",key="#productDTO.productId",unless = "#result==null")
	//after updating from db also update cache if doesn't exist then add updated data to cache
	public ProductDTO updateProduct(ProductDTO productDTO) {
		Product product=new Product();
		BeanUtils.copyProperties(productDTO,product);
		if(productRepository.existsById(product.getProductId()))
		{
			 productRepository.saveAndFlush(product);
			 return productDTO;
		}
		else
		{
			return null;
			
		}
	
	}

	@Override
	@CacheEvict(value = "ProductById",key = "#pid")
	//if removing data from db also remove from cache
	public String deleteProductById(Long pid) {
		if(productRepository.existsById(pid))
		{
			productRepository.deleteById(pid);
			return "product is deleted successfully!!";
		}
		else
		{
			return "product doesn't exist";
			
		}
			
	}

}
