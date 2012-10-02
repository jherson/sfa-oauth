package com.sfa.persistence.soql;

public class Select {
	
	private String selectClause;
	private String fromClause;
	private String whereClause;
	private String orderByClause;
    private String limitClause;
    
    public String toStatementString() {
    	StringBuilder buffer = new StringBuilder();
    	buffer.append("select ");
    	
    	if (selectClause != null && selectClause.trim().length() > 0) {
    		buffer.append(selectClause);	
    	}
    	
    	buffer.append(" from ");
    	buffer.append(fromClause);
    	
    	if (whereClause != null && whereClause.trim().length() > 0) {
    		buffer.append(" where ");
    		buffer.append(whereClause);
    	}
    	    	
    	if (orderByClause != null && orderByClause.trim().length() > 0) {
    		buffer.append(" order by ");
    		buffer.append(orderByClause);
    	}
    	
    	if (limitClause != null && limitClause.trim().length() > 0) {
    		buffer.append(" limit ");
    		buffer.append(limitClause);
    	}
    	
    	return buffer.toString();
    }
    
	public Select setSelectClause(String selectClause) {
		this.selectClause = selectClause;
		return this;
	}
	
	public Select setFromClause(String fromClause) {
		this.fromClause = fromClause;
		return this;
	}
	
	public Select setFromClause(String tableName, String alias) {
		this.fromClause = tableName + " " + alias;
		return this;
	}
	
	public Select setWhereClause(String whereClause) {
		this.whereClause = whereClause;
		return this;
	}
	
	public Select setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
		return this;
	}
	
	public Select setLimitClause(String limitClause) {
		this.limitClause = limitClause;
		return this;
	}       
}