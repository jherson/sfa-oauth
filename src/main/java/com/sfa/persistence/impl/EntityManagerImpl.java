package com.sfa.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.sfa.persistence.EntityManager;
import com.sfa.persistence.Query;
import com.sfa.persistence.binder.EntityBinder;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.persistence.type.EntityType;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class EntityManagerImpl implements EntityManager, Serializable {

	private static final long serialVersionUID = 8472659225063158884L;
				
	public EntityManagerImpl() {
		
	}
	
	@Override
	public SaveResult[] persist(List<SObject> sobjectList) throws ConnectionException {		
		List<SObject> updateList = new ArrayList<SObject>();
		List<SObject> createList = new ArrayList<SObject>();
		
		for (SObject sobject : sobjectList) {
			if (sobject.getId() != null) {
				updateList.add(sobject);
			} else {
				createList.add(sobject);
			}
		}
		
		SaveResult[] saveResult = new SaveResult[sobjectList.size()];
		if (updateList.size() > 0) {
			System.arraycopy(update(updateList.toArray(new SObject[updateList.size()])), 0, saveResult, 0, updateList.size());
		}
		
		if (createList.size() > 0) {
			System.arraycopy(create(createList.toArray(new SObject[updateList.size()])), 0, saveResult, updateList.size(), createList.size());
		}

		return saveResult;
		
	}
	
	@Override
	public SaveResult persist(SObject sobject) throws ConnectionException {		
		SaveResult saveResult = null;
		if (sobject.getId() != null) {
			saveResult = update(sobject);
		} else {
			saveResult = create(sobject);
		}
		
		return saveResult;
	}
	
	@Override
	public Query createQuery(String query) {			
		return new QueryImpl<Object>(getPartnerConnection(), query);				
	}
		
//	@Override
//	public DeleteResult[] delete(List<String> idList) throws ConnectionException {
//		return delete(idList.toArray(new String[idList.size()]));
//	}	
//	
//	@Override
//	public DeleteResult delete(String id) throws ConnectionException {
//		return delete(new String[] {id})[0];
//	}

	@Override
	public DeleteResult delete(SObject sobject) throws ConnectionException {
		return delete(new String[] {sobject.getId()})[0];
	}

	@Override
	public DeleteResult[] delete(List<SObject> sobjectList) throws ConnectionException {
		return delete(toIdArray(sobjectList));
	}

	@Override
	public SObject refresh(SObject sobject) throws ConnectionException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public <T> SObject find(Class<T> clazz, String id) throws ConnectionException {
		


			
		
//		Query q = createQuery(entityType);	
//		q.showQuery();
//		try {
//			q.execute();
//		} catch (QueryException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return null;
	}
	
	private DeleteResult[] delete(String[] ids) throws ConnectionException {
		return getPartnerConnection().delete(ids);
	}
	
	private SaveResult create(SObject sobject) throws ConnectionException {
		return getPartnerConnection().create(new SObject[] {sobject})[0];
	}
	
	private SaveResult[] create(SObject[] sobjects) throws ConnectionException {
		return getPartnerConnection().create(sobjects);
	}
	
	private SaveResult update(SObject sobject) throws ConnectionException {
		return getPartnerConnection().update(new SObject[] {sobject})[0];
	}
	
	private SaveResult[] update(SObject[] sobjects) throws ConnectionException {
		return getPartnerConnection().update(sobjects);
	}
	
	private PartnerConnection getPartnerConnection() {
		return ConnectionManager.getConnection();
	}
	
	private String[] toIdArray(List<SObject> sobjectList) {
		List<String> idList = new ArrayList<String>();
		for (SObject sobject : sobjectList) {
			idList.add(sobject.getId());
		}
		return idList.toArray(new String[idList.size()]);
	}
	
//	private <T> String scanProperties(Class<T> clazz) {
//		System.out.println("scanning properties for: " + clazz.getName());
//		String fieldString = "";
//		
//		Field[] fields = clazz.getDeclaredFields();
//		for (Field field : fields) {
//			Annotation[] annotations = field.getAnnotations();
//			
//			for (Annotation annotation : annotations) {
//				if (annotation instanceof Id) {
//					
//				} else if (annotation instanceof Column) {
//				    Column column = (Column) annotation;
//				    
//		            if (! "".equals(fieldString)) {
//		        	    fieldString += "," + System.getProperty("line.separator");
//		            }
//		            
//		            if (field.getAnnotation(OneToOne.class) != null) {
//		            	fieldString += scanOneToOneProperties(column.name(), field.getType());
//		            } else if (field.getAnnotation(OneToMany.class) != null) { 
//		            	fieldString += "(Select Id," + System.getProperty("line.separator");
//		            	fieldString += scanOneToManyProperties(field);
//		            	fieldString += System.getProperty("line.separator") + "From " + column.name() + ")";
//		            } else {
//		            	fieldString += column.name();
//		            	objectMapping.put(column.name(), field.getName());
//		            }
//				} 
//			}
//		}
//		
//		return fieldString;
//	}
//	
//	private <T> String scanOneToOneProperties(String relationshipName, Class<T> clazz) {
//		String fieldString = "";
//		
//		Field[] fields = clazz.getDeclaredFields();
//		for (Field field : fields) {
//			Annotation[] annotations = field.getAnnotations();
//			
//			for (Annotation annotation : annotations) {
//				if (annotation instanceof Column) {
//				    Column column = (Column) annotation;
//		            if (! "".equals(fieldString)) {
//		            	 fieldString += "," + System.getProperty("line.separator");
//		            }
//		            
//		            if (field.getAnnotation(OneToOne.class) != null) {
//		            	fieldString += scanOneToOneProperties(relationshipName + "." + column.name(), field.getType());
//		            } else {
//		                fieldString += relationshipName + "." + column.name();
//		            }
//				} 
//			}
//		}
//		
//		return fieldString;
//	}
//	
//	private <T> String scanOneToManyProperties(Field relationshipField) {
//		ParameterizedType parameterizedType = (ParameterizedType) relationshipField.getGenericType();   
//		Type type = parameterizedType.getActualTypeArguments()[0];
//
//		String fieldString = "";
//		
//		@SuppressWarnings("unchecked")
//		Class<T> clazz = (Class<T>) type;
//		
//		System.out.println("scanning properties for: " + clazz.getName());
//		
//		Field[] fields = clazz.getDeclaredFields();
//		for (Field field : fields) {
//			Annotation[] annotations = field.getAnnotations();
//			
//			for (Annotation annotation : annotations) {
//				if (annotation instanceof Column) {
//				    Column column = (Column) annotation;
//		            if (! "".equals(fieldString)) {
//		            	 fieldString += ", " + System.getProperty("line.separator");
//		            }
//		            
//		            if (field.getAnnotation(OneToOne.class) != null) {
//		            	fieldString += scanOneToOneProperties(column.name(), field.getType());
//		            } else {
//		                fieldString += column.name();
//		            }
//				} 
//			}
//		}
//		
//		return fieldString;
//	}
}