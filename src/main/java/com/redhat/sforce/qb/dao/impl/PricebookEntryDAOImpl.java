package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.factory.PricebookEntryFactory;

@SessionScoped

public class PricebookEntryDAOImpl extends SObjectDAO implements PricebookEntryDAO, Serializable {

	private static final long serialVersionUID = 7731570933466830064L;

	@Override
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException {
		return PricebookEntryFactory.deserialize(sm.queryPricebookEntry(pricebookId, productCode, currencyIsoCode));
	}
}