package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class MessageSegment implements Serializable {

	private static final long serialVersionUID = 4612716006128438840L;
	private String text;
	private String type;
	private List<Segment> segments;
	
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
	
	public List<Segment> getSegments() {
		return segments;
	}
	
	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	} 		
}