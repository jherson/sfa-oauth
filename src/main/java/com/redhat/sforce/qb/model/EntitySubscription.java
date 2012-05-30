package com.redhat.sforce.qb.model;

public class EntitySubscription extends SObject {

	private static final long serialVersionUID = 5918995528358573625L;
	private String parentId;
	private String subscriberId;
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getSubscriberId() {
		return subscriberId;
	}
	
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
}