package com.redhat.sforce.qb.bean.model;

import java.util.Date;
import java.util.List;

public class Opportunity {	
	
	private String id;
	private String name;
	private String accountId;
	private String description;
	private String stageName;
	private String amount;
	private String probability;
	private Date closeDate;
	private String type;
	private Boolean isClosed;
	private Boolean isWon;
	private String forecastCategory;
	private String currencyIsoCode;
	private Boolean hasOpportunityLineItem;
	private String pricebookId;
	private String pricebookName;
    private Date createdDate;
    private String createdById;
    private String createdByName;
    private String createdByFirstName;
    private String createdByLastName;
    private Date lastModifiedDate;
    private String lastModifiedById;
    private String lastModifiedByName;
    private String lastModifiedByFirstName;
    private String lastModifiedByLastName;
    private String fulfillmentChannel;
    private String billingAddress;
    private String billingCity;
    private String billingCountry;
    private String billingZipPostalCode;
    private String billingState;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private String shippingZipPostalCode;
    private String shippingState;
    private String opportunityNumber;
    private String payNow;
    private String countryOfOrder;
    private String accountName;
    private String oracleAccountNumber;
    private String accountAliasName;
    private User owner;
    private List<OpportunityLineItem> opportunityLineItems;
    private List<Contact> contacts;
    private List<User> salesTeam;
    private List<User> owners;
    
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Boolean getIsWon() {
		return isWon;
	}

	public void setIsWon(Boolean isWon) {
		this.isWon = isWon;
	}

	public String getForecastCategory() {
		return forecastCategory;
	}

	public void setForecastCategory(String forecastCategory) {
		this.forecastCategory = forecastCategory;
	}

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public Boolean getHasOpportunityLineItem() {
		return hasOpportunityLineItem;
	}

	public void setHasOpportunityLineItem(Boolean hasOpportunityLineItem) {
		this.hasOpportunityLineItem = hasOpportunityLineItem;
	}

	public String getPricebookId() {
		return pricebookId;
	}

	public void setPricebookId(String pricebookId) {
		this.pricebookId = pricebookId;
	}

	public String getPricebookName() {
		return pricebookName;
	}

	public void setPricebookName(String pricebookName) {
		this.pricebookName = pricebookName;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedByName() {
		return lastModifiedByName;
	}

	public void setLastModifiedByName(String lastModifiedByName) {
		this.lastModifiedByName = lastModifiedByName;
	}

	public String getLastModifiedByFirstName() {
		return lastModifiedByFirstName;
	}

	public void setLastModifiedByFirstName(String lastModifiedByFirstName) {
		this.lastModifiedByFirstName = lastModifiedByFirstName;
	}

	public String getLastModifiedByLastName() {
		return lastModifiedByLastName;
	}

	public void setLastModifiedByLastName(String lastModifiedByLastName) {
		this.lastModifiedByLastName = lastModifiedByLastName;
	}

	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}

	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
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

	public String getBillingZipPostalCode() {
		return billingZipPostalCode;
	}

	public void setBillingZipPostalCode(String billingZipPostalCode) {
		this.billingZipPostalCode = billingZipPostalCode;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public String getShippingZipPostalCode() {
		return shippingZipPostalCode;
	}

	public void setShippingZipPostalCode(String shippingZipPostalCode) {
		this.shippingZipPostalCode = shippingZipPostalCode;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getOpportunityNumber() {
		return opportunityNumber;
	}

	public void setOpportunityNumber(String opportunityNumber) {
		this.opportunityNumber = opportunityNumber;
	}

	public String getPayNow() {
		return payNow;
	}

	public void setPayNow(String payNow) {
		this.payNow = payNow;
	}

	public String getCountryOfOrder() {
		return countryOfOrder;
	}

	public void setCountryOfOrder(String countryOfOrder) {
		this.countryOfOrder = countryOfOrder;
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
	
	public List<OpportunityLineItem> getOpportunityLineItems() {
		return opportunityLineItems;
	}

	public void setOpportunityLineItems(List<OpportunityLineItem> opportunityLineItems) {
		this.opportunityLineItems = opportunityLineItems;
	}	

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<User> getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(List<User> salesTeam) {
		this.salesTeam = salesTeam;
	}
	
	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	public List<User> getOwners() {
		return owners;
	}	
}