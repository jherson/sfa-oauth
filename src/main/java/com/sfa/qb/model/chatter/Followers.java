package com.sfa.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Followers implements Serializable {

	private static final long serialVersionUID = -2812683320683683405L;
    private String currentPageUrl;
    private String nextPageUrl;
    private String previousPageUrl;
    private Integer total;
    private List<Follower> followers;
    private Boolean isCurrentUserFollowing;
    
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
	
	public String getPreviousPageUrl() {
		return previousPageUrl;
	}
	
	public void setPreviousPageUrl(String previousPageUrl) {
		this.previousPageUrl = previousPageUrl;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public List<Follower> getFollowers() {
		return followers;
	}
	
	public void setSubscriber(List<Follower> followers) {
		this.followers = followers;
	}
	
	public Boolean getIsCurrentUserFollowing() {
		return isCurrentUserFollowing;
	}
	
	public void setIsCurrentUserFollowing(Boolean isCurrentUserFollowing) {
		this.isCurrentUserFollowing = isCurrentUserFollowing;
	}
}