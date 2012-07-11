package com.redhat.sforce.persistence;

import java.util.List;

import com.redhat.sforce.qb.exception.QueryException;

public interface Query {
	
	public String getType();	
	public Integer getTotalSize();	
	public void addParameter(String param, String value);
	public void setLimit(Integer limit);
    public <X> List<X> getResultList() throws QueryException;
    public <X> X getSingleResult() throws QueryException;
}