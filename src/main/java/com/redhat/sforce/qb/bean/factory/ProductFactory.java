package com.redhat.sforce.qb.bean.factory;

import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.Product;
import com.redhat.sforce.util.JSONObjectWrapper;

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
		
		return product;
	}
}