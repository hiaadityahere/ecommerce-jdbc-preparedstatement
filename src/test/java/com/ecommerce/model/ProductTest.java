package com.ecommerce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ProductTest {

	// constructor Testing - save a product into DB
	@Test
	public void testConstructorForSavingProduct() {
		
		Category category = new Category("ELECTRONICS");
		
		Product p = new Product(
			"iphone 18 pro",
			new BigDecimal(2_19_000.0),
			category,
			100
		);
		
		assertEquals(null, p.getProductId());
		assertEquals("iphone 18 pro", p.getProductName());
		assertEquals(new BigDecimal(2_19_000.0), p.getPrice());
		assertEquals("ELECTRONICS", p.getCategory().getCategoryName());
		assertEquals(100, p.getQuantity());
		assertEquals(null, p.getCreatedAt());
		assertEquals(null, p.getUpdatedAt());
	}
	
	// constructor Testing - save a product into DB
	@Test
	public void testConstructorForFtechingProduct() {
		
		Category category = new Category("ELECTRONICS");
		LocalDateTime createdAt = LocalDateTime.of(2026, 10, 26, 13, 39, 00);
		LocalDateTime updatedAt = LocalDateTime.of(2026, 10, 26, 13, 40, 00);
		
		Product p = new Product(
			1L,
			"iphone 18 pro",
			new BigDecimal(2_19_000.0),
			category,
			100,
			createdAt,
			updatedAt
		);
		
		assertEquals(1L, p.getProductId());
		assertEquals("iphone 18 pro", p.getProductName());
		assertEquals(new BigDecimal(2_19_000.0), p.getPrice());
		assertEquals("ELECTRONICS", p.getCategory().getCategoryName());
		assertEquals(100, p.getQuantity());
		assertEquals(createdAt, p.getCreatedAt());
		assertEquals(updatedAt, p.getUpdatedAt());
	}
	
	// Setters Testing
	public void testSetters() {
		
		Category category = new Category("ELECTRONICS");
		LocalDateTime createdAt = LocalDateTime.of(2026, 10, 26, 13, 39, 00);
		LocalDateTime updatedAt = LocalDateTime.of(2026, 10, 26, 13, 40, 00);
		
		Product p = new Product(
			1L,
			"iphone 18 pro",
			new BigDecimal(2_19_000.0),
			category,
			100,
			createdAt,
			updatedAt
		);
		
		p.setProductId(2L);
		
		assertEquals(2L, p.getProductId());
		assertEquals("iphone 18 pro", p.getProductName());
		assertEquals(new BigDecimal(2_19_000.0), p.getPrice());
		assertEquals("ELECTRONICS", p.getCategory().getCategoryName());
		assertEquals(100, p.getQuantity());
		assertEquals(createdAt, p.getCreatedAt());
		assertEquals(updatedAt, p.getUpdatedAt());
		
	}
	
	
}
