package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class Like implements Serializable {

	private static final long serialVersionUID = -4787927575076331168L;
	
    private String id;
    private String url;
    private User user;
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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