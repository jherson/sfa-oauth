package com.sfa.qb.login.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;


public class OAuthCodeCallbackHandler implements CallbackHandler, Serializable {
	 
	private static final long serialVersionUID = 4440714717399157192L;
	
	private String code;
	private String username;
	private String password;
	private String securityToken;
	
	public OAuthCodeCallbackHandler(String code) {
		this.code = code;
	}
	
//	public OAuthCallbackHandler(String username, String password, String securityToken) {
//		this.username = username;
//		this.password = password;
//		this.securityToken = securityToken;
//	}
//	

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {		
		
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof OAuthCodeCallback) {
				OAuthCodeCallback codeCallback = (OAuthCodeCallback) callback;
				codeCallback.setCode(code);
			} else if (callback instanceof OAuthUserNameCallback) {
				OAuthUserNameCallback usernameCallback = (OAuthUserNameCallback) callback;
				usernameCallback.setUserName(username);
			} else if (callback instanceof OAuthPasswordCallback) {
				OAuthPasswordCallback passwordCallback = (OAuthPasswordCallback) callback;
				passwordCallback.setPassword(password);
			} else if (callback instanceof OAuthSecurityTokenCallback) {
				OAuthSecurityTokenCallback securityTokenCallback = (OAuthSecurityTokenCallback) callback;
				securityTokenCallback.setSecurityToken(securityToken);
			} else {
				throw new UnsupportedCallbackException(callback, "Unsupported callback type");
			}
		}				
	}
}