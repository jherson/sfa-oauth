package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class Credentials implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -6706474981160907114L;
	private String username;
	private String password; 
	private String securityToken;
	
	public Credentials(String username, String password, String securityToken) {
		this.username = username;
		this.password = password;
		this.securityToken = securityToken;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getSecurityToken() {
		return securityToken;
	}
}