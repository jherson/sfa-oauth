package com.sfa.qb.service;

import javax.ejb.Local;

import com.sfa.qb.exception.ServiceException;

@Local

public interface CommonService {

	public String getCurrentUserInfo(String sessionId) throws ServiceException;	
	public String query(String sessionId, String query) throws ServiceException;
	public void delete(String sessionId, String sobjectType, String id) throws ServiceException;	
}