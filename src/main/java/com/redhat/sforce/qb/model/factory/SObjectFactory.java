package com.redhat.sforce.qb.model.factory;

import com.sforce.ws.bind.XmlObject;

public class SObjectFactory {
	
	public static com.redhat.sforce.qb.model.sobject.SObject toSObject(XmlObject xmlObject) {
		System.out.println("XmlObject Name: " + xmlObject.getName());
		System.out.println("XmlObject XmlType: " + xmlObject.getXmlType());
		
		if ("Quote__c".equals(xmlObject.getName())) {
			
		}
		return null;
	}	
}