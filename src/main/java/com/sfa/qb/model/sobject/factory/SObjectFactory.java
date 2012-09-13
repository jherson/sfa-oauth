package com.sfa.qb.model.sobject.factory;

import java.text.ParseException;
import java.util.logging.Logger;

import com.sfa.qb.model.sobject.QuoteBuilderObject;
import com.sforce.soap.partner.sobject.SObject;

public class SObjectFactory {
	
	private static final Logger log = Logger.getLogger(SObjectFactory.class.getName());
	
	public static QuoteBuilderObject parse(SObject sobject) throws ParseException {
				
		if ("Quote__c".equals(sobject.getType())) {
	    	return QuoteFactory.parse(sobject);
	    }		
	    
	    if ("Opportunity".equals(sobject.getType())) {
	    	return OpportunityFactory.parse(sobject);
	    }
	    
	    if ("QuoteLineItem__c".equals(sobject.getType())) {
	    	return QuoteLineItemFactory.parse(sobject);
	    }
	    
	    if ("OpportunityLineItem".equals(sobject.getType())) {
	    	return OpportunityLineItemFactory.parse(sobject);
	    }
	    
	    if ("PricebookEntry".equals(sobject.getType())) {
	    	return PricebookEntryFactory.parse(sobject);
	    }
	    
	    if ("User".equals(sobject.getType())) {
	    	return UserFactory.parse(sobject);
	    }
	    
	    if ("CurrencyType".equals(sobject.getType())) {
	    	return CurrencyTypeFactory.parse(sobject);
	    }
	    
	    log.warning("Unable to parse sobject, no parser specified for SObject: " + sobject.getType());
		
		return null;
	}	
}