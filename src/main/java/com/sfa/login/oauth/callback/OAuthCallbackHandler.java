package com.sfa.login.oauth.callback;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletResponse;

public class OAuthCallbackHandler implements CallbackHandler, Serializable {

	private static final long serialVersionUID = 1173111272806803501L;
	
	private HttpServletResponse response;
	private OAuthFlowType flowType;
	private String code;
	private String refreshToken;
	private String username;
	private String password;
	private String securityToken;

	public OAuthCallbackHandler(
			HttpServletResponse response,
			OAuthFlowType flowType,			
			String code, 
			String refreshToken, 
			String username, 
			String password, 
			String securityToken) {
		
		this.response = response;
		this.flowType = flowType;
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
				oauthCallback.setResponse(response);
				oauthCallback.setFlowType(this.flowType);
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