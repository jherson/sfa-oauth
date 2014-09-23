package com.nowellpoint.oauth.model;

import java.io.Serializable;

import org.picketlink.idm.credential.AbstractBaseCredentials;

public class TokenCredential extends AbstractBaseCredentials implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5449940226075599228L;
	
	/**
	 * 
	 */
	
	private String token;
	
	public TokenCredential() {
		
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public void invalidate() {
		setToken(null);
	}
}