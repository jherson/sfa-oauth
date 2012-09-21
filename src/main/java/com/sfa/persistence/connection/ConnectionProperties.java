package com.sfa.persistence.connection;

import java.util.Locale;

public class ConnectionProperties {

	private static String ENVIRONMENT;
	private static Locale LOCALE;
	private static String SERVICE_ENDPOINT;
	private static String API_VERSION;
	private static String API_ENDPOINT;	
	private static String OAUTH_CLIENT_ID;
	private static String OAUTH_CLIENT_SECRET;
	private static String OAUTH_REDIRECT_URI;
				
	public static String getEnvironment() {
		return ENVIRONMENT;
	}
	
	public static void setEnvironment(String environment) {
		ENVIRONMENT = environment;
	}
	
	public static String getApiVersion() {
		return API_VERSION;
	}

	public static void setApiVersion(String apiVersion) {
		API_VERSION = apiVersion;
	}

	public static String getApiEndpoint() {
		return API_ENDPOINT;
	}

	public static void setApiEndpoint(String apiEndpoint) {
		API_ENDPOINT = apiEndpoint;
	}

	public static void setServiceEndpoint(String serviceEndpoint) {
		SERVICE_ENDPOINT = serviceEndpoint;
	}
	
	public static String getServiceEndpoint() {
		return SERVICE_ENDPOINT;
	}	
	
	public static Locale getLocale() {
		return LOCALE;
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
	}
	
	public static String getOAuthClientId() {
		return OAUTH_CLIENT_ID;
	}
	
	public static void setOAuthClientId(String oauthClientId) {
		OAUTH_CLIENT_ID = oauthClientId;
	}

	public static String getOAuthClientSecret() {
		return OAUTH_CLIENT_SECRET;
	}

	public static void setOAuthClientSecret(String oauthClientSecret) {
		OAUTH_CLIENT_SECRET = oauthClientSecret;
	}
	
	public static String getOAuthRedirectUri() {
		return OAUTH_REDIRECT_URI;
	}
	
	public static void setOAuthRedirectUri(String oauthRedirectUri) {
		OAUTH_REDIRECT_URI = oauthRedirectUri;
	}
}