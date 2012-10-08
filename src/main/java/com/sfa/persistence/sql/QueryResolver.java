package com.sfa.persistence.sql;

import com.sfa.persistence.annotation.Table;

public class QueryResolver {

	public static <T> String getBoundQuery(Class<T> clazz) {
		
		Table table = (Table) clazz.getAnnotation(Table.class);
	
		if ("Quote__c".equals(table.name())) {
			return NamedQuery.QUOTE_QUERY;
		}
		
		return null;
	}
	
}