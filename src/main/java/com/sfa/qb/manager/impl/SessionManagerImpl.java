package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Iterator;
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
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.bitwalker.useragentutils.UserAgent;

import com.google.gson.Gson;
import com.sfa.qb.controller.MainController;
import com.sfa.qb.controller.TemplatesEnum;
import com.sfa.qb.login.oauth.OAuthCallbackHandler;
import com.sfa.qb.login.oauth.OAuthPrincipal;
import com.sfa.qb.login.oauth.model.OAuth;
import com.sfa.qb.manager.SessionManager;
import com.sfa.qb.model.entities.LoginHistory;
import com.sfa.qb.model.entities.UserPreferences;
import com.sfa.qb.model.sobject.User;
import com.sfa.qb.qualifiers.SessionUser;
import com.sfa.qb.service.ServicesManager;
import com.sfa.qb.service.PersistenceService;
import com.sfa.qb.util.DateUtil;

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
	private PersistenceService loginHistoryWriter;
		
	@Inject
	private ServicesManager servicesManager;
	
	@Inject
	private MainController mainController;
	
	@Produces
	@Named
	@SessionUser
	private User sessionUser;
	
	public User getSessionUser() {
		if (sessionUser == null)
			this.authenticate();
		
		return sessionUser;
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
    	
    	UserPreferences preferences = null;
    	if (sessionUser.getUserPreferences() != null) {
    		preferences = sessionUser.getUserPreferences();
    	} else {
    		preferences = new UserPreferences();
    	}
    	
    	preferences.setUserId(sessionUser.getId());
    	preferences.setTheme(this.theme);
    	
    	sessionUser.setUserPreferences(preferences);
    	
    	loginHistoryWriter.saveUserPreferences(preferences);
    }

	public String getTheme() {				
		return theme;
	}
	
	private String INDEX_PAGE;
	
	private LoginContext loginContext;		

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
		 * revoke the Salesforce OAuth token and remove the Subject from SecurityContext
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
	    
	    /**
	     * redirect back to the index page
	     */
	    
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
				/**
				 * call the login context to validate user session
				 */
				
				loginContext = new LoginContext("OAuthRealm", new OAuthCallbackHandler(code));				
				loginContext.login();
				
				/**
				 * get returned OAuth token
				 */
				
				OAuth oauth = null;
			    					    			    	
                Subject subject = loginContext.getSubject();
                Iterator<Principal> iterator = subject.getPrincipals().iterator();
    	    	while (iterator.hasNext()) {
    	    		Principal principal = iterator.next();
    	    		if (principal instanceof OAuthPrincipal) {
    	    			OAuthPrincipal oauthPrincipal = (OAuthPrincipal) principal;
    	    			oauth = oauthPrincipal.getOAuth();
    	    			logger.info("AccessToken: " + oauth.getAccessToken());
    	    		}
    	    	}
    	    	
    	    	/**
    	    	 * get the session user details and add oauth token
    	    	 * set date and datetime format patterns
    	    	 */
    	    	
    	    	sessionUser = new Gson().fromJson(servicesManager.getCurrentUserInfo(), User.class);
				sessionUser.setOAuth(oauth);
				sessionUser.setLocale(DateUtil.stringToLocale(sessionUser.getLocaleSidKey()));
				sessionUser.setDateFormatPattern(DateUtil.getDateFormat(sessionUser.getLocale()));
				sessionUser.setDateTimeFormatPattern(DateUtil.getDateTimeFormat(sessionUser.getLocale()));
			    
				/**
				 * write login history
				 */
				
				LoginHistory history = new LoginHistory();
				history.setRemoteAddress(request.getRemoteAddr());
				history.setName(sessionUser.getUserName());
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
			    
			    /**
			     * set the session locale
			     */
				
				if (sessionUser.getLocale() != null) {
					FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getLocale());			
				} 		
				
				/**
				 * get the users preferences
				 */
				
			    UserPreferences userPreferences = entityManager.find(UserPreferences.class, oauth.getIdentity().getUserId());
			    if (userPreferences != null) {
				    sessionUser.setUserPreferences(userPreferences);
			    } else {
			    	sessionUser.setUserPreferences(new UserPreferences());
			    }
			    
				/**
				 * set LoggedIn
				 */
				
				setLoggedIn(Boolean.TRUE);
				
				/**
				 * set the template to the home page 
				 */
				
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