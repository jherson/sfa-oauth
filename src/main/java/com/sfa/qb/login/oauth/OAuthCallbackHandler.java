package com.sfa.qb.login.oauth;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class OAuthCallbackHandler implements CallbackHandler {
	 
	private OAuthConfig oauthConfig;
	private String code;
	
	public OAuthCallbackHandler(OAuthConfig oauthConfig, String code) {
		this.oauthConfig = oauthConfig;
		this.code = code;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {		
		
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthConfigCallback) {
				OAuthConfigCallback oauthConfigCallback = (OAuthConfigCallback) callback;
				oauthConfigCallback.setOAuthConfig(oauthConfig);
			}else if (callback instanceof OAuthCodeCallback) {
				OAuthCodeCallback oauthCodeCallback = (OAuthCodeCallback) callback;
				oauthCodeCallback.setCode(code);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}				
	}
}