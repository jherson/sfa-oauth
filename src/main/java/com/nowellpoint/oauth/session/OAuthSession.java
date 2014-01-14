package com.nowellpoint.oauth.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.client.OAuthClientRequest;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.Verifier;

@SessionScoped
public class OAuthSession implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private static Logger log = Logger.getLogger(OAuthSession.class.getName());
	
	private OAuthClient oauthClient;
	private String id;
	private Token token;
	private Identity identity;
	
	public OAuthSession() {
		generateId();
	}
	
	public OAuthSession(OAuthClient oauthClient) {
		setOAuthClient(oauthClient);
		generateId();
	}
	
	public String getId() {
		return id;
	}
	
	private void setId(String id) {
		this.id = id;
	}
	
	public void setOAuthClient(OAuthClient oauthClient) {
		this.oauthClient = oauthClient;
	}
	
	public <T extends OAuthServiceProvider> OAuthServiceProvider getServiceProvider() {
		return oauthClient.getServiceProvider();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends OAuthServiceProvider> T unwrap(Class<T> serviceProviderClass) {
		if (oauthClient.getServiceProvider().getClass().isAssignableFrom(serviceProviderClass)) {
			return (T) oauthClient.getServiceProvider();
		}
		return null;
	}
	        
    public void login(HttpServletResponse response) throws LoginException {
    	
    	/**
		 * get the OAuth from the serviceProvider
		 */
		
		String authUrl = oauthClient.getAuthEndpoint();
		
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
    	
    	/**
		 * get the OAuth from the serviceProvider
		 */
		
    	String authUrl = oauthClient.getAuthEndpoint();
		
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
    
    public void login(Credentials credentials) throws OAuthException {
    	OAuthClientRequest.BasicTokenRequest tokenRequest = OAuthClientRequest.basicTokenRequest()
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.setUsername(credentials.getUsername())
    			.setPassword(credentials.getPassword())
    			.build();
    	
    	Token token = getServiceProvider().requestToken(tokenRequest);
    	
    	initialize(token);
    }
    
    public void verifyToken(Verifier verifier) throws OAuthException {    	
    	OAuthClientRequest.VerifyTokenRequest tokenRequest = OAuthClientRequest.verifyTokenRequest()
    			.setCode(verifier.getCode())
    			.setCallbackUrl(oauthClient.getCallbackUrl())
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.build();
    	
    	Token token = getServiceProvider().requestToken(tokenRequest);
    	
    	if (token.getError() != null) {
		 	throw new OAuthException(token.getError() + ": " + token.getErrorDescription());		 
		}
    	
    	initialize(token);
    }
    
    public void refreshToken() throws OAuthException {  			
    	OAuthClientRequest.RefreshTokenRequest refreshTokenRequest = OAuthClientRequest.refreshTokenRequest()
    			.setRefreshToken(getToken().getRefreshToken())
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.build();
    	
    	Token token = getServiceProvider().refreshToken(refreshTokenRequest);
    	
    	initialize(token);
    }
    
    public void logout() throws OAuthException {
    	OAuthClientRequest.RevokeTokenRequest revokeTokenRequest = OAuthClientRequest.revokeTokenRequest()
    			.setAccessToken(getToken().getAccessToken())
    			.build();
    	
    	getServiceProvider().revokeToken(revokeTokenRequest);
    	
    	setToken(null);
    	setIdentity(null);
    }
    
    public Token getToken() {
    	return token;
    }
    
    public Identity getIdentity() {
    	return identity;
    }
    
	private void generateId() {
		setId(UUID.randomUUID().toString().replace("-", ""));
	}
	
	private Identity loadIdentity() {
		OAuthClientRequest.IdentityRequest identityRequest = OAuthClientRequest.identityRequest()
    			.setIdentityUrl(getToken().getId())
    			.setAccessToken(getToken().getAccessToken())
    			.build();
		
		Identity identity = null;
    	try {
    		identity = getServiceProvider().getIdentity(identityRequest);
		} catch (OAuthException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
    	
    	return identity;
	}
    
    private void initialize(Token token) throws OAuthException {    	
    	setToken(token);
    	setIdentity((Identity) Enhancer.create(Identity.class, new LazyLoader() {
    		public Identity loadObject() {
    			return loadIdentity();
    		}
    	}));    	
    }
    
    private void setToken(Token token) {
    	this.token = token;
    }
    
    private void setIdentity(Identity identity) {
    	this.identity = identity;
    }	
}