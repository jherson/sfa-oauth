package com.redhat.sforce.qb.manager;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public interface EntityManager {

	public SaveResult create(SObject sobject) throws ConnectionException;
	public SaveResult[] create(SObject[] sobjects) throws ConnectionException;
	public SaveResult update(SObject sobject) throws ConnectionException;
	public SaveResult[] update(SObject[] sobjects) throws ConnectionException;
	public DeleteResult delete(String id) throws ConnectionException;
	public DeleteResult[] delete(String[] ids) throws ConnectionException;
}
