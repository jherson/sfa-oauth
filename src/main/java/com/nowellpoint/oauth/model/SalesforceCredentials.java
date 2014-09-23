package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class SalesforceCredentials implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5719472610392020765L;

	private String username;
	
	private String password;
	
	private String securityToken;

	public SalesforceCredentials() {
		
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

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}