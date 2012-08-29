package com.sfa.qb.exception;

public class QueryException extends Exception {

	private static final long serialVersionUID = -833923640845837320L; 
	
	public QueryException(String message) {
		super(message);
	}

	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueryException(Throwable cause) {
		super(cause);
	}
}
