package com.sfa.persistence.connection;

import java.text.MessageFormat;
import java.util.logging.Logger;

import com.sfa.qb.login.oauth.model.OAuth;
import com.sfa.qb.util.Util;
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

    static {
    	PARTNER_CONNECTION = new ThreadLocal<PartnerConnection>();
    }
    
    public PartnerConnection openConnection() throws ConnectionException {  
    	
    	if (getConnection() != null)
    		return getConnection();

    	OAuth oauth = Util.getOAuthPrincipal().getOAuth();
		if (oauth != null) {						
			log.info(oauth.getAccessToken());
			return openConnection(oauth.getAccessToken());	    	
		} else {			
			return openDefaultConnection();
		}
    }
        
    public PartnerConnection openConnection(String sessionId) throws ConnectionException {
    	
    	if (getConnection() != null)
    		return getConnection();
    	    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setServiceEndpoint(System.getProperty("salesforce.service.endpoint"));
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionId);
    	
    	PartnerConnection connection = Connector.newConnection(config);
    	    	
    	setConnection(connection);
    	
    	return getConnection();
    }
    
    public PartnerConnection openConnection(String authEndpoint, String username, String password) throws ConnectionException {
    	
    	if (getConnection() != null)
    		return getConnection();
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(authEndpoint);
    	config.setUsername(username);
		config.setPassword(password);
						
		PartnerConnection connection = Connector.newConnection(config);
		
		setConnection(connection);
		
		return getConnection();
    }
    
    public void setConnection(PartnerConnection connection) {
    	PARTNER_CONNECTION.set(connection);
    }

    private PartnerConnection getConnection() {
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
    
    private PartnerConnection openDefaultConnection() throws ConnectionException {  	
  
	   	ConnectorConfig config = new ConnectorConfig();
	   	config.setAuthEndpoint(System.getProperty("salesforce.authEndpoint"));
	   	config.setUsername(System.getProperty("salesforce.username"));
		config.setPassword(System.getProperty("salesforce.password"));
		config.setSessionRenewer(this);
							
		PartnerConnection connection = Connector.newConnection(config);
			
		setConnection(connection);
			
		return getConnection();
    }
}