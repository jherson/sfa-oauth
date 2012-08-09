package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable  {

	private static final long serialVersionUID = 3929304800186045369L;
	private User actor;
	private Body body;
	private ClientInfo clientInfo;
	//private List<Comment> comments;
	private Date createdDate;
	private Boolean event;
	private String id;
	private Boolean isBookmarkedByCurrentUser;
	private Boolean isDeleteRestricted;
	private Boolean isLikedByCurrentUser;
	private Date modifiedDate;
	//private String myLike;
	private String originalFeedItem;
	private String originalFeedItemActor;
	
	public User getActor() {
		return actor;
	}
	
	public void setActor(User actor) {
		this.actor = actor;
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
	
//	public List<Comment> getComments() {
//		return comments;
//	}
//	
//	public void setComments(List<Comment> comments) {
//		this.comments = comments;
//	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {		
		this.createdDate = createdDate;
	}
	
	public Boolean getEvent() {
		return event;
	}
	
	public void setEvent(Boolean event) {
		this.event = event;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Boolean getIsBookmarkedByCurrentUser() {
		return isBookmarkedByCurrentUser;
	}
	
	public void setIsBookmarkedByCurrentUser(Boolean isBookmarkedByCurrentUser) {
		this.isBookmarkedByCurrentUser = isBookmarkedByCurrentUser;
	}
	
	public Boolean getIsDeleteRestricted() {
		return isDeleteRestricted;
	}
	
	public void setIsDeleteRestricted(Boolean isDeleteRestricted) {
		this.isDeleteRestricted = isDeleteRestricted;
	}
	
	public Boolean getIsLikedByCurrentUser() {
		return isLikedByCurrentUser;
	}
	
	public void setIsLikedByCurrentUser(Boolean isLikedByCurrentUser) {
		this.isLikedByCurrentUser = isLikedByCurrentUser;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
//	public String getMyLike() {
//		return myLike;
//	}
	
//	public void setMyLike(String myLike) {
//		this.myLike = myLike;
//	}
	
	public String getOriginalFeedItem() {
		return originalFeedItem;
	}
	
	public void setOriginalFeedItem(String originalFeedItem) {
		this.originalFeedItem = originalFeedItem;
	}
	
	public String getOriginalFeedItemActor() {
		return originalFeedItemActor;
	}
	
	public void setOriginalFeedItemActor(String originalFeedItemActor) {
		this.originalFeedItemActor = originalFeedItemActor;
	}
}