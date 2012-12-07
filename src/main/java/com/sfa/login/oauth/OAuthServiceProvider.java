package com.sfa.login.oauth;

import java.io.Serializable;

public class OAuthServiceProvider implements Serializable {

	private static final long serialVersionUID = 4658561512368737107L;
	
	private String tokenUrl;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String scope;
	private String prompt;
	private String display;	
	private String startUrl;
	
	public OAuthServiceProvider() {
		
	}
	
	public String getTokenUrl() {
		return tokenUrl;
	}
	
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
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
	
	public String getStartUrl() {
		return startUrl;
	}
	
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}
}