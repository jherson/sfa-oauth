package com.sfa.login.oauth;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import com.sfa.login.oauth.callback.OAuthCallbackHandler;
import com.sfa.login.oauth.callback.OAuthFlowType;

public class OAuthServiceProvider implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L; 
	private LoginContext loginContext;
	private Subject subject;
	
    public OAuthServiceProvider(OAuthConfig oauthConfig) {
    	setConfiguration(oauthConfig);
    }
	
	public OAuthServiceProvider(OAuthConfig oauthConfig, Subject subject) {
		setConfiguration(oauthConfig);
        setSubject(subject);        
	}
       
    public void login(HttpServletResponse response) throws LoginException {
    	OAuthConfig oauthConfig = (OAuthConfig) Configuration.getConfiguration();
    	
    	/**
    	 * initialize a new Subject 
    	 */
    	
    	setSubject(new Subject());
    	
    	/**
		 * build the OAuth URL based on the flow from options
		 */
		
		String authUrl = oauthConfig.buildAuthUrl();
		
		/**
		 * do the redirect
		 */
		
		try {
			response.sendRedirect(authUrl);
			return;
		} catch (IOException e) {
			throw new LoginException("Unable to do the redirect: " + e);
		}
    }
    
    public void login(FacesContext context) throws LoginException {
        OAuthConfig oauthConfig = (OAuthConfig) Configuration.getConfiguration();
    	
    	/**
    	 * initialize a new Subject 
    	 */
    	
    	setSubject(new Subject());
    	
    	/**
		 * build the OAuth URL based on the flow from options
		 */
		
		String authUrl = oauthConfig.buildAuthUrl();
		
		/**
		 * do the redirect
		 */
		
		try {
			context.getExternalContext().redirect(authUrl);
			return;
		} catch (IOException e) {
			throw new LoginException("Unable to do the redirect: " + e);
		}	
    }
    
    public void authenticate(String username, String password, String securityToken) throws LoginException {    	   	
    	OAuthCallbackHandler callbackHandler = new OAuthCallbackHandler(
    			null,
    			OAuthFlowType.USERNAME_PASSWORD_FLOW,
    			null,
    			null, 
    			username, 
    			password, 
    			securityToken);
		    	    	
    	login(callbackHandler);
    }
    
    public void authenticate(String code) throws LoginException {    	
    	OAuthCallbackHandler callbackHandler = new OAuthCallbackHandler(
    			null,
    			OAuthFlowType.WEB_SERVER_FLOW,
    			code, 
    			null, 
    			null, 
    			null, 
    			null);
    	
    	login(callbackHandler);
    }
    
    public void authenticate(HttpServletResponse response) throws LoginException {   	
    	OAuthCallbackHandler callbackHandler = new OAuthCallbackHandler(
    			null,
    			OAuthFlowType.USER_AGENT_FLOW,
    			null, 
    			null, 
    			null, 
    			null, 
    			null);
    	
    	login(callbackHandler);
    	
    }
    
    public void refreshToken(String refreshToken) throws LoginException {  			
    	OAuthCallbackHandler callbackHandler = new OAuthCallbackHandler(
    			null,
    			OAuthFlowType.REFRESH_TOKEN_FLOW,
    			null, 
    			refreshToken, 
    			null, 
    			null, 
    			null);
    	
    	login(callbackHandler);
    }
    
    public void logout() throws LoginException {
    	loginContext.logout();
    }
    
    public Subject getSubject() {
    	return subject;
    }
    
    public void setSubject(Subject subject) {
    	this.subject = subject;
    }
    
    private void login(CallbackHandler callbackHander) throws LoginException {
    	loginContext = new LoginContext("OAuth", getSubject(), callbackHander, Configuration.getConfiguration());    	
    	loginContext.login();
    	setSubject(loginContext.getSubject());
    }
   
    private void setConfiguration(OAuthConfig oauthConfig) {							
		Configuration.setConfiguration(oauthConfig);
    }
}