package com.nowellpoint.oauth;

import java.io.Serializable;

import javax.security.auth.login.Configuration;

import com.nowellpoint.oauth.callback.OAuthCallbackHandler;
import com.nowellpoint.oauth.callback.OAuthFlowType;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.Verifier;

public class OAuthServiceProvider implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	
	public OAuthServiceProvider() {
		
	}
	
    public OAuthServiceProvider(OAuthConfig oauthConfig) {
    	setConfiguration(oauthConfig);
    }
	
	public OAuthCallbackHandler getOAuthCallbackHandler(Verifier verifier) {
		return new OAuthCallbackHandler(
    			OAuthFlowType.WEB_SERVER_FLOW,
    			verifier.getCode(), 
    			null, 
    			null, 
    			null, 
    			null);
	}
	
	public OAuthCallbackHandler getOAuthCallbackHandler(Credentials credentials) {
		return new OAuthCallbackHandler(
    			OAuthFlowType.USERNAME_PASSWORD_FLOW,
    			null,
    			null, 
    			credentials.getUsername(), 
    			credentials.getPassword(), 
    			credentials.getSecurityToken());
	}
	
	public OAuthCallbackHandler getOAuthCallbackHandler(Token token) {
		return new OAuthCallbackHandler(
    			OAuthFlowType.REFRESH_TOKEN_FLOW,
    			null, 
    			token.getRefreshToken(),
    			null, 
    			null, 
    			null);
	}
   
    private void setConfiguration(OAuthConfig oauthConfig) {
		Configuration.setConfiguration(oauthConfig);
    }
    
    public OAuthConfig getConfiguration() {
    	return (OAuthConfig) Configuration.getConfiguration();
    }
}