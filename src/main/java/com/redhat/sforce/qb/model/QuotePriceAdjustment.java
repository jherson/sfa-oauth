package com.redhat.sforce.qb.model;

public class QuotePriceAdjustment extends SObject {

	private static final long serialVersionUID = 1L;
	private String quoteId;
    private Double amount;
    private String operator;
    private Double percent;
    private String reason;
    private String type;
    private Double amountBeforeAdjustment;
    private Double amountAfterAdjustment;

    public QuotePriceAdjustment() {
        super();
    }
    
    public QuotePriceAdjustment(
    		Double amount, 
    		String operator, 
    		Double percent, 
    		String reason, 
    		String type, 
    		Double amountBeforeAdjustment, 
    		Double amountAfterAdjustment) {
    	
    	super();
	    setAmount(amount);
	    setOperator(operator);
	    setPercent(percent);
	    setReason(reason);
        setType(type);
		setAmountBeforeAdjustment(amountBeforeAdjustment);
		setAmountAfterAdjustment(amountAfterAdjustment);
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public Double getAmountBeforeAdjustment() {
		return amountBeforeAdjustment;
	}

	public void setAmountBeforeAdjustment(Double amountBeforeAdjustment) {
		this.amountBeforeAdjustment = amountBeforeAdjustment;
	}

	public Double getAmountAfterAdjustment() {
		return amountAfterAdjustment;
	}

	public void setAmountAfterAdjustment(Double amountAfterAdjustment) {
		this.amountAfterAdjustment = amountAfterAdjustment;
	}
}