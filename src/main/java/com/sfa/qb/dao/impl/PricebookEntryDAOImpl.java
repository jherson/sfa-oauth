package com.sfa.qb.dao.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.sfa.persistence.Query;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.dao.PricebookEntryDAO;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.PricebookEntry;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class PricebookEntryDAOImpl extends DAO implements PricebookEntryDAO, Serializable {

	private static final long serialVersionUID = 7731570933466830064L;

	@Override
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws QueryException {		
		String queryString = getQueryString(pricebookId, productCode, "false", currencyIsoCode);
		
		try {
			ConnectionManager.openConnection(sessionUser);
		
		    Query q = em.createQuery(queryString);	
		
		    PricebookEntry pricebookEntry = q.getSingleResult();				
		
		    if (pricebookEntry == null) {
			    queryString = getQueryString(pricebookId, productCode.substring(0,4), "true", currencyIsoCode);
			    q = em.createQuery(queryString);	
			    pricebookEntry = q.getSingleResult();
		    }
		    
		    return pricebookEntry;
		    
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
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