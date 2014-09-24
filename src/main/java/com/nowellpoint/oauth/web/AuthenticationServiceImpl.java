package com.nowellpoint.oauth.web;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.credential.UsernamePasswordCredentials;
import org.picketlink.idm.model.Account;

import com.nowellpoint.oauth.model.SalesforceCredentials;

public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Inject
    private Identity identity;

    @Inject
    private DefaultLoginCredentials loginCredentials;

	@Override
	public Response authenticate(String username, String password, String securityToken) {
		
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
		credentials.setPassword(new Password(password + (securityToken != null ? securityToken : "")));
		credentials.setUsername(username);
		
        loginCredentials.setCredential(credentials);
		
		if (! identity.isLoggedIn()) {	
			identity.login();
		}
		
		Account account = identity.getAccount();

		return Response.status(Status.OK)
				.entity(account)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
	
	@Override
	public Response authenticate(SalesforceCredentials credentials) {
		return authenticate(credentials.getUsername(), credentials.getPassword(), credentials.getSecurityToken());
	}
}