package com.nowellpoint.oauth.service;

import javax.security.auth.login.LoginException;

import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.UserInfo;

public interface OAuthService {

	/**
	 * authorize
	 * @param tokenUrl
	 * @param clientId
	 * @param clientSecret
	 * @param username
	 * @param password
	 * @param securityToken
	 * @return Token
	 * @throws LoginException
	 */
	
	Token authorize(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException;
	
	/**
	 * authorize
	 * @param tokenUrl
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param code
	 * @return Token
	 * @throws LoginException
	 */
		
	Token authorize(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException;
	
	/**
	 * getIdentity
	 * @param identityUrl
	 * @param accessToken
	 * @return Identity
	 * @throws LoginException
	 */
	
	Identity getIdentity(String identityUrl, String accessToken) throws LoginException;
	
	/**
	 * refreshToken
	 * @param tokenUrl
	 * @param clientId
	 * @param clientSecret
	 * @param accessToken
	 * @return Token
	 * @throws LoginException
	 */
	
	Token refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException;
	
	/**
	 * revokeToken
	 * @param revokeUrl
	 * @param accessToken
	 * @throws LoginException
	 */
	
	void revokeToken(String revokeUrl, String accessToken) throws LoginException;
	
	/**
	 * getUserInfo
	 * @param sobjectUrl
	 * @param accessToken
	 * @return UserInfo
	 * @throws LoginException
	 */
	
	UserInfo getUserInfo(Token token, Identity identity) throws LoginException;
	
	/**
	 * getOrganizationInfo
	 * @param sobjectUrl
	 * @param accessToken
	 * @return OrganizationInfo
	 * @throws LoginException
	 */
	
	OrganizationInfo getOrganizationInfo(Token token, Identity identity) throws LoginException;
}