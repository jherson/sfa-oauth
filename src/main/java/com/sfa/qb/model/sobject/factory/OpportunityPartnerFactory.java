package com.sfa.qb.model.sobject.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sfa.qb.model.sobject.OpportunityPartner;
import com.sfa.qb.util.SObjectWrapper;
import com.sforce.ws.bind.XmlObject;

public class OpportunityPartnerFactory {
	
	public static List<OpportunityPartner> parse(Iterator<XmlObject> iterator) {
		
		List<OpportunityPartner> opportunityPartnerList = new ArrayList<OpportunityPartner>();
		
		while (iterator.hasNext()) {
			SObjectWrapper wrapper = new SObjectWrapper(iterator.next());
			
			OpportunityPartner opportunityPartner = new OpportunityPartner();
			opportunityPartner.setId(wrapper.getId());
			opportunityPartner.setBillingCity(wrapper.getXmlObject("Partner__r").getField("BillingCity").toString());
			opportunityPartner.setBillingCountry(wrapper.getXmlObject("Partner__r").getField("BillingCountry").toString());
			opportunityPartner.setBillingPostalCode(wrapper.getXmlObject("Partner__r").getField("BillingPostalCode").toString());
			opportunityPartner.setBillingState(wrapper.getXmlObject("Partner__r").getField("BillingState").toString());
			opportunityPartner.setBillingStreet(wrapper.getXmlObject("Partner__r").getField("BillingStreet").toString());
			opportunityPartner.setName(wrapper.getXmlObject("Partner__r").getField("Name").toString());
			opportunityPartner.setOracleAccountNumber(wrapper.getXmlObject("Partner__r").getField("OracleAccountNumber__c").toString());
			opportunityPartner.setShippingCity(wrapper.getXmlObject("Partner__r").getField("ShippingCity").toString());
			opportunityPartner.setShippingCountry(wrapper.getXmlObject("Partner__r").getField("ShippingCountry").toString());
			opportunityPartner.setShippingPostalCode(wrapper.getXmlObject("Partner__r").getField("ShippingPostalCode").toString());
			opportunityPartner.setShippingState(wrapper.getXmlObject("Partner__r").getField("ShippingState").toString());
			opportunityPartner.setShippingStreet(wrapper.getXmlObject("Partner__r").getField("ShippingStreet").toString());
			opportunityPartner.setVatNumber(wrapper.getXmlObject("Partner__r").getField("VATNumber__c").toString());			

			opportunityPartnerList.add(opportunityPartner);
			
		}
		
		return opportunityPartnerList;
	}

//	public static List<OpportunityPartner> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
//		List<OpportunityPartner> opportunityPartnerList = new ArrayList<OpportunityPartner>();
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
//			
//			OpportunityPartner opportunityPartner = new OpportunityPartner();
//			opportunityPartner.setId(wrapper.getId());
//			opportunityPartner.setBillingCity(wrapper.getJSONObject("Partner__r").getString("BillingCity"));
//			opportunityPartner.setBillingCountry(wrapper.getJSONObject("Partner__r").getString("BillingCountry"));
//			opportunityPartner.setBillingPostalCode(wrapper.getJSONObject("Partner__r").getString("BillingPostalCode"));
//			opportunityPartner.setBillingState(wrapper.getJSONObject("Partner__r").getString("BillingState"));
//			opportunityPartner.setBillingStreet(wrapper.getJSONObject("Partner__r").getString("BillingStreet"));
//			opportunityPartner.setName(wrapper.getJSONObject("Partner__r").getString("Name"));
//			opportunityPartner.setOracleAccountNumber(wrapper.getJSONObject("Partner__r").getString("OracleAccountNumber__c"));
//			opportunityPartner.setShippingCity(wrapper.getJSONObject("Partner__r").getString("ShippingCity"));
//			opportunityPartner.setShippingCountry(wrapper.getJSONObject("Partner__r").getString("ShippingCountry"));
//			opportunityPartner.setShippingPostalCode(wrapper.getJSONObject("Partner__r").getString("ShippingPostalCode"));
//			opportunityPartner.setShippingState(wrapper.getJSONObject("Partner__r").getString("ShippingState"));
//			opportunityPartner.setShippingStreet(wrapper.getJSONObject("Partner__r").getString("ShippingStreet"));
//			opportunityPartner.setVatNumber(wrapper.getJSONObject("Partner__r").getString("VATNumber__c"));			
//
//			opportunityPartnerList.add(opportunityPartner);
//		}
//
//		return opportunityPartnerList;
//	}
}