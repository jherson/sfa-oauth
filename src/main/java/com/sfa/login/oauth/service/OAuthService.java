package com.sfa.login.oauth.service;

import javax.security.auth.login.LoginException;

public interface OAuthService {

	String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException;
	String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException;
	String getAuthResponse(String tokenUrl, String clientId, String redirectUri) throws LoginException;
	String getIdentity(String tokenUrl, String id, String accessToken) throws LoginException;	
	String refreshAuthToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException;	
	void revokeToken(String tokenUrl, String accessToken) throws LoginException;
}