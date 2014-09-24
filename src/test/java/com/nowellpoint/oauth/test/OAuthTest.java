package com.nowellpoint.oauth.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.credential.UsernamePasswordCredentials;

import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.UserInfo;
import com.nowellpoint.oauth.provider.SalesforceLoginProvider;
import com.nowellpoint.oauth.session.OAuthSession;

public class OAuthTest {
	
	private static OAuthSession session;
	
	@BeforeClass
	public static void buildOAuthClient() {
		OAuthClient client = new OAuthClient.ClientBuilder()
				.clientId(System.getenv("CLIENT_ID"))
				.clientSecret(System.getenv("CLIENT_SECRET"))
				.serviceProvider(SalesforceLoginProvider.class)
				.build();
		
		session = client.createSession();
		
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
		credentials.setUsername(System.getenv("SALESFORCE_USERNAME"));
		credentials.setPassword(new Password(System.getenv("SALESFORCE_PASSWORD").concat(System.getenv("SALESFORCE_SECURITY_TOKEN"))));
		
		try {
			session.login(credentials);
		} catch (OAuthException e) {
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
	
	@Test
	public void testUserInfo() {
		System.out.println("testUserInfo");
		
		UserInfo userInfo = null;
		try {
			userInfo = session.unwrap(SalesforceLoginProvider.class).getUserInfo(session.getToken(), session.getIdentity());
		} catch (OAuthException e) {
			e.printStackTrace();
		}
		
		assertNotNull(userInfo);
		assertNotNull(userInfo.getProfile());
		
		System.out.println(userInfo.getName());
		System.out.println(userInfo.getEmail());
		System.out.println(userInfo.getProfile().getPermissionsCustomizeApplication());
	}
	
	@Test
	public void testOrganizationInfo() {
		System.out.println("testOrganizationInfo");
		
		OrganizationInfo organizationInfo = null;
		try {
			organizationInfo = session.unwrap(SalesforceLoginProvider.class).getOrganizationInfo(session.getToken(), session.getIdentity());
		} catch (OAuthException e) {
			e.printStackTrace();
		}
		
		assertNotNull(organizationInfo);
		
		System.out.println(organizationInfo.getName());
		System.out.println(organizationInfo.getAttributes().getUrl());
	}
	
	@AfterClass
	public static void cleanup() {
		try {
			session.logout();
		} catch (OAuthException e) {
			e.printStackTrace();
		}
		
		assertNull(session.getToken());
		assertNull(session.getIdentity());
	}
}