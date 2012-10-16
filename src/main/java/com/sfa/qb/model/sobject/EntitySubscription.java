package com.sfa.qb.model.sobject;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class EntitySubscription implements Serializable {

	private static final long serialVersionUID = 1571373513090120324L;
	
	@SerializedName("Id")
	private String id;
	
	@SerializedName("Subscriber")
	private User subscriber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(User subscriber) {
		this.subscriber = subscriber;
	}
}