package com.ecommerce.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ecommerce.exception.CategoryAlreadyExistsException;
import com.ecommerce.exception.CategoryNotFoundException;
import com.ecommerce.exception.CategoryNotNullException;
import com.ecommerce.exception.InvalidCategoryIdException;
import com.ecommerce.exception.InvalidCategoryNameException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepositoryImpl;
import com.ecommerce.util.ValidationUtil;

public class CategoryService {
	
	private final CategoryRepositoryImpl categoryRepository;
	
	public CategoryService (CategoryRepositoryImpl categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	private static final int MAX_CATEGORY_NAME_LENGTH = 50;
	
	// 1. Add a Category
	public Category addCategory(Category category) throws SQLException {
		
		// validate input -> cannot give category as null
		if (category == null) {
			throw new CategoryNotNullException("Category cannot be null!");
		}
		
		String categoryName = 
			ValidationUtil.validateAndTrim (
				category.getCategoryName(), 
				"Category Name", 
				MAX_CATEGORY_NAME_LENGTH, 
				InvalidCategoryNameException::new
			);

		// duplicate check
		if (categoryRepository.findByName(categoryName).isPresent()) {
			throw new CategoryAlreadyExistsException(
				"Category already exists : "
				+ categoryName
			);
		}
		
		// save the trimmed value
		category.setCategoryName(categoryName);
		
		// call to repository layer
		Long categoryId = categoryRepository.save(category);
		category.setCategoryId(categoryId);
		return category;
	}
	
	// 2. Get all Categories
	public List<Category> getAllCategories() throws SQLException {
		return categoryRepository.findAll();
	}
	
	// 3. Get Category by ID
	public Category getCategoryById(Long categoryId) throws CategoryNotFoundException, SQLException {
			
		ValidationUtil.validateId (
			categoryId, 
			"Category ID", 
			InvalidCategoryIdException::new
		);
		
		// call to repository layer
		return categoryRepository.findById(categoryId)
			.orElseThrow(() -> 
				new CategoryNotFoundException(
					"Category not found with ID: " 
					+ categoryId
				)
			);
	}
	
	// 4. Get Categories by keyword (Partial match, returns zero or more categories)
	public List<Category> searchCategories(String keyword) throws SQLException {
		String trimmedKeyword = 
			ValidationUtil.validateAndTrim (
				keyword, 
				"Search Keyword", 
				MAX_CATEGORY_NAME_LENGTH, 
				InvalidCategoryNameException::new
			);
		return categoryRepository.searchByName(trimmedKeyword);
	}
	
	// 5. Get Categories by Exact Name (returns at most one Category)
	public Category getCategoryByName(String name) throws CategoryNotFoundException, SQLException {
		
		String categoryName = 
			ValidationUtil.validateAndTrim (
				name, 
				"Category Name", 
				MAX_CATEGORY_NAME_LENGTH, 
				InvalidCategoryNameException::new
			);
		return categoryRepository.findByName(categoryName)
			.orElseThrow(() -> 
				new CategoryNotFoundException(
					"Category not found: "	
					+ categoryName
				)
			);
	}
	
	// Update Category Name
	public Category updateCategoryName(Long categoryId, String newName) throws CategoryNotFoundException, SQLException {
		
		ValidationUtil.validateId (
			categoryId, 
			"Category ID", 
			InvalidCategoryIdException::new
		);
		
		String categoryName =
		ValidationUtil.validateAndTrim (
			newName, 
			"Category Name", 
			MAX_CATEGORY_NAME_LENGTH, 
			InvalidCategoryNameException::new
		);
		
		Category category = getCategoryById(categoryId);
		
		// check whether another category has the same name with this updated category name
		Optional<Category> sameName = categoryRepository.findByName(categoryName);
		if (
			sameName.isPresent() 
			&& !sameName.get().getCategoryId().equals(categoryId) 
		) {
			throw new CategoryAlreadyExistsException("Category already exists: " + categoryName);
		}
		
		int rowsAffected = categoryRepository.updateName(categoryId, categoryName);
		if (rowsAffected != 1) {
	        // the category was deleted between our check and the update
	        throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
	    }
		
		category.setCategoryName(categoryName);
		return category;
	}
	
	// 7. Delete Category by ID
	public void deleteCategory(Long categoryId) throws SQLException {
		ValidationUtil.validateId (
			categoryId, 
			"Category ID", 
			InvalidCategoryIdException::new
		);
		
		int rowsAffected = categoryRepository.deleteById(categoryId);
		if (rowsAffected != 1) {
	        throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
	    }
	}
	
}
