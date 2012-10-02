package com.sfa.persistence.binder;

import java.lang.annotation.Annotation;

import com.sfa.persistence.annotation.Entity;
import com.sfa.persistence.annotation.Table;
import com.sfa.persistence.type.EntityType;

public final class AnnotationBinder {

	private AnnotationBinder() {
		
	}
	
	public static EntityType bindClass(String className) throws Exception {
		Class<?> clazz = Class.forName(className); 
		
		if (! clazz.isAnnotationPresent(Entity.class))
			throw new Exception(className + " is not an entity class");
		
		Annotation annotation = clazz.getAnnotation(Table.class);
		
		return null;
	}
}
