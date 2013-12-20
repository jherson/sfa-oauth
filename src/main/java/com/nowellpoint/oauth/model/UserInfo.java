package com.nowellpoint.oauth.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserInfo implements Serializable {

	/**
	 * "Alias":"JHers","CommunityNickname":"JHers","IsActive":true,"TimeZoneSidKey":"America/Los_Angeles","UserRole":null,"LocaleSidKey":"en_US","EmailEncodingKey":"ISO-8859-1","Profile":{"attributes":{"type":"Profile","url":"/services/data/v29.0/sobjects/Profile/00e30000000fJ0uAAE"},"Id":"00e30000000fJ0uAAE","Name":"System Administrator","PermissionsCustomizeApplication":true},"UserType":"Standard","LanguageLocaleKey":"en_US","EmployeeNumber":null,"DelegatedApproverId":null,"ManagerId":null,"AboutMe":null
	 */
	
	private static final long serialVersionUID = 5267942011876349443L;
	
	@SerializedName("Id")
	private String id;
	
	@SerializedName("Username")
	private String username;
	
	@SerializedName("LastName")
	private String lastName;
	
	@SerializedName("LastName")
	private String firstName;
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("Name")
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
}