package com.redhat.sforce.qb.model;

public class CreditCheck extends SObject {

	private static final long serialVersionUID = 1L;
	private Double arBalance;
	private Double arPastDueAmount;
	private String paymentTerms;
	private String comments;
	private Double creditLimit;
	private String creditStage;
	private String billingAccountNumberUsed;
	private String billingAccountNameUsed;

	public Double getArBalance() {
		return arBalance;
	}

	public void setArBalance(Double arBalance) {
		this.arBalance = arBalance;
	}

	public Double getArPastDueAmount() {
		return arPastDueAmount;
	}

	public void setArPastDueAmount(Double arPastDueAmount) {
		this.arPastDueAmount = arPastDueAmount;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCreditStage() {
		return creditStage;
	}

	public void setCreditStage(String creditStage) {
		this.creditStage = creditStage;
	}

	public String getBillingAccountNumberUsed() {
		return billingAccountNumberUsed;
	}

	public void setBillingAccountNumberUsed(String billingAccountNumberUsed) {
		this.billingAccountNumberUsed = billingAccountNumberUsed;
	}

	public String getBillingAccountNameUsed() {
		return billingAccountNameUsed;
	}

	public void setBillingAccountNameUsed(String billingAccountNameUsed) {
		this.billingAccountNameUsed = billingAccountNameUsed;
	}
}