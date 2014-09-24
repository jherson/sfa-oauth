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

import com.nowellpoint.oauth.annotations.Salesforce;
import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.event.LoggedInEvent;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.UserInfo;
import com.nowellpoint.oauth.provider.SalesforceLoginProvider;
import com.nowellpoint.oauth.provider.SalesforceProvider;
import com.nowellpoint.oauth.session.OAuthSession;

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

	@Override
	public void authenticate() {
		
		if (UsernamePasswordCredentials.class.equals(loginCredentials.getCredential().getClass())) {
			System.out.println("UsernamePasswordCredentials");
		} else if (TokenCredential.class.equals(loginCredentials.getCredential().getClass())) {
			System.out.println("TokenCredential");
		}
		
		UsernamePasswordCredentials credentials = (UsernamePasswordCredentials) loginCredentials.getCredential();
		
		try {
			
			OAuthSession oauthSession = oauthClient.createSession();
			oauthSession.login(credentials);
			
			loggedInEvent.fire(new LoggedInEvent(oauthSession));
			
			SalesforceProvider provider = oauthSession.unwrap(SalesforceLoginProvider.class);
			
			
			UserInfo userInfo = provider.getUserInfo(oauthSession.getToken(), oauthSession.getIdentity());
			
			User user = new User();
			user.setId(userInfo.getId());
			user.setLoginName(userInfo.getUsername());
			user.setFirstName(userInfo.getFirstName());
			user.setLastName(userInfo.getLastName());
			user.setEmail(userInfo.getEmail());
			
			setStatus(AuthenticationStatus.SUCCESS);
			setAccount(user);
			
		} catch (OAuthException e) {
			setStatus(AuthenticationStatus.FAILURE);
			throw new AuthenticationException(e.getMessage());
		}
	}
	
	@Override
	public void postAuthenticate() {
		
	}
}