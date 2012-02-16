package com.redhat.sforce.qb.bean.model;

public class PricebookEntry extends SObject {
	
	private static final long serialVersionUID = 1L;
    private String currencyIsoCode;
    private Product product;
    
	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}
	
	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}	
}