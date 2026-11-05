/*
 * WHY I USED BRAND AS A SEPARATE CLASS 
 * AND DIDN'T USE BRAND AS A PRODUCT FIELD ?
 * 
 * ANSWER -> 
 * 	Suppose a seller comes with a new brand, first he will have to request 
 * 	to admin to add his brand. 
 * 	once admin registers then
 * 
 * 	The application (DB) has a list of brands.
 * 	a new Seller (or same seller) will never put a brand with typo 
 * 	by mistake in future 
 */
package com.ecommerce.model;

import java.time.LocalDateTime;

public class Brand {
	
	private Long brandId; // auto-increment at DB level
	private String brandName; // Unique -> DB-Level
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	/* ===== CONSTRUCTOR =====
	 *  Uses:
	 *  	1. Insert a Product inside DB
	 */
	public Brand (
		String brandName
	) {
		this.brandName = brandName;
	}
	
	/*  ===== CONSTRUCTOR =====
	 *   Uses:
	 *   	1. fetch Product from DB by Brand
	 *   	2. Admin call to modify Brand Name
	 */
	public Brand(
		Long brandId,
		String brandName,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
		this(brandName);
		this.brandId = brandId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getBrandId() {
		return brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
	
}
