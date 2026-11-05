package com.ecommerce.repository;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.ecommerce.model.Category;
import com.ecommerce.util.DBHelper;

public class CategoryRepositoryImpl implements CategoryRepository {
	
	private static final Properties QUERIES = new Properties();
	
	static {
		try (
			InputStream input = 
				ProductRepositoryImpl.class
				.getClassLoader()
				.getResourceAsStream("category_queries.properties")
			) {
			if (input == null) {
				throw new RuntimeException("Unable to find category_queries.properties"); 
			}
			
			QUERIES.load(input);
		} catch (Exception e) {
			throw new ExceptionInInitializerError("Failed to load SQL queries: " + e.getMessage());
		}
	}
	
	// 1. CREATE -> INSERT A CATEGORY
	@Override
	public Long save(Category c) {
		String insertQuery = QUERIES.getProperty("category.save");
		
		Long generatedId = 
			DBHelper.executeInsert (
				insertQuery,
				Long.class,
				c.getCategoryName()
			);
		
		c.setCategoryId(generatedId);
		return generatedId;
	}
	

	// 2. READ-ALL
	@Override
	public List<Category> findAll() {
		String findAllQuery = QUERIES.getProperty("category.find.all");
		
		return DBHelper.executeQuery(findAllQuery, resultSet -> {
			List<Category> categories = new ArrayList<>();
			while (resultSet.next()) {
				categories.add(mapRowToCategory(resultSet));
			}
			return categories;
		});
	}

	
	// 3. READ BY ID
	@Override
	public Optional<Category> findById(Long categoryId) {
		String findByIdQuery = QUERIES.getProperty("category.find.by.id");
		
		return DBHelper.executeQuery(findByIdQuery, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToCategory(resultSet));
			}
			return Optional.<Category>empty();
		}, categoryId);
	}
	
	// 4. READ BY NAME (Partial match, returns zero or more categories)
	@Override
	public List<Category> searchByName(String keyword) {
		String searchByNameQuery = QUERIES.getProperty("category.search.by.name");
		
		return DBHelper.executeQuery(searchByNameQuery, resultSet -> {
			List<Category> categories = new ArrayList<>();
			while (resultSet.next()) {
				categories.add(mapRowToCategory(resultSet));
			}
			return categories;
		}, "%" + keyword + "%");
	}
	
	// 5. READ BY EXACT NAME (returns at most one category)
	@Override
	public Optional<Category> findByName(String categoryName) {
		String findByNameQuery = QUERIES.getProperty("category.find.by.name");
		
		return DBHelper.executeQuery(findByNameQuery, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToCategory(resultSet));
			}
			return Optional.<Category>empty();
		}, categoryName);
	}
	
	// 6. UPDATE NAME
	@Override
	public int updateName(Long categoryId, String newName) {
		String updateNameQuery = QUERIES.getProperty("category.update.name");
		
		return DBHelper.executeUpdate(updateNameQuery, newName, categoryId);
	}
	
	// 7. DELETE BY ID
	@Override
	public int deleteById(Long categoryId) {
		String deleteByIdQuery = QUERIES.getProperty("category.delete.by.id");
		return DBHelper.executeUpdate(deleteByIdQuery, categoryId);
	}
	
	/* ===== HELPER METHOD ===== */
	private Category mapRowToCategory(ResultSet set) throws SQLException {
		return new Category(
			set.getLong("category_id"),
			set.getString("category_name"),
			set.getTimestamp("created_at").toLocalDateTime(),
			set.getTimestamp("updated_at").toLocalDateTime()
		);
	}
	
}
