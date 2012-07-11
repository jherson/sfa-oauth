package com.redhat.sforce.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ConnectionManager {

    private static final ThreadLocal<PartnerConnection> threadLocal;

    static {
        threadLocal = new ThreadLocal<PartnerConnection>();
    }
    
    public static void openConnection() throws ConnectionException {
    	InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			throw new ConnectionException("Unable to load quotebuilder.properties file");
		}
    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
    	config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));
						
		PartnerConnection connection = Connector.newConnection(config);
		
		ConnectionProperties.setServiceEndpoint(connection.getConfig().getServiceEndpoint());
		
		threadLocal.set(connection);
    }
    
    public static void openConnection(String sessionId) throws ConnectionException {
    	    	
    	ConnectorConfig config = new ConnectorConfig();
    	config.setServiceEndpoint(ConnectionProperties.getServiceEndpoint());
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionId);
    	
    	PartnerConnection connection = Connector.newConnection(config);
    	
    	threadLocal.set(connection);
    }

    public static PartnerConnection getConnection() {
        return threadLocal.get();
    }
    
    public static void closeConnection() throws ConnectionException {	
    	threadLocal.remove();
    }
}