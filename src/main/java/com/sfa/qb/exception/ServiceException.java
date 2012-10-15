package com.sfa.qb.exception;

import org.jboss.resteasy.client.ClientResponse;

public class ServiceException extends Exception {
	
	private static final long serialVersionUID = -6616343924395661579L;		
	private String errorCode;
	private String message;
		
	public ServiceException(Exception exception) {
		super(exception);
	}
	
	public ServiceException(ClientResponse<String> response) {
		this.errorCode = response.getResponseStatus().getReasonPhrase();
		this.message = response.getEntity();
	}
			
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessage() {
		return message;
	}	
}