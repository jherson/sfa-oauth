package com.redhat.sforce.qb.manager.impl;

import com.redhat.sforce.qb.exception.QueryException;

public interface Query {
	
	public String getType();	
	public Integer getTotalSize();	
	public void addParameter(String param, String value);
	public void setLimit(Integer limit);
	public <E> ResultList<E> getResultList() throws QueryException;

}