package com.sfa.qb.auth;

import javax.security.auth.callback.Callback;

import com.sfa.qb.model.auth.OAuth;

public class OAuthCallback implements Callback {
	
	private OAuth oauth;
	
	public OAuth getOauth() {
		return oauth;
	}
	
	public void setOauth(OAuth oauth) {
		this.oauth = oauth;
	}
}