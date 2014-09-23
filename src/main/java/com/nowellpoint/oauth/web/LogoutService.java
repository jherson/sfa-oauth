package com.nowellpoint.oauth.web;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/logout")
public interface LogoutService {
	
	@POST
	public Response logout();

}