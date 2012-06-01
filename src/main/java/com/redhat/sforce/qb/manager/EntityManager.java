package com.redhat.sforce.qb.manager;

import java.util.List;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public interface EntityManager {

	public void logout() throws ConnectionException;
	public QueryResult query(String queryString) throws ConnectionException;
	public SaveResult[] persist(List<SObject> sobjectList) throws ConnectionException;
	public SaveResult persist(SObject sobject) throws ConnectionException;
	public DeleteResult delete(String id) throws ConnectionException;
	public DeleteResult[] delete(List<String> idList) throws ConnectionException;		
}