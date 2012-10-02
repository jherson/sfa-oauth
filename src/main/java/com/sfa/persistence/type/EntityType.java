package com.sfa.persistence.type;

import java.util.List;

import com.sfa.persistence.impl.PropertyMapping;

public class EntityType {
	
	private String className;
	private String entityName;
	private String queryString;
	private IdType idType;
	private List<ColumnType> columnTypes;
	private List<OneToOneType> oneToOneTypes;
	private List<OneToManyType> oneToManyTypes;
	
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
	
	public IdType getIdType() {
		return idType;
	}
	
	public void setIdType(IdType idType) {
		this.idType = idType;
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

	public List<ColumnType> getColumnTypes() {
		return columnTypes;
	}

	public void setColumnTypes(List<ColumnType> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public List<OneToOneType> getOneToOneTypes() {
		return oneToOneTypes;
	}

	public void setOneToOneTypes(List<OneToOneType> oneToOneTypes) {
		this.oneToOneTypes = oneToOneTypes;
	}

	public List<OneToManyType> getOneToManyTypes() {
		return oneToManyTypes;
	}

	public void setOneToManyTypes(List<OneToManyType> oneToManyTypes) {
		this.oneToManyTypes = oneToManyTypes;
	}
}