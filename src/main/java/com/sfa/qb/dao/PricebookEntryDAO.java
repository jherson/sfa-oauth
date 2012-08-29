package com.sfa.qb.dao;

import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.PricebookEntry;

public interface PricebookEntryDAO {

	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws QueryException;
}