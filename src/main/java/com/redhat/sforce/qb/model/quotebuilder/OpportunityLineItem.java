package com.redhat.sforce.qb.model.quotebuilder;

import java.util.Date;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class OpportunityLineItem extends QuoteBuilderObject {

	private static final long serialVersionUID = 1L;
	private String opportunityId;
	private String description;
	private Date actualStartDate;
	private Date actualEndDate;
	private Integer actualTerm;
	private String contractNumbers;
	private Double listPrice;
	private Double basePrice;
	private String newOrRenewal;
	private Integer quantity;
	private Double unitPrice;
	private Double totalPrice;
	private Double yearlySalesPrice;
	private Double year1Amount;
	private Double year2Amount;
	private Double year3Amount;
	private Double year4Amount;
	private Double year5Amount;
	private Double year6Amount;
	private String configuredSku;
	private String pricingAttributes;
	private String pricebookEntryId;
	private Product product;
	private boolean selected;

	public OpportunityLineItem() {
		super();
	}

	public String getOpportunityId() {
		return opportunityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public Integer getActualTerm() {
		return actualTerm;
	}

	public void setActualTerm(Integer actualTerm) {
		this.actualTerm = actualTerm;
	}

	public String getContractNumbers() {
		return contractNumbers;
	}

	public void setContractNumbers(String contractNumbers) {
		this.contractNumbers = contractNumbers;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
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

	public Double getYear1Amount() {
		return year1Amount;
	}

	public void setYear1Amount(Double year1Amount) {
		this.year1Amount = year1Amount;
	}

	public Double getYear2Amount() {
		return year2Amount;
	}

	public void setYear2Amount(Double year2Amount) {
		this.year2Amount = year2Amount;
	}

	public Double getYear3Amount() {
		return year3Amount;
	}

	public void setYear3Amount(Double year3Amount) {
		this.year3Amount = year3Amount;
	}

	public Double getYear4Amount() {
		return year4Amount;
	}

	public void setYear4Amount(Double year4Amount) {
		this.year4Amount = year4Amount;
	}

	public Double getYear5Amount() {
		return year5Amount;
	}

	public void setYear5Amount(Double year5Amount) {
		this.year5Amount = year5Amount;
	}

	public Double getYear6Amount() {
		return year6Amount;
	}

	public void setYear6Amount(Double year6Amount) {
		this.year6Amount = year6Amount;
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

	public String getPricebookEntryId() {
		return pricebookEntryId;
	}

	public void setPricebookEntryId(String pricebookEntryId) {
		this.pricebookEntryId = pricebookEntryId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isSelected() {
		// System.out.println("calling isSelected: " + selected);
		return selected;
	}

	public void setSelected(boolean selected) {
		// System.out.println("calling setSelected: " + selected);
		this.selected = selected;
	}
}
