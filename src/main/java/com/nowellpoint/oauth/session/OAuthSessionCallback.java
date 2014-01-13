package com.nowellpoint.oauth.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class OAuthSessionCallback implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 678969510395689676L;

	public abstract void onVerify(OAuthSessionContext context);

	public void init(HttpServletRequest request, HttpServletResponse response, OAuthSession oauthSession) {
		OAuthSessionContext context = new OAuthSessionContext();
	    context.setRequest(request);
	    context.setResponse(response);
	    context.setOauthSession(oauthSession);
	    onVerify(context);
	}
}