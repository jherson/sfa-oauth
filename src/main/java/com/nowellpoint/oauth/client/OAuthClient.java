package com.nowellpoint.oauth.client;

import java.io.Serializable;

import com.nowellpoint.oauth.OAuthConstants;
import com.nowellpoint.oauth.OAuthServiceProvider;

public class OAuthClient implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 2748321507418076091L;
	private OAuthServiceProvider serviceProvider;
	private String loginUrl;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private String scope;
	private String prompt;
	private String display;	
	private String state;
	private String logoutRedirect;
	
	/**
	 * no arg contructor used for injection 
	 */
	
	public OAuthClient() {
		
	}
	
	private OAuthClient(ClientBuilder builder) {
		this.clientId = builder.clientId;
		this.clientSecret = builder.clientSecret;
		this.callbackUrl = builder.callbackUrl;
		this.scope = builder.scope;
		this.prompt = builder.prompt;
		this.display = builder.display;
		this.state = builder.state;
		this.logoutRedirect = builder.logoutRedirect;
		
		try {
			this.serviceProvider = (OAuthServiceProvider) Class.forName(builder.serviceProvider).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		this.loginUrl = buildloginRedirect(serviceProvider.getAuthEndpoint());
	}
	
	public <T extends OAuthServiceProvider> OAuthServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	
	public String getLoginUrl() {
		return loginUrl;
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

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getScope() {
		return scope;
	}

	public String getPrompt() {
		return prompt;
	}

	public String getDisplay() {
		return display;
	}

	public String getState() {
		return state;
	}
	
	public String getLogoutRedirect() {
		return logoutRedirect;
	}
	
	private String buildloginRedirect(String authEndpoint) {
		StringBuilder endpoint = new StringBuilder()
				.append(authEndpoint)
				.append("?")
				.append(OAuthConstants.RESPONSE_TYPE_PARAMETER)
				.append("=")
				.append(OAuthConstants.CODE_PARAMETER)
				.append("&")
				.append(OAuthConstants.CLIENT_ID_PARAMETER)
				.append("=")
				.append(getClientId())
				.append("&")
				.append(OAuthConstants.REDIRECT_URI_PARAMETER)
				.append("=")
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
	
	public static class ClientBuilder {
		private String serviceProvider;
		private String clientId;
		private String clientSecret;
		private String callbackUrl;
		private String scope;
		private String prompt;
		private String display;	
		private String state;
		private String logoutRedirect;
		
		public ClientBuilder() {
			
		}
		
		public <T extends OAuthServiceProvider> ClientBuilder setServiceProvider(Class<T> serviceProvider) {
			this.serviceProvider = serviceProvider.getName();
			return this;
		}
		
		public ClientBuilder setClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public ClientBuilder setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}
		
		public ClientBuilder setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
			return this;
		}

		public ClientBuilder setScope(String scope) {
			this.scope = scope;
			return this;
		}

		public ClientBuilder setPrompt(String prompt) {
			this.prompt = prompt;
			return this;
		}

		public ClientBuilder setDisplay(String display) {
			this.display = display;
			return this;
		}
		
		public ClientBuilder setState(String state) {
			this.state = state;
			return this;
		}
		
		public ClientBuilder setLogoutRedirect(String logoutRedirect) {
			this.logoutRedirect = logoutRedirect;
			return this;
		}
		
		public OAuthClient build() {
			return new OAuthClient(this);
		}
	}
}