package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.bitwalker.useragentutils.UserAgent;

import org.jboss.resteasy.client.ClientRequest;

import com.google.gson.Gson;
import com.sfa.qb.controller.MainController;
import com.sfa.qb.controller.TemplatesEnum;
import com.sfa.qb.manager.SessionManager;
import com.sfa.qb.model.auth.Identity;
import com.sfa.qb.model.auth.OAuth;
import com.sfa.qb.model.auth.SessionUser;
import com.sfa.qb.model.entities.LoginHistory;
import com.sfa.qb.model.entities.UserPreferences;
import com.sfa.qb.qualifiers.LoggedIn;
import com.sfa.qb.service.LoginHistoryWriter;
import com.sfa.qb.service.OAuthCallbackHandler;
import com.sfa.qb.service.ServicesManager;

@SessionScoped
@Named(value="sessionManager")

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;
	
	@Inject
	private FacesContext context;
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private LoginHistoryWriter loginHistoryWriter;
		
	@Inject
	private ServicesManager servicesManager;
	
	@Inject
	private MainController mainController;
				
	private LoginContext loginContext;		
	
	private SessionUser sessionUser;		

	@Produces
	@LoggedIn
	@Named
	public SessionUser getSessionUser() {
		if (sessionUser == null)
		    this.authenticate();
				
		return sessionUser;
	}
	
	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}
	
	
	private UserPreferences userPreferences;
	
	public UserPreferences getUserPreferences() {								
		return userPreferences;
	}
	
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}
	
	
	@ManagedProperty(value = "false")
	private Boolean loggedIn;
	

	public Boolean getLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
		
	@ManagedProperty(value = "classic")
	private String theme;
	
    public void setTheme(String theme) {
    	if ("none".equals(theme)) {
    		this.theme = null;
    	} else {
    	    this.theme = theme;
    	}
    	
    	UserPreferences preferences = new UserPreferences();
    	preferences.setUserId(sessionUser.getId());
    	preferences.setTheme(this.theme);
    	
    	loginHistoryWriter.saveUserPreferences(preferences);
    }

	public String getTheme() {				
		return theme;
	}
	
	
	@ManagedProperty(value = "false")
	private Boolean editMode;

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public Boolean getEditMode() {
		return editMode;
	}
	
	@ManagedProperty(value = "false")
	private Boolean goalSeek;

	public void setGoalSeek(Boolean goalSeek) {
		this.goalSeek = goalSeek;
	}

	public Boolean getGoalSeek() {
		return goalSeek;
	}
	
	private String INDEX_PAGE;

	@PostConstruct
	public void init() {
		logger.info("init");			
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
						
		logger.info("accept language: " + request.getHeader("Accept-Language"));
				
		//en-US,en;q=0.8
		
		
		this.INDEX_PAGE = context.getExternalContext().getRequestContextPath() + "/index.jsf";
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("destroy");
		if (getLoggedIn())
		    logout();
	}
	
	@Override
	public void logout() {
		logger.info("logout");
		
		/**
		 * revoke the Salesforce OAuth token
		 */
		
		String revokeUrl = System.getProperty("salesforce.environment") 
					+ "/services/oauth2/revoke?" 
					+ "token=" + sessionUser.getOAuth().getAccessToken();
						
		ClientRequest request = new ClientRequest(revokeUrl);
		
		try {			
			request.post();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}
		
		/**
		 * remove the Subject from SecurityContext
		 */
		
		try {
			loginContext.logout();
		} catch (LoginException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}				
		
		setLoggedIn(Boolean.FALSE);
		
		/**
		 * invalidate the session
		 */
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.invalidate();
	    
	    redirect(INDEX_PAGE);	    
	}
	
	@Override
	public void login() {
											   				
		String authUrl = null;
		try {
			authUrl = System.getProperty("salesforce.environment")
					+ "/services/oauth2/authorize?response_type=code"
					+ "&client_id=" + System.getProperty("salesforce.oauth.clientId")
					+ "&redirect_uri=" + URLEncoder.encode(System.getProperty("salesforce.oauth.redirectUri"), "UTF-8")
					+ "&scope=" + URLEncoder.encode("full refresh_token", "UTF-8")
					+ "&prompt=login"
					+ "&display=popup";
												
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}					
				
		mainController.setMainArea(TemplatesEnum.INITIALIZE);
		
		redirect(authUrl);
	}
	
	public void authenticate() {					
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();	
		
		String code = request.getParameter("code");
		
		if (code != null) {
												
			try {					
			    String authResponse = servicesManager.getAuthResponse(code);
			    OAuth oauth = new Gson().fromJson(authResponse, OAuth.class);
	
			    if (oauth.getError() != null) {
			    	throw new Exception(oauth.getErrorDescription());		 
			    }	
			    
			    logger.info("AccessToken: " + oauth.getAccessToken());
			    			    			
			    String identityResponse = servicesManager.getIdentity(oauth.getInstanceUrl(), oauth.getId(), oauth.getAccessToken());
			    Identity identity = new Gson().fromJson(identityResponse, Identity.class);
			    
			    oauth.setIdentity(identity);
			    					    			    				
				loginContext = new LoginContext("OAuthRealm", new OAuthCallbackHandler(oauth));				
				loginContext.login();

                sessionUser = new SessionUser(oauth, identity);
			    			    				
				LoginHistory history = new LoginHistory();
				history.setRemoteAddress(request.getRemoteAddr());
				history.setName(sessionUser.getName());
				history.setLoginTime(new Timestamp(System.currentTimeMillis()));
				
				String userAgentString = request.getHeader("user-agent");				
				
				if (userAgentString != null) {
					
				    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);				    
				    history.setUserAgent(userAgentString);
				    history.setBrowser(userAgent.getBrowser().getName());
				    history.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
				    history.setOperatingSystem(userAgent.getOperatingSystem().getName());
				}
			    
			    loginHistoryWriter.write(history);
				
				if (sessionUser.getLocale() != null) {
					FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getLocale());			
				} else {
					FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getIdentity().getLocale());		
				}			
				
				if (userPreferences == null) {
				    userPreferences = entityManager.find(UserPreferences.class, sessionUser.getId());
				    theme = userPreferences.getTheme();
				}
																											
				setLoggedIn(Boolean.TRUE);			
				mainController.setMainArea(TemplatesEnum.HOME);
				
			} catch (LoginException e) {
				logger.log(Level.SEVERE, "Unable to authenticate", e);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to authenticate", e.getStackTrace()[0].toString()));				
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));	
			}	
			
		} else {
			mainController.setMainArea(TemplatesEnum.SIGN_IN);
		}
		
	    /**
		 * redirect back to index.jsf to render the proper template
		 */
		
		redirect(INDEX_PAGE);
		
	}
	
	private void redirect(String url) {
		try {
			context.getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}
	}
}