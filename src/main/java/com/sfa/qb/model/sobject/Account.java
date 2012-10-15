package com.sfa.qb.model.sobject;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Account implements Serializable {

	private static final long serialVersionUID = 4563614967304678524L;
	
	@SerializedName("Id")
	private String id;
		
	@SerializedName("BillingCity")	
	private String billingCity;
	
	@SerializedName("BillingCountry")	
	private String billingCountry;
	
	@SerializedName("BillingPostalCode")	
	private String billingPostalCode;
	
	@SerializedName("BillingState")	
	private String billingState;
	
	@SerializedName("BillingStreet")	
	private String billingStreet;
	
	@SerializedName("ShippingCity")	
	private String shippingCity;
	
	@SerializedName("ShippingCountry")	
	private String shippingCountry;
	
	@SerializedName("ShippingPostalCode")	
	private String shippingPostalCode;
	
	@SerializedName("ShippingState")	
	private String shippingState;
	
	@SerializedName("ShippingStreet")	
	private String shippingStreet;
	
	@SerializedName("VATNumber__c")	
	private String vatNumber;
	
	@SerializedName("Name")	
	private String name;
	
	@SerializedName("OracleAccountNumber__c")	
	private String oracleAccountNumber;
	
	@SerializedName("Account_Alias_Name__c")	
	private String accountAliasName;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBillingCity() {
		return billingCity;
	}
	
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	
	public String getBillingCountry() {
		return billingCountry;
	}
	
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	
	public String getBillingPostalCode() {
		return billingPostalCode;
	}
	
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}
	
	public String getBillingState() {
		return billingState;
	}
	
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	
	public String getBillingStreet() {
		return billingStreet;
	}
	
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	
	public String getShippingCity() {
		return shippingCity;
	}
	
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	
	public String getShippingCountry() {
		return shippingCountry;
	}
	
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}
	
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}
	
	public String getShippingState() {
		return shippingState;
	}
	
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	
	public String getShippingStreet() {
		return shippingStreet;
	}
	
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}
	
	public String getVatNumber() {
		return vatNumber;
	}
	
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOracleAccountNumber() {
		return oracleAccountNumber;
	}
	
	public void setOracleAccountNumber(String oracleAccountNumber) {
		this.oracleAccountNumber = oracleAccountNumber;
	}
	
	public String getAccountAliasName() {
		return accountAliasName;
	}
	
	public void setAccountAliasName(String accountAliasName) {
		this.accountAliasName = accountAliasName;
	}
}