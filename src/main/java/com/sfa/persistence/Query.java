package com.sfa.persistence;

import java.util.List;

import com.sfa.qb.exception.QueryException;

public interface Query {
	
	public String getType();	
	public Integer getTotalSize();	
	public Query showQuery();
	public Query setParameter(String param, String value);
	public Query setLimit(Integer limit);
	public Integer getLimit();
	public Query orderBy(String orderBy);
	public String getQueryString();
    public <X> List<X> getResultList() throws QueryException;
    public <X> X getSingleResult() throws QueryException;
}