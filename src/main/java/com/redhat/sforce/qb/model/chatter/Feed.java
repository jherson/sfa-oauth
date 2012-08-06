package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Feed implements Serializable {

	private static final long serialVersionUID = -7531434907929549243L;
	
	private String currentPageUrl;
	private Boolean isModifiedUrl;
	private String nextPageUrl;
	private List<Item> items;
	
	public String getCurrentPageUrl() {
		return currentPageUrl;
	}
	
	public void setCurrentPageUrl(String currentPageUrl) {
		this.currentPageUrl = currentPageUrl;
	}
	
	public Boolean getIsModifiedUrl() {
		return isModifiedUrl;
	}
	
	public void setIsModifiedUrl(Boolean isModifiedUrl) {
		this.isModifiedUrl = isModifiedUrl;
	}
	
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}		
}