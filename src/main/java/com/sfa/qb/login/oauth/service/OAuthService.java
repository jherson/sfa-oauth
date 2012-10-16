package com.sfa.qb.login.oauth.service;

import javax.security.auth.login.LoginException;

public interface OAuthService {

	public String getAuthResponse(String code) throws LoginException;
	public String getIdentity(String instanceUrl, String id, String accessToken) throws LoginException;
	public void revokeToken(String accessToken) throws LoginException;	
	public String refreshAuthToken(String accessToken) throws LoginException;
}