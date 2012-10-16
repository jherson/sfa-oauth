package com.sfa.qb.login.oauth;

import javax.security.auth.callback.Callback;

import com.sfa.qb.login.oauth.model.OAuth;

public class OAuthCallback implements Callback {
	
	private OAuth oauth;
	
	public OAuth getOauth() {
		return oauth;
	}
	
	public void setOauth(OAuth oauth) {
		this.oauth = oauth;
	}
}