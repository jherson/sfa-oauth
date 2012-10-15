package com.sfa.qb.model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Quote")

public class Quote implements Serializable, Cloneable {

	private static final long serialVersionUID = -1411790645889339263L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="SalesforceId", length=20, unique=true)
	private String salesforceId;
	
	public String getSalesforceId() {
		return salesforceId;
	}
	
	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}
	
	
	@Column(name="CreatedDate")
	private Date createdDate;
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	@Column(name="CreatedById")
	private String createdById;
	
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	
			
	@Column(name="LastModifiedDate")
	private Date lastModifiedDate;

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	@Column(name="LastModifiedById")
	private String lastModifiedById;
	
	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}
	
			
	@Column(name="Version")
	private Double version;
	
	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}
	
	
	@NotNull
	@Column(name="Name")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(name="Number")
	private String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	
	@Column(name="CurrencyIsoCode")
	private String currencyIsoCode;
	
	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}
	
	
	@Column(name="PayNow")
	private String payNow;
	
	public String getPayNow() {
		return payNow;
	}

	public void setPayNow(String payNow) {
		this.payNow = payNow;
	}
	
	
	@Column(name="PricebookId")
	private String pricebookId;
	
	public String getPricebookId() {
		return pricebookId;
	}

	public void setPricebookId(String pricebookId) {
		this.pricebookId = pricebookId;
	}
	
	
	@Column(name="Type")
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	@Column(name="IsActive")
	private Boolean isActive;
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
	@Column(name="Amount")
	private Double amount;
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	@Column(name="HasQuoteLineItems")
	private Boolean hasQuoteLineItems;
	
	public Boolean getHasQuoteLineItems() {
		return hasQuoteLineItems;
	}

	public void setHasQuoteLineItems(Boolean hasQuoteLineItems) {
		this.hasQuoteLineItems = hasQuoteLineItems;
	}
	
	
	@Column(name="IsCalculated")
	private Boolean isCalculated;
	
	public Boolean getIsCalculated() {
		return isCalculated;
	}

	public void setIsCalculated(Boolean isCalculated) {
		this.isCalculated = isCalculated;
	}
	
	
	@Column(name="Comments")
	private String comments;
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	@Column(name="ExpirationDate")
	private Date expirationDate;
	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	
	@Column(name="StartDate")
	private Date startDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	@Column(name="EndDate")
	private Date endDate;
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	@Column(name="Term")
	private Integer term;
	
	public Integer getTerm() {
		return term;
	}		

	public void setTerm(Integer term) {
		this.term = term;
	}
	
	
	@Column(name="Status")
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Column(name="EffectiveDate")
	private Date effectiveDate;
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
	@Column(name="ReferenceNumber")
	private String referenceNumber;
	
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	
	@Column(name="LastCalculatedDate")
	private Date lastCalculatedDate;
	
	public Date getLastCalculatedDate() {
		return lastCalculatedDate;
	}

	public void setLastCalculatedDate(Date lastCalculatedDate) {
		this.lastCalculatedDate = lastCalculatedDate;
	}
	
	
	@Column(name="LastPricedDate")
	private Date lastPricedDate;
	
	public Date getLastPricedDate() {
		return lastPricedDate;
	}

	public void setLastPricedDate(Date lastPricedDate) {
		this.lastPricedDate = lastPricedDate;
	}
	
	@OneToMany(mappedBy="quote")
	private List<PaymentSchedule> paymentSchedules;
	
	public List<PaymentSchedule> getPaymentSchedules() {
		return paymentSchedules;
	}
	
	public void setPaymentSchedules(List<PaymentSchedule> paymentSchedules) {
		this.paymentSchedules = paymentSchedules;
	}
	
	
	@OneToMany(mappedBy="quote")
    private List<QuoteLineItem> quoteLineItems;
	
	public List<QuoteLineItem> getQuoteLineItems() {
		return quoteLineItems;
	}
	
	public void setQuoteLineItems(List<QuoteLineItem> quoteLineItems) {
		this.quoteLineItems = quoteLineItems;
	}
	
	@Transient
	private Boolean editMode;
	
	public Boolean getEditMode() {
		return editMode;
	}

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}
	
	public Quote() {
		
	}

//	public Quote(Opportunity opportunity) {
//		super();
//		setOpportunity(opportunity);
//		//setOwnerId(opportunity.getOwner().getId());
//		//setOwnerName(opportunity.getOwner().getName());
//		setPayNow(opportunity.getPayNow());
//		setType("Standard");
//		setCurrencyIsoCode(opportunity.getCurrencyIsoCode());
//		setPricebookId(opportunity.getPricebook().getId());
//		setVersion(new Double(0));
//		setAmount(new Double(0));
//		setTerm(365);
//		setEffectiveDate(new Date());
//		setHasQuoteLineItems(Boolean.FALSE);
//		setStatus("New");
//
//		GregorianCalendar calendar = new GregorianCalendar();
//
//		calendar.setTime(getEffectiveDate());
//		calendar.add(Calendar.DATE, 15);
//		setExpirationDate(calendar.getTime());
//
//		calendar.setTime(opportunity.getCloseDate());
//		calendar.add(Calendar.DATE, 1);
//		setStartDate(calendar.getTime());
//
//		calendar.add(Calendar.YEAR, getTerm());
//		calendar.add(Calendar.DATE, -1);
//		setEndDate(calendar.getTime());
//
//		//setQuoteLineItems(new ArrayList<QuoteLineItem>());
//		setQuotePriceAdjustments(new ArrayList<QuotePriceAdjustment>());
//	
}