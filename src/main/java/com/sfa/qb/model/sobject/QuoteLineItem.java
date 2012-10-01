package com.sfa.qb.model.sobject;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.sfa.persistence.annotation.Column;

public class QuoteLineItem extends QuoteBuilderObject {

	private static final long serialVersionUID = 1L;

	/**
	+ "        CurrencyIsoCode, "
	+ "        CreatedDate, "
	+ "        CreatedBy.Id, "
	+ "        CreatedBy.Name, "
	+ "        LastModifiedDate, "
	+ "        LastModifiedBy.Id, "
	+ "        LastModifiedBy.Name, "
	+ "        Product__r.Id, "
	+ "        Product__r.Description, "
	+ "        Product__r.Name, "
	+ "        Product__r.Family, "
	+ "        Product__r.ProductCode, "
	+ "        Product__r.Primary_Business_Unit__c, "
	+ "        Product__r.Product_Line__c, "
	+ "        Product__r.Unit_Of_Measure__c, "
	+ "        Product__r.Term__c, "
	+ "        PricebookEntryId__c, "
	+ "        ProductDescription__c, "
	+ " From   QuoteLineItem__r ";
	**/
	
	@Column(name="QuoteId__c")
	private String quoteId;
	
	@Column(name="OpportunityLineItemId__c")
	private String opportunityLineItemId;
	
	
	private String sku;
	private String description;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="EndDate__c")
	private Date endDate;
	
	@Column(name="StartDate__c")
	private Date startDate;
	
	@Column(name="Term__c")
	private Integer term;
	
	@Column(name="ContractNumbers__c")
	private String contractNumbers;
	
	@Column(name="CurrencyIsoCode")
	private String currencyIsoCode;
	
	@Column(name="ListPrice__c")
	private Double listPrice;
	
	@Column(name="NewOrRenewal__c")
	private String newOrRenewal;
	
	@Column(name="OpportunityId__c")
	private String opportunityId;
	
	@Column(name="PricebookEntryId__c")
	private String pricebookEntryId;
	
	@Column(name="Quantity__c")
	private Integer quantity;
	
	@Column(name="UnitPrice__c")
	private Double unitPrice;
	
	@Column(name="TotalPrice__c")
	private Double totalPrice;
	
	@Column(name="YearlySalesPrice__c")
	private Double yearlySalesPrice;
	
	@Column(name="Configured_SKU__c")
	private String configuredSku;
	
	@Column(name="Pricing_Attributes__c")
	private String pricingAttributes;
	
	@Column(name="DiscountAmount__c")
	private Double discountAmount;
	
	@Column(name="DiscountPercent__c")
	private Double discountPercent;
	
	@Column(name="LineNumber__c")
	private Integer lineNumber;
	
	@NotNull
	private Product product;
	private Boolean selected;
	
	private Double basePrice;
	
	@Column(name="Code__c")
	private String code;
	
	@Column(name="Message__c")
	private String message;
	
	private List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustments;

	public QuoteLineItem() {
		super();
	}

	public QuoteLineItem(Quote quote) {
		setQuoteId(quote.getId());
		setEndDate(quote.getEndDate());
		setStartDate(quote.getStartDate());
		setTerm(quote.getTerm());
		setOpportunityId(quote.getOpportunity().getId());
		setQuantity(0);
		setUnitPrice(0.00);
		setTotalPrice(0.00);
		setYearlySalesPrice(0.00);
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

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean delete) {
		this.selected = delete;
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

	public List<QuoteLineItemPriceAdjustment> getQuoteLineItemPriceAdjustments() {
		return quoteLineItemPriceAdjustments;
	}

	public void setQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustments) {
		this.quoteLineItemPriceAdjustments = quoteLineItemPriceAdjustments;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
}
