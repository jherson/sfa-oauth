package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class Follower implements Serializable {

	private static final long serialVersionUID = -3360232894076164318L;
	private String id;
	private String url;
	private Subject subject;
	private Subscriber subscriber;
		
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
	
	public Subject getSubject() {
		return subject;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}
	
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
}