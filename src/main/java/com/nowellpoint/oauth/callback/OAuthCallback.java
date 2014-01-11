package com.nowellpoint.oauth.callback;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class OAuthCallback implements Callback, Serializable {

	private static final long serialVersionUID = 7612224786750192425L;

	private OAuthFlowType flowType;
	private String code;
	private String refreshToken;
	private String username;
	private String password;

	public OAuthCallback() {
		
	}
	

	public OAuthFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(OAuthFlowType flowType) {
		this.flowType = flowType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}