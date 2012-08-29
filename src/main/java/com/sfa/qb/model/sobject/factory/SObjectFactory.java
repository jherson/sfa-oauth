package com.sfa.qb.model.sobject.factory;

import java.text.ParseException;

import com.sfa.qb.model.sobject.QuoteBuilderObject;
import com.sforce.soap.partner.sobject.SObject;

public class SObjectFactory {
	
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
		
		return null;
	}	
}