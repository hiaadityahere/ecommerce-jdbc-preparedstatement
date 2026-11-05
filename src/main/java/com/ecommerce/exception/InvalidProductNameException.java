package com.ecommerce.exception;

public class InvalidProductNameException extends RuntimeException {
	
	public InvalidProductNameException(String message) {
		super(message);
	}
}
