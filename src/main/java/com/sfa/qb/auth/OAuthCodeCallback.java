package com.sfa.qb.auth;

import javax.security.auth.callback.Callback;

public class OAuthCodeCallback implements Callback {
	
	private String code;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}