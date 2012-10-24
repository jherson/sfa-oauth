package com.sfa.qb.login.oauth;

import java.io.Serializable;

public class OAuthServiceProvider implements Serializable {

	private static final long serialVersionUID = 4658561512368737107L;
	
	private String instance;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String scope;
	private String prompt;
	private String display;	
	
	public OAuthServiceProvider() {
		
	}

	public OAuthServiceProvider(String instance, String clientId, String clientSecret, String redirectUri) {
		this.instance = instance;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
	}
	
	public String getInstance() {
		return instance;
	}
	
	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String getRedirectUri() {
		return redirectUri;
	}
	
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
}