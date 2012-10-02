package com.sfa.persistence.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sfa.persistence.AnnotationScanner;
import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.OneToMany;
import com.sfa.persistence.annotation.OneToOne;
import com.sfa.persistence.annotation.Table;
import com.sfa.persistence.soql.Select;
import com.sfa.persistence.type.EntityType;

public class AnnotationScannerImpl implements AnnotationScanner, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String className;
	private Table table;
	private Column[] columns;
	private Map<String,String> propertyMap = new HashMap<String,String>();
	
	public AnnotationScannerImpl(String className) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object object = loader.loadClass(className).newInstance();
		scan(object);
		this.className = className;
	}
	
	@Override
	public Table getEntity() {
		return table;
		
	}
	
	
	
	private void scan(Object object) throws Exception {
		Annotation annotation = object.getClass().getAnnotation(Table.class);
		
		if (annotation == null)
			throw new Exception("Entity annotation not found on object: " + object.getClass().getName());

		table = (Table) annotation;
	}
	
	public void scanProperties(Object object) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("scanning properties for: " + object.getClass().getName());
		List<Column> columnList = new ArrayList<Column>();
		
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				if (annotation instanceof Id) {
					
				} else if (annotation instanceof Column) {
				    Column column = (Column) annotation;
		            
		            if (field.getAnnotation(OneToOne.class) != null) {
		            	columnList.addAll(scanOneToOneProperties(column.name(), field.getType().getName()));
		            } else if (field.getAnnotation(OneToMany.class) != null) { 
		            	
		            	scanOneToManyProperties(field);
		            	
		            } else {
		            	columnList.add(column);
		            }
				} 
			}
		}
		
		columns = columnList.toArray(new Column[columnList.size()]);
	}

	private List<Column> scanOneToOneProperties(String relationshipName, String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object object = loader.loadClass(className).newInstance();
		
		System.out.println("scanning properties for: " + object.getClass().getName());
		
		List<Column> columnList = new ArrayList<Column>();
		
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				if (annotation instanceof Column) {
				    Column column = (Column) annotation;
		            
		            if (field.getAnnotation(OneToOne.class) != null) {
		            	scanOneToOneProperties(relationshipName + "." + column.name(), field.getType().getName());
		            } else {
		            	columnList.add(column);
		            }
				} 
			}
		}
		
		return columnList;
	}
	
	private List<Column> scanOneToManyProperties(String relationshipField) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object object = loader.loadClass(relationshipField).newInstance();
		
		ParameterizedType parameterizedType = (ParameterizedType) relationshipField.getGenericType();   
		Type type = parameterizedType.getActualTypeArguments()[0];
		
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) type;
		
		System.out.println("scanning properties for: " + clazz.getName());
		
		List<Column> columnList = new ArrayList<Column>();
		
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
		            	fieldString += scanOneToOneProperties(column.name(), field.getType().getName());
		            } else {
		                fieldString += column.name();
		            }
				} 
			}
		}
		
		return fieldString;
	}
	
}