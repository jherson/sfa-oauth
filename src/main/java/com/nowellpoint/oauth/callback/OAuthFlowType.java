package com.nowellpoint.oauth.callback;

public enum OAuthFlowType {

    WEB_SERVER_FLOW("webserver"),
    REFRESH_TOKEN_FLOW("refreshToken"),
    USERNAME_PASSWORD_FLOW("usernamePassword");

	private String flowType;

	private OAuthFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getFlowType() {
		return flowType;
	}
}