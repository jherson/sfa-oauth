package com.sfa.qb.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateAdapter implements JsonDeserializer<Date> {
	
	private static final String ISO_8061_FORMAT = "yyyy-MM-dd'T'kk:mm:ss.SSS'Z'";

	@Override
	public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {		

		try {
			SimpleDateFormat format = new SimpleDateFormat(ISO_8061_FORMAT);
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return format.parse(element.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}