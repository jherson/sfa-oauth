package com.redhat.sforce.qb.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.validation.constraints.NotNull;

public class Quote extends SObject {	
	
	private static final long serialVersionUID = 1L;
	private String opportunityId;
	private String link;
	private Double version;		
	@NotNull
	private String name;
	private String number;
	private String currencyIsoCode;
	private String payNow;
	private String pricebookId;
	private String pricebookName;
	private String type;
	private Boolean isActive;
	private Double amount;
	private Double year1PaymentAmount;
	private Double year2PaymentAmount;
	private Double year3PaymentAmount;
	private Double year4PaymentAmount;
	private Double year5PaymentAmount;
	private Double year6PaymentAmount;
	private Boolean hasQuoteLineItems;
	private Boolean isCalculated;
	private String ownerId;
	private String ownerName;
	private String contactId;
	private String contactName;
	private String comments;
	private Date expirationDate;
	private Date startDate;
	private Date endDate;
	private Integer term;
	private Date effectiveDate;
	private String referenceNumber;
	private Boolean isNonStandardPayment;
	private String approvalsRequested;
	private Boolean aprvlRqstNonStd;
	private Boolean aprvlRqstPaymentTerms;
	private Boolean aprvlRqstPriceDiscnt;
	private Boolean hasApprovalRequests;
	private Date lastCalculatedDate;
	private List<QuotePriceAdjustment> quotePriceAdjustments;
	private List<QuoteLineItem> quoteLineItems;
	private List<QuoteLineItemSchedule> quoteLineItemSchedules;
	private Boolean selectAll;
	
	

    public Boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	public Quote() {
        super();
    }
    
    public Quote(Opportunity opportunity) {
        super();        
        setOpportunityId(opportunity.getId());
		setOwnerId(opportunity.getOwner().getId());
		setOwnerName(opportunity.getOwner().getName());
		setPayNow(opportunity.getPayNow());
		setType("Standard");
		setCurrencyIsoCode(opportunity.getCurrencyIsoCode());
		setPricebookId(opportunity.getPricebookId());
		setVersion(new Double(0));
		setAmount(new Double(0));
		setTerm(365);
		setEffectiveDate(new Date());
		setHasQuoteLineItems(Boolean.FALSE);
		setHasApprovalRequests(Boolean.FALSE);
		
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
        
        String[] reasons = new String[] {"Platform and Cloud", "Middleware", "Services"};
		List<QuotePriceAdjustment> quotePriceAdjustmentList = new ArrayList<QuotePriceAdjustment>();
		for (int i = 0; i < reasons.length; i++) {
			QuotePriceAdjustment quotePriceAdjustment = new QuotePriceAdjustment(0.00, "Percent", 0.00, reasons[i], "Amount");			
			quotePriceAdjustmentList.add(quotePriceAdjustment);
		}		
		setQuotePriceAdjustments(quotePriceAdjustmentList);
    }
    
	public String getOpportunityId() {
		return opportunityId;
	}
	
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
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
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getContactId() {
		return contactId;
	}
	
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
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
	
	public String getApprovalsRequested() {
		return approvalsRequested;
	}
	
	public void setApprovalsRequested(String approvalsRequested) {
		this.approvalsRequested = approvalsRequested;
	}
	
	public Boolean getAprvlRqstNonStd() {
		return aprvlRqstNonStd;
	}
	
	public void setAprvlRqstNonStd(Boolean aprvlRqstNonStd) {
		this.aprvlRqstNonStd = aprvlRqstNonStd;
	}
	
	public Boolean getAprvlRqstPaymentTerms() {
		return aprvlRqstPaymentTerms;
	}
	
	public void setAprvlRqstPaymentTerms(Boolean aprvlRqstPaymentTerms) {
		this.aprvlRqstPaymentTerms = aprvlRqstPaymentTerms;
	}
	
	public Boolean getAprvlRqstPriceDiscnt() {
		return aprvlRqstPriceDiscnt;
	}
	
	public void setAprvlRqstPriceDiscnt(Boolean aprvlRqstPriceDiscnt) {
		this.aprvlRqstPriceDiscnt = aprvlRqstPriceDiscnt;
	}
	
	public Boolean getHasApprovalRequests() {
		return hasApprovalRequests;
	}
	
	public void setHasApprovalRequests(Boolean hasApprovalRequests) {
		this.hasApprovalRequests = hasApprovalRequests;
	}
	
	public Date getLastCalculatedDate() {
		return lastCalculatedDate;
	}
	
	public void setLastCalculatedDate(Date lastCalculatedDate) {
		this.lastCalculatedDate = lastCalculatedDate;
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