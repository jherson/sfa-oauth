package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Comments implements Serializable {

	private static final long serialVersionUID = -9187928555740092624L;
	
	private String currentPageUrl;
    private String nextPageUrl;
    private Long total;
    private List<Comment> comments;
	
	public String getCurrentPageUrl() {
		return currentPageUrl;
	}
	
	public void setCurrentPageUrl(String currentPageUrl) {
		this.currentPageUrl = currentPageUrl;
	}
	
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}