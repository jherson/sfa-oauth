package com.redhat.sforce.qb.bean.model;

import java.util.Date;

public class QuoteLineItemSchedule extends SObject {

	private static final long serialVersionUID = 1L;
    private String name;
    private Double prorateUnitPrice;
    private Double prorateTotalPrice;
    private Double prorateYearTotalPrice;
    private String quoteId;
    private Date startDate;
    private Double pricePerDay;
    private Integer year;
    private String quoteLineItemId;
    private Date endDate;
    private Double prorateYearUnitPrice;
    private String productDescription;
    private String productCode;
    private Integer term;
    private Integer quantity;
    private Double yearlySalesPrice;
    private Double totalPrice;
    private String contractNumbers;

	public QuoteLineItemSchedule() {
        super();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getProrateUnitPrice() {
		return prorateUnitPrice;
	}

	public void setProrateUnitPrice(Double prorateUnitPrice) {
		this.prorateUnitPrice = prorateUnitPrice;
	}

	public Double getProrateTotalPrice() {
		return prorateTotalPrice;
	}

	public void setProrateTotalPrice(Double prorateTotalPrice) {
		this.prorateTotalPrice = prorateTotalPrice;
	}

	public Double getProrateYearTotalPrice() {
		return prorateYearTotalPrice;
	}

	public void setProrateYearTotalPrice(Double prorateYearTotalPrice) {
		this.prorateYearTotalPrice = prorateYearTotalPrice;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getQuoteLineItemId() {
		return quoteLineItemId;
	}

	public void setQuoteLineItemId(String quoteLineItemId) {
		this.quoteLineItemId = quoteLineItemId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getProrateYearUnitPrice() {
		return prorateYearUnitPrice;
	}

	public void setProrateYearUnitPrice(Double prorateYearUnitPrice) {
		this.prorateYearUnitPrice = prorateYearUnitPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getYearlySalesPrice() {
		return yearlySalesPrice;
	}

	public void setYearlySalesPrice(Double yearlySalesPrice) {
		this.yearlySalesPrice = yearlySalesPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getContractNumbers() {
		return contractNumbers;
	}

	public void setContractNumbers(String contractNumbers) {
		this.contractNumbers = contractNumbers;
	}
}