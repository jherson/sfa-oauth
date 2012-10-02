package com.sfa.persistence.binder;

import com.sfa.persistence.AnnotationScanner;
import com.sfa.persistence.impl.AnnotationScannerImpl;
import com.sfa.persistence.soql.Select;
import com.sfa.persistence.type.EntityType;

public class EntityBinder {
	
	private String name;
	
	
	private AnnotationScanner scanner;

	public EntityType bind(AnnotationScanner scanner, String id) {
		this.scanner = scanner;
		return initialize(id);
	}
	
	private EntityType initialize(String id) {
		
		EntityType entityType = new EntityType();
		entityType.setClassName(scanner.getTable().name());
		

		
		//buildSelect(id);
		
		return entityType;
		
	}
	
	
	
//	private Select buildSelect(String id) {
//		Select select = new Select();
//		select.setFromClause(scanner.getEntity().name());
//		select.setSelectClause(scanProperties(clazz));
//		select.setWhereClause("Where Id = '" + id + "'");
//	}
}