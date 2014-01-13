package com.nowellpoint.oauth.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.security.auth.login.LoginException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nowellpoint.oauth.OAuthClient;
import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.UserInfo;
import com.nowellpoint.oauth.provider.Salesforce;

public class OAuthTest {
	
	private static OAuthSession session;
	
	@BeforeClass
	public static void buildOAuthClient() {
		OAuthClient client = new OAuthClient.ClientBuilder()
			.clientId(System.getenv("CLIENT_ID"))
			.clientSecret(System.getenv("CLIENT_SECRET"))
			.serviceProvider(Salesforce.class)
			.build();
		
		session = new OAuthSession(client);
		
		Credentials credentials = new Credentials();
		credentials.setUsername(System.getenv("SALESFORCE_USERNAME"));
		credentials.setPassword(System.getenv("SALESFORCE_PASSWORD").concat(System.getenv("SALESFORCE_SECURITY_TOKEN")));
		
		try {
			session.login(credentials);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void oauthClientLoginTest() {
		System.out.println("oauthClientLoginTest");
		
		assertNotNull(session.getToken());
		assertNotNull(session.getIdentity());
		assertNotNull(session.getId());
		assertNotNull(session.getToken().getAccessToken());
		assertNotNull(session.getIdentity().getDisplayName());
		
		System.out.println("SessionId: " + session.getId());
		System.out.println("AccessToken: " + session.getToken().getAccessToken());
		System.out.println("DisplayName: " + session.getIdentity().getDisplayName());
		System.out.println(session.getIdentity().getUrls().getSObjects());
	}
	
	@AfterClass
	public static void cleanup() {
		try {
			session.logout();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		assertNull(session.getToken());
		assertNull(session.getIdentity());
	}
	
	@Test
	public void testUserInfo() {
		System.out.println("testUserInfo");
		UserInfo user = session.getUserInfo();
		assertNotNull(user);
		assertNotNull(user.getProfile());
		System.out.println(user.getName());
		System.out.println(user.getEmail());
		System.out.println(user.getProfile().getPermissionsCustomizeApplication());
	}
	
	@Test
	public void testOrganizationInfo() {
		System.out.println("testOrganizationInfo");
		OrganizationInfo organizationInfo = session.getOrganizationInfo();
		assertNotNull(organizationInfo);
		System.out.println(organizationInfo.getName());
		System.out.println(organizationInfo.getAttributes().getUrl());
	}
}