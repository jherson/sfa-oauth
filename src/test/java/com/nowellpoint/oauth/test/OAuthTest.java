package com.nowellpoint.oauth.test;

import javax.security.auth.login.LoginException;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import com.nowellpoint.oauth.OAuthConfig;
import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.util.OAuthUtil;


public class OAuthTest {

	@Test
	public void testLogin() {
		OAuthServiceProvider provider = new OAuthConfig().setClientId("3MVG99OxTyEMCQ3g4O4q6O_jbP1ai3d.qaC40M90IFKdb.d0q72XsNBwjxOr.kiHsRy_Cy6v_gglQnVRl6Xuo")
				.setClientSecret("313260469151264620")
				.setIsSandbox(Boolean.TRUE)
				.build();
		
		try {
			provider.authenticate(System.getenv("SALESFORCE_USERNAME"), 
					System.getenv("SALESFORCE_PASSWORD"), 
					System.getenv("SALESFORCE_SECURITY_TOKEN"));
			
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		assertNotNull(OAuthUtil.getToken(provider.getSubject()).getAccessToken());
		
	}
}