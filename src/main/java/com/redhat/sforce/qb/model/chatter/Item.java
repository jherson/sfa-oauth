package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Item implements Serializable  {

	private static final long serialVersionUID = 3929304800186045369L;
	private User actor;
	private Body body;
	private ClientInfo clientInfo;
	private List<Comment> comments;
	private Date createdDate;
	private Boolean event;
	private String id;
	private Boolean isBookmarkedByCurrentUser;
	private Boolean isDeleteRestricted;
	private Boolean isLikedByCurrentUser;
	private Date modifiedDate;
	private String myLike;
	private String originalFeedItem;
	private String originalFeedItemActor;
	
}