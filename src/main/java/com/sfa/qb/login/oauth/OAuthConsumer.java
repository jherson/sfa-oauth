package com.sfa.qb.login.oauth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

public class OAuthConsumer implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private HttpServletRequest request;
	private OAuthServiceProvider serviceProvider;
	private LoginContext loginContext;
	
    public OAuthConsumer(OAuthServiceProvider serviceProvider, HttpServletRequest request) { 
    	this.serviceProvider = serviceProvider;
    	this.request = request;
    }

    public String getOAuthTokenUrl() throws UnsupportedEncodingException {
    	return serviceProvider.getInstance() 
    			+ "/services/oauth2/authorize?response_type=code"
				+ "&client_id=" + serviceProvider.getClientId()
				+ "&redirect_uri=" + URLEncoder.encode(serviceProvider.getRedirectUri(), "UTF-8")
				+ "&scope=" + URLEncoder.encode(serviceProvider.getScope(), "UTF-8")
				+ "&prompt=" + serviceProvider.getPrompt()
				+ "&display=" + serviceProvider.getDisplay();        					
    }
    
    public Subject authenticate(String code) throws LoginException {
    	OAuthConfig oauthConfig = new OAuthConfig(serviceProvider);									
		Configuration.setConfiguration(oauthConfig);
		
		loginContext = new LoginContext("OAuth", new OAuthCallbackHandler(code));	
		loginContext.login();
		
		return loginContext.getSubject();
    }
    
    public void refreshToken() {
    	
    }
    
    public void logout() throws LoginException {
    	loginContext.logout();
    }
}