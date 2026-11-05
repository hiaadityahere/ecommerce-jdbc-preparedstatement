package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Brand;

public interface BrandRepository {

	// 1. CREATE
	public Long save(Brand b);
	
	// 2. READ ALL
	public List<Brand> findAll();
	
	// 3. READ BY ID
	public Optional<Brand> findById(Long brandId);
	
	// 4. READ BY NAME (Partial match, returns zero or more brands)
	public List<Brand> searchByName(String keyword);
	
	// 5. READ BY EXACT NAME (returns at most one brand)
	public Optional<Brand> findByName(String brandName);
	
	// 6. UPDATE NAME
	public int updateName(Long brandId, String newName);
	
	// 7. DELETE BY ID
	public int deleteById(Long brandId);
}
