package com.nowellpoint.oauth.web;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.picketlink.Identity;

public class LogoutServiceImpl implements LogoutService {
	
	@Inject
	private Identity identity;

	@Override
	public Response logout() {
		
		
		if (this.identity.isLoggedIn()) {
	        this.identity.logout();
	    }
		
		return Response.ok().build();
	}
}