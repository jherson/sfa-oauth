package com.redhat.sforce.persistence.connection;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SessionHeader_element;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.SessionRenewer;

public class Connection implements SessionRenewer {	
	
	public static final javax.xml.namespace.QName SESSION_HEADER_QNAME =
	        new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "SessionHeader");

    private static final ThreadLocal<PartnerConnection> threadLocal;

    static {
        threadLocal = new ThreadLocal<PartnerConnection>();
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
    
    public void setConnection(PartnerConnection connection) {
    	threadLocal.set(connection);
    }

    public PartnerConnection getConnection() {
    	return threadLocal.get();
    }
    
    public void closeConnection() throws ConnectionException {
    	threadLocal.remove();
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
    	config.setManualLogin(Boolean.TRUE);
    	
    	PartnerConnection connection = Connector.newConnection(config);
    	
    	System.out.println("renewing session");
        SessionRenewalHeader ret = new SessionRenewalHeader();
        ret.name = SESSION_HEADER_QNAME;
        SessionHeader_element se = new SessionHeader_element();
        se.setSessionId(connection.getConfig().getSessionId());
        System.out.println("renewing session: " + se.getSessionId());
        ret.headerElement = se;
        return ret;
    }
}