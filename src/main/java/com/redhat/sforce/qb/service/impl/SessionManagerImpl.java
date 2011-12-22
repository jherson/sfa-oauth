package com.redhat.sforce.qb.service.impl;

import com.redhat.sforce.qb.service.SessionManager;
import com.redhat.sforce.qb.service.Transaction;

public class SessionManagerImpl implements SessionManager {

	private String sessionId;
	private String type;
	private Transaction transaction;
		
	public SessionManagerImpl(String sessionId, String type) {
		this.sessionId = sessionId;
		this.type = type;		
		this.transaction = new Transaction();
	}

	@Override
	public Object find(String objectType, String objectId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Transaction getTransaction() {
		return transaction;
	}
}