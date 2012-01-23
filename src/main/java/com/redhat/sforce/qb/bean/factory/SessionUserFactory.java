package com.redhat.sforce.qb.bean.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import org.json.JSONObject;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.util.JSONObjectWrapper;

public class SessionUserFactory {
			
	public static SessionUser parseSessionUser(JSONObject jsonObject) throws  JSONException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
		
		SessionUser sessionUser = new SessionUser();
		sessionUser.setId(wrapper.getId());
		sessionUser.setUserName(wrapper.getString("UserName"));
		sessionUser.setLastName(wrapper.getString("LastName"));
		sessionUser.setFirstName(wrapper.getString("FirstName"));
		sessionUser.setName(wrapper.getString("Name"));
		sessionUser.setCompanyName(wrapper.getString("CompanyName"));
		sessionUser.setDivision(wrapper.getString("Division"));
		sessionUser.setContactId(wrapper.getString("ContactId"));
		sessionUser.setStreet(wrapper.getString("Street"));
		sessionUser.setCity(wrapper.getString("City"));
		sessionUser.setState(wrapper.getString("State"));
		sessionUser.setPostalCode(wrapper.getString("PostalCode"));
		sessionUser.setCountry(wrapper.getString("Country"));
		sessionUser.setTitle(wrapper.getString("Email"));
		sessionUser.setPhone(wrapper.getString("Phone"));
		sessionUser.setTitle(wrapper.getString("Title"));
		sessionUser.setFax(wrapper.getString("Fax"));
		sessionUser.setMobilePhone(wrapper.getString("MobilePhone"));
		sessionUser.setAlias(wrapper.getString("Alias"));
		sessionUser.setDefaultCurrencyIsoCode(wrapper.getString("DefaultCurrencyIsoCode"));
		sessionUser.setExtension(wrapper.getString("Extension"));
		sessionUser.setDepartment(wrapper.getString("Department"));
		sessionUser.setLocaleSidKey(wrapper.getString("LocaleSidKey"));
		sessionUser.setFullPhotoUrl(wrapper.getString("FullPhotoUrl"));
		sessionUser.setSmallPhotoUrl(wrapper.getString("SmallPhotoUrl"));
		sessionUser.setRegion(wrapper.getString("Region__c"));
		sessionUser.setRoleName(wrapper.getString("UserRole.Name"));
		sessionUser.setProfileName(wrapper.getString("Profile.Name"));
		
		Locale locale = stringToLocale(sessionUser.getLocaleSidKey());
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
	    SimpleDateFormat dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
	    
	    sessionUser.setDateFormatPattern(formatPattern(dateFormat));
        sessionUser.setDateTimeFormatPattern(formatPattern(dateTimeFormat));
		
		return sessionUser;		
	}
	
	private static Locale stringToLocale(String localeSidKey) {
	    StringTokenizer st = new StringTokenizer(localeSidKey, "_");
	    String language = null;
	    String country = null;
	    
	    if (st.hasMoreTokens())
	        language = st.nextElement().toString();
	    
	    if (st.hasMoreTokens())
	        country = st.nextElement().toString();
	    
	    return new Locale(language, country);
	}
	
	private static String formatPattern(SimpleDateFormat format) {
        String pattern = format.toPattern();
        
        if (! pattern.contains("yyyy"))
        	pattern = pattern.replace("yy", "yyyy");
        
        if (! pattern.contains("dd"))
        	pattern = pattern.replace("d", "dd");
        
        if (! pattern.contains("MM"))
        	pattern = pattern.replace("M", "MM");
        
        return pattern;
	}
}