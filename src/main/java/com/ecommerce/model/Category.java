package com.ecommerce.model;

import java.time.LocalDateTime;

public final class Category {
	private Long categoryId; // auto increment at DB level
	private String categoryName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// constructor - for insertion in DB 
	public Category (
		String categoryName
	) {
		this.categoryName = categoryName;
	}
	
	// constructor - fetch Product from DB by Category
	public Category (
		Long categoryId,
		String categoryName
	) {
		this(categoryName);
		this.categoryId = categoryId;
	}
	
	// constructor - for fetch from DB
	public Category (
		Long categoryId,
		String categoryName,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
		this(categoryId, categoryName);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	
}
