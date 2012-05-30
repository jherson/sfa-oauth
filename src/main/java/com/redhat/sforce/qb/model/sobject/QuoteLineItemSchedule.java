package com.redhat.sforce.qb.model.sobject;

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
	private Date endDate;
	private Double prorateYearUnitPrice;
	private QuoteLineItem quoteLineItem;

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

	public QuoteLineItem getQuoteLineItem() {
		return quoteLineItem;
	}

	public void setQuoteLineItem(QuoteLineItem quoteLineItem) {
		this.quoteLineItem = quoteLineItem;
	}
}