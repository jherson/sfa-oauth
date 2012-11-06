package com.sfa.login.oauth.service;

import javax.security.auth.login.LoginException;

public interface OAuthService {

	public String getAuthResponse(String instance, String clientId, String clientSecret, String redirectUri, String code) throws LoginException;
	public String getIdentity(String instanceUrl, String id, String accessToken) throws LoginException;
	public void revokeToken(String instance, String accessToken) throws LoginException;	
	public String refreshAuthToken(String instance, String clientId, String clientSecret, String accessToken) throws LoginException;
}