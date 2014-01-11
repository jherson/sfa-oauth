package com.nowellpoint.oauth;

import java.io.Serializable;

import javax.security.auth.login.LoginException;

import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.request.OAuthClientRequest;

public abstract class ServiceProvider implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5650578742390926431L;
	
	/**
	 * getAuthorizeEndpoint
	 * @return String
	 */
	
	public abstract String getAuthEndpoint();
	
	/**
	 * requestToken
	 * @param authorizationRequest
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token requestToken(OAuthClientRequest.BasicAuthorizationRequest authorizationRequest) throws LoginException;
	
	/**
	 * requestToken
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param code
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token requestToken(String code) throws LoginException;
	
	/**
	 * getIdentity 
	 * @param accessToken
	 * @return Identity
	 * @throws LoginException
	 */
	
	public abstract Identity getIdentity(String identityUrl, String accessToken) throws LoginException;
	
	/**
	 * refreshToken
	 * @param clientId
	 * @param clientSecret
	 * @param accessToken
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token refreshToken(String accessToken) throws LoginException;
	
	/**
	 * revokeToken
	 * @param accessToken
	 * @throws LoginException
	 */
	
	public abstract void revokeToken(String accessToken) throws LoginException;
}