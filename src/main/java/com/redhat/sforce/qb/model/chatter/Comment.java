package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 340898238288589557L;
	//private Attachment attachment;
    private Body body;
    private ClientInfo clientInfo;
    private Date createdDate;
    private Boolean deleteable;
    private FeedItem feedItem;
    private String id;
    //private Likes likes;
    // my like
    // parent
    private String type;
    private String url;
    private User user;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}