package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class Credentials implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -6706474981160907114L;
	private String username;
	private String password; 
	
	public Credentials() {
		
	}
	
	public Credentials(String username, String password) {
		setUsername(username);
		setPassword(password);
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