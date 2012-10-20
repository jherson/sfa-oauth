package com.sfa.qb.login.oauth;

import java.io.IOException;
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
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.login.oauth.model.Identity;
import com.sfa.qb.login.oauth.model.OAuth;
import com.sfa.qb.service.OAuthService;
import com.sfa.qb.service.ServicesManager;
import com.sfa.qb.service.impl.OAuthServiceImpl;
import com.sfa.qb.service.impl.ServicesManagerImpl;
import com.sforce.ws.ConnectionException;

@SuppressWarnings("unused")
public class OAuthLoginModule implements LoginModule {
	
	private static Logger log = Logger.getLogger(OAuthLoginModule.class.getName()); 	
	private Boolean success;
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	
	private OAuthService oauthService;
	private String instance;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private OAuth oauth;

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		
		if (success) {
			subject.getPrincipals().add(new OAuthPrincipal(oauth));
		    SecurityContext.setSubject(subject);
		} 
	    
	    return success;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.success = Boolean.FALSE;
		this.oauthService = new OAuthServiceImpl();
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;				
	}

	@Override
	public boolean login() throws LoginException {
		
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new OAuthConfigCallback();
		callbacks[1] = new OAuthCodeCallback();

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		}
		
		OAuthConfigCallback oauthConfigCallback = (OAuthConfigCallback) callbacks[0];
		OAuthCodeCallback oauthCodeCallback = (OAuthCodeCallback) callbacks[1];		
		
		instance = oauthConfigCallback.getOAuthConfig().getInstance();
		clientId = oauthConfigCallback.getOAuthConfig().getClientId();
		clientSecret = oauthConfigCallback.getOAuthConfig().getClientSecret();
		redirectUri = oauthConfigCallback.getOAuthConfig().getRedirectUri();
			
		String authResponse = oauthService.getAuthResponse(instance, clientId, clientSecret, redirectUri, oauthCodeCallback.getCode());
			
		oauth = new Gson().fromJson(authResponse, OAuth.class);
			
		if (oauth.getError() != null) {
		 	throw new FailedLoginException(oauth.getErrorDescription());		 
		}	
		    			    			
		String identityResponse = oauthService.getIdentity(oauth.getInstanceUrl(), oauth.getId(), oauth.getAccessToken());
		Identity identity = new Gson().fromJson(identityResponse, Identity.class);
		    
		oauth.setIdentity(identity);
		    
		success = Boolean.TRUE;		    
		
		return success;
	}

	@Override
	public boolean logout() throws LoginException {		
		oauthService.revokeToken(instance, oauth.getAccessToken());		
		SecurityContext.clearSubject();
		return true;
	}
}