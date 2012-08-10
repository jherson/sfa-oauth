package com.redhat.sforce.qb.model.quotebuilder.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.quotebuilder.User;
import com.redhat.sforce.qb.util.JSONObjectWrapper;
import com.redhat.sforce.qb.util.SObjectWrapper;
import com.redhat.sforce.qb.util.Util;
import com.sforce.ws.bind.XmlObject;

public class UserFactory {
	
	public static List<User> parse(Iterator<XmlObject> iterator) {
		
		List<User> userList = new ArrayList<User>();

		while (iterator.hasNext()) {
			User user = parse(iterator.next());
			userList.add(user);
		}

		return userList;
		
	}
	
	public static User parse(XmlObject xmlObject) {
		
		SObjectWrapper wrapper = new SObjectWrapper(xmlObject);
		
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
		user.setFullPhotoUrl(wrapper.getString("FullPhotoUrl"));
		user.setSmallPhotoUrl(wrapper.getString("SmallPhotoUrl"));
		user.setRoleName(wrapper.getString("UserRole", "Name"));
		user.setProfileName(wrapper.getString("Profile", "Name"));
		user.setLocale(Util.stringToLocale(wrapper.getString("LocaleSidKey")));
		user.setTimeZone(wrapper.getString("TimeZoneSidKey"));

		if (user.getLocale() != null) {
			SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, user.getLocale());
			SimpleDateFormat dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,user.getLocale());				
			
			user.setDateFormatPattern(Util.formatPattern(dateFormat));
			user.setDateTimeFormatPattern(Util.formatPattern(dateTimeFormat));
			user.setShortTimeFormat(Util.getShortTimeFormat(user.getLocale()));			
		}

		return user;
	}

	public static List<User> deserialize(JSONArray jsonArray) throws JSONException {
		
		List<User> userList = new ArrayList<User>();

		for (int i = 0; i < jsonArray.length(); i++) {
			User user = deserialize(jsonArray.getJSONObject(i));
			userList.add(user);
		}

		return userList;
	}

	public static User deserialize(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null)
			return null;

		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);

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
		user.setFullPhotoUrl(wrapper.getString("FullPhotoUrl"));
		user.setSmallPhotoUrl(wrapper.getString("SmallPhotoUrl"));
		user.setRoleName(wrapper.getString("UserRole", "Name"));
		user.setProfileName(wrapper.getString("Profile", "Name"));
		user.setLocale(Util.stringToLocale(wrapper.getString("LocaleSidKey")));
		user.setTimeZone(wrapper.getString("TimeZoneSidKey"));

		if (user.getLocale() != null) {
			SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, user.getLocale());
			SimpleDateFormat dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,user.getLocale());

			user.setDateFormatPattern(Util.formatPattern(dateFormat));
			user.setDateTimeFormatPattern(Util.formatPattern(dateTimeFormat));
		}

		return user;
	}
}