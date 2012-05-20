package com.redhat.sforce.qb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RelatedList<T> {
	
	public Map<String, ? extends SObject> items = new LinkedHashMap<String, SObject>();
	
	public List<SObject> getItems() {
		return new ArrayList<SObject>(items.values());
	}

	public String[] getIds() {
		return items.keySet().toArray(new String[items.keySet().size()]);
	}
	
	public void add(SObject sobject) {
		items.put(sobject.getId(), sobject);
	}
	
	public void remove(SObject sobject) {
		items.remove(sobject.getId());
	}
	
	public void replace(SObject sobject) {
		items.put(sobject.getId(), sobject);
	}
	
	public void removeAll() {
		items.clear();
	}

	public void addAll(Collection<SObject> sobjects) {
		for (SObject sobject : sobjects) {
			add(sobject);
		}
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
}