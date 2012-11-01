package com.sfa.qb.login.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthUserNameCallback implements Callback, Serializable {

	private static final long serialVersionUID = 1311717852522152761L;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}