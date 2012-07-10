package com.redhat.sforce.persistence;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ConnectionManager {

    private static final ThreadLocal<PartnerConnection> threadLocal;

    static {
        threadLocal = new ThreadLocal<PartnerConnection>();
    }
    
    public static void openConnection(String sessionId) throws ConnectionException {
    	ConnectorConfig config = new ConnectorConfig();
    	config.setManualLogin(Boolean.TRUE);
    	config.setSessionId(sessionId);
    	PartnerConnection connection = Connector.newConnection(config);
    	
    	threadLocal.set(connection);
    }

    public static PartnerConnection getConnection() {
        return threadLocal.get();
    }
    
    public static void closeConnection() throws ConnectionException {
    	getConnection().logout();		
    	threadLocal.remove();
    }
}