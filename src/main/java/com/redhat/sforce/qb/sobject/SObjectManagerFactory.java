package com.redhat.sforce.qb.sobject;

public class SObjectManagerFactory {
	
	public static SObjectManager createSessionManager(String sessionId) {
		return new SObjectManagerImpl(sessionId);
	}
}