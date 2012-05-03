package com.redhat.sforce.qb.model.factory;

import com.redhat.sforce.qb.model.SObject;
import com.sforce.soap.partner.QueryResult;

public class SObjectFactory {

	public static SObject toSObject(QueryResult queryResult) {
		String type = queryResult.getRecords()[0].getType();
		if ("PricebookEntry".equals(type)) {
			return PricebookEntryFactory.toPricebookEntry(queryResult);
		}
		
		return null;
	}
	
}
