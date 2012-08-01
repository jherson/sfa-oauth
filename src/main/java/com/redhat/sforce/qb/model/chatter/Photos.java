package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Photos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@SerializedName("picture")
	private String picture;
	
	@SerializedName("thumbnail")
	private String thumbnail;
	

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
