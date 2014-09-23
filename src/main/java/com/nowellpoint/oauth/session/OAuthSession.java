package com.nowellpoint.oauth.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.client.OAuthClientRequest;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.VerificationCode;

@SessionScoped
public class OAuthSession implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private static Logger log = Logger.getLogger(OAuthSession.class.getName());
	
	private OAuthClient oauthClient;
	private String id;
	private Token token;
	private Identity identity;
	private String redirectUrl;
	
	public OAuthSession() {
		generateId();
	}
	
	public OAuthSession(OAuthClient oauthClient) {
		setOAuthClient(oauthClient);
		generateId();
	}
	
	public OAuthSession(OAuthClient oauthClient, Token token) {
		setOAuthClient(oauthClient);
		setToken(token);
		setIdentity(getIdentityByToken(token));    
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
	
	@SuppressWarnings("unchecked")
	public <T extends OAuthServiceProvider> T unwrap(Class<T> serviceProviderClass) {
		if (oauthClient.getServiceProvider().getClass().isAssignableFrom(serviceProviderClass)) {
			return (T) oauthClient.getServiceProvider();
		}
		return null;
	}
	        
    public void login(HttpServletResponse response) throws OAuthException {
    	
    	/**
		 * get the OAuth from the serviceProvider
		 */
		
		String loginUrl = oauthClient.getLoginUrl();
		
		/**
		 * do the redirect
		 */
		
		try {
			response.sendRedirect(loginUrl);
			return;
		} catch (IOException e) {
			throw new OAuthException("Unable to do the redirect: " + e);
		}
    }
    
    public void login(FacesContext context) throws OAuthException {
    	
    	/**
		 * get the OAuth from the serviceProvider
		 */
		
    	String loginUrl = oauthClient.getLoginUrl();
		
		/**
		 * do the redirect
		 */
		
		try {
			context.getExternalContext().redirect(loginUrl);
			return;
		} catch (IOException e) {
			throw new OAuthException("Unable to do the redirect: " + e);
		}	
    }
    
    public void login(Credentials credentials) throws OAuthException {
    	OAuthClientRequest.BasicTokenRequest tokenRequest = OAuthClientRequest.basicTokenRequest()
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.setUsername(credentials.getUsername())
    			.setPassword(credentials.getPassword())
    			.build();
    	
    	Token token = oauthClient.getServiceProvider().requestToken(tokenRequest);
    	
    	if (token.getError() != null) {
		 	throw new OAuthException(token.getErrorDescription());		 
		}
    	
    	setToken(token);
    	setIdentity(getIdentityByToken(token));    
    }
    
    public void verify(VerificationCode verificationCode) throws OAuthException {    	
    	OAuthClientRequest.VerifyTokenRequest tokenRequest = OAuthClientRequest.verifyTokenRequest()
    			.setCode(verificationCode.getCode())
    			.setCallbackUrl(oauthClient.getCallbackUrl())
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.build();
    	
    	Token token = oauthClient.getServiceProvider().requestToken(tokenRequest);
    	
    	if (token.getError() != null) {
		 	throw new OAuthException(token.getErrorDescription());		 
		}
    	
    	setToken(token);
    	setIdentity(getIdentityByToken(token));    
    }
    
    public void refreshToken() throws OAuthException {  			
    	OAuthClientRequest.RefreshTokenRequest refreshTokenRequest = OAuthClientRequest.refreshTokenRequest()
    			.setRefreshToken(getToken().getRefreshToken())
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.build();
    	
    	Token token = oauthClient.getServiceProvider().refreshToken(refreshTokenRequest);
    	
    	setToken(token);
    	setIdentity(getIdentityByToken(token));    
    }
    
    public void logout() throws OAuthException {
    	OAuthClientRequest.RevokeTokenRequest revokeTokenRequest = OAuthClientRequest.revokeTokenRequest()
    			.setAccessToken(getToken().getAccessToken())
    			.build();
    	
    	oauthClient.getServiceProvider().revokeToken(revokeTokenRequest);
    	
    	setToken(null);
    	setIdentity(null);
    }
    
    public Token getToken() {
    	return token;
    }
    
    public Identity getIdentity() {
    	return identity;
    }
    
    public void setRedirectUrl(String redirectUrl) {
    	this.redirectUrl = redirectUrl;
    }
    
    public String getRedirectUrl() {
    	return redirectUrl;
    }
    
	private void generateId() {
		setId(UUID.randomUUID().toString().replace("-", ""));
	}
	
	private Identity getIdentityByToken(Token token) {
		OAuthClientRequest.IdentityRequest identityRequest = OAuthClientRequest.identityRequest()
    			.setIdentityUrl(token.getId())
    			.setAccessToken(token.getAccessToken())
    			.build();
		
		Identity identity = null;
    	try {
    		identity = oauthClient.getServiceProvider().getIdentity(identityRequest);
		} catch (OAuthException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
    	
    	return identity;
	}
    
    private void setToken(Token token) {
    	this.token = token;
    }
    
    private void setIdentity(Identity identity) {
    	this.identity = identity;
    }	
}