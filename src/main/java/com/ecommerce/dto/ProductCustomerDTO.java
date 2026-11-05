package com.ecommerce.dto;

import java.math.BigDecimal;

public class ProductCustomerDTO {

	private Long productId;
	private String productName;
	private BigDecimal price;
	private String availability;

	public ProductCustomerDTO(Long productId, String productName, BigDecimal price, String availability) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.availability = availability;
	}
	
	public Long getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getAvailability() {
		return availability;
	}
	
}
