package com.sfa.qb.model.sobject;

import java.io.Serializable;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;
import com.sfa.qb.model.auth.OAuth;

public class User implements Serializable {

	private static final long serialVersionUID = -5495949651775917498L;
	
	@SerializedName("Id")
	private String id;

	@SerializedName("Username")
	private String userName;
	
	@SerializedName("FirstName")
	private String firstName;
	
	@SerializedName("LastName")
	private String lastName;
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("CompanyName")
	private String companyName;
	
	@SerializedName("Division")
	private String division;
	
	@SerializedName("Department")
	private String department;
	
	@SerializedName("Title")
	private String title;
	
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
	
	@SerializedName("Email")
	private String email;
	
	@SerializedName("Phone")
	private String phone;
	
	@SerializedName("Fax")
	private String fax;
	
	@SerializedName("MobilePhone")
	private String mobilePhone;
	
	@SerializedName("Alias")
	private String alias;
	
	@SerializedName("Extension")
	private String extension;
	
	@SerializedName("Region__c")
	private String region;
	
	@SerializedName("ContactId")
	private String contactId;
	
	@SerializedName("UserRole")
	private UserRole userRole;
	
	@SerializedName("Profile")
	private Profile profile;
	
	@SerializedName("DefaultCurrencyIsoCode")
	private String defaultCurrencyIsoCode;
	
	@SerializedName("FullPhototUrl")
	private String fullPhotoUrl;
	
	@SerializedName("SmallPhotoUrl")
	private String smallPhotoUrl;
	
	@SerializedName("LocaleSidKey")
	private String localeSidKey;

	@SerializedName("TimeZoneSidKey")
	private String timeZone;
	
	private Locale locale;
	private String dateFormatPattern;
	private String shortTimeFormat;
	private String dateTimeFormatPattern;
	private OAuth oauth;

	public User() {
		super();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDefaultCurrencyIsoCode() {
		return defaultCurrencyIsoCode;
	}

	public void setDefaultCurrencyIsoCode(String defaultCurrencyIsoCode) {
		this.defaultCurrencyIsoCode = defaultCurrencyIsoCode;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getFullPhotoUrl() {
		return fullPhotoUrl;
	}

	public void setFullPhotoUrl(String fullPhotoUrl) {
		this.fullPhotoUrl = fullPhotoUrl;
	}

	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}

	public void setSmallPhotoUrl(String smallPhotoUrl) {
		this.smallPhotoUrl = smallPhotoUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getDateFormatPattern() {
		return dateFormatPattern;
	}

	public void setDateFormatPattern(String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
	}
	
	public String getShortTimeFormat() {
		return shortTimeFormat;
	}
	
	public void setShortTimeFormat(String shortTimeFormat) {
		this.shortTimeFormat = shortTimeFormat;
	}

	public String getDateTimeFormatPattern() {
		return dateTimeFormatPattern;
	}

	public void setDateTimeFormatPattern(String dateTimeFormatPattern) {
		this.dateTimeFormatPattern = dateTimeFormatPattern;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public OAuth getOAuth() {
		return oauth;
	}
	
	public void setOAuth(OAuth oauth) {
		this.oauth = oauth;
	}

	public String getLocaleSidKey() {
		return localeSidKey;
	}

	public void setLocaleSidKey(String localeSidKey) {
		this.localeSidKey = localeSidKey;
	}
}