package com.redhat.sforce.qb.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import com.redhat.sforce.qb.model.sobject.SObject;

public class ResultList<X> {
	
	private Map<String, X> records = new LinkedHashMap<String, X>();
	
	public Integer getSize() {
		return records.values().size();
	}
	
	public String[] getIds() {
		return records.keySet().toArray(new String[records.size()]);
	}

	public X getRecord(String id) {
		return records.get(id);
	}
	
	public List<X> getRecords() {
		return new ArrayList<X>(records.values());
	}
	
	public void add(X object) {
		SObject sobject = new SObject();
		sobject = sobject.getClass().cast(object);
		records.put(sobject.getId(), object);
	}	
	
	public void replace(X object) {
		add(object);
	}
	
	public void addAll(List<X> records) {
		for (X t : records) {
			add(t);
		}
	}
	
	public void remove(String id) {
		records.remove(id);
	}
	
	public boolean isEmpty() {
		return records.isEmpty();
	}
	
	public void removeAll() {
		records.clear();
	}
}