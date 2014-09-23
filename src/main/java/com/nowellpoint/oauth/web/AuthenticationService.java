package com.nowellpoint.oauth.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nowellpoint.oauth.model.SalesforceCredentials;

@Path("/authenticate")
public interface AuthenticationService {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(
			@QueryParam(value = "username") String username, 
			@QueryParam(value = "password") String password, 
			@QueryParam(value = "securityToken") String securityToken);
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(SalesforceCredentials credentials);
	
}