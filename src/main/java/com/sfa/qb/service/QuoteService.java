package com.sfa.qb.service;

import javax.ejb.Local;

import com.sfa.qb.exception.ServiceException;

@Local

public interface QuoteService {
	
	public String calculateQuote(String sessionId, String quoteId) throws ServiceException;			
	public String activateQuote(String sessionId, String quoteId) throws ServiceException;	
	public String copyQuote(String sessionId, String quoteId) throws ServiceException;
	public String priceQuote(String sessionId, String xml) throws ServiceException;
	public String createQuote(String sessionId, String jsonString) throws ServiceException;
}