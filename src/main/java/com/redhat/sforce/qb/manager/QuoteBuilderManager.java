package com.redhat.sforce.qb.manager;

import java.util.Locale;

import com.redhat.sforce.qb.model.CurrencyIsoCodes;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

public interface QuoteBuilderManager {
		
	public PartnerConnection getPartnerConnection();
	public String getOpportunityDetailUrl();
	public String getSessionId();
	public Locale getLocale();
	public String getApiVersion();
	public String getApiEndpoint();
    public String getServiceEndpoint();
	public CurrencyIsoCodes getCurrencyIsoCodes();
	public CurrencyIsoCodes queryCurrencyIsoCodes() throws ConnectionException;
}