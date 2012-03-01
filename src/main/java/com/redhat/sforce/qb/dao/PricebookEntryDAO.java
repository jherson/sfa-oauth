package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface PricebookEntryDAO {

	public PricebookEntry queryPricebookEntry(String accessToken, String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException;
	
}