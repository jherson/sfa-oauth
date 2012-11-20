package com.sfa.login.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class OAuthCallbackHandler implements CallbackHandler, Serializable {

	private static final long serialVersionUID = 1173111272806803501L;
	
	private String flowType;
	private String instance; 
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String code;
	private String refreshToken;
	private String username;
	private String password;
	private String securityToken;

	public OAuthCallbackHandler(
			String flowType,
			String instance, 
			String clientId, 
			String clientSecret, 
			String redirectUri, 
			String code, 
			String refreshToken, 
			String username, 
			String password, 
			String securityToken) {
		
		this.flowType = flowType;
		this.instance = instance;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.code = code;
		this.refreshToken = refreshToken;
		this.username = username;
		this.password = password;
		this.securityToken = securityToken;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthCallback) {
				OAuthCallback oauthCallback = (OAuthCallback) callback;
				oauthCallback.setFlowType(this.flowType);
				oauthCallback.setInstance(this.instance);
				oauthCallback.setClientId(this.clientId);
				oauthCallback.setClientSecret(this.clientSecret);
				oauthCallback.setRedirectUri(this.redirectUri);
				oauthCallback.setCode(this.code);
				oauthCallback.setRefreshToken(this.refreshToken);
				oauthCallback.setUsername(this.username);
				oauthCallback.setPassword(this.password);
				oauthCallback.setSecurityToken(this.securityToken);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}		
	}	
}