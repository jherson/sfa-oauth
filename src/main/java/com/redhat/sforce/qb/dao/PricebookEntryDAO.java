package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.quotebuilder.PricebookEntry;

public interface PricebookEntryDAO {

	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws QueryException;
}