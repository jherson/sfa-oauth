package com.redhat.sforce.qb.exception;

public class SforceServiceException extends Exception {

	private static final long serialVersionUID = -7458264398759679642L;
	
	public SforceServiceException(String message) {
		super(message);
	}
	
	public SforceServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}