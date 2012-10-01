package com.sfa.persistence.impl;

import java.util.Map;

public class PropertyMapping {
	
	private Map<String, String> propertyMapping;
	private Map<String, String> columnMapping;
	
	public PropertyMapping(Map<String, String> propertyMapping, Map<String, String> columnMapping) {
		this.propertyMapping = propertyMapping;
		this.columnMapping = columnMapping;
	}
	
	public String getColumn(String property) {
		return columnMapping.get(property);
	}
	
	public String getProperty(String column) {
		return propertyMapping.get(column);
	}
}