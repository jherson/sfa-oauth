package com.nowellpoint.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class OAuthCallbackHandler implements CallbackHandler, Serializable {

	private static final long serialVersionUID = 1173111272806803501L;
	
	private OAuthFlowType flowType;
	private String code;
	private String refreshToken;
	private String username;
	private String password;

	public OAuthCallbackHandler(
			OAuthFlowType flowType,			
			String code, 
			String refreshToken, 
			String username, 
			String password) {
		
		this.flowType = flowType;
		this.code = code;
		this.refreshToken = refreshToken;
		this.username = username;
		this.password = password;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthCallback) {
				OAuthCallback oauthCallback = (OAuthCallback) callback;
				oauthCallback.setFlowType(this.flowType);
				oauthCallback.setCode(this.code);
				oauthCallback.setRefreshToken(this.refreshToken);
				oauthCallback.setUsername(this.username);
				oauthCallback.setPassword(this.password);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}		
	}	
}