package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.factory.PricebookEntryFactory;
import com.sforce.soap.partner.QueryResult;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class PricebookEntryDAOImpl extends SObjectDAO implements PricebookEntryDAO, Serializable {

	private static final long serialVersionUID = 7731570933466830064L;

	@Override
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws ConnectionException {		
		String queryString = getQueryString(pricebookId, productCode, "false", currencyIsoCode);
		
		QueryResult queryResult = em.query(queryString); 
		
		if (queryResult == null || queryResult.getSize() == 0) {
			queryString = getQueryString(pricebookId, productCode, "true", currencyIsoCode);
			queryResult = em.query(queryString);
		}
		
		return PricebookEntryFactory.toPricebookEntry(queryResult);		
	}
	
	private String getQueryString(String pricebookId, String productCode, String configurable, String currencyIsoCode) {
		String query = pricebookEntryQuery.replace("#pricebookId#", pricebookId);
		query = query.replace("#productCode#", productCode);
		query = query.replace("#configurable#", configurable);
		query = query.replace("#currencyIsoCode#", currencyIsoCode);
		
		return query;
	}
	
	String pricebookEntryQuery = "Select Id, "
        + "CurrencyIsoCode, "
        + "UnitPrice, "
        + "Product2.Id, "
        + "Product2.Description, "
        + "Product2.Name, "
        + "Product2.Family, "                      
        + "Product2.ProductCode, "
        + "Product2.Primary_Business_Unit__c, "
        + "Product2.Product_Line__c, "
        + "Product2.Unit_Of_Measure__c, "
        + "Product2.Term__c, "
        + "Product2.Configurable__c, "
        + "Product2.IsActive "
        + "From PricebookEntry "
        + "Where Pricebook2.Id = '#pricebookId#' " 
        + "And Product2.ProductCode = '#productCode#' "
        + "And Product2.Configurable__c = #configurable# " 
        + "And CurrencyIsoCode = '#currencyIsoCode#' "
        + "And IsActive = true "
        + "And Product2.IsActive = true limit 1";	
}