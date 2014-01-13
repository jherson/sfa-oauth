package com.nowellpoint.oauth.client;

import java.util.Map;

public class OAuthClientRequest {
	
	private static BasicTokenRequestBuilder basicTokenRequestBuilder = new BasicTokenRequestBuilder();
	private static VerifyTokenRequestBuilder verifyTokenRequestBuilder = new VerifyTokenRequestBuilder();
	private static IdentityRequestBuilder identityRequestBuilder = new IdentityRequestBuilder();
	private static RefreshTokenRequestBuilder refreshTokenRequestBuilder = new RefreshTokenRequestBuilder();
	private static RevokeTokenRequestBuilder revokeTokenRequestBuilder = new RevokeTokenRequestBuilder();
	
	public static BasicTokenRequestBuilder basicTokenRequest() {
		return basicTokenRequestBuilder;
	}
	
	public static VerifyTokenRequestBuilder verifyTokenRequest() {
		return verifyTokenRequestBuilder;
	}
	
	public static IdentityRequestBuilder identityRequest() {
		return identityRequestBuilder;
	}
	
	public static RefreshTokenRequestBuilder refreshTokenRequest() {
		return refreshTokenRequestBuilder;
	}
	
	public static RevokeTokenRequestBuilder revokeTokenRequest() {
		return revokeTokenRequestBuilder;
	}
	
	public static class BasicTokenRequest {
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;
		private Map<String, Object> parameters;
		
		private BasicTokenRequest(BasicTokenRequestBuilder builder) {
			this.username = builder.username;
			this.password = builder.password;
			this.clientId = builder.clientId;
			this.clientSecret = builder.clientSecret;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String getClientId() {
			return clientId;
		}
		
		public String getClientSecret() {
			return clientSecret;
		}
		
		public Map<String, Object> getParameters() {
			return parameters;
		}
	}
	
	public static class BasicTokenRequestBuilder {
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;
		
		public BasicTokenRequestBuilder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public BasicTokenRequestBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public BasicTokenRequestBuilder setClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public BasicTokenRequestBuilder setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}
		
		public BasicTokenRequest build() {
			return new BasicTokenRequest(this);
		}
	}
	
	public static class VerifyTokenRequest {
		private String code;
		private String callbackUrl;
		private String clientId;
		private String clientSecret;
		
		private VerifyTokenRequest(VerifyTokenRequestBuilder builder) {
			this.code = builder.code;
			this.callbackUrl = builder.callbackUrl;
			this.clientId = builder.clientId;
			this.clientSecret = builder.clientSecret;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getCallbackUrl() {
			return callbackUrl;
		}
		
		public String getClientId() {
			return clientId;
		}
		
		public String getClientSecret() {
			return clientSecret;
		}
	}
	
	public static class VerifyTokenRequestBuilder {
		private String code;
		private String callbackUrl;
		private String clientId;
		private String clientSecret;
		
		public VerifyTokenRequestBuilder setCode(String code) {
			this.code = code;
			return this;
		}
		
		public VerifyTokenRequestBuilder setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
			return this;
		}

		public VerifyTokenRequestBuilder setClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public VerifyTokenRequestBuilder setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}
		
		public VerifyTokenRequest build() {
			return new VerifyTokenRequest(this);
		}
	}
	
	public static class IdentityRequest {
		private String identityUrl;
		private String accessToken;
		
		private IdentityRequest(IdentityRequestBuilder builder) {
			this.identityUrl = builder.identityUrl;
			this.accessToken = builder.accessToken;
		}
		
		public String getIdentityUrl() {
			return identityUrl;
		}
		
		public String getAccessToken() {
			return accessToken;
		}
	}
	
	public static class IdentityRequestBuilder {
		private String identityUrl;
		private String accessToken;
		
		public IdentityRequestBuilder setIdentityUrl(String identityUrl) {
			this.identityUrl = identityUrl;
			return this;
		}
		
		public IdentityRequestBuilder setAccessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}
		
		public IdentityRequest build() {
			return new IdentityRequest(this);
		}
	}
	
	public static class RefreshTokenRequest {
		private String refreshToken;
		private String clientId;
		private String clientSecret;
		
		private RefreshTokenRequest(RefreshTokenRequestBuilder builder) {
			this.refreshToken = builder.refreshToken;
			this.clientId = builder.clientId;
			this.clientSecret = builder.clientSecret;
		}
		
		public String getRefreshToken() {
			return refreshToken;
		}
		
		public String getClientId() {
			return clientId;
		}
		
		public String getClientSecret() {
			return clientSecret;
		}
	}
	
	public static class RefreshTokenRequestBuilder {
		private String refreshToken;
		private String clientId;
		private String clientSecret;
		
		public RefreshTokenRequestBuilder setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}
		
		public RefreshTokenRequestBuilder setClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public RefreshTokenRequestBuilder setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}
		
		public RefreshTokenRequest build() {
			return new RefreshTokenRequest(this);
		}
	}
	
	public static class RevokeTokenRequest {
		private String accessToken;
		
		private RevokeTokenRequest(RevokeTokenRequestBuilder builder) {
			this.accessToken = builder.accessToken;
		}
		
		public String getAccessToken() {
			return accessToken;
		}
	}
	
	public static class RevokeTokenRequestBuilder {
		private String accessToken;
		
		public RevokeTokenRequestBuilder setAccessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}
		
		public RevokeTokenRequest build() {
			return new RevokeTokenRequest(this);
		}
	}
}