package com.sfa.qb.model.sobject;

import java.util.Date;
import java.util.List;

import com.sfa.persistence.Column;
import com.sfa.persistence.OneToOne;

public class Opportunity extends QuoteBuilderObject {

	private static final long serialVersionUID = -1577960793119757037L;
/**
	+ "OpportunityId__r.CreatedDate, "
	+ "OpportunityId__r.LastModifiedDate, " 
	+ "OpportunityId__r.FulfillmentChannel__c, "
**/
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="StageName")
	private String stageName;
	
	@Column(name="Amount")
	private Double amount;
	
	@Column(name="Probability")
	private String probability;
	
	@Column(name="CloseDate")
	private Date closeDate;
	
	@Column(name="OpportunityType__c")
	private String opportunityType;
	
	@Column(name="IsClosed")
	private Boolean isClosed;
	
	@Column(name="IsWon")
	private Boolean isWon;
	
	@Column(name="ForecastCategory")
	private String forecastCategory;
	
	@Column(name="CurrencyIsoCode")
	private String currencyIsoCode;
	
	@Column(name="HasOpportunityLineItem")
	private Boolean hasOpportunityLineItem;
	
	@OneToOne
	@Column(name="Pricebook2")
	private Pricebook pricebook;
	
	@Column(name="FulfillmentChannel__c")
	private String fulfillmentChannel;
	
	@Column(name="BillingAddress__c")
	private String billingAddress;
	
	@Column(name="BillingCity__c")
	private String billingCity;
	
	@Column(name="BillingCountry__c")
	private String billingCountry;
	
	@Column(name="BillingZipPostalCode__c")
	private String billingZipPostalCode;
	
	@Column(name="BillingState__c")
	private String billingState;
	
	@Column(name="ShippingAddress__c")
	private String shippingAddress;
	
	@Column(name="ShippingCity__c")
	private String shippingCity;
	
	@Column(name="ShippingCountry__c")
	private String shippingCountry;
	
	@Column(name="ShippingZipPostalCode__c")
	private String shippingZipPostalCode;
	
	@Column(name="ShippingState__c")
	private String shippingState;
	
	@Column(name="OpportunityNumber__c")
	private String opportunityNumber;
	
	@Column(name="Pay_Now__c")
	private String payNow;
	
	@Column(name="Country_of_Order__c")
	private String countryOfOrder;
	
	@Column(name="Super_Region__c")
	private String superRegion;
	
	@Column(name="PaymentType__c")
	private String paymentType;
	
	@OneToOne
    @Column(name="Account")
	private Account account;
	
	private User owner;
	
	private List<CreditCheck> creditChecks;
	
	private List<OpportunityLineItem> opportunityLineItems;
	
	private List<OpportunityPartner> opportunityPartners;
	private List<Contact> contacts;
	private List<User> salesTeam;
	private List<User> owners;

	public Opportunity() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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

	public String getOpportunityType() {
		return opportunityType;
	}

	public void setOpportunityType(String opportunityType) {
		this.opportunityType = opportunityType;
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

	public Pricebook getPricebook() {
		return pricebook;
	}

	public void setPricebook(Pricebook pricebook) {
		this.pricebook = pricebook;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<CreditCheck> getCreditChecks() {
		return creditChecks;
	}

	public void setCreditChecks(List<CreditCheck> creditChecks) {
		this.creditChecks = creditChecks;
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

	public String getSuperRegion() {
		return superRegion;
	}

	public void setSuperRegion(String superRegion) {
		this.superRegion = superRegion;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	public List<OpportunityLineItem> getOpportunityLineItems() {
		return opportunityLineItems;
	}

	public void setOpportunityLineItems(List<OpportunityLineItem> opportunityLineItems) {
		this.opportunityLineItems = opportunityLineItems;
	}
		
	public List<OpportunityPartner> getOpportunityPartners() {
		return opportunityPartners;
	}

	public void setOpportunityPartners(List<OpportunityPartner> opportunityPartners) {
		this.opportunityPartners = opportunityPartners;
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