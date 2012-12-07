package com.sfa.login.oauth.callback;

public enum OAuthFlowType {

    WEB_SERVER_FLOW("webserver"),
    REFRESH_TOKEN_FLOW("refreshToken"),
    USER_AGENT_FLOW("useragent"),
    USERNAME_PASSWORD_FLOW("usernamePassword");

	private String flowType;

	private OAuthFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getFlowType() {
		return flowType;
	}
}