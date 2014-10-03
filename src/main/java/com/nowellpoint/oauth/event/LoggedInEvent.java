package com.nowellpoint.oauth.event;

import com.nowellpoint.oauth.OAuthSession;

public class LoggedInEvent {

	private OAuthSession oauthSession;

	public LoggedInEvent(OAuthSession oauthSession) {
		this.oauthSession = oauthSession;
	}

	public OAuthSession getOAuthSession() {
		return oauthSession;
	}
}