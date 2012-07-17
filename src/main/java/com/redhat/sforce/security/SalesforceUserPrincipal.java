package com.redhat.sforce.security;

import java.io.Serializable;
import java.security.Principal;

public class SalesforceUserPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 877826587533809602L;
	private String name;
	private String sessionId;
	
	public SalesforceUserPrincipal(String name, String sessionId) {
		this.name = name;
		this.sessionId = sessionId;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String getSessionId() {
		return sessionId;
	}
}