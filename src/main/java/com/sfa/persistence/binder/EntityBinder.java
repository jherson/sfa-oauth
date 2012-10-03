package com.sfa.persistence.binder;

import com.sfa.persistence.soql.Select;
import com.sfa.persistence.type.ColumnType;
import com.sfa.persistence.type.EntityType;
import com.sfa.persistence.type.OneToOneType;

public class EntityBinder {	
	
	public EntityType bind(String className) throws Exception {		
			
		EntityType entityType = AnnotationBinder.bindClass(className);	
		entityType.setQueryString(queryBuilder(entityType));
		
		return entityType;
		
	}
	
	private String queryBuilder(EntityType entityType) {
		Select select = new Select();
		select.setFromClause(entityType.getTable().getTableName());
		
		String queryString = "Id";
		for (ColumnType column : entityType.getColumnTypes()) {
			queryString += ", " + column.getColumnName();
		}
		
		for (OneToOneType oneToOne : entityType.getOneToOneTypes()) {
			queryString += ", " + oneToOne.getRelationshipName() + ".";
		}
			
		
		select.setSelectClause(queryString);
		
		return select.toStatementString();
	}
}