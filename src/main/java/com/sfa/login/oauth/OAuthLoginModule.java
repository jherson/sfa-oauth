package com.sfa.login.oauth;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jboss.as.controller.security.SecurityContext;

import com.google.gson.Gson;
import com.sfa.login.oauth.callback.OAuthCallback;
import com.sfa.login.oauth.callback.OAuthFlowType;
import com.sfa.login.oauth.model.Identity;
import com.sfa.login.oauth.model.Token;
import com.sfa.login.oauth.principal.IdentityPrincipal;
import com.sfa.login.oauth.principal.TokenPrincipal;
import com.sfa.login.oauth.service.OAuthService;
import com.sfa.login.oauth.service.impl.OAuthServiceImpl;

@SuppressWarnings("unused")
public class OAuthLoginModule implements LoginModule, Serializable {
	
	private static final long serialVersionUID = 1328002810741307326L;

	private static Logger log = Logger.getLogger(OAuthLoginModule.class.getName()); 	
	
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	
	private OAuthService oauthService;
	
	private Boolean success;
	private Token token;
	private Identity identity;

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		
		subject.getPrincipals(TokenPrincipal.class).clear();
	    subject.getPrincipals(IdentityPrincipal.class).clear();
		
		if (success) {
		    
			subject.getPrincipals().add(new TokenPrincipal(token));	
			subject.getPrincipals().add(new IdentityPrincipal(identity));
			
			SecurityContext.setSubject(subject);
		} 
	    
	    return success;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;						
		this.success = Boolean.FALSE;
		this.oauthService = new OAuthServiceImpl();
	}

	@Override
	public boolean login() throws LoginException {
		
		/**
		 * read in oauth parameters from handler options map
		 */
		
		String tokenUrl = getTokenUrl();
		String clientId = getClientId();
		String clientSecret = getClientSecret();
		String redirectUri = getRedirectUri();
		
		/**
		 * process the callback hander
		 */
							
		Callback[] callbacks = new Callback[1];
		callbacks[0] = new OAuthCallback();
	
		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		}
			
		OAuthCallback callback = (OAuthCallback) callbacks[0];
						
		/**
		 * get authResponse from Salesforce based on the flow type 
		 */
		
		String authResponse = null;	
		
		if (callback.getFlowType().equals(OAuthFlowType.WEB_SERVER_FLOW)) {
			authResponse = oauthService.getAuthResponse(tokenUrl, clientId, clientSecret, redirectUri, callback.getCode());
		} else if (callback.getFlowType().equals(OAuthFlowType.REFRESH_TOKEN_FLOW)) {
			authResponse = oauthService.refreshAuthToken(tokenUrl, clientId, clientSecret, callback.getRefreshToken());
		} else if (callback.getFlowType().equals(OAuthFlowType.USERNAME_PASSWORD_FLOW)) {	
			authResponse = oauthService.getAuthResponse(tokenUrl, clientId, clientSecret, callback.getUsername(), callback.getPassword(), callback.getSecurityToken());
		} else if (callback.getFlowType().equals(OAuthFlowType.USER_AGENT_FLOW)) {
			authResponse = oauthService.getAuthResponse(getAuthUrl(), clientId, redirectUri);
		} else {
			throw new LoginException("Unsupported authorization flow: " + callback.getFlowType());
		}
		
		/**
		 * parse the authResponse response into a Token object
		 */
																		
		token = new Gson().fromJson(authResponse, Token.class);
				
		if (token.getError() != null) {
		 	throw new FailedLoginException(token.getError() + ": " + token.getErrorDescription());		 
		}	
		
		/**
		 * query the Salesforce Identity server for the user's Identity info
		 */
			    			    			
		identity = getIdentity(token);	
		
		/**
		 * set success
		 */
		
		success = Boolean.TRUE;
		
       /**
        * return success
        */
				
		return success;
	}

	@Override
	public boolean logout() throws LoginException {		
		oauthService.revokeToken(getRevokeUrl(), token.getAccessToken());		
		subject.getPrincipals().clear();
		return true;
	}	
	
	private String getTokenUrl() throws LoginException {
		if (options.get(OAuthConstants.TOKEN_ENDPOINT) != null) {
			return String.valueOf(options.get(OAuthConstants.TOKEN_ENDPOINT));
		} else {
			throw new LoginException("Missing token url");
		}
	}
	
	private String getRevokeUrl() throws LoginException {
		if (options.get(OAuthConstants.REVOKE_ENDPOINT) != null) {
			return String.valueOf(options.get(OAuthConstants.REVOKE_ENDPOINT));
		} else {
			throw new LoginException("Missing revoke url");
		}
	}
	
	private String getAuthUrl() throws LoginException {
		if (options.get(OAuthConstants.AUTHORIZE_ENDPOINT) != null) {
			return String.valueOf(options.get(OAuthConstants.AUTHORIZE_ENDPOINT));
		} else {
			throw new LoginException("Missing authorize url");
		}
	}
	
	private String getClientId() throws LoginException {
		if (options.get(OAuthConstants.CLIENT_ID_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.CLIENT_ID_PARAMETER));
		} else {
			throw new LoginException("Missing client id parameter");
		}
	}
	
	private String getClientSecret() throws LoginException {
		if (options.get(OAuthConstants.CLIENT_SECRET_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.CLIENT_SECRET_PARAMETER));
		} else {
			throw new LoginException("Missing client secret parameter");
		}
	}
	
	private String getRedirectUri() {
		if (options.get(OAuthConstants.REDIRECT_URI_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.REDIRECT_URI_PARAMETER));
		} else {
		    return null;
		}
	}
	
	private String getScope() {
		if (options.get(OAuthConstants.SCOPE_PARAMETER) != null) {
		    return String.valueOf(options.get(OAuthConstants.SCOPE_PARAMETER));
		} else {
			return null;
		}
	}
	
	private String getPrompt() {
		if (options.get(OAuthConstants.PROMPT_PARAMETER) != null) {
		    return String.valueOf(options.get(OAuthConstants.PROMPT_PARAMETER));	
		} else {
			return null;
		}		
	}
	
	private String getDisplay() {
		if (options.get(OAuthConstants.DISPLAY_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.DISPLAY_PARAMETER));
		} else {
			return null;
		}
	}
	
	private Identity getIdentity(Token token) throws LoginException {
        String identityResponse = oauthService.getIdentity(token.getInstanceUrl(), token.getId(), token.getAccessToken());
		return new Gson().fromJson(identityResponse, Identity.class);	
	}
}
