package com.sfa.qb.util;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.sfa.qb.qualifiers.MessageBundle;

public class Resources {

	@SuppressWarnings("unused")
	@Produces
	@PersistenceContext(unitName = "sfa-qb", type = PersistenceContextType.TRANSACTION)
	private static EntityManager entityManager;

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	@MessageBundle
	public ResourceBundle getBundle() {
		return FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msgs");
	}
}