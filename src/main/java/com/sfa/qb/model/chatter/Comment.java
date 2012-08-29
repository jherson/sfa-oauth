package com.sfa.qb.model.chatter;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 340898238288589557L;
	//private attachment
	private Long total;
	private Body body;
	private ClientInfo clientInfo;
	private Date createdDate;
	private FeedItem feedItem;
	private String id;
	private Boolean isDeleteRestricted;
	private Likes likes;
	private MyLike myLike;
	private Parent parent;
	private String type;
	private String url;
	private User user;
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
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
	
	public Likes getLikes() {
		return likes;
	}
	
	public void setLikes(Likes likes) {
		this.likes = likes;
	}
	
	public MyLike getMyLike() {
		return myLike;
	}
	
	public void setMyLike(MyLike myLike) {
		this.myLike = myLike;
	}
	
	public Parent getParent() {
		return parent;
	}
	
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}