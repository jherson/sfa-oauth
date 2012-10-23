package com.sfa.qb.login.oauth;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;

public class OAuthConfig extends javax.security.auth.login.Configuration {
	private String instance;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String scope;
	private String prompt;
	private String display;
	
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

	@Override
	public AppConfigurationEntry[] getAppConfigurationEntry(String name) {				
		Map<String,String> optionsMap = new HashMap<String,String>();
		optionsMap.put("instance", getInstance());
		optionsMap.put("clientId", getClientId());
		optionsMap.put("clientSecret", getClientSecret());
		optionsMap.put("redirectUri", getRedirectUri());
		optionsMap.put("scope", getScope());
		optionsMap.put("prompt", getPrompt());
		optionsMap.put("display", getDisplay());
		
		AppConfigurationEntry[] entries = new AppConfigurationEntry[1];
		entries[0] = new AppConfigurationEntry("com.sfa.qb.login.oauth.OAuthLoginModule", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, optionsMap);
		
		return entries;
	}
}