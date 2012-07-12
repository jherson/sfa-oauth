package com.redhat.sforce.persistence;

import java.util.List;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public interface EntityManager {

	public Query createQuery(String query);
	public SaveResult[] persist(List<SObject> sobjectList) throws ConnectionException;
	public SaveResult persist(SObject sobject) throws ConnectionException;
	public DeleteResult delete(String id) throws ConnectionException;
	public DeleteResult[] delete(List<String> idList) throws ConnectionException;		
}