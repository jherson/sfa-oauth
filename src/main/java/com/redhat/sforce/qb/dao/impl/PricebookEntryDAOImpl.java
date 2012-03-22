package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;

import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.factory.PricebookEntryFactory;

public class PricebookEntryDAOImpl extends SObjectDAO implements PricebookEntryDAO, Serializable {

	private static final long serialVersionUID = 7731570933466830064L;

	@Override
	public PricebookEntry queryPricebookEntry(String accessToken,String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException {
		return PricebookEntryFactory.deserialize(sm.queryPricebookEntry(accessToken, pricebookId, productCode, currencyIsoCode));
	}
}