package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class MessageSegment implements Serializable {

	private static final long serialVersionUID = 4612716006128438840L;
	private String text;
	private String type;
	private Segment segments;
	
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
	
	public Segment getSegments() {
		return segments;
	}
	
	public void setSegments(Segment segments) {
		this.segments = segments;
	} 		
}