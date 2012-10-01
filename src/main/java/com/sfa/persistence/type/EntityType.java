package com.sfa.persistence.type;

import com.sfa.persistence.impl.PropertyMapping;

public class EntityType {
	
	private String className;
	private String entityName;
	private String id;
	private String queryString;
	private PropertyMapping propertyMapping;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getQueryString() {
		return queryString;
	}
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public PropertyMapping getPropertyMapping() {
		return propertyMapping;
	}

	public void setPropertyMapping(PropertyMapping propertyMapping) {
		this.propertyMapping = propertyMapping;
	}	
}