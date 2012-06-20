package com.redhat.sforce.qb.model.factory;

import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.sobject.OpportunityLineItem;
import com.redhat.sforce.qb.model.sobject.Product;
import com.redhat.sforce.qb.util.JSONObjectWrapper;
import com.redhat.sforce.qb.util.SObjectWrapper;
import com.sforce.ws.bind.XmlObject;

public class ProductFactory {
	
	public static Product parse(XmlObject xmlObject) {
		
		SObjectWrapper wrapper = new SObjectWrapper(xmlObject);
		
		Product product = new Product();
		product.setId(wrapper.getId());
		product.setName(wrapper.getString("Name"));
		product.setDescription(wrapper.getString("Description"));
		product.setFamily(wrapper.getString("Family"));
		product.setProductCode(wrapper.getString("ProductCode"));
		product.setPrimaryBusinessUnit(wrapper.getString("Primary_Business_Unit__c"));
		product.setProductLine(wrapper.getString("Product_Line__c"));
		product.setUnitOfMeasure(wrapper.getString("Unit_Of_Measure__c"));
		product.setTerm(wrapper.getInteger("Term__c"));
		product.setConfigurable(wrapper.getBoolean("Configurable__c"));
		product.setIsActive(wrapper.getBoolean("IsActive"));

		return product;
		
	}

	public static Product parseProduct(JSONObject jsonObject) throws JSONException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);

		Product product = new Product();
		product.setId(wrapper.getId());
		product.setName(wrapper.getString("Name"));
		product.setDescription(wrapper.getString("Description"));
		product.setFamily(wrapper.getString("Family"));
		product.setProductCode(wrapper.getString("ProductCode"));
		product.setPrimaryBusinessUnit(wrapper.getString("Primary_Business_Unit__c"));
		product.setProductLine(wrapper.getString("Product_Line__c"));
		product.setUnitOfMeasure(wrapper.getString("Unit_Of_Measure__c"));
		product.setTerm(wrapper.getInteger("Term__c"));
		product.setConfigurable(wrapper.getBoolean("Configurable__c"));
		product.setIsActive(wrapper.getBoolean("IsActive"));

		return product;
	}

	public static JSONObject serialize(OpportunityLineItem opportunityLineItem) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Id", opportunityLineItem.getProduct().getId());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return jsonObject;
	}
	
	public static Product toProduct(XmlObject xmlObject) {
		Product product = new Product();
		product.setId(xmlObject.getField("Id").toString());
		product.setDescription(xmlObject.getField("Description").toString());
		product.setName(xmlObject.getField("Name").toString());
		product.setFamily(xmlObject.getField("Family").toString());
		product.setProductCode(xmlObject.getField("ProductCode").toString());
		product.setPrimaryBusinessUnit(xmlObject.getField("Primary_Business_Unit__c").toString());
		product.setProductLine(xmlObject.getField("Product_Line__c").toString());
		product.setUnitOfMeasure(xmlObject.getField("Unit_Of_Measure__c").toString());
		product.setTerm(Double.valueOf(xmlObject.getField("Term__c").toString()).intValue());
		product.setConfigurable(Boolean.valueOf(xmlObject.getField("Configurable__c").toString()));
		
		return product;
	}
}