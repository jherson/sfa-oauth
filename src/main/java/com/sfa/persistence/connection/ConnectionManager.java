package com.sfa.persistence.connection;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

public class ConnectionManager {
	
	private static final Connection connection;
	
	static {
		connection = new Connection();
	}
    
	public static PartnerConnection openConnection() throws ConnectionException {
		return connection.openConnection();
	}
	
	public static PartnerConnection openConnection(String sessionId) throws ConnectionException {
		return connection.openConnection(sessionId);
	}
	
	public static PartnerConnection openConnection(String authEndpoint, String username, String password) throws ConnectionException {
		return connection.openConnection(authEndpoint, username, password);
	}
	
    public static void setConnection(PartnerConnection partnerConnection) {
    	connection.setConnection(partnerConnection);
    }
    
    public static void closeConnection() throws ConnectionException {
    	connection.closeConnection();
    }
    
    public static void logout() throws ConnectionException {
    	connection.logout();
    }
}