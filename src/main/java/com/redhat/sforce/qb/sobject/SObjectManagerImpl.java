package com.redhat.sforce.qb.sobject;

import javax.inject.Inject;

import org.json.JSONObject;

import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.SObject;
import com.redhat.sforce.qb.exception.SforceServiceException;
import com.redhat.sforce.qb.service.SforceService;

public class SObjectManagerImpl implements SObjectManager {
	
	private String sessionId;
	
	@Inject
	SforceService sforceService;
	
	public SObjectManagerImpl(String sessionId) {
		this.sessionId = sessionId;
	}

    @Override
    public void persist(SObject sobject) throws SforceServiceException {
    	if (sobject.getId() != null) {
    	    sforceService.update(sessionId, "Quote__c", sobject.getId(), covertSObjectToJSON(sobject));
    	} else {
    		sforceService.create(sessionId, "Quote__c", covertSObjectToJSON(sobject));
    	}
        return;
    }

    @Override
    public void remove(SObject sobject) {
        return;
    }

    @Override
    public void refresh(SObject sobject) {
        return;
    }

    @Override
    public SObject query(String query) {
        return null;
    }
    
    @Override
    public SObject find(SObject sobject, String id) {
    	return null;
    }
    
    private JSONObject covertSObjectToJSON(SObject sobject) {
    	if (sobject instanceof Quote) {
    		return QuoteFactory.toJson((Quote) sobject);
    	}
    	
    	return null;
    }
}
