package com.redhat.sforce.qb.bean.factory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.util.JSONObjectWrapper;

public class UserFactory {
	
	public static List<User> parseUsers(JSONArray jsonArray) throws JSONException {
		List<User> userList = new ArrayList<User>();
					
		for (int i = 0; i < jsonArray.length(); i++) {	

			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i).getJSONObject("User"));
			
			User user = new User();
			user.setId(wrapper.getId());
			user.setUserName(wrapper.getString("UserName"));
			user.setLastName(wrapper.getString("LastName"));
			user.setFirstName(wrapper.getString("FirstName"));
			user.setName(wrapper.getString("Name"));
			user.setCompanyName(wrapper.getString("CompanyName"));
			user.setDivision(wrapper.getString("Division"));
			user.setContactId(wrapper.getString("ContactId"));
			user.setStreet(wrapper.getString("Street"));
			user.setCity(wrapper.getString("City"));
			user.setState(wrapper.getString("State"));
			user.setPostalCode(wrapper.getString("PostalCode"));
			user.setCountry(wrapper.getString("Country"));
			user.setTitle(wrapper.getString("Email"));
			user.setPhone(wrapper.getString("Phone"));
			user.setTitle(wrapper.getString("Title"));
			user.setFax(wrapper.getString("Fax"));
			user.setMobilePhone(wrapper.getString("MobilePhone"));
			user.setAlias(wrapper.getString("Alias"));
			user.setExtension(wrapper.getString("Extension"));
			user.setDepartment(wrapper.getString("Department"));
			user.setRegion(wrapper.getString("Region__c"));
			user.setRoleName(wrapper.getString("UserRole.Name"));
			user.setProfileName(wrapper.getString("Profile.Name"));
			
			userList.add(user);			
		}
		
		return userList;		
	}	
}