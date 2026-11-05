package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductAdminDTO {

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
	
	
	public ProductAdminDTO(Long productId, String productName, BigDecimal price, Long categoryId, Long brandId,
			Integer stockQuantity, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.stockQuantity = stockQuantity;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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


	public Long getCategoryId() {
		return categoryId;
	}


	public Long getBrandId() {
		return brandId;
	}


	public Integer getStockQuantity() {
		return stockQuantity;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	
	
}
