package com.sfa.login.oauth.principal;

import java.io.Serializable;
import java.security.Principal;

import com.sfa.login.oauth.model.Organization;

public class OrganizationPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = -1002186158318993615L;
	
	private Organization organization;
	
	public OrganizationPrincipal(Organization organization) {
		this.organization = organization;
	}

	@Override
	public String getName() {
		return organization.getName();
	}
	
	public Organization getOrganization() {
		return organization;
	}
}