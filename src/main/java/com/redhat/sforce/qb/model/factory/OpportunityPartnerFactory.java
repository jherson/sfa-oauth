package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.model.OpportunityPartner;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class OpportunityPartnerFactory {

	public static List<OpportunityPartner> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<OpportunityPartner> opportunityPartnerList = new ArrayList<OpportunityPartner>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
			
			OpportunityPartner opportunityPartner = new OpportunityPartner();
			opportunityPartner.setId(wrapper.getId());
			opportunityPartner.setBillingCity(wrapper.getJSONObject("Partner__r").getString("BillingCity"));
			opportunityPartner.setBillingCountry(wrapper.getJSONObject("Partner__r").getString("BillingCountry"));
			opportunityPartner.setBillingPostalCode(wrapper.getJSONObject("Partner__r").getString("BillingPostalCode"));
			opportunityPartner.setBillingState(wrapper.getJSONObject("Partner__r").getString("BillingState"));
			opportunityPartner.setBillingStreet(wrapper.getJSONObject("Partner__r").getString("BillingStreet"));
			opportunityPartner.setName(wrapper.getJSONObject("Partner__r").getString("Name"));
			opportunityPartner.setOracleAccountNumber(wrapper.getJSONObject("Partner__r").getString("OracleAccountNumber__c"));
			opportunityPartner.setShippingCity(wrapper.getJSONObject("Partner__r").getString("ShippingCity"));
			opportunityPartner.setShippingCountry(wrapper.getJSONObject("Partner__r").getString("ShippingCountry"));
			opportunityPartner.setShippingPostalCode(wrapper.getJSONObject("Partner__r").getString("ShippingPostalCode"));
			opportunityPartner.setShippingState(wrapper.getJSONObject("Partner__r").getString("ShippingState"));
			opportunityPartner.setShippingStreet(wrapper.getJSONObject("Partner__r").getString("ShippingStreet"));
			opportunityPartner.setVatNumber(wrapper.getJSONObject("Partner__r").getString("VATNumber__c"));			

			opportunityPartnerList.add(opportunityPartner);
		}

		return opportunityPartnerList;
	}
}