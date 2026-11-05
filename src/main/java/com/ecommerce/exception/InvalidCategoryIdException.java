package com.ecommerce.exception;

public class InvalidCategoryIdException extends RuntimeException {

	public InvalidCategoryIdException(String message) {
		super(message);
	}
}
