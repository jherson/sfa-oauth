package com.redhat.sforce.qb.bean.model;

import java.util.Date;

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
	private String ownerId;
	private String ownerName;
	private String ownerFirstName;
	private String ownerLastName;
	private String ownerRoleName;
	private String ownerProfileName;
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
	
}
