package com.nowellpoint.oauth.request;

import java.util.Map;

public class OAuthClientRequest {
	
	public static class BasicAuthorizationRequest {
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;
		private Map<String, Object> parameters;
		
		public String getUsername() {
			return username;
		}
		
		public BasicAuthorizationRequest setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public String getPassword() {
			return password;
		}
		
		public BasicAuthorizationRequest setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public String getClientId() {
			return clientId;
		}
		
		public BasicAuthorizationRequest setClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}
		
		public String getClientSecret() {
			return clientSecret;
		}
		
		public BasicAuthorizationRequest setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		public Map<String, Object> getParameters() {
			return parameters;
		}

		public BasicAuthorizationRequest  setParameters(Map<String, Object> parameters) {
			this.parameters = parameters;
			return this;
		}
	}
}