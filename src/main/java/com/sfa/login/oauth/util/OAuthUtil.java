package com.sfa.login.oauth.util;

import java.util.Iterator;

import javax.security.auth.Subject;

import com.sfa.login.oauth.model.Identity;
import com.sfa.login.oauth.model.Token;
import com.sfa.login.oauth.model.Organization;
import com.sfa.login.oauth.principal.IdentityPrincipal;
import com.sfa.login.oauth.principal.TokenPrincipal;
import com.sfa.login.oauth.principal.OrganizationPrincipal;

public class OAuthUtil {

	public static Identity getIdentity(Subject subject) {
	    Iterator<IdentityPrincipal> iterator = subject.getPrincipals(IdentityPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getIdentity();
		}
		return null;
	}
	
	public static Token getToken(Subject subject) {
	    Iterator<TokenPrincipal> iterator = subject.getPrincipals(TokenPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getToken();
	    }
		return null;
	}
	
	public static Organization getOrganization(Subject subject) {
	    Iterator<OrganizationPrincipal> iterator = subject.getPrincipals(OrganizationPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getOrganization();
	    }
		return null;
	}
}