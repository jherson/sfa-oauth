package com.nowellpoint.oauth.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserInfo implements Serializable {

	/**
	 * "UserRole":null,"Profile":{"attributes":{"type":"Profile","url":"/services/data/v29.0/sobjects/Profile/00e30000000fJ0uAAE"},"Id":"00e30000000fJ0uAAE","Name":"System Administrator","PermissionsCustomizeApplication":true},
	 */
	
	private static final long serialVersionUID = 5267942011876349443L;
	
	@SerializedName("Id")
	private String id;
	
	@SerializedName("Username")
	private String username;
	
	@SerializedName("LastName")
	private String lastName;
	
	@SerializedName("FirstName")
	private String firstName;
	
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
	
	@SerializedName("Latitude")
	private String latitude;

	@SerializedName("Longitude")
	private String longitude;
	
	@SerializedName("Email")
	private String email;
	
	@SerializedName("SenderEmail")
	private String senderEmail;
	
	@SerializedName("SenderName")
	private String senderName;
	
	@SerializedName("Signature")
	private String signature;
	
	@SerializedName("Phone")
	private String phone;
	
	@SerializedName("Fax")
	private String fax;
	
	@SerializedName("MobilePhone")
	private String mobilePhone;
	
	@SerializedName("Alias")
	private String alias;
	
	@SerializedName("CommunityNickname")
	private String communityNickname;
	
	@SerializedName("IsActive")
	private Boolean isActive;
	
	@SerializedName("TimeZoneSidKey")
	private String timeZoneSidKey;
	
	@SerializedName("LocaleSidKey")
	private String localeSidKey;
	
	@SerializedName("EmailEncodingKey")
	private String emailEncodingKey;
	
	@SerializedName("UserType")
	private String userType;
	
	@SerializedName("LanguageLocaleKey")
	private String languageLocaleKey;
	
	@SerializedName("EmployeeNumber")
	private String employeeNumber;
	
	@SerializedName("DelegatedApproverId")
	private String delegatedApproverId;
	
	@SerializedName("ManagerId")
	private String managerId;
	
	@SerializedName("AboutMe")
	private String aboutMe;
	
	private Attributes attributes;
	
	public UserInfo() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getCommunityNickname() {
		return communityNickname;
	}

	public void setCommunityNickname(String communityNickname) {
		this.communityNickname = communityNickname;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getTimeZoneSidKey() {
		return timeZoneSidKey;
	}

	public void setTimeZoneSidKey(String timeZoneSidKey) {
		this.timeZoneSidKey = timeZoneSidKey;
	}

	public String getLocaleSidKey() {
		return localeSidKey;
	}

	public void setLocaleSidKey(String localeSidKey) {
		this.localeSidKey = localeSidKey;
	}

	public String getEmailEncodingKey() {
		return emailEncodingKey;
	}

	public void setEmailEncodingKey(String emailEncodingKey) {
		this.emailEncodingKey = emailEncodingKey;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLanguageLocaleKey() {
		return languageLocaleKey;
	}

	public void setLanguageLocaleKey(String languageLocaleKey) {
		this.languageLocaleKey = languageLocaleKey;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getDelegatedApproverId() {
		return delegatedApproverId;
	}

	public void setDelegatedApproverId(String delegatedApproverId) {
		this.delegatedApproverId = delegatedApproverId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
}