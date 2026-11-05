package com.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.service.CategoryService;

public class ProductRepositoryTest {
	
	private ProductRepositoryImpl productRepository;
	private Category category;
	private CategoryService categoryService;
	
	private final List<Long> createdProductIds = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws SQLException {
		productRepository = new ProductRepositoryImpl();
		categoryService = 
				new CategoryService(new CategoryRepositoryImpl());
		category = categoryService.getCategoryById(12); // " 12 -> Test Category"
	}
	
	@AfterEach
	void tearDown() throws SQLException {
		for (Long id : createdProductIds) {
			productRepository.deleteById(id);
		}
		
		createdProductIds.clear();
	}
	
	/* ===== HELPER METHOD ===== */
	private Long createProduct(
		String productName,
		String price,
		int quantity
	) throws SQLException {
		Product p = new Product (
			productName,
			new BigDecimal(price),
			category,
			quantity
		);
		Long productId = productRepository.save(p);
		createdProductIds.add(productId);
		return productId;
	}
	
	
	
	/* ===== TESTS =====*/
	@Test
	void testSave_shouldReturnGenerateId() throws SQLException {
		Product p = new Product(
			"Test Product 1", 
			new BigDecimal("399.0"), 
			category, 
			58,
			true
		);
		
		Long productId = productRepository.save(p);
		createdProductIds.add(productId); // adds inside List<Long> createdProductIds
	}
	
	@Test
	void testSave_shouldThrowExceptionWhenCategoryDoesNotExist() throws SQLException {
		Category category = new Category(-1, "TestCategory");
		Product p = new Product(
			"Test Product 1", 
			new BigDecimal("5999.0"), 
			category,
			58
		);
		
		assertThrows(SQLException.class, () -> productRepository.save(p));
	}
	
	@Test
	void testFindById_shouldReturnSavedProduct() throws SQLException {
		
		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		Product foundProduct = productRepository.findById(productId);
		
		assertEquals(productId, foundProduct.getProductId());
		assertEquals("Test Product 1", foundProduct.getProductName());
		assertEquals(0, new BigDecimal("399.0").compareTo(foundProduct.getPrice()));
		assertEquals("Test Category", foundProduct.getCategory().getCategoryName());
		assertEquals(58, foundProduct.getQuantity());
	}
	
	@Test
	void testFindByName_shouldContainSavedProduct() throws SQLException {

		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		List<Product> products = 
				productRepository.findByName("Test Product 1");
		
		Product actualProduct =
			products.stream()
				.filter(p -> productId.equals(p.getProductId()))
				.findFirst()
				.orElse(null);
		
		assertNotNull(actualProduct);
		assertEquals(0, new BigDecimal("399.0").compareTo(actualProduct.getPrice()));
		assertEquals(58, actualProduct.getQuantity());
	}
	
	@Test
	void testFindByCategory_shouldContainsSavedProduct() throws SQLException {
		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		List<Product> products = productRepository.findByCategory(12);
		assertTrue(
			products.stream()
			.anyMatch(p -> productId.equals(p.getProductId()))
		);
	}
	
	@Test
	void testFindAll_shouldContainAllSavedProducts() throws SQLException {
		Long productId1 = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		Long productId2 = createProduct(
			"Test Product 2", 
			"499.0", 
			18
		);
		
		List<Product> products = productRepository.findAll();
		
		assertTrue(
			products.stream()
			.anyMatch(p -> productId1.equals(p.getProductId()))
		);
		
		assertTrue(
			products.stream()
			.anyMatch(p -> productId2.equals(p.getProductId()))
		);
	}
	
	@Test
	void testDeleteById_shouldremoveProduct() throws SQLException {
		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		int rowsAffected = productRepository.deleteById(productId);
		
		assertNull(productRepository.findById(productId));
	}
	
	@Test
	void testUpdatePrice_shouldChangePrice() throws SQLException {
		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		productRepository.updatePrice(productId, new BigDecimal("39.99"));
		
		Product foundProduct = productRepository.findById(productId);
		assertEquals(0, new BigDecimal("39.99").compareTo(foundProduct.getPrice()));
	}
	
	@Test
	void testUpdateName_shouldChangeName() throws SQLException {
		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		productRepository.updateName(productId, "Test Product 2");
		Product updatedProduct = productRepository.findById(productId);
		assertEquals("Test Product 2", updatedProduct.getProductName());
	}
	
	@Test
	void testUpdateCategory_shouldChangeCategory() throws SQLException {
		Long productId = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		Category newCategory = categoryService.getCategoryById(1);
		
		productRepository.updateCategory(productId, newCategory.getCategoryId());
		Product updatedProduct = productRepository.findById(productId);
		
		assertEquals(newCategory.getCategoryId(), updatedProduct.getCategory().getCategoryId());
		assertEquals(newCategory.getCategoryName(), updatedProduct.getCategory().getCategoryName());
		assertNotEquals(12, updatedProduct.getCategory().getCategoryId());
	}
	
	@Test
	void testFindByPriceRange_shouldReturnProductsWithinRange() throws SQLException {
		Long productId1 = createProduct(
			"Test Product 1", 
			"399.0", 
			58
		);
		
		Long productId2 = createProduct(
			"Test Product 2", 
			"499.0", 
			68
		);
		
		Long productId3 = createProduct(
			"Test Product 3", 
			"599.0", 
			78
		);
		
		BigDecimal minPrice = new BigDecimal("200.0");
		BigDecimal maxPrice = new BigDecimal("400.0");
		
		List<Product> products =
			productRepository.findByPriceRange(
				minPrice, 
				maxPrice
			);
		// assert: our in-range product is returned
		assertTrue(products.stream().anyMatch(p -> productId1.equals(p.getProductId())));

		// assert: our out-of-range products are NOT returned
		assertFalse(products.stream().anyMatch(p -> productId2.equals(p.getProductId())));
		assertFalse(products.stream().anyMatch(p -> productId3.equals(p.getProductId())));

		// assert: every returned product (including pre-existing rows) is really within the range
		assertTrue(products.stream().allMatch(p ->
			p.getPrice().compareTo(minPrice) >= 0 && p.getPrice().compareTo(maxPrice) <= 0
		));
	}
}