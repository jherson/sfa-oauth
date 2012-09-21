package com.sfa.persistence.connection;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

import com.sfa.qb.model.auth.SessionUser;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SessionHeader_element;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.SessionRenewer;

public class Connection implements SessionRenewer {	
	
	private static final Logger log = Logger.getLogger(Connection.class.getName());
	
	public static final javax.xml.namespace.QName SESSION_HEADER_QNAME =
	        new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "SessionHeader");

    private static final ThreadLocal<PartnerConnection> PARTNER_CONNECTION;
    
    private static final ThreadLocal<SessionUser> SESSION_USER;

    static {
    	PARTNER_CONNECTION = new ThreadLocal<PartnerConnection>();
        SESSION_USER = new ThreadLocal<SessionUser>();
    }
    
    public void openConnection() throws ConnectionException {
    	
    	loadConnectionProperties();    	
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(MessageFormat.format(System.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
    	config.setUsername(System.getProperty("salesforce.username"));
		config.setPassword(System.getProperty("salesforce.password"));
		config.setSessionRenewer(this);
						
		PartnerConnection connection = Connector.newConnection(config);
		
		System.setProperty("salesforce.default.locale", connection.getUserInfo().getUserLocale());
		System.setProperty("salesforce.service.endpoint", connection.getConfig().getServiceEndpoint());
		System.setProperty("salesforce.api.endpoint", connection.getConfig().getServiceEndpoint().substring(0, connection.getConfig().getServiceEndpoint().indexOf("/Soap")));
				
		setConnection(connection);
    }
    
    public void openConnection(String sessionId) throws ConnectionException {
    	    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setServiceEndpoint(System.getProperty("salesforce.service.endpoint"));
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionId);
    	
    	PartnerConnection connection = Connector.newConnection(config);
    	    	
    	setConnection(connection);
    }
    
    public void openConnection(String username, String password) throws ConnectionException {
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(MessageFormat.format(System.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
    	config.setUsername(username);
		config.setPassword(password);
						
		PartnerConnection connection = Connector.newConnection(config);
		
		setConnection(connection);
    }
    
    public void openConnection(SessionUser sessionUser) throws ConnectionException {
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setServiceEndpoint(sessionUser.getIdentity().getUrls().getPartner().replace("{version}", System.getProperty("salesforce.api.version")));
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionUser.getOAuth().getAccessToken());
    	
    	PartnerConnection connection = Connector.newConnection(config);
    	    	
    	setConnection(connection);
    	
    	setSessionUser(sessionUser);
    }
    
    public void setSessionUser(SessionUser sessionUser) {
    	SESSION_USER.set(sessionUser);
    }
    
    public SessionUser getSessionUser() {
    	return SESSION_USER.get();
    }
    
    public void setConnection(PartnerConnection connection) {
    	PARTNER_CONNECTION.set(connection);
    }

    public PartnerConnection getConnection() {
    	return PARTNER_CONNECTION.get();
    }
    
    public void closeConnection() throws ConnectionException {
    	PARTNER_CONNECTION.remove();
    }
    
    public void logout() throws ConnectionException {
    	if (getConnection() != null)
    		getConnection().logout();
    }

    @Override
    public SessionRenewalHeader renewSession(ConnectorConfig config) throws ConnectionException {
    	log.info("renewing session");
    	
    	if (config.getPassword() != null) {
    		PartnerConnection connection = Connector.newConnection(config);	
    		config = connection.getConfig();
    	} else {
    		return null;
    	}
    	
        SessionRenewalHeader renewalHeader = new SessionRenewalHeader();
        renewalHeader.name = SESSION_HEADER_QNAME;
        SessionHeader_element element = new SessionHeader_element();
        element.setSessionId(config.getSessionId());
        renewalHeader.headerElement = element;
        
        return renewalHeader;
    }
    
    private void loadConnectionProperties() {
    	
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties"));
		} catch (IOException e) {
			log.severe("Unable to load quotebuilder.properties file: " + e.getMessage());
		}	
		
		for (String key : props.stringPropertyNames()) {
		    String value = props.getProperty(key);
			System.out.println(key + " => " + value);
			System.setProperty(key, value);
		}

		//System.setProperty("salesforce.environment", properties.getProperty("salesforce.environment"));
		//System.setProperty("salesforce.authEndpoint", "{0}/services/Soap/u/25.0");
		//System.setProperty("salesforce.username", "intadmin@redhat.com.vpm");
		//System.setProperty("salesforce.password", "fedora10H8wBs6OlRuP4OWGu4nQNHZox");
		//System.setProperty("salesforce.api.version", "25.0");
		//System.setProperty("salesforce.oauth.clientId", "3MVG94DzwlYDSHS6zfNz9quij13YGleDlZ9u7Le_XIqGTcYWo6Sp7FJJDI4KrvSMjfwhjfpZm_hD_8Ou9fj4w");
		//System.setProperty("salesforce.oauth.clientSecret", "5180567430750494425");
		//System.setProperty("salesforce.oauth.redirectUri", "https://jherson-ws.devlab.phx1.redhat.com:8443/quotebuilder/index.jsf");
    }
}