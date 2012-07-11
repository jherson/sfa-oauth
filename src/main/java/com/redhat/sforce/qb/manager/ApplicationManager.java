package com.redhat.sforce.qb.manager;

import java.util.List;

public interface ApplicationManager {

	public String getFrontDoorUrl();

	public String getApiVersion();

	public String getApiEndpoint();

	public List<String> getCurrencyIsoCodes();

}