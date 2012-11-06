package com.sfa.login.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthCodeCallback implements Callback, Serializable {

	private static final long serialVersionUID = 1311717852522152761L;
	
	private String code;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}