package com.ecommerce.repository;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.ecommerce.model.Brand;
import com.ecommerce.util.DBHelper;

public class BrandRepositoryImpl implements BrandRepository {

private static final Properties QUERIES = new Properties();
	
	static {
		try (
			InputStream input = 
				ProductRepositoryImpl.class
				.getClassLoader()
				.getResourceAsStream("brand_queries.properties")
			) {
			if (input == null) {
				throw new RuntimeException("Unable to find brand_queries.properties"); 
			}
			
			QUERIES.load(input);
		} catch (Exception e) {
			throw new ExceptionInInitializerError("Failed to load SQL queries: " + e.getMessage());
		}
	}
	
	// 1. CREATE
	@Override
	public Long save(Brand b) {
		String insertQuery = QUERIES.getProperty("brand.save");
		
		Long generatedId = 
			DBHelper.executeInsert (
				insertQuery,
				Long.class,
				b.getBrandName()
			);
		
		b.setBrandId(generatedId);
		return generatedId;
	}
	
	// 2. READ ALL
	@Override
	public List<Brand> findAll() {
		String findAllQuery = QUERIES.getProperty("brand.find.all");
		
		return DBHelper.executeQuery(findAllQuery, resultSet -> {
			List<Brand> brands = new ArrayList<>();
			while (resultSet.next()) {
				brands.add(mapRowToBrand(resultSet));
			}
			return brands;
		});
	}
	
	// 3. READ BY ID
	@Override
	public Optional<Brand> findById(Long brandId) {
		String findByIdQuery = QUERIES.getProperty("brand.find.by.id");
		
		return DBHelper.executeQuery(findByIdQuery, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToBrand(resultSet));
			}
			return Optional.<Brand>empty();
		}, brandId);
	}
	
	// 4. READ BY NAME (Partial match, returns zero or more brands)
	@Override
	public List<Brand> searchByName(String keyword) {
		String searchByNameQuery = QUERIES.getProperty("brand.search.by.name");
		
		return DBHelper.executeQuery(searchByNameQuery, resultSet -> {
			List<Brand> brands = new ArrayList<>();
			while (resultSet.next()) {
				brands.add(mapRowToBrand(resultSet));
			}
			return brands;
		}, "%" + keyword + "%");
	}
	
	// 5. READ BY EXACT NAME (returns at most one brand)
	@Override
	public Optional<Brand> findByName(String brandName) {
		String findByNameQuery = QUERIES.getProperty("brand.find.by.name");
		
		return DBHelper.executeQuery(findByNameQuery, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToBrand(resultSet));
			}
			return Optional.<Brand>empty();
		}, brandName);
	}
	
	// 6. UPDATE NAME
	@Override
	public int updateName(Long brandId, String newName) {
		String updateNameQuery = QUERIES.getProperty("brand.update.name");
		
		return DBHelper.executeUpdate(updateNameQuery, newName, brandId);
	}
	
	// 7. DELETE BY ID
	@Override
	public int deleteById(Long brandId) {
		String deleteByIdQuery = QUERIES.getProperty("brand.delete.by.id");
		return DBHelper.executeUpdate(deleteByIdQuery, brandId);
	}
	
	/* ===== HELPER METHOD ===== */
	private Brand mapRowToBrand(ResultSet set) throws SQLException {
		return new Brand(
			set.getLong("brand_id"),
			set.getString("brand_name"),
			set.getTimestamp("created_at").toLocalDateTime(),
			set.getTimestamp("updated_at").toLocalDateTime()
		);
	}
	
}
