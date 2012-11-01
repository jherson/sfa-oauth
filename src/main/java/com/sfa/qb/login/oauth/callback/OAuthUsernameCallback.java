package com.sfa.qb.login.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthUsernameCallback implements Callback, Serializable {

	private static final long serialVersionUID = 1311717852522152761L;
	
	private String username;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}