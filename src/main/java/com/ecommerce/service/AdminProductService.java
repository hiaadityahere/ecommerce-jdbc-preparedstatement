package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.ecommerce.dto.ProductAdminDTO;
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

public class AdminProductService {

private final ProductRepository productRepository;
	
	public AdminProductService (ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	private static final int MAX_PRODUCT_NAME_LENGTH = 200;
	private static final int MAX_CATEGORY_NAME_LENGTH = 50;
	
	// CREATE
	
	
	// READ-ALL
	public List<ProductAdminDTO> getAllProducts() {
		return productRepository.findAllActive()
				.stream()
				.map(product -> toAdminDTO(product))
				.toList();
	}
	
	// READ BY ID
	public ProductAdminDTO getProductById(Long productId) {
		ValidationUtil.validateId (
			productId,
			"Product ID",
			InvalidProductIdException::new
		);
		
		return productRepository.findById(productId)
				.filter(product -> product.getIsActive())
				.map(product -> toAdminDTO(product))
				.orElseThrow(() -> 
					new ProductNotFoundException("Product not found: " + productId)
				);
	}
	
	// READ-BY NAME  (Partial match, returns zero or more products)
	public List<ProductAdminDTO> searchByName(String keyword) {
		
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				keyword, 
				"Search Keyword", 
				MAX_PRODUCT_NAME_LENGTH,
				InvalidProductNameException::new
			);
		return productRepository.searchByName(trimmedKeyword)
				.stream()
				.map(product -> toAdminDTO(product))
				.toList();
	}

	// READ BY EXACT NAME (returns at most one Product)
	public ProductAdminDTO getProductByName(String productName) {
		
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				productName, 
				"Product Name", 
				MAX_PRODUCT_NAME_LENGTH,
				InvalidProductNameException::new
			);
		
		return productRepository.findByName(trimmedKeyword)
				.map(product -> toAdminDTO(product))
				.orElseThrow(() -> 
					new ProductNotFoundException("Product not found: " + productName)
				);
				
	}
	
	// READ-BY CATEGORY
	public List<ProductAdminDTO> getProductByCategory(String categoryName) {
		
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				categoryName, 
				"Category Name", 
				MAX_CATEGORY_NAME_LENGTH,
				InvalidCategoryNameException::new
			);                   
		
		return productRepository.findByCategory(trimmedKeyword)
				.stream()
				.map(product -> toAdminDTO(product))
				.toList();
	}
	
	// READ-BY MINIMUM PRICE (no upper limit)
	// READ-BY PRICE RANGE
	public List<ProductAdminDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
	    BigDecimal min = (minPrice == null) ? BigDecimal.ZERO : minPrice;
	    validatePriceRange(minPrice, min, maxPrice);

	    List<Product> products = (maxPrice == null)
	            ? productRepository.findByMinPrice(min)
	            : productRepository.findByPriceRange(min, maxPrice);

	    return products.stream()
	            .map(product -> toAdminDTO(product))
	            .toList();
	}
	
	// UPDATE NAME
	public Product updateProductName(Long productId, String newProductName) {
		
		// validate productId
		ValidationUtil.validateId (
			productId,
			newProductName, 
			InvalidProductIdException::new  
		);
		
		//validate new product name
		String productName = 
			ValidationUtil.validateAndTrim (
				newProductName,
				"Product Name",
				MAX_PRODUCT_NAME_LENGTH,
				InvalidProductNameException::new
			);
		 
		ProductAdminDTO product = getProductById(productId);
		
		// check whether another product has the same name with this updated product name
		Optional<ProductCustomerDTO> sameName = productRepository.findByName(productName);
		
	}
	
	// UPDATE CATEGORY
	// UPDATE PRICE
	// DELETE BY ID
	
	/* ===== HELPER METHOD ===== */
	private ProductAdminDTO toAdminDTO(Product p) {
		return new ProductAdminDTO(
			p.getProductId(), 
			p.getProductName(),
			p.getPrice(),
			p.getCategoryId(),
			p.getBrandId(),
			p.getStockQuantity(),
			p.getIsActive(),
			p.getCreatedAt(),
			p.getUpdatedAt()
		);
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
