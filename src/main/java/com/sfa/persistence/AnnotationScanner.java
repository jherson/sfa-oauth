package com.sfa.persistence;

import com.sfa.persistence.type.EntityType;

public interface AnnotationScanner {

	public <T> EntityType scan(Class<T> clazz);	
	public EntityType scan(String className);

}