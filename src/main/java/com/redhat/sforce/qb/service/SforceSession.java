package com.redhat.sforce.qb.service;

public class SforceSession {
	
	private static String sessionId;
	
	public SforceSession(String sessionId) {
		SforceSession.sessionId = sessionId;
	}
	
	public static void setSessionId(String sessionId) {
		SforceSession.sessionId = sessionId;
	}
	
	public static String getSessionId() {
		return SforceSession.sessionId;
	}
	
	public SessionManagerFactory createSessionManagerFactory(String type) {
		return new SessionManagerFactory(sessionId, type);
	}
}
