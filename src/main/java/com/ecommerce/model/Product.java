/*
 * @Author - Aaditya Kumar
 * 
 */

package com.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
	private Long productId;
	private String productName;
	private BigDecimal price;
	private Long categoryId;
	private Long brandId;
	private Integer stockQuantity;
	private Boolean isActive;
	
	// audit records
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// constructor - save a product into DB
	public Product (
		String productName,
		BigDecimal price,
		Long categoryId,
		Long brandId,
		Integer stockQuantity,
		Boolean isActive
	) {
		super();
		this.productName = productName;
		this.price = price;
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.stockQuantity = stockQuantity;
		this.isActive = isActive;
	}
	
	// constructor - for product retrieval from DB
	public Product (
		Long productId,
		String productName,
		BigDecimal price,
		Long categoryId,
		Long brandId,
		Integer stockQuantity,
		Boolean isActive,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
		this(productName, price, categoryId, brandId, stockQuantity, isActive);
		this.productId = productId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getBrandId() {
		return brandId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	
}
