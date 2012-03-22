package com.redhat.sforce.qb.model;

public class QuotePriceAdjustment extends SObject {

	private static final long serialVersionUID = 1L;
	private String quoteId;
    private Double adjustmentAmount;
    private String operator;
    private Double percent;
    private String reason;
    private String type;
    private String appliesTo;

    public QuotePriceAdjustment() {
        super();
    }
    
    public QuotePriceAdjustment(
    		Double adjustmentAmount, 
    		String operator, 
    		Double percent, 
    		String reason, 
    		String type) {
    	
    	super();
    	setAdjustmentAmount(adjustmentAmount);
	    setOperator(operator);
	    setPercent(percent);
	    setReason(reason);
        setType(type);
    }

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public Double getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(Double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
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
	
	public String getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}
}