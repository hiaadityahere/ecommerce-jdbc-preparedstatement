/*
 * I AM FOLLOWING THE PRACTICE OF: 
 * 	- show only those products to customer which are ACTIVE (isActive == true), 
 * 		there's no sense of showing products which are INACTIVE
 * 		(isActive == false)
 *  
 *  
 *  READ BY PRICE RANGE 'getProductsByPriceRange' validation handles
 *  +----------------------------------------------------------------------------------------------------+
 *  | `minPrice` | `maxPrice` | Expected Result                                                          |
 *  | ---------- | ---------- | ------------------------------------------------------------------------ |
 *  | `null`     | `null`     | `InvalidPriceRangeException`                                             |
 *  | `null`     | `5000`     | Min defaults to `0`; call `findByPriceRange(0, 5000)`                    |
 *  | `20000`    | `null`     | Call `findByMinPrice(20000)`                                             |
 *  | `1000`     | `5000`     | Call `findByPriceRange(1000, 5000)`                                      |
 *  | `5000`     | `1000`     | `InvalidPriceRangeException` (min exceeds max)                           |
 *  | `-10`      | `5000`     | `InvalidPriceException`                                                  |
 *  | `null`     | `-10`      | `InvalidPriceException` (min defaults to `0`, then max validation fails) |
 *  | `1000`     | `1000`     | Allowed; returns products priced exactly `1000`                          |
 *  +----------------------------------------------------------------------------------------------------+
 */

package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.dto.ProductCustomerDTO;
import com.ecommerce.exception.InvalidCategoryNameException;
import com.ecommerce.exception.InvalidPriceException;
import com.ecommerce.exception.InvalidPriceRangeException;
import com.ecommerce.exception.InvalidProductIdException;
import com.ecommerce.exception.InvalidProductNameException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.util.ValidationUtil;

public class CustomerProductService {
	
	private final ProductRepository productRepository;
	
	public CustomerProductService (ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	private static final int MAX_PRODUCT_NAME_LENGTH = 200;
	private static final int MAX_CATEGORY_NAME_LENGTH = 50;
	
	/* ===== PUBLIC METHODS ===== */
	
	// READ ALL ACTIVE PRODUCTS
	public List<ProductCustomerDTO> getAllProducts() {
		return productRepository.findAllActive()
				.stream()
				.map(product -> toCustomerDTO(product))
				.toList();
	}
	
	// READ BY ID 
	public ProductCustomerDTO getProductById(Long productId) {
		ValidationUtil.validateId (
			productId,
			"Product ID",
			InvalidProductIdException::new
		);
		
		return productRepository.findById(productId)
				.filter(product -> product.getIsActive())
				.map(product -> toCustomerDTO(product))
				.orElseThrow(() -> 
					new ProductNotFoundException("Product not found: " + productId)
				);
	}
	
	// READ BY NAME (Partial match, returns zero or more products)
	public List<ProductCustomerDTO> searchByName(String keyword) {
		
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				keyword, 
				"Search Keyword", 
				MAX_PRODUCT_NAME_LENGTH,
				InvalidProductNameException::new
			);
		return productRepository.searchByName(trimmedKeyword)
				.stream()
				.filter(product -> product.getIsActive())
				.map(product -> toCustomerDTO(product))
				.toList();
	}
	
	// READ BY EXACT NAME (returns at most one Product)
	public ProductCustomerDTO getProductByName(String productName) {
		
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				productName, 
				"Product Name", 
				MAX_PRODUCT_NAME_LENGTH,
				InvalidProductNameException::new
			);
		
		return productRepository.findByName(trimmedKeyword)
				.filter(product -> product.getIsActive())
				.map(product -> toCustomerDTO(product))
				.orElseThrow(() -> 
					new ProductNotFoundException("Product not found: " + productName)
				);
				
	}
	
	// READ BY CATEGORY
	public List<ProductCustomerDTO> getProductByCategory(String categoryName) {
		
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				categoryName, 
				"Category Name", 
				MAX_CATEGORY_NAME_LENGTH,
				InvalidCategoryNameException::new
			);                   
		
		return productRepository.findByCategory(trimmedKeyword)
				.stream()
				.filter(product -> product.getIsActive())
				.map(product -> toCustomerDTO(product))
				.toList();
	}
	
	// READ-BY PRICE RANGE
	public List<ProductCustomerDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
	    BigDecimal min = (minPrice == null) ? BigDecimal.ZERO : minPrice;
	    validatePriceRange(minPrice, min, maxPrice);

	    List<Product> products = (maxPrice == null)
	            ? productRepository.findByMinPrice(min)
	            : productRepository.findByPriceRange(min, maxPrice);

	    return products.stream()
	            .filter(product -> product.getIsActive())
	            .map(product -> toCustomerDTO(product))
	            .toList();
	}
	
	/* ===== HELPER METHOD ===== */
	private ProductCustomerDTO toCustomerDTO(Product p) {
		return new ProductCustomerDTO(
			p.getProductId(), 
			p.getProductName(),
			p.getPrice(),
			resolveAvailability(p.getStockQuantity())
		);
	}
	
	private String resolveAvailability(Integer stockQuantity) {
		
		if (stockQuantity == 0) {
            return "Out of stock";
        }
        if (stockQuantity <= 5) {
            return "Only " + stockQuantity + " left";
        }
        return "In stock";
	}

	private void validatePriceRange(BigDecimal originalMin, BigDecimal min, BigDecimal max) {
	    if (originalMin == null && max == null) {
	        throw new InvalidPriceRangeException("Provide at least a minimum or a maximum price");
	    }
	    if (min.compareTo(BigDecimal.ZERO) < 0
	            || (max != null && max.compareTo(BigDecimal.ZERO) < 0)) {
	        throw new InvalidPriceException("Price cannot be negative");
	    }
	    if (max != null && min.compareTo(max) > 0) {
	        throw new InvalidPriceRangeException(
	            "Minimum price (" + min + ") cannot exceed maximum price (" + max + ")");
	    }
	}
}
