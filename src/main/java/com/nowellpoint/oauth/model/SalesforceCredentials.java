package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class SalesforceCredentials extends UsernamePasswordCredentials implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 5719472610392020765L;

	private String securityToken;

	public SalesforceCredentials() {
		super();
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}