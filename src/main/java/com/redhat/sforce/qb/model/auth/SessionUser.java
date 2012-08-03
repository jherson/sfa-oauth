package com.redhat.sforce.qb.model.auth;

import com.redhat.sforce.qb.model.quotebuilder.User;

public class SessionUser extends User {

	private static final long serialVersionUID = -7339108386156929994L;
	
	private OAuth oauth;
	
	public SessionUser(User user) {
		this.setId(user.getId());
		this.setAlias(user.getAlias());
		this.setCity(user.getCity());
		this.setCompanyName(user.getCompanyName());
		this.setContactId(user.getContactId());
		this.setCountry(user.getCountry());
		this.setCreatedByFirstName(user.getCreatedByFirstName());
		this.setCreatedById(user.getCreatedById());
		this.setCreatedByLastName(user.getCreatedByLastName());
		this.setCreatedByName(user.getCreatedByName());
		this.setCreatedDate(user.getCreatedDate());
		this.setCurrencyIsoCode(user.getCurrencyIsoCode());
		this.setDateFormatPattern(user.getDateFormatPattern());
		this.setDateTimeFormatPattern(user.getDateTimeFormatPattern());
		this.setDefaultCurrencyIsoCode(user.getDefaultCurrencyIsoCode());
		this.setDepartment(user.getDepartment());
		this.setDivision(user.getDivision());
		this.setEmail(user.getEmail());
		this.setExtension(user.getExtension());
		this.setFax(user.getFax());
		this.setFirstName(user.getFirstName());
		this.setFullPhotoUrl(user.getFullPhotoUrl());
		this.setLastModifiedByFirstName(user.getLastModifiedByFirstName());
		this.setLastModifiedById(user.getLastModifiedById());
		this.setLastModifiedByLastName(user.getLastModifiedByLastName());
		this.setLastModifiedByName(user.getLastModifiedByName());
		this.setLastModifiedDate(user.getLastModifiedDate());
		this.setLastName(user.getLastName());
		this.setLocale(user.getLocale());
		this.setMobilePhone(user.getMobilePhone());
		this.setName(user.getName());
		this.setPhone(user.getPhone());
		this.setPostalCode(user.getPostalCode());
		this.setProfileName(user.getProfileName());
		this.setRegion(user.getRegion());
		this.setRoleName(user.getRoleName());
		this.setSmallPhotoUrl(user.getSmallPhotoUrl());
		this.setState(user.getState());
		this.setStreet(user.getStreet());
		this.setTimeZone(user.getTimeZone());
		this.setTitle(user.getTitle());
		this.setUserName(user.getUserName());
	}

	public OAuth getOAuth() {
		return oauth;
	}

	public void setOAuth(OAuth oauth) {
		this.oauth = oauth;
	}	
}