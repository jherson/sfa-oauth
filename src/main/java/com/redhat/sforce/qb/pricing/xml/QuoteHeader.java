package com.redhat.sforce.qb.pricing.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class QuoteHeader implements Serializable {

	private static final long serialVersionUID = -531586874421363870L;
	
	@XmlElement(name="QuoteNumber")
	private String quoteNumber;
	
	@XmlElement(name="QuoteSource")
	private String quoteSource;
	
	@XmlElement(name="SuperRegion")
	private String superRegion;
	
	@XmlElement(name="CountryOfOrder")
	private String countryOfOrder;
	
	@XmlElement(name="CurrencyIso3Code")
	private String currencyIso3Code;
	
	@XmlElement(name="SalesRepEmail")
	private SalesrepEmail salesrepEmail;

	@XmlElement(name="OpportunityType")
	private String opportunityType;
	
	@XmlElement(name="OpportunityNumber")
	private String opportunityNumber;
	
	public String getQuoteNumber() {
		return quoteNumber;
	}
	
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	
	public String getQuoteSource() {
		return quoteSource;
	}
	
	public void setQuoteSource(String quoteSource) {
		this.quoteSource = quoteSource;
	}
	
	public String getSuperRegion() {
		return superRegion;
	}
	
	public void setSuperRegion(String superRegion) {
		this.superRegion = superRegion;
	}
	
	public String getCountryOfOrder() {
		return countryOfOrder;
	}
	
	public void setCountryOfOrder(String countryOfOrder) {
		this.countryOfOrder = countryOfOrder;
	}
	
	public String getCurrencyIso3Code() {
		return currencyIso3Code;
	}
	
	public void setCurrencyIso3Code(String currencyIso3Code) {
		this.currencyIso3Code = currencyIso3Code;
	}
	
	
	public SalesrepEmail getSalesrepEmail() {
		return salesrepEmail;
	}

	public void setSalesrepEmail(SalesrepEmail salesrepEmail) {
		this.salesrepEmail = salesrepEmail;
	}
	
	public String getOpportunityType() {
		return opportunityType;
	}
	
	public void setOpportunityType(String opportunityType) {
		this.opportunityType = opportunityType;
	}
	
	public String getOpportunityNumber() {
		return opportunityNumber;
	}
	
	public void setOpportunityNumber(String opportunityNumber) {
		this.opportunityNumber = opportunityNumber;
	}	
}