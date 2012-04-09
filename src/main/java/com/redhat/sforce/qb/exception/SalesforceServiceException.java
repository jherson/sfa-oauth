package com.redhat.sforce.qb.exception;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SalesforceServiceException extends Exception {
	private static final long serialVersionUID = -6616343924395661579L;
	private Logger log = Logger.getLogger(SalesforceServiceException.class);

	public SalesforceServiceException(Exception exception) {
		super(exception);
	}

	public SalesforceServiceException(String errorCode, String message) {
		super(errorCode, new Throwable(message));
	}

	public SalesforceServiceException(InputStream is) {
		try {
			JSONObject object = new JSONObject(new JSONTokener(
					new InputStreamReader(is)));
			new SalesforceServiceException(object.getString("errorCode"),
					object.getString("message"));
		} catch (JSONException e) {
			log.error(e);
		}
	}
}