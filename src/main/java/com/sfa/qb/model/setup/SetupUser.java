package com.sfa.qb.model.setup;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named

public class SetupUser implements Serializable {

	private static final long serialVersionUID = 396475020512197356L;

	private String instance;
	
	private String username;
	
	private String password;
	
	private String securityToken;
	
	private String status;
	

	public String getInstance() {
		return instance;
	}
	
	public void setInstance(String instance) {
		this.instance = instance;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}	
}