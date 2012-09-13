package com.sfa.qb.model.sobject;

public class CurrencyType extends QuoteBuilderObject {

	private static final long serialVersionUID = -2541242374886726243L;
	private String isoCode;
	
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	
	public String getIsoCode() {
		return isoCode;
	}
}