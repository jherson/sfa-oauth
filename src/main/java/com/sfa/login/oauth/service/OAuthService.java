package com.sfa.login.oauth.service;

import javax.security.auth.login.LoginException;

public interface OAuthService {

	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException;
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException;
	public String getIdentity(String instanceUrl, String identityUrl, String accessToken) throws LoginException;	
	public String refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException;	
	public String getAuthResponse(String tokenUrl, String clientId, String redirectUrl) throws LoginException;
	public void revokeToken(String revokeUrl, String accessToken) throws LoginException;
}
