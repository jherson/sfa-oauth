package com.nowellpoint.oauth.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class OrganizationInfo implements Serializable {

	/**
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = -7062846116139974541L;
	
	@SerializedName("Id")
	private String id;
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("Division")
	private String division;
	
	@SerializedName("Street")
	private String street;
	
	@SerializedName("City")
	private String city;
	
	@SerializedName("State")
	private String state;
	
	@SerializedName("PostalCode")
	private String postalCode;
	
	@SerializedName("Country")
	private String country;
	
	@SerializedName("PrimaryContact")
	private String primaryContact;
	
	@SerializedName("DefaultLocaleSidKey")
	private String defaultLocaleSidKey;
	
	@SerializedName("LanguageLocaleKey")
	private String languageLocaleKey;
	
	@SerializedName("FiscalYearStartMonth")
	private Integer fiscalYearStartMonth;
	
	public OrganizationInfo() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getDefaultLocaleSidKey() {
		return defaultLocaleSidKey;
	}

	public void setDefaultLocaleSidKey(String defaultLocaleSidKey) {
		this.defaultLocaleSidKey = defaultLocaleSidKey;
	}

	public String getLanguageLocaleKey() {
		return languageLocaleKey;
	}

	public void setLanguageLocaleKey(String languageLocaleKey) {
		this.languageLocaleKey = languageLocaleKey;
	}

	public Integer getFiscalYearStartMonth() {
		return fiscalYearStartMonth;
	}

	public void setFiscalYearStartMonth(Integer fiscalYearStartMonth) {
		this.fiscalYearStartMonth = fiscalYearStartMonth;
	}
}