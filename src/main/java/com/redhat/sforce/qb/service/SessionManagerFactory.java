package com.redhat.sforce.qb.service;

import com.redhat.sforce.qb.service.impl.SessionManagerImpl;

public class SessionManagerFactory {
	
	private String sessionId;
	private String type;

	public SessionManagerFactory(String sessionId, String type) {
		this.sessionId = sessionId;
		this.type = type;
	}	
	
	public SessionManager createSessionManager() {
		return new SessionManagerImpl(sessionId, type);
	}
	
}