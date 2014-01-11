package com.nowellpoint.oauth.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.security.auth.login.LoginException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nowellpoint.oauth.OAuthClient;
import com.nowellpoint.oauth.OAuthConfig;
import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.UserInfo;
import com.nowellpoint.oauth.provider.Salesforce;

public class OAuthTest {
	
	private static OAuthServiceProvider provider;
	private static OAuthSession session;
	
	//@BeforeClass
	public static void init() {
		
		provider = new OAuthConfig().setClientId(System.getenv("CLIENT_ID"))
					.setClientSecret(System.getenv("CLIENT_SECRET"))
					.setUseSandbox(Boolean.FALSE)
					.build();
		
		session = new OAuthSession(provider);
		
		System.out.println("SessionId: " + session.getId());
		
		Credentials credentials = new Credentials();
		credentials.setUsername(System.getenv("SALESFORCE_USERNAME"));
		credentials.setPassword(System.getenv("SALESFORCE_PASSWORD").concat(System.getenv("SALESFORCE_SECURITY_TOKEN")));
		
		try {
			session.login(credentials);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		assertNotNull(session.getSubject());
	}
	
	@Test
	public void oauthClientTest() {
		OAuthClient oauthClient = new OAuthClient.ClientBuilder().clientId(System.getenv("CLIENT_ID"))
				.clientSecret(System.getenv("CLIENT_SECRET"))
				.serviceProvider(Salesforce.class)
				.addParameter("sandbox", Boolean.FALSE)
				.build();
		
		session = new OAuthSession(oauthClient);
		
        System.out.println("SessionId: " + session.getId());
		
		Credentials credentials = new Credentials();
		credentials.setUsername(System.getenv("SALESFORCE_USERNAME"));
		credentials.setPassword(System.getenv("SALESFORCE_PASSWORD").concat(System.getenv("SALESFORCE_SECURITY_TOKEN")));
		
		try {
			session.login(credentials);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
	
	//@AfterClass
	public static void cleanup() {
		try {
			session.logout();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		assertNull(session.getToken());
		assertNull(session.getIdentity());
	}

	//@Test
	public void testSession() {		
		Token token = session.getToken();
		Identity identity = session.getIdentity();
		
		assertNotNull(token);
		assertNotNull(identity);
		
		System.out.println(token.getId());
		System.out.println(token.getAccessToken());
		System.out.println(identity.getDisplayName());
		System.out.println(identity.getUrls().getSObjects());
	}
	
	//@Test
	public void testUserInfo() {
		System.out.println("testUserInfo");
		UserInfo user = session.getUserInfo();
		assertNotNull(user);
		assertNotNull(user.getProfile());
		System.out.println(user.getName());
		System.out.println(user.getEmail());
		System.out.println(user.getProfile().getPermissionsCustomizeApplication());
	}
	
	//@Test
	public void testOrganizationInfo() {
		System.out.println("testOrganizationInfo");
		OrganizationInfo organizationInfo = session.getOrganizationInfo();
		assertNotNull(organizationInfo);
		System.out.println(organizationInfo.getName());
		System.out.println(organizationInfo.getAttributes().getUrl());
	}
}