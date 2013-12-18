package com.nowellpoint.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.Token;

public abstract class AuthenticationCallback {

	public abstract void handle(AuthenticationContext context);
	
	public void init(HttpServletRequest request, HttpServletResponse response, Token token, Identity identity) {
		AuthenticationContext context = new AuthenticationContext();
		context.setRequest(request);
		context.setResponse(response);
		context.setIdentity(identity);
		context.setToken(token);
		handle(context);
	}
}