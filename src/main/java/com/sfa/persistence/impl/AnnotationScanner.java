package com.sfa.persistence.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.OneToMany;
import com.sfa.persistence.annotation.OneToOne;
import com.sfa.persistence.annotation.Table;
import com.sfa.persistence.soql.Select;
import com.sfa.persistence.type.ColumnType;
import com.sfa.persistence.type.OneToManyType;
import com.sfa.persistence.type.OneToOneType;

public class AnnotationScanner implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Table table;
	private Column[] columns;
	
	private List<ColumnType> columnTypes = new ArrayList<ColumnType>();
	private List<OneToManyType> oneToManyTypes = new ArrayList<OneToManyType>();
	private List<OneToOneType> oneToOneTypes = new ArrayList<OneToOneType>();
	
	public AnnotationScanner() {}
		
	public AnnotationScanner(String className) throws Exception {		
		scan(className);
	}				
	
	public Table getTable() {
		return table;
	}
	
	private void scan(String className) throws Exception {
		Class<?> clazz = Class.forName(className);
		
		Annotation annotation = clazz.getAnnotation(Table.class);
		
		if (annotation == null)
			throw new Exception("Table annotation not found on object: " + clazz.getName());

		table = (Table) annotation;		
		
        StringBuilder selectClause = new StringBuilder();
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			selectClause.append(scanField(field));						
		}
		
		Select select = new Select();
		select.setFromClause(table.name());
		select.setSelectClause(selectClause.toString());
		
		System.out.println(select.toStatementString());
	}
	
	private String scanField(Field field) throws ClassNotFoundException {
				
		StringBuilder queryFragment = new StringBuilder();
		
		Annotation[] annotations = field.getAnnotations();	
		
		for (Annotation annotation : annotations) {
			
			if (annotation instanceof Id) {
							
				if (queryFragment.length() > 0) {
	            	queryFragment.append("," + System.getProperty("line.separator"));
	            }
				
				queryFragment.append("Id");
				
			} else if (annotation instanceof Column) {
			    Column property = (Column) annotation;
			    
			    if (queryFragment.length() > 0) {
	            	queryFragment.append("," + System.getProperty("line.separator"));
	            }
	            
	            queryFragment.append(property.name());
	            		            
			} else if (annotation instanceof OneToOne) {
				OneToOne property = (OneToOne) annotation;
				queryFragment.append(scanOneToOneRelationship(property.name(), field.getType().getName()));
            } else if (annotation instanceof OneToMany) { 
            	OneToMany property = (OneToMany) annotation;   
            	queryFragment.append("(Select ");
            	queryFragment.append(scanOneToManyRelationship(property.name(), field));
            	queryFragment.append(System.getProperty("line.separator") + "From " + property.name() + ")");
            } 
		}
		
		return queryFragment.toString();
	}
	
	private String scanOneToOneRelationship(String relationshipName, String className) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		
		StringBuilder queryFragment = new StringBuilder();
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				if (annotation instanceof Id) {					
					
					if (queryFragment.length() > 0) {
		            	queryFragment.append("," + System.getProperty("line.separator"));
		            }
					
					queryFragment.append(relationshipName + "." + "Id");
					
				} else if (annotation instanceof Column) {
				    Column property = (Column) annotation;
				    
				    if (queryFragment.length() > 0) {
		            	queryFragment.append("," + System.getProperty("line.separator"));
		            }
		            
		            queryFragment.append(relationshipName + "." + property.name());
		            
				} else if (annotation instanceof OneToOne) {
					OneToOne property = (OneToOne) annotation;
					queryFragment.append(scanOneToOneRelationship(relationshipName + "." + property.name(), field.getType().getName()));
				} 
			}
		}
		
		return queryFragment.toString();		
	}
	
	private <T> String scanOneToManyRelationship(String relationshipName, Field relationshipField) throws ClassNotFoundException {
		ParameterizedType parameterizedType = (ParameterizedType) relationshipField.getGenericType();   
		Type type = parameterizedType.getActualTypeArguments()[0];
	
		StringBuilder queryFragment = new StringBuilder();
		
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) type;
						
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				
				if (annotation instanceof Id) {
					
					if (queryFragment.length() > 0) {
		            	queryFragment.append("," + System.getProperty("line.separator"));
		            } 
					
					queryFragment.append("Id");
					
				} else if (annotation instanceof Column) {
				    Column column = (Column) annotation;
				    
				    if (queryFragment.length() > 0) {
		            	queryFragment.append(", " + System.getProperty("line.separator"));
		            } 
		            
				    queryFragment.append(column.name());
		            
				} else if (field.getAnnotation(OneToOne.class) != null) {
					OneToOne property = (OneToOne) annotation;
	            	queryFragment.append(scanOneToOneRelationship(property.name(), field.getType().getName()));
	            } 
			}						
		}
						
		return queryFragment.toString();
	}	
}