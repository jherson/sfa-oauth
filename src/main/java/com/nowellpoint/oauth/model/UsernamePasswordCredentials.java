package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class UsernamePasswordCredentials implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -297043038483117982L;

	private String username;

	private char[] password;

	public UsernamePasswordCredentials() {
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}
}