package com.nowellpoint.oauth.http;

public class HttpException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 646390471929895078L;

	public HttpException(Exception exception) {
		super(exception);
	}
	
	public HttpException(String exception) {
		super(exception);
	}
}