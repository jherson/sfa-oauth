package com.sfa.login.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;


public class OAuthCodeCallbackHandler implements CallbackHandler, Serializable {
	 
	private static final long serialVersionUID = 4440714717399157192L;
	
	private String code;
	
	public OAuthCodeCallbackHandler(String code) {
		this.code = code;
	}
		
	public String getCode() {
		return code;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {		
		
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthCodeCallback) {
				OAuthCodeCallback codeCallback = (OAuthCodeCallback) callback;
				codeCallback.setCode(code);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}				
	}
}