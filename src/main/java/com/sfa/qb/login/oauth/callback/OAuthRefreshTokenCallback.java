package com.sfa.qb.login.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthRefreshTokenCallback implements Callback, Serializable {

	private static final long serialVersionUID = 1311717852522152761L;
	
	private String refreshToken;
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}