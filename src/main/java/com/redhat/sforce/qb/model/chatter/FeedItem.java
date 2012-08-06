package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class FeedItem implements Serializable {

	private static final long serialVersionUID = 1277876767580411966L;
    private String id;
    private String url;
    
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
}