package com.nowellpoint.oauth.exception;

public class OAuthException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -2369287148480580693L;
	
	public OAuthException(Exception exception) {
		super(exception);
	}
	
	public OAuthException(String error) {
		super(error);
	}
}