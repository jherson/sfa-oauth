package com.sfa.login.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;


public class OAuthRefreshTokenCallbackHandler implements CallbackHandler, Serializable {
	 
	private static final long serialVersionUID = 4440714717399157192L;
	
	private String refreshToken;
		
	public OAuthRefreshTokenCallbackHandler(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {		
		
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
            if (callback instanceof OAuthRefreshTokenCallback) {
				OAuthRefreshTokenCallback refreshTokenCallback = (OAuthRefreshTokenCallback) callback;
				refreshTokenCallback.setRefreshToken(refreshToken);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}				
	}
}