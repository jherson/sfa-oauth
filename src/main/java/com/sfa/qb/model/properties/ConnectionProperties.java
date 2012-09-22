package com.sfa.qb.model.properties;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named

public class ConnectionProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Properties properties = System.getProperties();
	
	//System.setProperty("salesforce.environment", properties.getProperty("salesforce.environment"));
	//System.setProperty("salesforce.authEndpoint", "{0}/services/Soap/u/25.0");
	//System.setProperty("salesforce.username", "intadmin@redhat.com.vpm");
	//System.setProperty("salesforce.password", "fedora10H8wBs6OlRuP4OWGu4nQNHZox");
	//System.setProperty("salesforce.api.version", "25.0");
	//System.setProperty("salesforce.oauth.clientId", "3MVG94DzwlYDSHS6zfNz9quij13YGleDlZ9u7Le_XIqGTcYWo6Sp7FJJDI4KrvSMjfwhjfpZm_hD_8Ou9fj4w");
	//System.setProperty("salesforce.oauth.clientSecret", "5180567430750494425");
	//System.setProperty("salesforce.oauth.redirectUri", "https://jherson-ws.devlab.phx1.redhat.com:8443/quotebuilder/index.jsf");
    //System.setProperty("salesforce.default.locale", connection.getUserInfo().getUserLocale());
    //System.setProperty("salesforce.service.endpoint", connection.getConfig().getServiceEndpoint());
    //System.setProperty("salesforce.api.endpoint", connection.getConfig().getServiceEndpoint().substring(0, connection.getConfig().getServiceEndpoint().indexOf("/Soap")));

	public String getEnvironment() {
		return properties.getProperty("salesforce.environment");
	}
	
	public void setEnvironment(String environment) {
		properties.setProperty("salesforce.environment", environment);
	}
	
	public String getAuthEndpoint() {
		return properties.getProperty("salesforce.authEndpoint");
	}
	
	public void setAuthEndpoint(String authEndpoint) {
		properties.setProperty("salesforce.authEndpoint", authEndpoint);
	}
	
	public String getUsername() {
		return properties.getProperty("salesforce.username");
	}
	
	public void setUsername(String username) {
		properties.setProperty("salesforce.username", username);
	}
	
	public String getPassword() {
		return properties.getProperty("salesforce.password");
	}
	
	public void setPassword(String password) {
		properties.setProperty("salesforce.password", password);
	}
	
	public String getApiVersion() {
		return properties.getProperty("salesforce.api.version");
	}
	
	public void setApiVersion(String apiVersion) {
		properties.setProperty("salesforce.api.version", apiVersion);
	}
	
	public String getOauthClientId() {
		return properties.getProperty("salesforce.oauth.clientId");
	}
	
	public void setOauthClientId(String oauthClientId) {
		properties.setProperty("salesforce.oauth.clientId", oauthClientId);
	}
	
	public String getOauthClientSecret() {
		return properties.getProperty("salesforce.environment");
	}
	
	public void setOauthClientSecret(String oauthClientSecret) {
		properties.setProperty("salesforce.environment", oauthClientSecret);
	}
	
	public String getOauthRedirectUri() {
		return properties.getProperty("salesforce.oauth.redirectUri");
	} 
	
	public void setOauthRedirectUri(String oauthRedirectUri) {
		properties.setProperty("salesforce.oauth.redirectUri", oauthRedirectUri);
	}
	
	public String getServiceEndpoint() {
		return properties.getProperty("salesforce.service.endpoint");
	} 
	
	public void setServiceEndpoint(String serviceEndpoint) {
		properties.setProperty("salesforce.service.endpoint", serviceEndpoint);
	}
	
	public String getApiEndpoint() {
		return properties.getProperty("salesforce.api.endpoint");
	} 
	
	public void setApiEndpoint(String apiEndpoint) {
		properties.setProperty("salesforce.api.endpoint", apiEndpoint);
	}
}