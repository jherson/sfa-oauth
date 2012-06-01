package com.redhat.sforce.qb.manager.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.redhat.sforce.qb.model.sobject.SObject;

public class ResultList<T> {
	
	private Map<String, T> records = new LinkedHashMap<String, T>();
	
	public Integer getSize() {
		return records.values().size();
	}
	
	public String[] getIds() {
		return records.keySet().toArray(new String[records.size()]);
	}

	public T getRecord(String id) {
		return records.get(id);
	}
	
	public Collection<T> getRecords() {
		return records.values();
	}
	
	public void add(T object) {
		SObject sobject = new SObject();
		sobject = sobject.getClass().cast(object);
		records.put(sobject.getId(), object);
	}	
	
	public void replace(T object) {
		add(object);
	}
	
	public void addAll(Collection<T> records) {
		for (T t : records) {
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