package com.nowellpoint.oauth;

public abstract interface AuthenticationCallback {

	void handle(AuthenticationContext context);
}