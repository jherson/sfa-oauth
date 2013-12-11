package com.nowellpoint.principal;

import java.io.Serializable;
import java.security.Principal;

import com.nowellpoint.oauth.model.Identity;

public class IdentityPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 3590685957273523697L;
	
	private Identity identity;
	
	public IdentityPrincipal(Identity identity) {
		this.identity = identity;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Identity getIdentity() {
		return identity;
	}
}