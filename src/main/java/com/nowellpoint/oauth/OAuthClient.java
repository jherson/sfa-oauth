package com.nowellpoint.oauth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OAuthClient implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 2748321507418076091L;
	private ServiceProvider serviceProvider;
	private String authEndpoint;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private String scope;
	private String prompt;
	private String display;	
	private String state;
	private Map<String, Object> parameters;
	
	/**
	 * no arg contructor used for injection 
	 */
	
	public OAuthClient() {
		
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
		private Map<String, Object> parameters = new HashMap<String, Object>();	
		
		public <T extends ServiceProvider> ClientBuilder serviceProvider(Class<T> serviceProvider) {
			this.serviceProvider = serviceProvider.getName();
			return this;
		}
		
		public ClientBuilder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public ClientBuilder clientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}
		
		public ClientBuilder callbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
			return this;
		}

		public ClientBuilder scope(String scope) {
			this.scope = scope;
			return this;
		}

		public ClientBuilder prompt(String prompt) {
			this.prompt = prompt;
			return this;
		}

		public ClientBuilder display(String display) {
			this.display = display;
			return this;
		}
		
		public ClientBuilder state(String state) {
			this.state = state;
			return this;
		}
		
		public ClientBuilder addParameter(String key, Object value) {
			this.parameters.put(key, value);
			return this;
		}
		
		public OAuthClient build() {
			return new OAuthClient(this);
		}
	}
	
	private OAuthClient(ClientBuilder builder) {
		this.clientId = builder.clientId;
		this.clientSecret = builder.clientSecret;
		this.callbackUrl = builder.callbackUrl;
		this.scope = builder.scope;
		this.prompt = builder.prompt;
		this.display = builder.display;
		this.state = builder.state;
		
		try {
			this.serviceProvider = (ServiceProvider) Class.forName(builder.serviceProvider).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		this.authEndpoint = buildAuthEndpoint(serviceProvider.getAuthEndpoint());
	}
	
	public <T extends ServiceProvider> ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	
	public String getAuthEndpoint() {
		return authEndpoint;
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

	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	private String buildAuthEndpoint(String authorizationResource) {
		StringBuilder endpoint = new StringBuilder()
				.append(authorizationResource)
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
			endpoint.append("&").append(OAuthConstants.SCOPE_PARAMETER)
					.append("=").append(getScope());
		}
    	
    	if (getPrompt() != null) {
			endpoint.append("&").append(OAuthConstants.PROMPT_PARAMETER)
					.append("=").append(getPrompt());
    	}
    	
    	if (getDisplay() != null) {
			endpoint.append("&").append(OAuthConstants.DISPLAY_PARAMETER)
					.append("=").append(getDisplay());
    	}
    	
    	if (getState() != null) {
			endpoint.append("&").append(OAuthConstants.STATE_PARAMETER)
					.append("=").append(getState());
    	}
    	
    	return endpoint.toString();
    }
}