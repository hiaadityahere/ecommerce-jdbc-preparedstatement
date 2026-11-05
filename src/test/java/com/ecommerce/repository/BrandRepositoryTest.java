package com.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.model.Brand;

public class BrandRepositoryTest {

	private BrandRepositoryImpl brandRepository;
	
	private final List<Long> createdBrandIds = new ArrayList<>();
	
	@BeforeEach
	void setUp() {
		brandRepository = new BrandRepositoryImpl();
	}
	
	@AfterEach
	void tearDown() throws SQLException {
		for (Long id : createdBrandIds) {
			brandRepository.deleteById(id);
		}
		createdBrandIds.clear();
	}
	
	/* ===== HELPER METHOD ===== */
	private Long createBrand(String brandName) throws SQLException {
		Brand b = new Brand(brandName);
		Long brandId = brandRepository.save(b);
		createdBrandIds.add(brandId);
		return brandId;
	}
	
	private Brand findById(List<Brand> brands, Long id) {
	    return brands.stream()
	            .filter(b -> id.equals(b.getBrandId()))
	            .findFirst()
	            .orElse(null);
	}
	
	/* ===== TESTS =====*/
	@Test
	void testSave_shouldReturnGenerateId() throws SQLException {
		Brand b = new Brand("Alpha Brand");
		
		Long brandId = brandRepository.save(b);
		createdBrandIds.add(brandId); // register for cleanup
		
		// the id was generated
		assertNotNull(brandId);
		assertTrue(brandId > 0);
		
		// the id was also set on the object
		assertEquals(brandId, b.getBrandId());
		
		// the row really exists in the DB with the right data
		Brand savedBrand = brandRepository.findById(brandId);
		assertNotNull(savedBrand);
		assertEquals("Alpha Brand", savedBrand.getBrandName());
	}
	
	@Test
	void testFindAll_shouldContainAllSavedBrands() throws SQLException {
		Long brandId1 = createBrand("Alpha Brand");
		Long brandId2 = createBrand("Beta Brand");
		
		List<Brand> brands = brandRepository.findAll();
		
		assertNotNull(brands);
		
		assertTrue(
			brands.stream()
				.anyMatch(b -> brandId1.equals(b.getBrandId()))
		);
		
		assertTrue(
			brands.stream()
				.anyMatch(b -> brandId2.equals(b.getBrandId()))
		);
	}
	
	@Test
	void testFindById_shouldReturnSavedBrand() throws SQLException {
		Long brandId = createBrand("Alpha Brand");
		
		Brand foundBrand = brandRepository.findById(brandId);
		
		assertNotNull(foundBrand);
		assertEquals(brandId, foundBrand.getBrandId());
		assertEquals("Alpha Brand", foundBrand.getBrandName());
	}
	
	@Test
	void testSearchByName_partialKeyword_returnsAllBrandsContainingText() throws SQLException {
		Long brandId1 = createBrand("Alpha Brand");
		Long brandId2 = createBrand("My Alpha Brand");
		
		List<Brand> brands = brandRepository.searchByName("Alpha");
		
		Brand actualBrand1 = findById(brands, brandId1);
		
		assertNotNull(actualBrand1);
		assertEquals("Alpha Brand", actualBrand1.getBrandName());
		
		Brand actualBrand2 = findById(brands, brandId2);
		
		assertNotNull(actualBrand2);
		assertEquals("My Alpha Brand", actualBrand2.getBrandName());
	}
	
	@Test
	void testFindByName_shouldContainSavedBrand() throws SQLException {
		Long brandId = createBrand("Alpha Brand");
		
		Optional<Brand> brands = brandRepository.findByName("Alpha Brand");
		
		Brand actualBrand = 
			brands.stream()
				.filter(b -> brandId.equals(b.getBrandId()))
				.findFirst()
				.orElse(null);
		
		assertNotNull(actualBrand);
		assertEquals("Alpha Brand", actualBrand.getBrandName());
	}
	
	@Test
	void testUpdateName_shouldChangeName() throws SQLException {
		Long brandId = createBrand("Alpha Brand");
		
		brandRepository.updateName(brandId, "Beta Brand");
		Brand updatedBrand = brandRepository.findById(brandId);
		
		assertNotNull(updatedBrand);
		assertEquals(brandId, updatedBrand.getBrandId());
		assertEquals("Beta Brand", updatedBrand.getBrandName());
	}
	
	@Test
	void testDeleteById_shouldremoveBrand() throws SQLException {
		Long brandId = createBrand("Alpha Brand");
		
		int rowsAffected = brandRepository.deleteById(brandId);
		
		assertEquals(1, rowsAffected);
		assertNull(brandRepository.findById(brandId));
	}
}
