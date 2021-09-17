package com.pms.service;

import java.util.List;

import com.pms.model.ProductDTO;

public interface IProductService {
	public List<ProductDTO> searchAllProducts();
	public ProductDTO searchByProductId(Long pid);
	public List<ProductDTO> searchByManufacturer(String manufacturer);
	public String addProduct(ProductDTO productDTO);
	public ProductDTO updateProduct(ProductDTO productDTO);
	public String deleteProductById(Long pid);

}
