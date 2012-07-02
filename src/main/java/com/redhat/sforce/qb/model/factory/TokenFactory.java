package com.redhat.sforce.qb.model.factory;

import com.redhat.sforce.qb.model.quotebuilder.Token;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenFactory {

	public static Token deserialize(JSONObject jsonObject) throws JSONException {
		Token token = new Token();
		token.setAccessToken(jsonObject.getString("access_token"));
		token.setId(jsonObject.getString("id"));
		token.setInstanceUrl(jsonObject.getString("instance_url"));
		token.setIssuedAt(jsonObject.getString("issued_at"));
		token.setRefreshToken(jsonObject.getString("refresh_token"));
		token.setSignature(jsonObject.getString("signature"));

		return token;
	}
}
