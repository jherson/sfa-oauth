package com.nowellpoint.oauth;

import java.io.Serializable;

import javax.security.auth.login.LoginException;

import com.nowellpoint.oauth.client.OAuthClientRequest;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.client.ServiceProviderOptions;

public abstract class OAuthServiceProvider implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5650578742390926431L;
	
	/**
	 * 
	 */
	
	private ServiceProviderOptions options;
	
	/**
	 * setServiceProviderOptions
	 * @param options
	 */
	
	public void setServiceProviderOptions(ServiceProviderOptions options) {
		this.options = options;
	}
	
	/**
	 * getServiceProviderOptions
	 * @return
	 */
	
	public ServiceProviderOptions getServiceProviderOptions() {
		return options;
	}
	
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
	
	public abstract Token requestToken(OAuthClientRequest.BasicTokenRequest basicTokenRequest) throws OAuthException;
	
	/**
	 * requestToken
	 * @param verifyTokenRequest
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token requestToken(OAuthClientRequest.VerifyTokenRequest verifyTokenRequest) throws OAuthException;
	
	/**
	 * getIdentity 
	 * @param identityRequest
	 * @return Identity
	 * @throws LoginException
	 */
	
	public abstract Identity getIdentity(OAuthClientRequest.IdentityRequest identityRequest) throws OAuthException;
	
	/**
	 * refreshToken
	 * @param refreshTokenRequest
	 * @return Token
	 * @throws LoginException
	 */
	
	public abstract Token refreshToken(OAuthClientRequest.RefreshTokenRequest refreshTokenRequest) throws OAuthException;
	
	/**
	 * revokeToken
	 * @param revokeTokenRequest
	 * @throws LoginException
	 */
	
	public abstract void revokeToken(OAuthClientRequest.RevokeTokenRequest revokeTokenRequest) throws OAuthException;
}