package com.redhat.sforce.qb.manager.impl;

import com.redhat.sforce.qb.exception.QueryException;

public interface Query {
	
	public <E> ResultList<E> getResultList() throws QueryException;

}