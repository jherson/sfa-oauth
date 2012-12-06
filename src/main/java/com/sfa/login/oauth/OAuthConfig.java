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
		optionsMap.put(OAuthConstants.CLIENT_ID_PARAMETER, serviceProvider.getEndpoint());
		optionsMap.put(OAuthConstants.CLIENT_ID_PARAMETER, serviceProvider.getClientId());
		optionsMap.put(OAuthConstants.CLIENT_SECRET_PARAMETER, serviceProvider.getClientSecret());
		optionsMap.put(OAuthConstants.REDIRECT_URI_PARAMETER, serviceProvider.getRedirectUri());
		optionsMap.put(OAuthConstants.SCOPE_PARAMETER, serviceProvider.getScope());
		optionsMap.put(OAuthConstants.PROMPT_PARAMETER, serviceProvider.getPrompt());
		optionsMap.put(OAuthConstants.DISPLAY_PARAMETER, serviceProvider.getDisplay());
		optionsMap.put(OAuthConstants.START_URL_PARAMETER, serviceProvider.getStartUrl());
		
		AppConfigurationEntry[] entries = new AppConfigurationEntry[1];
		entries[0] = new AppConfigurationEntry("com.sfa.login.oauth.OAuthLoginModule", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, optionsMap);
		
		return entries;
	}
}