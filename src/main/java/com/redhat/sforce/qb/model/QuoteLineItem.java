package com.redhat.sforce.qb.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class QuoteLineItem extends SObject {

	private static final long serialVersionUID = 1L;
	private String quoteId;
	private String opportunityLineItemId;
	private String sku;
	private String description;
	private String name;
	private Date endDate;
	private Date startDate;
	private Integer term;
	private String contractNumbers;
	private String currencyIsoCode;
	private Double listPrice;
	private String newOrRenewal;
	private String opportunityId;
	private String pricebookEntryId;
	private Integer quantity;
	private Double unitPrice;
	private Double totalPrice;
	private Double yearlySalesPrice;
	private String configuredSku;
	private String pricingAttributes;
	private Double basePrice;
	private Double discountAmount;
	private Double discountPercent;
	private Integer sortOrder;
	@NotNull
	private Product product;
	private Boolean delete;
	private String code;
	private String message;

	public QuoteLineItem() {
		super();
	}

	public QuoteLineItem(Quote quote) {
		setQuoteId(quote.getId());
		setEndDate(quote.getEndDate());
		setStartDate(quote.getStartDate());
		setTerm(quote.getTerm());
		setOpportunityId(quote.getOpportunityId());
		setQuantity(0);
		setUnitPrice(0.00);
		setTotalPrice(0.00);
		setYearlySalesPrice(0.00);
		setBasePrice(0.00);
		setDiscountAmount(0.00);
		setDiscountPercent(0.00);
		setListPrice(0.00);
		setCurrencyIsoCode(quote.getCurrencyIsoCode());
		setProduct(new Product());
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
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

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
