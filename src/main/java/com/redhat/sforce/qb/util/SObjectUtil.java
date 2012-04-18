package com.redhat.sforce.qb.util;

import java.util.ArrayList;
import java.util.List;

import com.redhat.sforce.qb.model.SObject;

public class SObjectUtil {

	public static Double doubleValue(Object field) {
		return field != null ? new Double(field.toString()) : null;	
	}
	
	public static String stringValue(Object field) {
		return field != null ? field.toString() : null;
	}
	
	public static List<String> getSObjectIds(List<SObject> sobjectList) {
		List<String> idList = new ArrayList<String>();
		for (SObject sobject : sobjectList) {
			idList.add(sobject.getId());
		}
		return idList;
	}	
}