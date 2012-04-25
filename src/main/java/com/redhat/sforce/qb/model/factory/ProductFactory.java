package com.redhat.sforce.qb.model.factory;

import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Product;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class ProductFactory {

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
}