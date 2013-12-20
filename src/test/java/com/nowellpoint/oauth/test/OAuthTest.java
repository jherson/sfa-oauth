package com.nowellpoint.oauth.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.security.auth.login.LoginException;

import org.junit.Test;

import com.nowellpoint.oauth.OAuthConfig;
import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.util.OAuthUtil;

public class OAuthTest {

	@Test
	public void testLogin() {
		
		OAuthServiceProvider provider = new OAuthConfig().setClientId("3MVG99OxTyEMCQ3g4O4q6O_jbP1ai3d.qaC40M90IFKdb.d0q72XsNBwjxOr.kiHsRy_Cy6v_gglQnVRl6Xuo")
				.setClientSecret("313260469151264620")
				.setIsSandbox(Boolean.FALSE)
				.build();
		
		try {
			provider.authenticate(System.getenv("SALESFORCE_USERNAME"), System.getenv("SALESFORCE_PASSWORD"), System.getenv("SALESFORCE_SECURITY_TOKEN"));
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		Token token = OAuthUtil.getToken(provider.getSubject());
		
		assertNotNull(token);
		
		Identity identity = OAuthUtil.getIdentity(provider.getSubject());
		
		assertNotNull(identity);
		
		System.out.println(identity.getDisplayName());
		
		try {
			String userInfo = provider.getUserInfo();
			System.out.println(userInfo);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		OrganizationInfo organizationInfo = null;
		
		try {
			organizationInfo = provider.getOrganizationInfo();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		System.out.println(organizationInfo.getName());
		
		assertNotNull(organizationInfo);
		
		try {
			provider.logout();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		assertNull(OAuthUtil.getToken(provider.getSubject()));
	}
}