package com.redhat.sforce.qb.service;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	
	private List<Object> objectList;

	public void begin() {
		objectList = new ArrayList<Object>();
	}
	
	public void commit() {
		
	}
	
	public void persist(Object object) {
		objectList.add(object);		
	}
	
	public void persist(List<Object> objectList) {
		objectList.add(objectList);
	}
}
