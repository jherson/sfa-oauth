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

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.sfa.login.oauth.callback.OAuthCodeCallback;
import com.sfa.login.oauth.callback.OAuthRefreshTokenCallback;
import com.sfa.login.oauth.model.Identity;
import com.sfa.login.oauth.model.OAuth;
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
	private OAuth oauth;

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		
		if (success) {
			subject.getPrincipals().add(new OAuthPrincipal(oauth));	
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
		
		String instance = options.get("instance").toString(); 
		String clientId = options.get("clientId").toString();
		String clientSecret = options.get("clientSecret").toString();
		String redirectUri = options.get("redirectUri").toString();
		
		String authResponse = null;
		
		//if (callbackHandler instanceof OAuthCodeCallback) {
			
			Callback[] callbacks = new Callback[1];
			callbacks[0] = new OAuthCodeCallback();
	
			try {
				callbackHandler.handle(callbacks);
			} catch (IOException e) {
				throw new LoginException("IOException calling handle on callbackHandler");
			} catch (UnsupportedCallbackException e) {
				throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
			}
			
			OAuthCodeCallback callback = (OAuthCodeCallback) callbacks[0];		
									
			authResponse = oauthService.getAuthResponse(instance, clientId, clientSecret, redirectUri, callback.getCode());
			
		//} else if (callbackHandler instanceof OAuthRefreshTokenCallback) {
			
		//	Callback[] callbacks = new Callback[1];
		//	callbacks[0] = new OAuthRefreshTokenCallback();
	
		//	try {
		//		callbackHandler.handle(callbacks);
		//	} catch (IOException e) {
		//		throw new LoginException("IOException calling handle on callbackHandler");
		//	} catch (UnsupportedCallbackException e) {
		//		throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		//	}
			
		//	OAuthRefreshTokenCallback callback = (OAuthRefreshTokenCallback) callbacks[0];	
			
		//	authResponse = oauthService.refreshAuthToken(instance, clientId, clientSecret, callback.getRefreshToken());
			
		//}						
				
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
		oauthService.revokeToken(options.get("instance").toString(), oauth.getAccessToken());		
		subject.getPrincipals(OAuthPrincipal.class).clear();
		return true;
	}	
}