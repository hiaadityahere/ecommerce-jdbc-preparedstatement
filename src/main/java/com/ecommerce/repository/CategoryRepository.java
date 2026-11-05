package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Category;

public interface CategoryRepository {

	// 1. CREATE
	Long save(Category c);
	
	// 2. READ-ALL
	List<Category> findAll();
	
	// 3. READ BY ID
	Optional<Category> findById(Long categoryId);

	// 4. READ BY NAME (Partial match, returns zero or more categories)
	List<Category> searchByName(String keyword);
	
	// 5. READ BY EXACT NAME (returns at most one category)
	Optional<Category> findByName(String categoryName);
	
	// 6. UPDATE NAME
	int updateName(Long categoryId, String newName);
	
	// 7. DELETE BY ID
	int deleteById(Long categoryId);
}
