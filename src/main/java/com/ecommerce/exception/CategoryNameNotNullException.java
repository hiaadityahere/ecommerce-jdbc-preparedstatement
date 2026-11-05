package com.ecommerce.exception;

public class CategoryNameNotNullException extends RuntimeException {
	public CategoryNameNotNullException (String message) {
		super(message);
	}
}
