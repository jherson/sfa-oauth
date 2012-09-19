package com.sfa.qb.exception;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class SalesforceServiceException extends Exception {
	private static final long serialVersionUID = -6616343924395661579L;	
	private String errorCode;
	private String message;
		
	public SalesforceServiceException(Exception exception) {
		super(exception);
	}
		
	public SalesforceServiceException(String error) {
	    try {
			JSONArray jsonArray = new JSONArray(new JSONTokener(error));
			errorCode = jsonArray.getJSONObject(0).getString("errorCode");
			message = jsonArray.getJSONObject(0).getString("message");
			new SalesforceServiceException(errorCode, message);
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessage() {
		return message;
	}

	public SalesforceServiceException(String errorCode, String message) {
		super(errorCode, new Throwable(message));
	}
}