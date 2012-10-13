package com.sfa.qb.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.sfa.qb.model.auth.OAuth;

public class OAuthCallbackHandler implements CallbackHandler {
	
	private static Logger log = Logger.getLogger(OAuthCallbackHandler.class.getName()); 
	private OAuth oauth;
	
	public OAuthCallbackHandler() {
		
	}
	
	public OAuthCallbackHandler(OAuth oauth) {
		this.oauth = oauth;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {		
		
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthCallback) {
				OAuthCallback oauthCallback = (OAuthCallback) callback;
				oauthCallback.setOauth(oauth);		
			} else {
				log.log(Level.SEVERE, "Unsupported callback type");
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}				
	}
}