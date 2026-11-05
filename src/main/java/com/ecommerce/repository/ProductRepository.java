package com.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Product;

public interface ProductRepository {

	// CREATE
	Long save(Product p);
	
	// READ-ALL
	List<Product> findAll();
	
	// READ ALL ACTIVE (products visible to customers)
	List<Product> findAllActive();
	
	// READ BY ID
	Optional<Product> findById(Long productId);
	
	// READ BY NAME  (Partial match, returns zero or more products)
	List<Product> searchByName(String keyword);
	
	// READ BY EXACT NAME (returns at most one Product)
	Optional<Product> findByName(String productName);
	
	// READ-BY CATEGORY
	List<Product> findByCategory(String categoryName);
	
	// READ-BY MINIMUM PRICE (no upper limit)
	List<Product> findByMinPrice(BigDecimal minPrice);
	
	// READ-BY PRICE RANGE
	List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
	
	// UPDATE NAME
	int updateName(Long productId, String newProductName);
	
	// UPDATE CATEGORY
	int updateCategory(Long productId, Long categoryId);
	
	// UPDATE PRICE
	int updatePrice(Long productId, BigDecimal newPrice);
	
	// DELETE BY ID
	int deleteById(Long productId);
}
