package com.sfa.login.oauth;

import java.io.Serializable;
import java.util.Iterator;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import com.sfa.login.oauth.callback.OAuthCallbackHandler;
import com.sfa.login.oauth.callback.OAuthFlowType;
import com.sfa.login.oauth.model.Identity;
import com.sfa.login.oauth.model.Token;
import com.sfa.login.oauth.principal.IdentityPrincipal;
import com.sfa.login.oauth.principal.TokenPrincipal;

public class OAuthConsumer implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private LoginContext loginContext;
	private Subject subject;
	
	public OAuthConsumer() {
		
	}
	
    public OAuthConsumer(OAuthServiceProvider serviceProvider) {
    	setConfiguration(serviceProvider);
    }
	
	public OAuthConsumer(OAuthServiceProvider serviceProvider, Subject subject) {
		setConfiguration(serviceProvider);
        setSubject(subject);        
	}
       
    public void login(HttpServletResponse response) throws LoginException {
    	OAuthCallbackHandler callbackHandler = new OAuthCallbackHandler(
    			response,
    			OAuthFlowType.WEB_SERVER_FLOW,
    			null, 
    			null, 
    			null, 
    			null, 
    			null);
    	
    	login(callbackHandler);
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
    
    public Identity getIdentity() {
    	Iterator<IdentityPrincipal> iterator = subject.getPrincipals(IdentityPrincipal.class).iterator();
		if (iterator.hasNext()) {
	        return iterator.next().getIdentity();
		}
		return null;
    }
    
    public Token getToken() {
    	Iterator<TokenPrincipal> iterator = subject.getPrincipals(TokenPrincipal.class).iterator();
		if (iterator.hasNext()) {
	        return iterator.next().getToken();
		}
		return null;
    }
    
    private void login(CallbackHandler callbackHander) throws LoginException {
    	loginContext = new LoginContext("OAuth", getSubject(), callbackHander, Configuration.getConfiguration());    	
    	loginContext.login();
    	setSubject(loginContext.getSubject());
    }
    
    private void setSubject(Subject subject) {
    	this.subject = subject;
    }
    
    private void setConfiguration(OAuthServiceProvider serviceProvider) {
    	OAuthConfig oauthConfig = new OAuthConfig(serviceProvider);									
		Configuration.setConfiguration(oauthConfig);
    }
}