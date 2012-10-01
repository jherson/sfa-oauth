package com.sfa.persistence.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.sfa.persistence.AnnotationScanner;
import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.OneToMany;
import com.sfa.persistence.annotation.OneToOne;
import com.sfa.persistence.annotation.Table;
import com.sfa.persistence.soql.Select;
import com.sfa.persistence.type.EntityType;

public class AnnotationScannerImpl implements AnnotationScanner {
	
	private Select select;
	private PropertyMapping propertyMapping;
	

	@Override
	public <T> EntityType scan(Class<T> clazz) {
		
		Annotation annotation = clazz.getAnnotation(Table.class);

		Table table = null;
		if (annotation instanceof Table){
			table = (Table) annotation;
		}
		
		select = new Select();
		select.setFromClause(table.name());
		select.setSelectClause(scanProperties(clazz));
				
		return null;

	}
		
	@Override
	public EntityType scan(String className) {
		return null;
	}	
	
	private <T> String scanProperties(Class<T> clazz) {
		System.out.println("scanning properties for: " + clazz.getName());
		String fieldString = "";
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				if (annotation instanceof Id) {
					
				} else if (annotation instanceof Column) {
				    Column column = (Column) annotation;
				    
		            if (! "".equals(fieldString)) {
		        	    fieldString += "," + System.getProperty("line.separator");
		            }
		            
		            if (field.getAnnotation(OneToOne.class) != null) {
		            	fieldString += scanOneToOneProperties(column.name(), field.getType());
		            } else if (field.getAnnotation(OneToMany.class) != null) { 
		            	fieldString += "(Select Id," + System.getProperty("line.separator");
		            	fieldString += scanOneToManyProperties(field);
		            	fieldString += System.getProperty("line.separator") + "From " + column.name() + ")";
		            } else {
		            	fieldString += column.name();
		            }
				} 
			}
		}
		
		return fieldString;
	}

	private <T> String scanOneToOneProperties(String relationshipName, Class<T> clazz) {
		String fieldString = "";
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				if (annotation instanceof Column) {
				    Column column = (Column) annotation;
		            if (! "".equals(fieldString)) {
		            	 fieldString += "," + System.getProperty("line.separator");
		            }
		            
		            if (field.getAnnotation(OneToOne.class) != null) {
		            	fieldString += scanOneToOneProperties(relationshipName + "." + column.name(), field.getType());
		            } else {
		                fieldString += relationshipName + "." + column.name();
		            }
				} 
			}
		}
		
		return fieldString;
	}
	
	private <T> String scanOneToManyProperties(Field relationshipField) {
		ParameterizedType parameterizedType = (ParameterizedType) relationshipField.getGenericType();   
		Type type = parameterizedType.getActualTypeArguments()[0];
	
		String fieldString = "";
		
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) type;
		
		System.out.println("scanning properties for: " + clazz.getName());
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				if (annotation instanceof Column) {
				    Column column = (Column) annotation;
		            if (! "".equals(fieldString)) {
		            	 fieldString += ", " + System.getProperty("line.separator");
		            }
		            
		            if (field.getAnnotation(OneToOne.class) != null) {
		            	fieldString += scanOneToOneProperties(column.name(), field.getType());
		            } else {
		                fieldString += column.name();
		            }
				} 
			}
		}
		
		return fieldString;
	}
	
}