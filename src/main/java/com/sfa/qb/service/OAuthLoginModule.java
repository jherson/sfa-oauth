package com.sfa.qb.service;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jboss.as.controller.security.SecurityContext;

import com.sfa.qb.model.auth.OAuth;

@SuppressWarnings("unused")
public class OAuthLoginModule implements LoginModule {
	
	private static Logger log = Logger.getLogger(OAuthLoginModule.class.getName()); 	
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
		
		if (oauth == null)
			return false;
		
	    subject.getPrincipals().add(new OAuthPrincipal(oauth));
	    SecurityContext.setSubject(subject);
	    return true;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;				
	}

	@Override
	public boolean login() throws LoginException {
		
		Callback[] callbacks = new Callback[1];
		callbacks[0] = new OAuthCallback();

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		}
		
		OAuthCallback oauthCallback = (OAuthCallback) callbacks[0];
		
		oauth = oauthCallback.getOauth();
				
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().clear();
		return true;
	}
}
