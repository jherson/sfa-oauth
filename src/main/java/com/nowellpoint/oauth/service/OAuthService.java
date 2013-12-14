package com.nowellpoint.oauth.service;

import javax.security.auth.login.LoginException;

public interface OAuthService {

	/**
	 * getAuthResponse
	 * @param tokenUrl
	 * @param clientId
	 * @param clientSecret
	 * @param username
	 * @param password
	 * @param securityToken
	 * @return String
	 * @throws LoginException
	 */
	
	String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException;
	
	/**
	 * getAuthResponse
	 * @param tokenUrl
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param code
	 * @return String
	 * @throws LoginException
	 */
	
	String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException;
	
	/**
	 * getIdentity
	 * @param identityUrl
	 * @param accessToken
	 * @return String
	 * @throws LoginException
	 */
	
	String getIdentity(String identityUrl, String accessToken) throws LoginException;
	
	/**
	 * refreshToken
	 * @param tokenUrl
	 * @param clientId
	 * @param clientSecret
	 * @param accessToken
	 * @return String
	 * @throws LoginException
	 */
	
	String refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException;
	
	/**
	 * revokeToken
	 * @param revokeUrl
	 * @param accessToken
	 * @throws LoginException
	 */
	
	void revokeToken(String revokeUrl, String accessToken) throws LoginException;
}