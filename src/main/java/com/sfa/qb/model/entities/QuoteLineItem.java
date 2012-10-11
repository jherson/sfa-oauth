package com.sfa.qb.model.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Table(name = "QuoteLineItem")

public class QuoteLineItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
	
	@Column(name="SalesforceId", length=20)
	private String salesforceId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="QuoteId")
	private Quote quote;
	
	@Column(name="Name", length=60)
	private String name;
	
	@Column(name="EndDate")
	private Date endDate;
	
	@Column(name="StartDate")
	private Date startDate;
	
	@Column(name="Term")
	private Integer term;
	
	@Column(name="ContractNumbers", length=255)
	private String contractNumbers;
	
	@Column(name="CurrencyIsoCode", length=3)
	private String currencyIsoCode;
	
	@Column(name="ListPrice")
	private Double listPrice;
	
	@Column(name="NewOrRenewal", length=10)
	private String newOrRenewal;
	
	@Column(name="Quantity")
	private Integer quantity;
	
	@Column(name="UnitPrice")
	private Double unitPrice;
	
	@Column(name="TotalPrice")
	private Double totalPrice;
	
	@Column(name="YearlySalesPrice")
	private Double yearlySalesPrice;
	
	@Column(name="ConfiguredSKU", length=20)
	private String configuredSku;
	
	@Column(name="PricingAttributes", length=255)
	private String pricingAttributes;
	
	@Column(name="DiscountAmount")
	private Double discountAmount;
	
	@Column(name="DiscountPercent")
	private Double discountPercent;
	
	@Column(name="LineNumber")
	private Integer lineNumber;
	
	@OneToOne(optional = false)
    @JoinColumn(name = "PricebookEntryId", referencedColumnName = "Id")
	private PricebookEntry pricebookEntry;
	
	@Column(name="BasePrice")
	private Double basePrice;
	
	@Column(name="IsPriced")
	private Boolean isPriced;
	
	@Column(name="Code", length=10)
	private String code;
	
	@Column(name="Message", length=255)
	private String message;
	
	@Transient
	private Boolean selected;

	public QuoteLineItem() {

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

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Boolean getIsPriced() {
		return isPriced;
	}

	public void setIsPriced(Boolean isPriced) {
		this.isPriced = isPriced;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public String getSalesforceId() {
		return salesforceId;
	}

	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}

	public PricebookEntry getPricebookEntry() {
		return pricebookEntry;
	}

	public void setPricebookEntry(PricebookEntry pricebookEntry) {
		this.pricebookEntry = pricebookEntry;
	}
}