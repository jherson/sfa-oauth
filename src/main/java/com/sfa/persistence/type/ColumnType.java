package com.sfa.persistence.type;

public class ColumnType {
	
	private String fieldName;
	private Object fieldType;
	private String columnName;
	
	public ColumnType(String fieldName, Object fieldType, String columnName) {
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.columnName = columnName;
	}
		
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldType() {
		return fieldType;
	}

	public void setFieldType(Object fieldType) {
		this.fieldType = fieldType;
	}

	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}