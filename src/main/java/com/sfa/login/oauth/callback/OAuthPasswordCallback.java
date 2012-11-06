package com.sfa.login.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthPasswordCallback implements Callback, Serializable {

	private static final long serialVersionUID = 1311717852522152761L;
	
	private String password;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}