package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Followers implements Serializable {

	private static final long serialVersionUID = -2812683320683683405L;
    private String currentPageUrl;
    private String nextPageUrl;
    private String previousPageUrl;
    private Integer total;
    private List<Subscriber> subscriber;
    private MySubscription mySubscription;
    
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
	
	public List<Subscriber> getSubscriber() {
		return subscriber;
	}
	
	public void setSubscriber(List<Subscriber> subscriber) {
		this.subscriber = subscriber;
	}
	
	public MySubscription getMySubscription() {
		return mySubscription;
	}
	
	public void setMySubscription(MySubscription mySubscription) {
		this.mySubscription = mySubscription;
	}
	
}