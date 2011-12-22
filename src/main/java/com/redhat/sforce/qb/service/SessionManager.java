package com.redhat.sforce.qb.service;

public interface SessionManager {
	
	public Object find(String objectType, String objectId);
	public Transaction getTransaction();

}
