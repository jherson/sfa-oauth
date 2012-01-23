package com.redhat.sforce.qb.bean.model;

import java.util.Date;

public class QuoteLineItem extends SObject {

	private static final long serialVersionUID = 1L;
	private String quoteId;
	private String opportunityLineItemId;
	private String description;
	private String name;
	private String family;
	private String productCode;
	private String productDescription;
	private String productFamily;
	private Date endDate;
	private Date startDate;
	private Double term;
	private String contractNumbers;
	private String currencyIsoCode;
	private Double listPrice;
	private String newOrRenewal;
	private String opportunityId;
	private String pricebookEntryId;
	private String productId;
	private Double quantity;
	private Double unitPrice;
	private Double totalPrice;
	private Double yearlySalesPrice;
    private String configuredSku;
    private String pricingAttributes;
    private Double basePrice;
    private String unitOfMeasure;
    private Integer sortOrder;

    public QuoteLineItem() {
        super();
    }
	
	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getOpportunityLineItemId() {
		return opportunityLineItemId;
	}
	
	public void setOpportunityLineItemId(String opportunityLineItemId) {
		this.opportunityLineItemId = opportunityLineItemId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFamily() {
		return family;
	}
	
	public void setFamily(String family) {
		this.family = family;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Double getTerm() {
		return term;
	}
	
	public void setTerm(Double term) {
		this.term = term;
	}
	
	public String getContractNumbers() {
		return contractNumbers;
	}
	
	public void setContractNumbers(String contractNumbers) {
		this.contractNumbers = contractNumbers;
	}
	
	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}
	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}
	
	public Double getListPrice() {
		return listPrice;
	}
	
	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}
	
	public String getNewOrRenewal() {
		return newOrRenewal;
	}
	
	public void setNewOrRenewal(String newOrRenewal) {
		this.newOrRenewal = newOrRenewal;
	}
	
	public String getOpportunityId() {
		return opportunityId;
	}
	
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	
	public String getPricebookEntryId() {
		return pricebookEntryId;
	}
	
	public void setPricebookEntryId(String pricebookEntryId) {
		this.pricebookEntryId = pricebookEntryId;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Double getYearlySalesPrice() {
		return yearlySalesPrice;
	}
	
	public void setYearlySalesPrice(Double yearlySalesPrice) {
		this.yearlySalesPrice = yearlySalesPrice;
	}
		
	public String getConfiguredSku() {
		return configuredSku;
	}
	
	public void setConfiguredSku(String configuredSku) {
		this.configuredSku = configuredSku;
	}
	
	public String getPricingAttributes() {
		return pricingAttributes;
	}
	
	public void setPricingAttributes(String pricingAttributes) {
		this.pricingAttributes = pricingAttributes;
	}
	
	public Double getBasePrice() {
		return basePrice;
	}
	
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
