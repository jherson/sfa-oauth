package com.redhat.sforce.qb.manager;

import java.util.List;
import java.util.Locale;

import com.sforce.soap.partner.PartnerConnection;

public interface ApplicationManager {

	public PartnerConnection getPartnerConnection();

	public String getFrontDoorUrl();

	public String getSessionId();

	public Locale getLocale();

	public String getApiVersion();

	public String getApiEndpoint();

	public String getServiceEndpoint();

	public List<String> getCurrencyIsoCodes();

}