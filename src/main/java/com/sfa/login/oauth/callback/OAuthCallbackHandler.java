package com.sfa.login.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class OAuthCallbackHandler implements CallbackHandler, Serializable {
	
	private static final long serialVersionUID = -5406905785684587233L;
	
	private String code;
	private String refreshToken;
	private String username;
	private String password;
	private String securityToken;

	public OAuthCallbackHandler() {
		
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}