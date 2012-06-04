package com.redhat.sforce.qb.manager.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.redhat.sforce.qb.model.sobject.SObject;

public class ResultList<E> {
	
	private Map<String, E> records = new LinkedHashMap<String, E>();
	
	public Integer getSize() {
		return records.values().size();
	}
	
	public String[] getIds() {
		return records.keySet().toArray(new String[records.size()]);
	}

	public E getRecord(String id) {
		return records.get(id);
	}
	
	public Collection<E> getRecords() {
		return records.values();
	}
	
	public void add(E object) {
		SObject sobject = new SObject();
		sobject = sobject.getClass().cast(object);
		records.put(sobject.getId(), object);
	}	
	
	public void replace(E object) {
		add(object);
	}
	
	public void addAll(Collection<E> records) {
		for (E e : records) {
			add(e);
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