package com.pms.model;

import java.io.Serializable;

public class ProductDTO implements Serializable{
	//ProductDTO doesn't contain mapping info. which is hide from restcontroller or rest client
	private Long productId;
	private String productName;
	private Double price;
	private String manufacturer;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	

}
