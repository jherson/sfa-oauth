package com.redhat.sforce.persistence.impl;

import com.redhat.sforce.persistence.EntityManager;

public class EntityManagerFactory {
	
	private static EntityManager em = new EntityManagerImpl();

	public static EntityManager createEntityManager() {
		return em;
	}
}