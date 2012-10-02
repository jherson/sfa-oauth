package com.sfa.qb.model.sobject.rev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Entity;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.OneToMany;
import com.sfa.persistence.annotation.OneToOne;
import com.sfa.persistence.annotation.Table;
import com.sfa.qb.model.chatter.Followers;
import com.sfa.qb.model.sobject.Contact;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.model.sobject.QuoteLineItemSchedule;
import com.sfa.qb.model.sobject.QuotePriceAdjustment;
import com.sforce.soap.partner.sobject.SObject;

@Entity
@Table(name="Quote__c")

public class Quote extends SObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name="CreatedDate")
	private Date createdDate;
	
	@Column(name="CreatedById")
	private String createdById;
		
	@OneToOne(name="CreatedBy", referenceColumnName="CreatedById")
	private User createdBy;
	
	@OneToOne(name="LastModifiedBy", referenceColumnName="LastModifiedById")
	private User lastModifiedBy;
	
	@Column(name="LastModifiedDate")
	private Date lastModifiedDate;
	
	@Column(name="LastModifiedById")
	private String lastModifiedById;
		
	@Column(name="Link__c")
	private String link;
	
	@Column(name="Version__c")
	private Double version;
	
	@NotNull
	@Column(name="Name")
	private String quoteName;
	
	@Column(name="Number__c")
	private String number;
	
	@Column(name="CurrencyIsoCode")
	private String currencyIsoCode;
	
	@Column(name="PayNow__c")
	private String payNow;
	
	@Column(name="PricebookId__c")
	private String pricebookId;
	
	private String pricebookName;
	
	@Column(name="Type__c")
	private String type;
	
	@Column(name="IsActive__c")
	private Boolean isActive;
	
	@Column(name="Amount__c")
	private Double amount;
	
	@Column(name="Year1PaymentAmount__c")
	private Double year1PaymentAmount;
	
	@Column(name="Year2PaymentAmount__c")
	private Double year2PaymentAmount;
	
	@Column(name="Year3PaymentAmount__c")
	private Double year3PaymentAmount;
	
	@Column(name="Year4PaymentAmount__c")
	private Double year4PaymentAmount;
	
	@Column(name="Year5PaymentAmount__c")
	private Double year5PaymentAmount;
	
	@Column(name="Year6PaymentAmount__c")
	private Double year6PaymentAmount;
	
	@Column(name="HasQuoteLineItems__c")
	private Boolean hasQuoteLineItems;
	
	@Column(name="IsCalculated__c")
	private Boolean isCalculated;
	
	@OneToOne(name="OwnerId__r", referenceColumnName="OwnerId__c")
	private User owner;
	
	@OneToOne(name="ContactId__r", referenceColumnName="ContactId__c")
	private Contact contact;
	
	@Column(name="Comments__c")
	private String comments;
	
	@Column(name="ExpirationDate__c")
	private Date expirationDate;
	
	@Column(name="StartDate__c")
	private Date startDate;
	
	@Column(name="EndDate__c")
	private Date endDate;
	
	@Column(name="Term__c")
	private Integer term;
	
	@Column(name="Status__c")
	private String status;
	
	@Column(name="EffectiveDate__c")
	private Date effectiveDate;
	
	@Column(name="ReferenceNumber__c")
	private String referenceNumber;
	
	@Column(name="IsNonStandardPayment__c")
	private Boolean isNonStandardPayment;
	
	@Column(name="LastCalculatedDate__c")
	private Date lastCalculatedDate;
	
	@Column(name="LastPricedDate__c")
	private Date lastPricedDate;
	
	@OneToOne(name="OpportunityId__r", referenceColumnName="OpportunityId__c")
	private Opportunity opportunity;
	
	
	private Followers followers;
	
	
	private List<QuotePriceAdjustment> quotePriceAdjustments;
	
	@OneToMany(name="QuoteLineItem__r")
	private List<QuoteLineItem> quoteLineItems;
	
	
	private List<QuoteLineItemSchedule> quoteLineItemSchedules;

	public Quote() {
		super();
	}

	public Quote(Opportunity opportunity) {
		super();
		setOpportunity(opportunity);
		//setOwnerId(opportunity.getOwner().getId());
		//setOwnerName(opportunity.getOwner().getName());
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

		//setQuoteLineItems(new ArrayList<QuoteLineItem>());
		setQuotePriceAdjustments(new ArrayList<QuotePriceAdjustment>());
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
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

	public Followers getFollowers() {
		return followers;
	}

	public void setFollowers(Followers followers) {
		this.followers = followers;
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
}