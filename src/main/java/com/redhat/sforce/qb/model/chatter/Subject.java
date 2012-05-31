package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class Subject implements Serializable {

	private static final long serialVersionUID = 1052474892447863768L;
	private String id;
	private MySubscription mySubscription;
	private String name;
	private String type;
	private String url;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public MySubscription getMySubscription() {
		return mySubscription;
	}
	
	public void setMySubscription(MySubscription mySubscription) {
		this.mySubscription = mySubscription;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
}