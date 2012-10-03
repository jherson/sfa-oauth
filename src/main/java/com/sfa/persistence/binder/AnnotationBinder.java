package com.sfa.persistence.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Entity;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.OneToMany;
import com.sfa.persistence.annotation.OneToOne;
import com.sfa.persistence.annotation.Table;
import com.sfa.persistence.type.ColumnType;
import com.sfa.persistence.type.EntityType;
import com.sfa.persistence.type.IdType;
import com.sfa.persistence.type.OneToManyType;
import com.sfa.persistence.type.OneToOneType;
import com.sfa.persistence.type.TableType;

public final class AnnotationBinder {
	
	private AnnotationBinder() {
		
	}
	
	public static EntityType bindClass(String className) throws Exception {
		Class<?> clazz = Class.forName(className);
		
		if (! clazz.isAnnotationPresent(Entity.class))
			return null;
		
		System.out.println("binding class: " + className);
		
		Annotation annotation = clazz.getAnnotation(Table.class);
	    Table table = (Table) annotation;
	    
	    TableType tableType = new TableType();
	    tableType.setTableName(table.name());
	    
	    IdType idType = null;
	    List<ColumnType> columnTypes = null;
	    List<OneToOneType> oneToOneTypes = null;
	    List<OneToManyType> oneToManyTypes = null;
	    
	    Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			
			Object object = scanField(field);
			
			if (object instanceof IdType) {
				
				idType = (IdType) object;
				
			} else if (object instanceof ColumnType) {
				
				if (columnTypes == null)
					columnTypes = new ArrayList<ColumnType>();
				
				columnTypes.add((ColumnType) object);
				
			} else if (object instanceof OneToOneType) {
				
				if (oneToOneTypes == null)
					oneToOneTypes = new ArrayList<OneToOneType>();
				
				oneToOneTypes.add((OneToOneType) object);
				
			} else if (object instanceof OneToManyType) {
				
				if (oneToManyTypes == null)
					oneToManyTypes = new ArrayList<OneToManyType>();
				
				oneToManyTypes.add((OneToManyType) object);
			}
		}
	    
	    return new EntityType(className, tableType, idType, columnTypes, oneToOneTypes, oneToManyTypes);
	}
	
	private static Object scanField(Field field) throws Exception {
				
		Annotation[] annotations = field.getAnnotations();	
		
		for (Annotation annotation : annotations) {
			
			if (annotation instanceof Id) {
				return new IdType(field.getName());			
				
			} else if (annotation instanceof Column) {
			    Column property = (Column) annotation;
			    return new ColumnType(field.getName(), field.getType(), property.name());
			    	            		            
			} else if (annotation instanceof OneToOne) {
				OneToOne property = (OneToOne) annotation;				
				return new OneToOneType(property.name(), property.referenceColumnName(), null);
				
            } else if (annotation instanceof OneToMany) { 
            	OneToMany property = (OneToMany) annotation;   
            	return new OneToManyType(property.name(), null);
            } 
		}
		
		return null;
		
	}
}
