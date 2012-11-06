package com.sfa.login.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthSecurityTokenCallback implements Callback, Serializable {

	private static final long serialVersionUID = 1311717852522152761L;
	
	private String securityToken;
	
	public String getRefreshToken() {
		return securityToken;
	}
	
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}