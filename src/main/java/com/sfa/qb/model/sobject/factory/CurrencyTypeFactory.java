package com.sfa.qb.model.sobject.factory;

import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;

import com.sfa.qb.model.sobject.CurrencyType;
import com.sfa.qb.util.SObjectWrapper;

public class CurrencyTypeFactory {	
	
	public static List<CurrencyType> parse(SObject[] sobjects) {
		List<CurrencyType> currencyTypeList = new ArrayList<CurrencyType>();
		
		for (SObject sobject : sobjects) {
			currencyTypeList.add(parse(sobject));
		}
		
		return currencyTypeList;
	}
	
    public static CurrencyType parse(XmlObject xmlObject) {
    	
    	SObjectWrapper wrapper = new SObjectWrapper(xmlObject);
    	
    	CurrencyType currencyType = new CurrencyType();
    	currencyType.setId(wrapper.getId());
    	currencyType.setIsoCode(wrapper.getString("IsoCode"));
    	
    	return currencyType;
    	
    }    
}