package com.ecommerce.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.connection.DBConnection;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;

public class UserRepository {

	// CREATE -> INSERT A CUSTOMER
	public int save(User c) throws SQLException {
		String insertQuery = 
			"INSERT INTO customer "
			+ "(name, email, mobile, address) "
			+ "VALUES (?, ?, ?, ?)";
		
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);
		) {
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getEmail());
			pstmt.setString(3, c.getMobile());
			pstmt.setString(4, c.getAddress());
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected;
		}
	}
	
	// READ-ALL
	public List<User> findAll() throws SQLException {
		String readAllQuery = 
			"SELECT * FROM customer";
		
		List<User> users = new ArrayList<>();
		
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(readAllQuery);
			ResultSet set = pstmt.executeQuery();
		) {
			while(set.next()) {
				User u = new User(
					set.getLong("user_id"),
					Role.valueOf(set.getString("role")),
					set.getString("name"),
					set.getString("email"),
					set.getString("mobile"),
					set.getString("address"),
					set.getTimestamp("created_at").toLocalDateTime(),
					set.getTimestamp("updated_at").toLocalDateTime()
				);
				users.add(u);
			}
		}
		return users;
	}
	
	// READ/SEARCH BY ID
	public User findById(Long userId) throws SQLException {
		String readByIdQuery = 
			"SELECT * FROM user "
			+ "WHERE id = ?";
		
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(readByIdQuery);
		) {
			pstmt.setLong(1, userId);
			
			try (ResultSet set = pstmt.executeQuery()) {
				if (set.next()) {
					User u = new User(
						set.getLong("user_id"),
						Role.valueOf(set.getString("role")),
						set.getString("name"),
						set.getString("email"),
						set.getString("mobile"),
						set.getString("address"),
						set.getTimestamp("created_at").toLocalDateTime(),
						set.getTimestamp("updated_at").toLocalDateTime()
					);
					return u;
				}
				return null;
			}
		}
	}
	
	// UPDATE ADDRESS
	public int updateAddress(Long userId, String newAddress) throws SQLException {
		String updateAddressQuery = 
			"UPDATE user SET address = ? "
			+ "WHERE user_id = ?";
		
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(updateAddressQuery);
		) {
			pstmt.setString(1,  updateAddressQuery);
			pstmt.setLong(2, userId);
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected;
		}
	}
	
	// UPDATE NAME
	public int updateName(Long userId, String newName) throws SQLException {
		
		String updateAddressQuery = 
			"UPDATE user SET name = ? "
			+ "WHERE user_id = ?";
		
		try (
			Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(updateAddressQuery);
		) {
			pstmt.setString(1,  newName);
			pstmt.setLong(2, userId);
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected;
		}
	}
	
}