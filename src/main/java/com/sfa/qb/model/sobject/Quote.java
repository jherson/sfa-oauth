package com.sfa.qb.model.sobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

public class Quote implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	@SerializedName("Id")
	private String id;
	
	@SerializedName("Version__c")
	private Double version;
	
	@NotNull
	@SerializedName("Name")
	private String name;
	
	@SerializedName("Number__c")
	private String number;
	
	@SerializedName("CurrencyIsoCode")
	private String currencyIsoCode;
	
	@SerializedName("PayNow__c")
	private String payNow;
	
	@SerializedName("PricebookId__c")
	private String pricebookId;
	
	private String pricebookName;
	
	@SerializedName("Type__c")
	private String type;
	
	@SerializedName("IsActive__c")
	private Boolean isActive;
	
	@SerializedName("Amount__c")
	private Double amount;
	
	@SerializedName("Year1PaymentAmount__c")
	private Double year1PaymentAmount;
	
	@SerializedName("Year2PaymentAmount__c")
	private Double year2PaymentAmount;
	
	@SerializedName("Year3PaymentAmount__c")
	private Double year3PaymentAmount;
	
	@SerializedName("Year4PaymentAmount__c")
	private Double year4PaymentAmount;
	
	@SerializedName("Year5PaymentAmount__c")
	private Double year5PaymentAmount;
	
	@SerializedName("Year6PaymentAmount__c")
	private Double year6PaymentAmount;
	
	@SerializedName("HasQuoteLineItems__c")
	private Boolean hasQuoteLineItems;
	
	@SerializedName("IsCalculated__c")
	private Boolean isCalculated;
	
	@SerializedName("QuoteOwnerId__r")
	private User owner;
	
	@SerializedName("ContactId__r")
	private Contact contact;
	
	@SerializedName("Comments__c")
	private String comments;
	
	@SerializedName("ExpirationDate__c")
	private Date expirationDate;

	@SerializedName("StartDate__c")
	private Date startDate;
	
	@SerializedName("EndDate__c")
	private Date endDate;
	
	@SerializedName("Term__c")
	private Integer term;
	
	@SerializedName("Status__c")
	private String status;
	
	@SerializedName("EffectiveDate__c")
	private Date effectiveDate;
	
	@SerializedName("ReferenceNumber__c")
	private String referenceNumber;
	
	@SerializedName("IsNonStandardPayment__c")
	private Boolean isNonStandardPayment;
	
	@SerializedName("LastCalculatedDAte__c")
	private Date lastCalculatedDate;
	
	@SerializedName("LastPricedDate__c")
	private Date lastPricedDate;
	
	@SerializedName("OpportunityId__r")
	private Opportunity opportunity;
	
	@SerializedName("FeedSubscriptionsForEntity")
	private EntitySubscription entitySubscription;
		
	private List<QuotePriceAdjustment> quotePriceAdjustments;
	
	@SerializedName("QuoteLineItems__r")
	private List<QuoteLineItem> quoteLineItems;
		
	private List<QuoteLineItemSchedule> quoteLineItemSchedules;
	
	private Boolean editMode;

	public Quote() {
		
	}

	public Quote(Opportunity opportunity) {
		super();
		setOpportunity(opportunity);
		setPayNow(opportunity.getPayNow());
		setType("Standard");
		setCurrencyIsoCode(opportunity.getCurrencyIsoCode());
		setPricebookId(opportunity.getPricebook().getId());
		setVersion(new Double(0));
		setAmount(new Double(0));
		setTerm(365);
		setEffectiveDate(new Date());
		setHasQuoteLineItems(Boolean.FALSE);
		setStatus("New");
		
		User owner = new User();
		owner.setId(opportunity.getOwner().getId());
		owner.setName(opportunity.getOwner().getName());
		setOwner(owner);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTime(getEffectiveDate());
		calendar.add(Calendar.DATE, 15);
		setExpirationDate(calendar.getTime());

		calendar.setTime(opportunity.getCloseDate());
		calendar.add(Calendar.DATE, 1);
		setStartDate(calendar.getTime());

		calendar.add(Calendar.YEAR, getTerm());
		calendar.add(Calendar.DATE, -1);
		setEndDate(calendar.getTime());

		setQuoteLineItems(new ArrayList<QuoteLineItem>());
		setQuotePriceAdjustments(new ArrayList<QuotePriceAdjustment>());
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public String getPayNow() {
		return payNow;
	}

	public void setPayNow(String payNow) {
		this.payNow = payNow;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getYear1PaymentAmount() {
		return year1PaymentAmount;
	}

	public void setYear1PaymentAmount(Double year1PaymentAmount) {
		this.year1PaymentAmount = year1PaymentAmount;
	}

	public Double getYear2PaymentAmount() {
		return year2PaymentAmount;
	}

	public void setYear2PaymentAmount(Double year2PaymentAmount) {
		this.year2PaymentAmount = year2PaymentAmount;
	}

	public Double getYear3PaymentAmount() {
		return year3PaymentAmount;
	}

	public void setYear3PaymentAmount(Double year3PaymentAmount) {
		this.year3PaymentAmount = year3PaymentAmount;
	}

	public Double getYear4PaymentAmount() {
		return year4PaymentAmount;
	}

	public void setYear4PaymentAmount(Double year4PaymentAmount) {
		this.year4PaymentAmount = year4PaymentAmount;
	}

	public Double getYear5PaymentAmount() {
		return year5PaymentAmount;
	}

	public void setYear5PaymentAmount(Double year5PaymentAmount) {
		this.year5PaymentAmount = year5PaymentAmount;
	}

	public Double getYear6PaymentAmount() {
		return year6PaymentAmount;
	}

	public void setYear6PaymentAmount(Double year6PaymentAmount) {
		this.year6PaymentAmount = year6PaymentAmount;
	}

	public Boolean getHasQuoteLineItems() {
		return hasQuoteLineItems;
	}

	public void setHasQuoteLineItems(Boolean hasQuoteLineItems) {
		this.hasQuoteLineItems = hasQuoteLineItems;
	}

	public Boolean getIsCalculated() {
		return isCalculated;
	}

	public void setIsCalculated(Boolean isCalculated) {
		this.isCalculated = isCalculated;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTerm() {
		return term;
	}		

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Boolean getIsNonStandardPayment() {
		return isNonStandardPayment;
	}

	public void setIsNonStandardPayment(Boolean isNonStandardPayment) {
		this.isNonStandardPayment = isNonStandardPayment;
	}

	public Date getLastCalculatedDate() {
		return lastCalculatedDate;
	}

	public void setLastCalculatedDate(Date lastCalculatedDate) {
		this.lastCalculatedDate = lastCalculatedDate;
	}
	
	public Date getLastPricedDate() {
		return lastPricedDate;
	}

	public void setLastPricedDate(Date lastPricedDate) {
		this.lastPricedDate = lastPricedDate;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public List<QuotePriceAdjustment> getQuotePriceAdjustments() {
		return quotePriceAdjustments;
	}

	public void setQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustments) {
		this.quotePriceAdjustments = quotePriceAdjustments;
	}

	public List<QuoteLineItem> getQuoteLineItems() {
		return quoteLineItems;
	}

	public void setQuoteLineItems(List<QuoteLineItem> quoteLineItems) {
		this.quoteLineItems = quoteLineItems;
	}

	public List<QuoteLineItemSchedule> getQuoteLineItemSchedules() {
		return quoteLineItemSchedules;
	}

	public void setQuoteLineItemSchedules(List<QuoteLineItemSchedule> quoteLineItemSchedules) {
		this.quoteLineItemSchedules = quoteLineItemSchedules;
	}

	public Boolean getEditMode() {
		return editMode;
	}

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public EntitySubscription getEntitySubscription() {
		return entitySubscription;
	}

	public void setEntitySubscription(EntitySubscription entitySubscription) {
		this.entitySubscription = entitySubscription;
	}
}