package com.sfa.login.oauth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;

import javax.security.auth.login.AppConfigurationEntry;

public class OAuthConfig extends Configuration implements Serializable {
	
	private static final long serialVersionUID = 7354592843495066771L;
	private OAuthServiceProvider serviceProvider;

	public OAuthConfig(OAuthServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	@Override
	public AppConfigurationEntry[] getAppConfigurationEntry(String name) {				
		Map<String,String> optionsMap = new HashMap<String,String>();
		optionsMap.put("instance", serviceProvider.getInstance());
		optionsMap.put("clientId", serviceProvider.getClientId());
		optionsMap.put("clientSecret", serviceProvider.getClientSecret());
		optionsMap.put("redirectUri", serviceProvider.getRedirectUri());
		optionsMap.put("scope", serviceProvider.getScope());
		optionsMap.put("prompt", serviceProvider.getPrompt());
		optionsMap.put("display", serviceProvider.getDisplay());
		optionsMap.put("startUrl", serviceProvider.getStartUrl());
		
		AppConfigurationEntry[] entries = new AppConfigurationEntry[1];
		entries[0] = new AppConfigurationEntry("com.sfa.login.oauth.OAuthLoginModule", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, optionsMap);
		
		return entries;
	}
}