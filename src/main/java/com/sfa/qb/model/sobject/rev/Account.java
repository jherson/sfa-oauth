package com.sfa.qb.model.sobject.rev;

import java.io.Serializable;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Entity;
import com.sfa.persistence.annotation.Table;
import com.sforce.soap.partner.sobject.SObject;

@Entity
@Table(name="Account")

public class Account extends SObject implements Serializable {

	private static final long serialVersionUID = 4563614967304678524L;
		
	@Column(name="BillingCity")	
	private String billingCity;
	
	@Column(name="BillingCountry")	
	private String billingCountry;
	
	@Column(name="BillingPostalCode")	
	private String billingPostalCode;
	
	@Column(name="BillingState")	
	private String billingState;
	
	@Column(name="BillingStreet")	
	private String billingStreet;
	
	@Column(name="ShippingCity")	
	private String shippingCity;
	
	@Column(name="ShippingCountry")	
	private String shippingCountry;
	
	@Column(name="ShippingPostalCode")	
	private String shippingPostalCode;
	
	@Column(name="ShippingState")	
	private String shippingState;
	
	@Column(name="ShippingStreet")	
	private String shippingStreet;
	
	@Column(name="VATNumber__c")	
	private String vatNumber;
	
	@Column(name="Name")	
	private String accountName;
	
	@Column(name="OracleAccountNumber__c")	
	private String oracleAccountNumber;
	
	@Column(name="Account_Alias_Name__c")	
	private String accountAliasName;
	
	
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
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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