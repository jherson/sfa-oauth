package com.nowellpoint.oauth.web;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.TokenCredential;
import org.picketlink.idm.credential.UsernamePasswordCredentials;
import org.picketlink.idm.model.basic.User;

import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.annotations.Salesforce;
import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.event.LoggedInEvent;
import com.nowellpoint.oauth.exception.OAuthException;

@RequestScoped
@PicketLink
public class ApplicationAuthenticator extends BaseAuthenticator {
	
	@Inject
	@Salesforce
	private OAuthClient oauthClient;
	
	@Inject
    private DefaultLoginCredentials loginCredentials;
	
	@Inject
	private Event<LoggedInEvent> loggedInEvent;
	
	private OAuthSession oauthSession;

	@Override
	public void authenticate() {
			
		oauthSession = oauthClient.createSession();
			
		try {	
			
			if (UsernamePasswordCredentials.class.equals(loginCredentials.getCredential().getClass())) {
				UsernamePasswordCredentials credentials = (UsernamePasswordCredentials) loginCredentials.getCredential();				
				oauthSession.login(credentials);
			} else if (TokenCredential.class.equals(loginCredentials.getCredential().getClass())) {
				oauthSession.refreshToken();
			}
			
			User user = new User();
			user.setId(oauthSession.getIdentity().getId());
			user.setEmail(oauthSession.getIdentity().getEmail());
			user.setLoginName(oauthSession.getIdentity().getUsername());
			
			setAccount(user);
			setStatus(AuthenticationStatus.SUCCESS);
			
		} catch (OAuthException e) {
			setStatus(AuthenticationStatus.FAILURE);
			throw new AuthenticationException(e.getMessage());
		}
	}
	
	@Override
	public void postAuthenticate() {
		loggedInEvent.fire(new LoggedInEvent(oauthSession));
	}
}