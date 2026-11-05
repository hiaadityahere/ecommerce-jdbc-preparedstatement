package com.ecommerce.model;

import java.time.LocalDateTime;

public class User {

	private Long userId; // auto generated at DB-level
	private Role role;
	private String name;
	private String email;
	private String mobile;
	private String address;
	private LocalDateTime createdAt;
//	private String createdBy;
	private LocalDateTime updatedAt;
//	private String updatedBy;
//	private LocalDateTime deleteAt;
//	private String deletedBy;
	// two field is more required - Orders and Cart
	
	// constructor - save a user into DB
	public User (
		Role role,
		String name,
		String email,
		String mobile,
		String address
	) {
		this.role = role;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
	}
	
	// constructor - for user-details view from DB
	public User (
		Long userId,
		Role role,
		String name,
		String email,
		String mobile,
		String address,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
		this(role, name, email, mobile, address);
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Role getRole() {
		return role;
	}

	public String getMobile() {
		return mobile;
	}

	public String getAddress() {
		return address;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
