package com.ecommerce.repository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.ecommerce.model.Product;
import com.ecommerce.util.DBHelper;

public class ProductRepositoryImpl implements ProductRepository {

	private static final Properties QUERIES = new Properties();
	
	static {
		try (
			InputStream input = 
				ProductRepositoryImpl.class
				.getClassLoader()
				.getResourceAsStream("product_queries.properties")
			) {
			if (input == null) {
				throw new RuntimeException("Unable to find product_queries.properties"); 
			}
			
			QUERIES.load(input);
		} catch (Exception e) {
			throw new ExceptionInInitializerError("Failed to load SQL queries: " + e.getMessage());
		}
	}
	
	// CREATE -> INSERT A PRODUCT
	@Override
	public Long save(Product p) {
		String insertQuery = QUERIES.getProperty("product.save");

		Long generatedId = 
			DBHelper.executeInsert (
				insertQuery,
				Long.class,
				p.getProductName(),
				p.getPrice(),
				p.getCategoryId(),
				p.getBrandId(),
				p.getStockQuantity(),
				p.getIsActive()
			);
		
		p.setProductId(generatedId);
		return generatedId;
	}
	
	// READ-ALL
	@Override
	public List<Product> findAll() {
		String findAllQuery = QUERIES.getProperty("product.find.all");
		
		return DBHelper.executeQuery(findAllQuery, resultSet -> {
			List<Product> products = new ArrayList<>();
			while(resultSet.next()) {
				products.add(mapRowToProduct(resultSet));
			}
			return products;
		});
	}
	
	// READ ALL ACTIVE
	@Override
	public List<Product> findAllActive() {
	    String sql = QUERIES.getProperty("product.find.all.active");

	    return DBHelper.executeQuery(sql, resultSet -> {
	        List<Product> products = new ArrayList<>();
	        while (resultSet.next()) {
	            products.add(mapRowToProduct(resultSet));
	        }
	        return products;
	    });
	}

	// READ BY ID
	@Override
	public Optional<Product> findById(Long productId) {
		String findByIdQuery = QUERIES.getProperty("product.find.by.id");
		
		return DBHelper.executeQuery(findByIdQuery, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToProduct(resultSet));
			}
			return Optional.<Product>empty();
		}, productId);
	}

	// READ-BY NAME  (Partial match, returns zero or more products)
	@Override
	public List<Product> searchByName(String keyword) {
		String searchByNameQuery = QUERIES.getProperty("product.search.by.name");
		
		return DBHelper.executeQuery(searchByNameQuery, resultSet -> {
			List<Product> products = new ArrayList<>();
			while(resultSet.next()) {
				products.add(mapRowToProduct(resultSet));
			}
			return products;
		}, "%" + keyword + "%");
	}

	// READ BY EXACT NAME (returns at most one Product)
	@Override
	public Optional<Product> findByName(String productName) {
		String findByNameQuery = QUERIES.getProperty("product.find.by.name");
		
		return DBHelper.executeQuery(findByNameQuery, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToProduct(resultSet));
			}
			return Optional.<Product>empty();
		}, productName);
	}
	
	// READ-BY CATEGORY
	@Override
	public List<Product> findByCategory(String categoryName) {
		String findByCategoryQuery = QUERIES.getProperty("product.find.by.category");
		
		return DBHelper.executeQuery(findByCategoryQuery, resultSet -> {
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				products.add(mapRowToProduct(resultSet));
			}
			return products;
		}, categoryName);
	}

	// READ-BY MINIMUM PRICE (no upper limit)
	@Override
	public List<Product> findByMinPrice(BigDecimal minPrice) {
	    String sql = QUERIES.getProperty("product.find.by.min.price");

	    return DBHelper.executeQuery(sql, resultSet -> {
	        List<Product> products = new ArrayList<>();
	        while (resultSet.next()) {
	            products.add(mapRowToProduct(resultSet));
	        }
	        return products;
	    }, minPrice);
	}
	
	// READ-BY PRICE RANGE
	@Override
	public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
		String findByPriceRangeQuery = QUERIES.getProperty("product.find.by.price.range");
		
		return DBHelper.executeQuery(findByPriceRangeQuery, resultSet -> {
			List<Product> products = new ArrayList<>();
			while(resultSet.next()) {
				products.add(mapRowToProduct(resultSet));
			}
			return products;
		}, minPrice, maxPrice);
	}
	
	// UPDATE NAME
	@Override
	public int updateName(Long productId, String newProductName) {
		String updateProductNameQuery = QUERIES.getProperty("product.update.name");
		
		return DBHelper.executeUpdate(updateProductNameQuery, newProductName, productId);
	}

	// UPDATE CATEGORY
	@Override
	public int updateCategory(Long productId, Long categoryId) {
		String updateProductCategoryQuery = QUERIES.getProperty("product.update.category");
		
		return DBHelper.executeUpdate(updateProductCategoryQuery, productId, categoryId);
	}
	
	// UPDATE PRICE
	@Override
	public int updatePrice(Long productId, BigDecimal newPrice) {
		String updatePriceQuery = QUERIES.getProperty("product.update.price");
		
		return DBHelper.executeUpdate(updatePriceQuery, productId, newPrice);
	}

	// DELETE BY ID
	@Override
	public int deleteById(Long productId) {
		String deletebyIdQuery = QUERIES.getProperty("product.delete.by.id");
		
		return DBHelper.executeUpdate(deletebyIdQuery, deletebyIdQuery);
	}
	
	/* ===== HELPER METHOD ===== */
	private Product mapRowToProduct(ResultSet set) throws SQLException {
		return new Product(
			set.getLong("product_id"),
			set.getString("product_name"),
			set.getBigDecimal("price"),
			set.getLong("category_id"),
			set.getLong("brand_id"),
			set.getInt("stock_quantity"),
			set.getBoolean("is_active"),
			set.getTimestamp("created_at").toLocalDateTime(),
			set.getTimestamp("updated_at").toLocalDateTime()
		);
	}

}
