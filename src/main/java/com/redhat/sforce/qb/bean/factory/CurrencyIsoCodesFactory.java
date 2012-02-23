package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.CurrencyIsoCodes;

public class CurrencyIsoCodesFactory {

	public static CurrencyIsoCodes deserialize(JSONArray jsonArray) throws JSONException, ParseException {					
		CurrencyIsoCodes currencyIsoCodes = new CurrencyIsoCodes();
		for (int i = 0; i < jsonArray.length(); i++) {
			currencyIsoCodes.add(jsonArray.getJSONObject(i).getString("IsoCode"));			
		}
		return currencyIsoCodes;
	}
}