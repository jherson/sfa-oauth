package com.sfa.qb.model.sobject.rev;

import java.io.Serializable;
import java.util.Locale;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.OneToOne;
import com.sforce.soap.partner.sobject.SObject;

public class User extends SObject implements Serializable {

	private static final long serialVersionUID = -5495949651775917498L;
	
	@Id
	private String id;

	@Column(name="UserName")
	private String userName;
	
	@Column(name="FirstName")
	private String firstName;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="Name")
	private String fullName;
	
	@Column(name="CompanyName")
	private String companyName;
	
	@Column(name="Division")
	private String division;
	
	@Column(name="Department")
	private String department;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="Street")
	private String street;
	
	@Column(name="City")
	private String city;
	
	@Column(name="State")
	private String state;
	
	@Column(name="PostalCode")
	private String postalCode;
	
	@Column(name="Country")
	private String country;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="Fax")
	private String fax;
	
	@Column(name="MobilePhone")
	private String mobilePhone;
	
	@Column(name="Alias")
	private String alias;
	
	@Column(name="Extension")
	private String extension;
	
	@Column(name="Region__c")
	private String region;
	
	@Column(name="ContactId")
	private String contactId;
	
	@OneToOne(name="Role", referenceColumnName="RoleId")
	private Role role;
	
	@OneToOne(name="Profile", referenceColumnName="ProfileId")
	private Profile profile;
	
	@Column(name="DefaultCurrencyIsoCode")
	private String defaultCurrencyIsoCode;
	
	@Column(name="FullPhototUrl")
	private String fullPhotoUrl;
	
	@Column(name="SmallPhotoUrl")
	private String smallPhotoUrl;
	
	@Column(name="Locale")
	private Locale locale;
		
	private String roleName;
	private String profileName;
	private String timeZone;	
	private String dateFormatPattern;
	private String shortTimeFormat;
	private String dateTimeFormatPattern;

	public User() {
		super();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return userName;
	}

	public void setFullName(String userName) {
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

	public String getUserName() {
		return fullName;
	}

	public void setUserName(String fullName) {
		this.fullName = fullName;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}