package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 340898238288589557L;
    private Body body;
    private ClientInfo clientInfo;
    private Date createdDate;
    private FeedItem feedItem;
    private String id;
    private Boolean isDeleteRestricted;
    private Like likes;
	private String nextPageUrl;
	private String previousPageUrl;
	private Long total;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsDeleteRestricted() {
		return isDeleteRestricted;
	}

	public void setIsDeleteRestricted(Boolean isDeleteRestricted) {
		this.isDeleteRestricted = isDeleteRestricted;
	}

	public Like getLikes() {
		return likes;
	}

	public void setLikes(Like likes) {
		this.likes = likes;
	}

	public String getPreviousPageUrl() {
		return previousPageUrl;
	}

	public void setPreviousPageUrl(String previousPageUrl) {
		this.previousPageUrl = previousPageUrl;
	}
	
	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
	
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
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
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public FeedItem getFeedItem() {
		return feedItem;
	}

	public void setFeedItem(FeedItem feedItem) {
		this.feedItem = feedItem;
	}
}