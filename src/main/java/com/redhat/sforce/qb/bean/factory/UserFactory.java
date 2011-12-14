package com.redhat.sforce.qb.bean.factory;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.User;

public class UserFactory {

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
	
	private void getDateFormat(User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat();

//        if (getCountry() != null && getDefaultCurrencyIsoCode() != null) {
//            dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, new Locale(getLanguage(), getCountry(),
//                    getDefaultCurrencyIsoCode()));
//            dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale(
//                    getLanguage(), getCountry(), getDefaultCurrencyIsoCode()));
//        } else if (getCountry() != null && getDefaultCurrencyIsoCode() == null) {
//            dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, new Locale(getLanguage(), getCountry()));
//            dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale(
//                    getLanguage(), getCountry()));
//        } else {
//            dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, new Locale(getLanguage()));
//            dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale(
//                    getLanguage()));
//        }
//
//        String pattern = dateFormat.toPattern();
//        int len = pattern.length();
//        int yearBegin = pattern.lastIndexOf('y') + 1;
//        if (yearBegin < 4)
//            pattern = pattern.substring(0, yearBegin) + "yy" + (yearBegin < len ? pattern.substring(yearBegin, len) : "");
//        setDateFormatPattern(pattern);
//        setDateFormat(new SimpleDateFormat(pattern));
//        pattern = dateTimeFormat.toPattern();
//        
//
//        DateFormatSymbols symbols = dateFormat.getDateFormatSymbols();
//        symbols.setLocalPatternChars("GyMdkHmsSEDFwWahKzZ");
//        dateFormat.setDateFormatSymbols(symbols);
//        setCalendarFormatPattern(dateFormat.toLocalizedPattern().toLowerCase().replaceFirst("yyyy", "%Y").replaceFirst("yy", "%Y")
//                .replaceFirst("mm", "m").replaceFirst("dd", "d").replaceFirst("m", "%m").replaceFirst("d", "%d"));
    }
}