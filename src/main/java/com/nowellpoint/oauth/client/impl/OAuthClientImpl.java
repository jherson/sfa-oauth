package com.nowellpoint.oauth.client.impl;

import java.io.Serializable;

import com.nowellpoint.oauth.OAuthConstants;
import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.client.OAuthClientBuilder;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.session.OAuthSessionImpl;

public class OAuthClientImpl implements OAuthClient, Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4972528949996825798L;
	
	private OAuthServiceProvider serviceProvider;
	private String loginUrl;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private String scope;
	private String prompt;
	private String display;
	private String state;
	
	public OAuthClientImpl() {
		
	}
	
	public OAuthClientImpl(OAuthClientBuilder builder) {
		this.clientId = builder.getClientId();
		this.clientSecret = builder.getClientSecret();
		this.callbackUrl = builder.getCallbackUrl();
		this.scope = builder.getScope();
		this.prompt = builder.getPrompt();
		this.display = builder.getDisplay();
		this.state = builder.getState();
		
		try {
			this.serviceProvider = (OAuthServiceProvider) Class.forName(builder.getServiceProvider()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		this.loginUrl = buildLoginRedirect(serviceProvider.getAuthEndpoint());
	}
	
	@Override
	public <T extends OAuthServiceProvider> OAuthServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	
	@Override
	public String getLoginUrl() {
		return loginUrl;
	}
	
	@Override
	public String getClientId() {
		return clientId;
	}
	
	@Override
	public String getClientSecret() {
		return clientSecret;
	}
	
	@Override
	public String getCallbackUrl() {
		return callbackUrl;
	}
	
	@Override
	public String getScope() {
		return scope;
	}
	
	@Override
	public String getPrompt() {
		return prompt;
	}
	
	@Override
	public String getDisplay() {
		return display;
	}
	
	@Override
	public String getState() {
		return state;
	}
	
	@Override
	public OAuthSession createSession() {
		return new OAuthSessionImpl(this);
	}
	
	@Override
	public OAuthSession createSession(Token token) {
		return new OAuthSessionImpl(this, token);
	}
	
	private String buildLoginRedirect(String authEndpoint) {
		StringBuilder endpoint = new StringBuilder().append(authEndpoint)
			.append("?").append(OAuthConstants.RESPONSE_TYPE_PARAMETER)
			.append("=").append(OAuthConstants.CODE_PARAMETER).append("&")
			.append(OAuthConstants.CLIENT_ID_PARAMETER).append("=")
			.append(getClientId()).append("&")
			.append(OAuthConstants.REDIRECT_URI_PARAMETER).append("=")
			.append(getCallbackUrl());
		
		if (getScope() != null) {
			endpoint.append("&").append(OAuthConstants.SCOPE_PARAMETER).append("=").append(getScope());
		}
		
		if (getPrompt() != null) {
			endpoint.append("&").append(OAuthConstants.PROMPT_PARAMETER).append("=").append(getPrompt());
		}
		
		if (getDisplay() != null) {
			endpoint.append("&").append(OAuthConstants.DISPLAY_PARAMETER).append("=").append(getDisplay());
		}
		
		if (getState() != null) {
			endpoint.append("&").append(OAuthConstants.STATE_PARAMETER).append("=").append(getState());
		}
		
		return endpoint.toString();
	}
}
