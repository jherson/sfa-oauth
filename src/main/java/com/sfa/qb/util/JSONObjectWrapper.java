package com.sfa.qb.util;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectWrapper {

	private JSONObject jsonObject;

	public JSONObjectWrapper(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getId() throws JSONException {
		return jsonObject.getString("Id");
	}

	public String getString(String key) throws JSONException {
		return jsonObject.isNull(key) ? null : jsonObject.getString(key);
	}

	public Double getDouble(String key) throws JSONException {
		return jsonObject.isNull(key) ? null : jsonObject.getDouble(key);
	}

	public Boolean getBoolean(String key) throws JSONException {
		return jsonObject.isNull(key) ? null : jsonObject.getBoolean(key);
	}

	public Integer getInteger(String key) throws JSONException {
		return jsonObject.isNull(key) ? null : jsonObject.getInt(key);
	}

	public String getString(String object, String key) throws JSONException {
		return jsonObject.isNull(object) ? null : jsonObject.getJSONObject(object).getString(key);
	}

	public Double getDouble(String object, String key) throws JSONException {
		return jsonObject.isNull(object) ? null : jsonObject.getJSONObject(object).getDouble(key);
	}

	public Integer getInteger(String object, String key) throws JSONException {
		return jsonObject.isNull(object) ? null : jsonObject.getJSONObject(object).getInt(key);
	}

	public Date getDateTime(String key) throws ParseException, JSONException {
		return jsonObject.isNull(key) ? null : Util.parseDateTime(jsonObject.getString(key));
	}

	public Date getDate(String key) throws ParseException, JSONException {
		return jsonObject.isNull(key) ? null : Util.parseDate(jsonObject.getString(key));
	}

	public JSONObject getJSONObject(String key) throws JSONException {
		return jsonObject.isNull(key) ? null : jsonObject.getJSONObject(key);
	}

	public JSONArray getRecords(String object) throws JSONException {
		return jsonObject.isNull(object) ? null : jsonObject.getJSONObject(object).getJSONArray("records");
	}
}