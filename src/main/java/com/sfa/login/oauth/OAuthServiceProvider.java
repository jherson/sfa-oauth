package com.sfa.login.oauth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OAuthServiceProvider implements Serializable {

	private static final long serialVersionUID = 4658561512368737107L;
	
	private String tokenUrl;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private String scope;
	private String prompt;
	private String display;	
	private String state;
	private String startUrl;
	
	public OAuthServiceProvider() {
		
	}
	
	public String getTokenUrl() {
		return tokenUrl;
	}
	
	public OAuthServiceProvider setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
		return this;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public OAuthServiceProvider setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public OAuthServiceProvider setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	
	public OAuthServiceProvider setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
		return this;
	}
	
	public String getScope() {
		if (scope != null) {
			try {
				return URLEncoder.encode(scope, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public OAuthServiceProvider setScope(String scope) {
		this.scope = scope;
		return this;
	}

	public String getPrompt() {
		return prompt;
	}

	public OAuthServiceProvider setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}

	public String getDisplay() {
		return display;
	}

	public OAuthServiceProvider setDisplay(String display) {
		this.display = display;
		return this;
	}
	
	public String getState() {
		return state;
	}
	
	public OAuthServiceProvider setState(String state) {
		this.state = state;
		return this;
	}
	
	public String getStartUrl() {
		return startUrl;
	}
	
	public OAuthServiceProvider setStartUrl(String startUrl) {
		this.startUrl = startUrl;
		return this;
	}
	
	public OAuthConsumer build() {
		return new OAuthConsumer(this);
	}
}