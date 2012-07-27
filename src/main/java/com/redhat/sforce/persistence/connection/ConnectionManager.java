package com.redhat.sforce.persistence.connection;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

public class ConnectionManager {
	
	private static final Connection connection;
	
	static {
		connection = new Connection();
	}
    
	public static void openConnection() throws ConnectionException {
		connection.openConnection();
	}
	
	public static void openConnection(String sessionId) throws ConnectionException {
		connection.openConnection(sessionId);
	}
	
	public void openConnection(String username, String password) throws ConnectionException {
		connection.openConnection(username, password);
	}
	
    public void setConnection(PartnerConnection partnerConnection) {
    	connection.setConnection(partnerConnection);
    }

    public static PartnerConnection getConnection() {
    	return connection.getConnection();
    }
    
    public static void closeConnection() throws ConnectionException {
    	connection.closeConnection();
    }
    
    public static void logout() throws ConnectionException {
    	connection.logout();
    }
}
