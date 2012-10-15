package com.sfa.qb.auth;

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

import com.google.gson.Gson;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.auth.Identity;
import com.sfa.qb.model.auth.OAuth;
import com.sfa.qb.service.ServicesManager;
import com.sfa.qb.service.impl.ServicesManagerImpl;

@SuppressWarnings("unused")
public class OAuthLoginModule implements LoginModule {
	
	private static Logger log = Logger.getLogger(OAuthLoginModule.class.getName()); 	
	private Boolean success;
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	
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
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;				
	}

	@Override
	public boolean login() throws LoginException {
		
		Callback[] callbacks = new Callback[1];
		callbacks[0] = new OAuthCodeCallback();

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		}
		
		OAuthCodeCallback oauthCodeCallback = (OAuthCodeCallback) callbacks[0];
		
		try {
			ServicesManager servicesManager = new ServicesManagerImpl();
			String authResponse = servicesManager.getAuthResponse(oauthCodeCallback.getCode());
			
			oauth = new Gson().fromJson(authResponse, OAuth.class);
			
			if (oauth.getError() != null) {
		    	throw new FailedLoginException(oauth.getErrorDescription());		 
		    }	
		    			    			
		    String identityResponse = servicesManager.getIdentity(oauth.getInstanceUrl(), oauth.getId(), oauth.getAccessToken());
		    Identity identity = new Gson().fromJson(identityResponse, Identity.class);
		    
		    oauth.setIdentity(identity);
		    
		    success = Boolean.TRUE;
		    
		} catch (SalesforceServiceException e) {
			throw new FailedLoginException(e.getMessage());
		} 
		
		return success;
	}

	@Override
	public boolean logout() throws LoginException {
		SecurityContext.clearSubject();
		return true;
	}
}
