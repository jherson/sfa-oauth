package com.sfa.persistence.type;

import java.util.List;

public class TableType {

	private String tableName;
	private String className;
	private List<ColumnType> columnTypes;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}		
	
	public List<ColumnType> getColumns() {
		return columnTypes;
	}
	
	public void setColumns(List<ColumnType> columns) {
		this.columnTypes = columns;
	}
}