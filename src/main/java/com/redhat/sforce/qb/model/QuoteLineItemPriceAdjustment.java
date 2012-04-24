package com.redhat.sforce.qb.model;

public class QuoteLineItemPriceAdjustment extends SObject {

	private static final long serialVersionUID = 1L;
	private String quoteLineItemId;
	private String quoteId;
	private Double amount;
	private Double percent;
	private String reason;
	private String type;
	private String operator;

	public QuoteLineItemPriceAdjustment() {
		super();
	}

	public QuoteLineItemPriceAdjustment(Double amount, Double percent, String reason, String type) {

		super();
		setAmount(amount);
		setPercent(percent);
		setReason(reason);
		setType(type);
	}

	public String getQuoteLineItemId() {
		return quoteLineItemId;
	}

	public void setQuoteLineItemId(String quoteLineItemId) {
		this.quoteLineItemId = quoteLineItemId;
	}
	
	public String getQuoteId() {
		return quoteId;
	}
	
	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}	
}