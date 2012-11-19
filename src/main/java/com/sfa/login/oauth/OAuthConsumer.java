package com.sfa.login.oauth;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import com.sfa.login.oauth.callback.OAuthCodeCallbackHandler;
import com.sfa.login.oauth.callback.OAuthRefreshTokenCallbackHandler;
import com.sfa.login.oauth.callback.OAuthUserNamePasswordCallbackHandler;
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
    	OAuthConfig oauthConfig = new OAuthConfig(serviceProvider);									
		Configuration.setConfiguration(oauthConfig);
    }

    public String getOAuthTokenUrl() throws UnsupportedEncodingException {
    	AppConfigurationEntry[] entries = Configuration.getConfiguration().getAppConfigurationEntry("com.sfa.login.oauth.OAuthLoginModule");    	
		Map<String,?> optionsMap = entries[0].getOptions();
		
    	return optionsMap.get("instance") 
    			+ "/services/oauth2/authorize?response_type=code"
				+ "&client_id=" + optionsMap.get("clientId")
				+ "&redirect_uri=" + URLEncoder.encode(String.valueOf(optionsMap.get("redirectUri")), "UTF-8")
				+ "&scope=" + URLEncoder.encode(String.valueOf(optionsMap.get("scope")), "UTF-8")
				+ "&prompt=" + optionsMap.get("prompt")
				+ "&display=" + optionsMap.get("display")
				+ "&startURL=" + optionsMap.get("startUrl");        					
    }
    
    public void login(FacesContext context) throws UnsupportedEncodingException, IOException {
    	context.getExternalContext().redirect(getOAuthTokenUrl());
    }
    
    public void login(HttpServletResponse response) throws UnsupportedEncodingException, IOException {
    	response.sendRedirect(getOAuthTokenUrl());
    }
    
    public void login(String username, String password, String securityToken) throws LoginException {
    	loginContext = new LoginContext("OAuth", new OAuthUserNamePasswordCallbackHandler(username, password, securityToken));	
    	loginContext.login();	
    	setSubject(loginContext.getSubject());
    }
    
    public void authenticate(String code) throws LoginException {    	
		loginContext = new LoginContext("OAuth", new OAuthCodeCallbackHandler(code));	
		loginContext.login();
		setSubject(loginContext.getSubject());
    }
    
    public void refreshToken(String refreshToken) throws LoginException {  	
    	loginContext = new LoginContext("OAuth", new OAuthRefreshTokenCallbackHandler(refreshToken));	
    	loginContext.login();
    	setSubject(loginContext.getSubject());
    }
    
    public void logout() throws LoginException {
    	loginContext.logout();
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
    
    private void setSubject(Subject subject) {
    	this.subject = subject;
    }
}