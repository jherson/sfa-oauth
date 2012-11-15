package com.sfa.login.oauth.principal;

import java.io.Serializable;
import java.security.Principal;

import com.sfa.login.oauth.model.Token;

public class TokenPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 3590685957273523697L;
	
	private Token token;
	
	public TokenPrincipal(Token token) {
		this.token = token;
	}

	@Override
	public String getName() {
		return token.getAccessToken();
	}
	
	public Token getToken() {
		return token;
	}
}