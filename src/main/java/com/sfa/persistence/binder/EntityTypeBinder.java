package com.sfa.persistence.binder;

import com.sfa.persistence.type.EntityType;

public class EntityTypeBinder {

	public <T> EntityType bind(Class<T> clazz, String id) {
		EntityType entityType = new EntityType();
		
		
		//setWhereClause("Id = '" + id + "'");
		
		return entityType;
	}
}