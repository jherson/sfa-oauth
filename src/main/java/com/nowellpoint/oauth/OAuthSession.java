package com.nowellpoint.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;

public abstract class OAuthSession {

	public abstract void handleCallback(OAuthSessionContext context);
	
	public void init(HttpServletRequest request, HttpServletResponse response, Token token, Identity identity) {
		OAuthSessionContext context = new OAuthSessionContext();
		context.setRequest(request);
		context.setResponse(response);
		context.setIdentity(identity);
		context.setToken(token);
		handleCallback(context);
	}
}