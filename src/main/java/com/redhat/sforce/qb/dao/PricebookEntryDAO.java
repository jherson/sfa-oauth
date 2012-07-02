package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.model.quotebuilder.PricebookEntry;
import com.sforce.ws.ConnectionException;

public interface PricebookEntryDAO {

	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws ConnectionException;
}