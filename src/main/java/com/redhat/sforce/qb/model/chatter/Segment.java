package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class Segment implements Serializable   {

	private static final long serialVersionUID = 1887471665591434154L;
	private String text;
	private String type;
	private String url;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
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