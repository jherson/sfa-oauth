package com.nowellpoint.oauth;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Iterator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nowellpoint.oauth.callback.OAuthCallbackHandler;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.Verifier;
import com.nowellpoint.oauth.service.OAuthService;
import com.nowellpoint.oauth.service.impl.OAuthServiceImpl;
import com.nowellpoint.principal.IdentityPrincipal;
import com.nowellpoint.principal.TokenPrincipal;

@SessionScoped
public class OAuthSession implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private static final String SOBJECTS_ENDPOINT = "{0}/services/data/v{1}/sobjects/";
	private static final String API_VERSION = "29.0";
	
	private static final String USER_FIELDS = "Id,Username,LastName,FirstName,Name,CompanyName,Division,Department," +
			"Title,Street,City,State,PostalCode,Country,Latitude,Longitude," +
			"Email,SenderEmail,SenderName,Signature,Phone,Fax,MobilePhone,Alias," +
			"CommunityNickname,IsActive,TimeZoneSidKey,UserRole.Id,UserRole.Name,LocaleSidKey," +
			"EmailEncodingKey,Profile.Id,Profile.Name,Profile.PermissionsCustomizeApplication," +
			"UserType,LanguageLocaleKey,EmployeeNumber,DelegatedApproverId,ManagerId,AboutMe";
		
	private static final String ORGANIZATION_FIELDS = "Id,Name,Division,Street,City,State,PostalCode,Country," +
			"PrimaryContact,DefaultLocaleSidKey,LanguageLocaleKey,FiscalYearStartMonth";
	
	private OAuthServiceProvider oauthServiceProvider;
	private LoginContext loginContext;
	private Subject subject;
	private Token token;
	private Identity identity;
	
	public OAuthSession() {
		
	}
	
	public void setOAuthServiceProvider(OAuthServiceProvider oauthServiceProvider) {
		this.oauthServiceProvider = oauthServiceProvider;
	}
       
    public void login(HttpServletResponse response) throws LoginException {
    	
    	/**
    	 * initialize a new Subject 
    	 */
    	
    	setSubject(new Subject());
    	
    	/**
		 * get the OAuth URL from the configured OAuthServiceProvider
		 */
		
		String authUrl = oauthServiceProvider.getAuthorizationUrl();
		
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
    	 * initialize a new Subject 
    	 */
    	
    	setSubject(new Subject());
    	
    	/**
		 * build the OAuth URL based on the flow from options
		 */
		
    	String authUrl = oauthServiceProvider.getAuthorizationUrl();
		
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
    
    public void login(String username, String password, String securityToken) throws LoginException {    	  
    	Credentials credentials = new Credentials(username, password, securityToken);
    	OAuthCallbackHandler callbackHandler = oauthServiceProvider.getOAuthCallbackHandler(credentials);
    	login(callbackHandler);
    }
    
    public void verify(String code) throws LoginException {    	
    	Verifier verifier = new Verifier(code);
    	OAuthCallbackHandler callbackHandler = oauthServiceProvider.getOAuthCallbackHandler(verifier);
    	login(callbackHandler);
    }
    
    public void refreshToken() throws LoginException {  			
    	OAuthCallbackHandler callbackHandler = oauthServiceProvider.getOAuthCallbackHandler(token);
    	login(callbackHandler);
    }
    
    public void logout() throws LoginException {
    	loginContext.logout();
    	setSubject(null);
    	setToken(null);
    	setIdentity(null);
    }
    
    public Subject getSubject() {
    	return subject;
    }
    
    public Token getToken() {
    	return token;
    }
    
    public Identity getIdentity() {
    	return identity;
    }
    
    public String getUserInfo() throws LoginException {
    	String instanceUrl = getToken().getInstanceUrl();
    	String accessToken = getToken().getAccessToken();
    	String userId = getIdentity().getUserId();
    	
    	OAuthService oauthService = new OAuthServiceImpl();
    	String sobject = oauthService.getSObject(getUserInfoUrl(instanceUrl, userId), accessToken);
    	
    	System.out.println(sobject);
    	
    	return sobject;
    }
    
    public OrganizationInfo getOrganizationInfo() throws LoginException {
    	String instanceUrl = getToken().getInstanceUrl();
    	String accessToken = getToken().getAccessToken();
    	String organizationId = getIdentity().getOrganizationId();
    	
    	OAuthService oauthService = new OAuthServiceImpl();
    	String sobject = oauthService.getSObject(getOrganizationInfoUrl(instanceUrl, organizationId), accessToken);
    	
    	System.out.println(sobject);
    	
    	OrganizationInfo organizationInfo = new Gson().fromJson(sobject, OrganizationInfo.class);
    	
    	return organizationInfo;
    }
    
    private void login(CallbackHandler callbackHander) throws LoginException {
    	loginContext = new LoginContext("OAuth", getSubject(), callbackHander, Configuration.getConfiguration());    	
    	loginContext.login();
    	setSubject(loginContext.getSubject());
    	setToken(getToken(loginContext.getSubject()));
    	setIdentity(getIdentity(loginContext.getSubject()));
    }
    
    private void setSubject(Subject subject) {
    	this.subject = subject;
    }
    
    private void setToken(Token token) {
    	this.token = token;
    }
    
    private void setIdentity(Identity identity) {
    	this.identity = identity;
    }
    
	private String getUserInfoUrl(String instanceUrl, String userId) {
		return new StringBuilder().append(MessageFormat.format(SOBJECTS_ENDPOINT, instanceUrl, API_VERSION))
				.append("User/")
				.append(userId)
				.append("?fields=")
				.append(USER_FIELDS)
				.toString();
	}
	
	private String getOrganizationInfoUrl(String instanceUrl, String userId) {
		return new StringBuilder().append(MessageFormat.format(SOBJECTS_ENDPOINT, instanceUrl, API_VERSION))
				.append("Organization/")
				.append(userId)
				.append("?fields=")
				.append(ORGANIZATION_FIELDS)
				.toString();
	}
	
	private Identity getIdentity(Subject subject) {
	    Iterator<IdentityPrincipal> iterator = subject.getPrincipals(IdentityPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getIdentity();
		}
		return null;
	}
	
	private Token getToken(Subject subject) {
	    Iterator<TokenPrincipal> iterator = subject.getPrincipals(TokenPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getToken();
	    }
		return null;
	}	
}