package com.ecommerce.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ecommerce.exception.BrandAlreadyExistsException;
import com.ecommerce.exception.BrandNotFoundException;
import com.ecommerce.exception.BrandNotNullException;
import com.ecommerce.exception.InvalidBrandIdException;
import com.ecommerce.exception.InvalidBrandNameException;
import com.ecommerce.model.Brand;
import com.ecommerce.repository.BrandRepositoryImpl;
import com.ecommerce.util.ValidationUtil;

public class BrandService {
	
	private final BrandRepositoryImpl brandRepository;
	
	public BrandService (BrandRepositoryImpl brandRepository) {
		this.brandRepository = brandRepository;
	}
	
	private static final int MAX_BRAND_NAME_LENGTH = 100;
	
	// Add a Brand
	public Brand addBrand(Brand brand) throws SQLException {
		
		// validate input -> cannot give brand as null
		if (brand == null) {
			throw new BrandNotNullException("Brand cannot be null!");
		}
		
		String brandName = ValidationUtil.validateAndTrim(
			brand.getBrandName(), 
			"Brand name",
			MAX_BRAND_NAME_LENGTH,
			InvalidBrandNameException::new
		);
		
		// duplicate check
		if (brandRepository.findByName(brandName).isPresent()) {
			throw new BrandAlreadyExistsException(
				"Brand already exists: "
				+ brandName
			);
		}
		
		// save the trimmed value 
		brand.setBrandName(brandName);
		
		// call to repository layer
		Long brandId = brandRepository.save(brand);
		brand.setBrandId(brandId);
		return brand; 
	}
	
	// 2. Get ALL Brands
	public List<Brand> getAllBrands() throws SQLException {
		return brandRepository.findAll();
	}
	
	// 3. Get Brand by ID
	public Brand getBrandById(Long brandId) throws BrandNotFoundException, SQLException {
		
		ValidationUtil.validateId (
			brandId, 
			"Brand ID", 
			InvalidBrandIdException::new
		);
		
		// call to repository layer
		return brandRepository.findById(brandId)
			.orElseThrow(() -> 
				new BrandNotFoundException(
					"Brand not found with ID: " 
					+ brandId 
				)
			);
	}
	
	// 4. Get Brands by keyword (Partial match, returns zero or more brands)
	public List<Brand> searchBrands(String keyword) throws SQLException {
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				keyword, 
				"Search Keyword", 
				MAX_BRAND_NAME_LENGTH, 
				InvalidBrandNameException::new
			);
		return brandRepository.searchByName(trimmedKeyword);
	}
	
	// 5. Get Brands by Exact Name (returns at most one Brand)
	public Brand getBrandByName(String name) throws BrandNotFoundException, SQLException {
		
		String brandName = 
			ValidationUtil.validateAndTrim (
				name, 
				"Brand Name", 
				MAX_BRAND_NAME_LENGTH, 
				InvalidBrandNameException::new
			);
		return brandRepository.findByName(brandName)
			.orElseThrow(() -> 
				new BrandNotFoundException(
					"Brand not found: "	
					+ brandName
				)
			);
	}
	
	// 6. Update Brand Name
	public Brand updateBrandName(Long brandId, String newName) throws BrandNotFoundException, SQLException {
		
		ValidationUtil.validateId (
			brandId, 
			"Brand ID", 
			InvalidBrandIdException::new
		);
		
		String brandName =
		ValidationUtil.validateAndTrim (
			newName, 
			"Brand Name", 
			MAX_BRAND_NAME_LENGTH, 
			InvalidBrandNameException::new
		);
		
		Brand brand = getBrandById(brandId);
		
		// check whether another brand has the same name with this updated brand name
		Optional<Brand> sameName = brandRepository.findByName(brandName);
		if (
			sameName.isPresent() 
			&& !sameName.get().getBrandId().equals(brandId) 
		) {
			throw new BrandAlreadyExistsException("Brand already exists: " + brandName);
		}
		
		int rowsAffected = brandRepository.updateName(brandId, brandName);
		if (rowsAffected != 1) {
	        // the brand was deleted between our check and the update
	        throw new BrandNotFoundException("Brand not found with ID: " + brandId);
	    }
		
		brand.setBrandName(brandName);
		return brand;
	}
	
	// 7. Delete Brand by ID
	public void deleteBrand(Long brandId) throws SQLException {
		ValidationUtil.validateId (
			brandId, 
			"Brand ID", 
			InvalidBrandIdException::new
		);
		
		int rowsAffected = brandRepository.deleteById(brandId);
		if (rowsAffected != 1) {
	        throw new BrandNotFoundException("Brand not found with ID: " + brandId);
	    }
	}
	
}
