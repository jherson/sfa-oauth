package com.nowellpoint.oauth;

import java.io.Serializable;

import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginException;

import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;

public abstract class ServiceProvider implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5650578742390926431L;
	
	/**
	 * setConfiguration
	 * @param oauthConfig
	 */
	
	public void setConfiguration(OAuthConfig oauthConfig) {
		Configuration.setConfiguration(oauthConfig);
	}
	
	/**
	 * getConfiguration
	 * @return OAuthConfig
	 */
	
	public OAuthConfig getConfiguration() {
		return (OAuthConfig) Configuration.getConfiguration();
	}
	
	/**
	 * getAuthEndpoint
	 * @return String
	 */
	
	public abstract String getAuthEndpoint();
	
	/**
	 * login
	 * @param clientId
	 * @param clientSecret
	 * @param username
	 * @param password
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token login(String clientId, String clientSecret, String username, String password) throws LoginException;

	/**
	 * requestToken
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param code
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token requestToken(String clientId, String clientSecret, String redirectUri, String code) throws LoginException;
	
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
	
	public abstract Token refreshToken(String clientId, String clientSecret, String accessToken) throws LoginException;
	
	/**
	 * revokeToken
	 * @param accessToken
	 * @throws LoginException
	 */
	
	public abstract void revokeToken(String accessToken) throws LoginException;
}