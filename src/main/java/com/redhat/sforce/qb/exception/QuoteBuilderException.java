package com.redhat.sforce.qb.exception;

public class QuoteBuilderException extends Exception {

	private static final long serialVersionUID = -7458264398759679642L;
	
	public QuoteBuilderException(String message) {
		super(message);
	}
	
	public QuoteBuilderException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public QuoteBuilderException(Throwable cause) {
		super(cause);
	}	
}