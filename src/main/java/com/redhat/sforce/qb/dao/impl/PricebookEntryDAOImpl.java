package com.redhat.sforce.qb.dao.impl;

import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public class PricebookEntryDAOImpl implements PricebookEntryDAO {

	@Override
	public PricebookEntry queryPricebookEntry(String accessToken,
			String pricebookId, String productCode, String currencyIsoCode)
			throws SforceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
