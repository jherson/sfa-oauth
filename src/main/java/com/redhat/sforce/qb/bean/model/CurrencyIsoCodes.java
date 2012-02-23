package com.redhat.sforce.qb.bean.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrencyIsoCodes implements Serializable {

	private static final long serialVersionUID = 1280874194120620529L;

	private List<String> currencyList = new ArrayList<String>();
	
	public List<String> getCurrencyList() {		
		return currencyList;
	}
	
	public void setCurrencyList(List<String> currencyList) {
		this.currencyList = currencyList;
	}
	
	public void add(String currencyIsoCode) {
		currencyList.add(currencyIsoCode);
	}
}