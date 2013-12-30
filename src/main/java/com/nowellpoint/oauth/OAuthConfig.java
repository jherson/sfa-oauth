package com.nowellpoint.oauth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;
import javax.security.auth.login.AppConfigurationEntry;

public class OAuthConfig extends Configuration implements Serializable {
	
	private static final long serialVersionUID = 7354592843495066771L;
	private static final String LOGIN_URL = "https://login.salesforce.com";
	private static final String TEST_URL = "https://test.salesforce.com";
	private static final Map<String, String> ENDPOINTS;
	
	private String authorizationUrl;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private String scope;
	private String prompt;
	private String display;	
	private String state;
	private Boolean useSandbox;
	private String dataDirectory;
	private String logoutRedirect;
	
	static {
        ENDPOINTS = new HashMap<String, String>();        
        ENDPOINTS.put(OAuthConstants.AUTHORIZE_ENDPOINT, "/services/oauth2/authorize");
        ENDPOINTS.put(OAuthConstants.TOKEN_ENDPOINT, "/services/oauth2/token");
        ENDPOINTS.put(OAuthConstants.REVOKE_ENDPOINT, "/services/oauth2/revoke");
    }
	
	public OAuthConfig() {
		this.useSandbox = Boolean.FALSE;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public OAuthConfig setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public OAuthConfig setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	
	public OAuthConfig setCallbackUrl(String callbackUrl) {
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

	public OAuthConfig setScope(String scope) {
		this.scope = scope;
		return this;
	}

	public String getPrompt() {
		return prompt;
	}

	public OAuthConfig setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}

	public String getDisplay() {
		return display;
	}

	public OAuthConfig setDisplay(String display) {
		this.display = display;
		return this;
	}
	
	public String getState() {
		return state;
	}
	
	public OAuthConfig setState(String state) {
		this.state = state;
		return this;
	}
	
	public Boolean getUseSandbox() {
		return useSandbox;
	}
	
	public OAuthConfig setUseSandbox(Boolean useSandbox) {
		this.useSandbox = useSandbox;
		return this;
	}
	
	public String getDataDirectory() {
		return dataDirectory;
	}
	
	public OAuthConfig setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
		return this;
	}
	
	public String getLogoutRedirect() {
		return logoutRedirect;
	}
	
	public OAuthConfig setLogoutRedirect(String logoutRedirect) {
		this.logoutRedirect = logoutRedirect;
		return this;
	}
	
	public String getAuthorizationUrl() {
		return authorizationUrl;
	}
	
	public OAuthServiceProvider build() {
		this.authorizationUrl = buildAuthorizationUrl();
		return new OAuthServiceProvider(this);
	}
	
	@Override
	public AppConfigurationEntry[] getAppConfigurationEntry(String name) {				
		Map<String,String> optionsMap = new HashMap<String,String>();
		optionsMap.put(OAuthConstants.TOKEN_ENDPOINT, getInstanceUrl() + ENDPOINTS.get(OAuthConstants.TOKEN_ENDPOINT));
		optionsMap.put(OAuthConstants.REVOKE_ENDPOINT, getInstanceUrl() + ENDPOINTS.get(OAuthConstants.REVOKE_ENDPOINT));
		optionsMap.put(OAuthConstants.CLIENT_ID_PARAMETER, this.getClientId());
		optionsMap.put(OAuthConstants.CLIENT_SECRET_PARAMETER, this.getClientSecret());
		optionsMap.put(OAuthConstants.REDIRECT_URI_PARAMETER, this.getCallbackUrl());
		optionsMap.put(OAuthConstants.SCOPE_PARAMETER, this.getScope());
		optionsMap.put(OAuthConstants.PROMPT_PARAMETER, this.getPrompt());
		optionsMap.put(OAuthConstants.DISPLAY_PARAMETER, this.getDisplay());
		optionsMap.put(OAuthConstants.STATE_PARAMETER, this.getState());
	
		AppConfigurationEntry[] entries = new AppConfigurationEntry[1];
		entries[0] = new AppConfigurationEntry("com.nowellpoint.oauth.OAuthLoginModule", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, optionsMap);
		
		return entries;
	}
	
	private String buildAuthorizationUrl() {
    	StringBuilder url = new StringBuilder().append(getInstanceUrl())
    			.append(ENDPOINTS.get(OAuthConstants.AUTHORIZE_ENDPOINT))
    			.append("?")
    			.append(OAuthConstants.RESPONSE_TYPE_PARAMETER)
    			.append("=")
    			.append(OAuthConstants.CODE_PARAMETER)
    			.append("&")
    			.append(OAuthConstants.CLIENT_ID_PARAMETER)
    			.append("=")
    			.append(getClientId())
    			.append("&")
    			.append(OAuthConstants.REDIRECT_URI_PARAMETER).append("=")
    			.append(getCallbackUrl());
    	
    	if (getScope() != null) { 
    		url.append("&").append(OAuthConstants.SCOPE_PARAMETER).append("=").append(getScope());
    	}
    	
    	if (getPrompt() != null) {
    		url.append("&").append(OAuthConstants.PROMPT_PARAMETER).append("=").append(getPrompt());
    	}
    	
    	if (getDisplay() != null) {
    		url.append("&").append(OAuthConstants.DISPLAY_PARAMETER).append("=").append(getDisplay());
    	}
    	
    	if (getState() != null) {
    		url.append("&").append(OAuthConstants.STATE_PARAMETER).append("=").append(getState());
    	}
    	
    	return url.toString();
    }
	
	private String getInstanceUrl() {
		String instanceUrl = null;
		if (getUseSandbox()) {
			instanceUrl = TEST_URL;
		} else {
			instanceUrl = LOGIN_URL;
		}
		return instanceUrl;
	}
}