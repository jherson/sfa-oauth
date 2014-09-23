package com.nowellpoint.oauth.event;

import com.nowellpoint.oauth.session.OAuthSession;

public class LoggedOutEvent {
	
	private OAuthSession oauthSession;
	
	public LoggedOutEvent(OAuthSession oauthSession) {
		this.oauthSession = oauthSession;
	}

	public OAuthSession getOAuthSession() {
		return oauthSession;
	}
}