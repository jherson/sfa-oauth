package com.sfa.qb.login.oauth;

import javax.security.auth.callback.Callback;

public class OAuthConfigCallback implements Callback {
	
	private OAuthConfig oauthConfig;

	public OAuthConfig getOAuthConfig() {
		return oauthConfig;
	}

	public void setOAuthConfig(OAuthConfig oauthConfig) {
		this.oauthConfig = oauthConfig;
	}	
}