package com.sfa.qb.exception;

public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = -7458264398759679642L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}
}