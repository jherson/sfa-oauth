package com.redhat.sforce.qb.bean.factory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.util.JSONObjectWrapper;

public class UserFactory {
	
	public static List<User> parseUsers(JSONArray jsonArray) throws JSONException {
		List<User> userList = new ArrayList<User>();
		
		for (int i = 0; i < jsonArray.length(); i++) {	
			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i).getJSONObject("User"));
			
			User user = new User();
			user.setId(wrapper.getId());
			user.setLastName(wrapper.getString("LastName"));
			user.setFirstName(wrapper.getString("FirstName"));
			user.setName(wrapper.getString("Name"));
			user.setContactId(wrapper.getString("ContactId"));
			user.setTitle(wrapper.getString("Email"));
			user.setPhone(wrapper.getString("Phone"));
			user.setTitle(wrapper.getString("Title"));
			user.setDepartment(wrapper.getString("Department"));
		}
		
		return userList;
		
	}

	public static User getUser(JSONObject jsonObject)  throws JSONException {
		User user = new User();
		user.setId(jsonObject.getString("Id"));
		user.setUserName(jsonObject.getString("UserName"));
		user.setLastName(jsonObject.getString("LastName"));
		user.setFirstName(jsonObject.getString("FirstName"));
		user.setName(jsonObject.getString("Name"));
		user.setCompanyName(jsonObject.getString("CompanyName"));
		user.setDivision(jsonObject.getString("Division"));
		user.setDepartment(jsonObject.getString("Department"));
		user.setTitle(jsonObject.getString("Title"));
		user.setStreet(jsonObject.getString("Street"));
		user.setCity(jsonObject.getString("City"));
		user.setState(jsonObject.getString("State"));
		user.setPostalCode(jsonObject.getString("PostalCode"));
		user.setCountry(jsonObject.getString("Country"));
		user.setEmail(jsonObject.getString("Email"));
		user.setPhone(jsonObject.getString("Phone"));
		user.setFax(jsonObject.getString("Fax"));
		user.setMobilePhone(jsonObject.getString("MobilePhone"));
		user.setAlias(jsonObject.getString("Alias"));
		user.setDefaultCurrencyIsoCode(jsonObject.getString("DefaultCurrencyIsoCode"));
		user.setExtension(jsonObject.getString("Extension"));
		user.setRegion(jsonObject.getString("Region__c"));
		user.setRoleName(jsonObject.getString("UserRole.Name"));
		user.setProfileName(jsonObject.getString("Profile.Name"));
			
		return user;
	}
}