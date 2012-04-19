package com.redhat.sforce.qb.exception;

public class SalesforceServiceException extends Exception {
	private static final long serialVersionUID = -6616343924395661579L;

	public SalesforceServiceException(Exception exception) {
		super(exception);
	}
	
	public SalesforceServiceException(String error) {
		super(error);
	}

	public SalesforceServiceException(String errorCode, String message) {
		super(errorCode, new Throwable(message));
	}
}