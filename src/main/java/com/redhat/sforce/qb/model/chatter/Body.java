package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class Body implements Serializable {

	private static final long serialVersionUID = -974016703042359684L;
    private MessageSegment messageSegments;
    private String text;
    
	public MessageSegment getMessageSegments() {
		return messageSegments;
	}
	
	public void setMessageSegments(MessageSegment messageSegments) {
		this.messageSegments = messageSegments;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}	
}