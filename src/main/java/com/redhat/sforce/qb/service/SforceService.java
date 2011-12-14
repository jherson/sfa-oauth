package com.redhat.sforce.qb.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SforceServiceException;

public interface SforceService {
	
	public String create(String accessToken, String sobject, JSONObject jsonObject) throws SforceServiceException;
	public JSONArray read(String accessToken, String query);
	public void update(String accessToken, String sobject, String id, JSONObject jsonObject) throws SforceServiceException;
	public void delete(String accessToken, String sobject, String id);

}