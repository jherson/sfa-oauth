package com.redhat.sforce.qb.manager;

import java.util.Locale;

import com.redhat.sforce.qb.model.CurrencyIsoCodes;
import com.sforce.ws.ConnectionException;

public interface QuoteBuilderManager {
		
	public String getSessionId();
	public Locale getLocale();
	public String getApiVersion();
	public String getApiEndpoint();
	public String getRedirectUri();
	public String getClientId();
	public String getClientSecret();
	public String getEnvironment();
	public CurrencyIsoCodes getCurrencyIsoCodes();
	public CurrencyIsoCodes queryCurrencyIsoCodes() throws ConnectionException;
}