package com.nowellpoint.oauth.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class Photos implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("picture")
	private String picture;

	@JsonProperty("thumbnail")
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