package com.nowellpoint.oauth;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;

public class AuthenticationContext implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8030968908682514184L;
	
	/**
	 * 
	 */
	
	private HttpServletResponse response;
	
	/**
	 * 
	 */
	
	private HttpServletRequest request;
	
	/**
	 * 
	 */
	
	private Token token;
	
	/**
	 * 
	 */
	
	private Identity identity;
	
	public AuthenticationContext() {
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
}