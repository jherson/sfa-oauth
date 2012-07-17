package com.redhat.sforce.security;

import java.io.Serializable;
import java.security.Principal;

public class SalesforceRolePrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 877826587533809602L;
	private String name;
	
	public SalesforceRolePrincipal(String name, String sessionId) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}