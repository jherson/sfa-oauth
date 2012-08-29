package com.sfa.qb.model.sobject;

public class PricebookEntry extends QuoteBuilderObject {

	private static final long serialVersionUID = 1L;
	private String currencyIsoCode;
	private Double unitPrice;
	private Product product;

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public String toString() {
	    return "Id: " + getId() +
	    		" CurrencyIsoCode: " + getCurrencyIsoCode() +
	    		" UnitPrice: " + getUnitPrice() +
	    		" Product (" + product.toString() + ")";
	}
}