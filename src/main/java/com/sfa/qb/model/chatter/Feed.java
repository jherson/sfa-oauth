package com.sfa.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Feed implements Serializable {

	private static final long serialVersionUID = -7531434907929549243L;
	
	private List<Item> items;
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}		
}