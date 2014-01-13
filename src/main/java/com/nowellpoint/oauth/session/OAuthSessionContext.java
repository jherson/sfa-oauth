package com.nowellpoint.oauth.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuthSessionContext implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8030968908682514184L;
	
	/**
	 * 
	 */
	
	private HttpServletResponse response;
	
	/**
	 * 
	 */
	
	private HttpServletRequest request;
	
	/**
	 * 
	 */
	
	private OAuthSession oauthSession;
	
	public OAuthSessionContext() {
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public OAuthSession getOauthSession() {
		return oauthSession;
	}

	public void setOauthSession(OAuthSession oauthSession) {
		this.oauthSession = oauthSession;
	}
}