package com.redhat.sforce.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectWrapper {
	private static final Locale locale = new Locale("en", "in");
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'.000+0000'", locale);
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);	

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
	
	public Date getDateTime(String key) throws ParseException, JSONException {
		return jsonObject.isNull(key) ? null : dateTimeFormat.parse(jsonObject.getString(key));
	}
	
	public Date getDate(String key) throws ParseException, JSONException {
		return jsonObject.isNull(key) ? null : dateFormat.parse(jsonObject.getString(key));
		
	}
	
	public JSONArray getRecords(String object) throws JSONException {
		return jsonObject.isNull(object) ? null : jsonObject.getJSONObject(object).getJSONArray("records");
	}
	
	private Date applyLocalDatePattern(Date dateValue) throws ParseException {
		SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
		//formatter.applyLocalizedPattern("MM/dd/yyyy");
		System.out.println(formatter.parse(formatter.format(dateValue)));
		return (formatter.parse(formatter.format(dateValue)));
	}
	
	private Double applyLocalCurrencyPattern(Double doubleValue) throws ParseException {
		NumberFormat numberFormat = DecimalFormat.getNumberInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		return Double.valueOf(numberFormat.format(doubleValue));
	}
}