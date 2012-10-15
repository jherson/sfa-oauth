package com.sfa.qb.model.entities;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Table(name="User")

public class User implements Serializable {

	private static final long serialVersionUID = -5495949651775917498L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="SalesforceId", length=20, unique=true)
	private String salesforceId;

	@Column(name="UserName")
	private String userName;
	
	@Column(name="FirstName")
	private String firstName;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="Name")
	private String name;
	
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
	
	@Column(name="Region")
	private String region;
	
	@Column(name="ContactId")
	private String contactId;
	
	@OneToOne
    @JoinColumn(name = "RoleId", referencedColumnName = "Id")
	private UserRole role;
	
	@OneToOne
    @JoinColumn(name = "ProfileId", referencedColumnName = "Id")
	private Profile profile;
	
	@Column(name="DefaultCurrencyIsoCode")
	private String defaultCurrencyIsoCode;
	
	@Column(name="FullPhototUrl")
	private String fullPhotoUrl;
	
	@Column(name="SmallPhotoUrl")
	private String smallPhotoUrl;
	
	@Column(name="Locale")
	private Locale locale;
		
	@Transient
	private String timeZone;
	
	@Transient
	private String dateFormatPattern;
	
	@Transient
	private String shortTimeFormat;
	
	@Transient
	private String dateTimeFormatPattern;

	public User() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSalesforceId() {
		return salesforceId;
	}

	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}