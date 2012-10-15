package com.sfa.qb.auth;

import java.io.Serializable;
import java.security.Principal;

import com.sfa.qb.model.auth.OAuth;

public class OAuthPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 3590685957273523697L;
	
	private OAuth oauth;
	
	public OAuthPrincipal(OAuth oauth) {
		this.oauth = oauth;
	}

	@Override
	public String getName() {
		return oauth.getIdentity().getUsername();
	}
	
	public OAuth getOAuth() {
		return oauth;
	}
}