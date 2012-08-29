package com.sfa.qb.model.chatter;

import java.io.Serializable;

public class Photo implements Serializable  {

	private static final long serialVersionUID = -5691282649653517997L;
	private String largePhotoUrl;
	private String photoVersionId;
	private String smallPhotoUrl;
	
	public String getLargePhotoUrl() {
		return largePhotoUrl;
	}
	
	public void setLargePhotoUrl(String largePhotoUrl) {
		this.largePhotoUrl = largePhotoUrl;
	}
	
	public String getPhotoVersionId() {
		return photoVersionId;
	}
	
	public void setPhotoVersionId(String photoVersionId) {
		this.photoVersionId = photoVersionId;
	}
	
	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}
	
	public void setSmallPhotoUrl(String smallPhotoUrl) {
		this.smallPhotoUrl = smallPhotoUrl;
	}
}