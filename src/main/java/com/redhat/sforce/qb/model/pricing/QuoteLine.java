package com.redhat.sforce.qb.model.pricing;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class QuoteLine implements Serializable {

	private static final long serialVersionUID = -1368913363399728362L;
	
	@XmlElement(name="LineNumber")
	private String lineNumber;
	
	@XmlElement(name="Product")
	private Product product;
	
	@XmlElement(name="Quantity")
	private Quantity quantity;
	
	@XmlElement(name="ServiceDuration")
	private String serviceDuration;
	
	@XmlElement(name="ServicePeriod")
	private String servicePeriod;
	
	@XmlElement(name="Price")
	private List<Price> prices;
	
	@XmlElement(name="Status")
	private Status status;
	
	@XmlElement(name="PricingEffectiveDate")
	private String pricingEffectiveDate;
	
	public String getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getServiceDuration() {
		return serviceDuration;
	}
	
	public void setServiceDuration(String serviceDuration) {
		this.serviceDuration = serviceDuration;
	}
	
	public String getServicePeriod() {
		return servicePeriod;
	}
	
	public void setServicePeriod(String servicePeriod) {
		this.servicePeriod = servicePeriod;
	}
	
	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPricingEffectiveDate() {
		return pricingEffectiveDate;
	}
	
	public void setPricingEffectiveDate(String pricingEffectiveDate) {
		this.pricingEffectiveDate = pricingEffectiveDate;
	}
	
	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}