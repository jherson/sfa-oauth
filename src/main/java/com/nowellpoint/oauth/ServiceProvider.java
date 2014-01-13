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
	 * @param basicTokenRequest
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token requestToken(OAuthClientRequest.BasicTokenRequest basicTokenRequest) throws LoginException;
	
	/**
	 * requestToken
	 * @param verifyTokenRequest
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token requestToken(OAuthClientRequest.VerifyTokenRequest verifyTokenRequest) throws LoginException;
	
	/**
	 * getIdentity 
	 * @param identityRequest
	 * @return Identity
	 * @throws LoginException
	 */
	
	public abstract Identity getIdentity(OAuthClientRequest.IdentityRequest identityRequest) throws LoginException;
	
	/**
	 * refreshToken
	 * @param refreshTokenRequest
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token refreshToken(OAuthClientRequest.RefreshTokenRequest refreshTokenRequest) throws LoginException;
	
	/**
	 * revokeToken
	 * @param revokeTokenRequest
	 * @throws LoginException
	 */
	
	public abstract void revokeToken(OAuthClientRequest.RevokeTokenRequest revokeTokenRequest) throws LoginException;
}