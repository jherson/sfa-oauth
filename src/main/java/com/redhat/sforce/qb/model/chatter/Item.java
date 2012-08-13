package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Item implements Serializable {

	private static final long serialVersionUID = 3929304800186045369L;	
	
	private User parent;
	private String id;
	private String type;
	private ClientInfo clientInfo;
	private String url;	
	private Body body;
	private Date createdDate;
	private Date modifiedDate;
	private String photoUrl;	
	private Comments comments;
	//private Likes likes;
	private Boolean isBookmarkedByCurrentUser;
	private Boolean isDeleteRestricted;
	private Boolean isLikedByCurrentUser;
	// private MyLike
	private User actor;		
	private Boolean event;
	//private Attachement;
	private String originalFeedItem;
	private String originalFeedItemActor;
	
	public User getParent() {
		return parent;
	}
	
	public void setParent(User parent) {
		this.parent = parent;
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
	
	public Comments getComments() {
		return comments;
	}
	
	public void setComments(Comments comments) {
		this.comments = comments;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreatedDate() {
		SimpleDateFormat format = (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.SHORT);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(createdDate);
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public User getActor() {
		return actor;
	}

	public void setActor(User actor) {
		this.actor = actor;
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
	
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

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