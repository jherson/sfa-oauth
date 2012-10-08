package com.sfa.persistence;

import java.util.List;

import com.sfa.qb.exception.QueryException;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public interface EntityManager {

	public Query createQuery(String query);
	public Query createNamedQuery(String namedQuery);
	public SaveResult[] persist(List<SObject> sobjectList) throws ConnectionException;
	public SaveResult[] persist(Object object) throws ConnectionException;
	public SaveResult persist(SObject sobject) throws ConnectionException;
	public DeleteResult[] delete(List<SObject> sobjectList) throws ConnectionException; 
	public DeleteResult delete(SObject sobject) throws ConnectionException;
	public SObject refresh(SObject sobject) throws ConnectionException;
	public <T> T find(Class<T> clazz, String id) throws QueryException;
}