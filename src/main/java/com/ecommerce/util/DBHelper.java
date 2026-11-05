/*
 * WHY DBHelper ?
 * ANSWER -> 
 *	1. DBHelper class will save time to not write try with resources
 * 		every time in repository level
 * 	2. In this project there is only one-one method for all DB queries, such as-
 * 		CRUD, Read-All etc.
 * 	============================================================================
 * WHY GENERICS (<T>) and Functional Interface (Lambdas) ?
 * ANSWER ->
 * 	 In JDBC, the database handles commands differently:
 * 		1. UPDATE / DELETE: 
 * 			Uses statement.executeUpdate() 
 * 				-> returns an int (rows affected)
 * 		2. INSERT: 
 * 			Uses statement.executeUpdate(..., RETURN_GENERATED_KEYS)
 * 				-> returns a generated key (Long, Integer, String, etc.)
 * 		3. SELECT: Uses statement.executeQuery()
 * 				-> returns a ResultSet which must be converted into 
 * 				an object (Product, Category, List<Product>, BigDecimal, etc.).
 * 
 * 		To make DBHelper return anything cleanly and type-safely, we provide two generic methods:
 *		- One for returning any single value / generated key (Class<T>).
 *		- One for returning any query result (ResultSetHandler<T>).
 */

package com.ecommerce.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecommerce.connection.DBConnection;
import com.ecommerce.exception.DataAccessException;

public final class DBHelper {

	private DBHelper() {
		// Utility class; do not instantiate.
	}
	
	/* ===== FUNCTIONAL INTERFACE ===== 
	* caller method will decide what to return from a ResultSet
	*/
	@FunctionalInterface
	public interface ResultSetHandler<T> {
		T handle(ResultSet set) throws SQLException;
	}
	
	/* ===== Generic Query ===== 
	 * return anything (Product, List<Product>, String, etc.)
	 */
	public static <T> T executeQuery(String sqlQuery, ResultSetHandler<T>handler, Object...parameters) {
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery)
		) {
			setParameters(statement, parameters);
			
			try (ResultSet set = statement.executeQuery()) {
				return handler.handle(set);
			}
		}  catch (SQLException e) {
			throw new DataAccessException("Query failed: " + sqlQuery, e);
		}
	}
	
	/* ===== Generic Insert ===== 
	 * return any key type (Long, Integer, String, etc.)
	 */
	public static <T> T executeInsert(String sqlQuery, Class<T> keyType, Object...parameters) {
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = 
				connection.prepareStatement(
					sqlQuery,
					Statement.RETURN_GENERATED_KEYS
				)
		) {
			setParameters(statement, parameters);
			statement.executeUpdate();
			
			try (ResultSet set = statement.getGeneratedKeys()) {
				if (set.next()) {
					return set.getObject(1, keyType);
				}
				throw new SQLException("Insert succeeded, but no generated key was returned.");
			}
		}  catch (SQLException e) {
	        throw new DataAccessException("Insert failed: " + sqlQuery, e);
	    }
	}
	
	/* ===== Update ===== 
	 * return int (rows affected)
	 */
	public static int executeUpdate(String sqlQuery, Object...parameters) {
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery)
		) {
			setParameters(statement, parameters);
			return statement.executeUpdate();
		}  catch (SQLException e) {
	        throw new DataAccessException("Update failed: " + sqlQuery, e);
	    }
	}

	private static void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {

		for (int i = 0; i < parameters.length; i++) {
			statement.setObject(i + 1, parameters[i]);
		}
	}
}