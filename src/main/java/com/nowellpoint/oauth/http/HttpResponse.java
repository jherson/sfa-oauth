package com.nowellpoint.oauth.http;

import java.io.Serializable;

public class HttpResponse implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5837099327881770370L;
	

	private int statusCode;
	
	private String entity;
	
	public HttpResponse(int statusCode, String entity) {
		this.statusCode = statusCode;
		this.entity = entity;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getEntity() {
		return entity;
	}
}