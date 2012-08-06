package com.redhat.sforce.persistence.connection;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.redhat.sforce.qb.model.auth.OAuth;
import com.redhat.sforce.qb.model.auth.SessionUser;
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
    	Properties properties = getProperties();
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
    	config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));
		config.setSessionRenewer(this);
						
		PartnerConnection connection = Connector.newConnection(config);
		
		ConnectionProperties.setServiceEndpoint(connection.getConfig().getServiceEndpoint());
		
		setConnection(connection);
    }
    
    public void openConnection(String sessionId) throws ConnectionException {
    	    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setServiceEndpoint(ConnectionProperties.getServiceEndpoint());
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionId);
    	config.setSessionRenewer(this);
    	
    	PartnerConnection connection = Connector.newConnection(config);
    	    	
    	setConnection(connection);
    }
    
    public void openConnection(String username, String password) throws ConnectionException {
    	Properties properties = getProperties();
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
    	config.setUsername(username);
		config.setPassword(password);
		config.setSessionRenewer(this);
						
		PartnerConnection connection = Connector.newConnection(config);
		
		ConnectionProperties.setServiceEndpoint(connection.getConfig().getServiceEndpoint());
		
		setConnection(connection);
    }
    
    public void openConnection(SessionUser sessionUser) throws ConnectionException {    	
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setServiceEndpoint(ConnectionProperties.getServiceEndpoint());
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionUser.getOAuth().getAccessToken());
    	config.setSessionRenewer(this);
    	
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
    
    private Properties getProperties() throws ConnectionException {
    	InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			throw new ConnectionException("Unable to load quotebuilder.properties file");
		}
		return properties;
    }

    @Override
    public SessionRenewalHeader renewSession(ConnectorConfig config) throws ConnectionException {
    	log.info("renewing session");
    	
    	if (config.getPassword() != null) {
    		PartnerConnection connection = Connector.newConnection(config);	
    		config = connection.getConfig();
    	} else if (getSessionUser() != null){
    		config.setManualLogin(Boolean.TRUE);
    		
    		String url = System.getProperty("salesforce.environment") + "/services/oauth2/token";
            
    		ClientRequest request = new ClientRequest(url);
    		request.header("Content-type", "application/json");		
    		request.queryParameter("grant_type", "refresh_token");		
    		request.queryParameter("client_id", System.getProperty("salesforce.oauth.clientId"));
    		request.queryParameter("client_secret", System.getProperty("salesforce.oauth.clientSecret"));
    		request.queryParameter("refresh_token", getSessionUser().getOAuth().getRefreshToken());
    		
    		ClientResponse<String> response = null;
			try {
				response = request.post(String.class);
			} catch (Exception e) {
				log.log(Level.SEVERE, "Exception", e);
				throw new ConnectionException(e.getMessage());
			}
			
    		if (response.getResponseStatus() == Status.OK) {
    			OAuth token = new Gson().fromJson(response.toString(), OAuth.class);
    			config.setSessionId(token.getAccessToken());
    		}
    	} else {
    		return null;
    	}
    	
        SessionRenewalHeader ret = new SessionRenewalHeader();
        ret.name = SESSION_HEADER_QNAME;
        SessionHeader_element se = new SessionHeader_element();
        se.setSessionId(config.getSessionId());
        System.out.println("renewing session: " + se.getSessionId());
        ret.headerElement = se;
        return ret;
    }
}