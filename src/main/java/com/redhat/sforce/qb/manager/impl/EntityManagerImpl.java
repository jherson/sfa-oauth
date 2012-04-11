package com.redhat.sforce.qb.manager.impl;

import javax.inject.Inject;

import com.redhat.sforce.qb.manager.EntityManager;
import com.redhat.sforce.qb.util.SessionConnection;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class EntityManagerImpl implements EntityManager {

	@Inject
	@SessionConnection
	private PartnerConnection partnerConnection;
	
	@Override
	public SaveResult create(SObject sobject) throws ConnectionException {
		return partnerConnection.create(new SObject[] {sobject})[0];
	}
	
	@Override
	public SaveResult[] create(SObject[] sobjects) throws ConnectionException {
		return partnerConnection.create(sobjects);
	}
	
	@Override
	public SaveResult update(SObject sobject) throws ConnectionException {
		return partnerConnection.update(new SObject[] {sobject})[0];
	}
	
	@Override
	public SaveResult[] update(SObject[] sobjects) throws ConnectionException {
		return partnerConnection.update(sobjects);
	}
	
	@Override
	public DeleteResult delete(String id) throws ConnectionException {
		return partnerConnection.delete(new String[] {id})[0];
	}
	
	@Override
	public DeleteResult[] delete(String[] ids) throws ConnectionException {
		return partnerConnection.delete(ids);
	}
}
