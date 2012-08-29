package com.sfa.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Likes implements Serializable {

	private static final long serialVersionUID = 1387521524107898555L;
	
	private String currentPageUrl;
    private List<Like> likes;
    private String nextPageUrl;
    private String previousPageUrl;
    private Long total;
    
	public String getCurrentPageUrl() {
		return currentPageUrl;
	}
	
	public void setCurrentPageUrl(String currentPageUrl) {
		this.currentPageUrl = currentPageUrl;
	}
	
	public List<Like> getLike() {
		return likes;
	}

	public void setLike(List<Like> likes) {
		this.likes = likes;
	}

	public String getNextPageUrl() {
		return nextPageUrl;
	}
	
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	
	public String getPreviousPageUrl() {
		return previousPageUrl;
	}
	
	public void setPreviousPageUrl(String previousPageUrl) {
		this.previousPageUrl = previousPageUrl;
	}
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}        
}