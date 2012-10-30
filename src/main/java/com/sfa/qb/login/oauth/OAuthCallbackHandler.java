package com.sfa.qb.login.oauth;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class OAuthCallbackHandler implements CallbackHandler, Serializable {
	 
	private static final long serialVersionUID = 4440714717399157192L;
	
	private String code;
	
	public OAuthCallbackHandler(String code) {
		this.code = code;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {		
		
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthCodeCallback) {
				OAuthCodeCallback oauthCodeCallback = (OAuthCodeCallback) callback;
				oauthCodeCallback.setCode(code);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}				
	}
}