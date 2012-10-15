package com.sfa.qb.model.sobject.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sfa.qb.model.sobject.Contact;
import com.sfa.qb.util.SObjectWrapper;
import com.sforce.ws.bind.XmlObject;

public class ContactFactory {

//	public static List<Contact> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
//		
//		List<Contact> contactList = new ArrayList<Contact>();
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//
//			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i).getJSONObject("Contact"));
//
//			Contact contact = new Contact();
//			contact.setId(wrapper.getId());
//			contact.setContactId(wrapper.getString("ContactId"));
//			contact.setDepartment(wrapper.getString("Department"));
//			contact.setEmail(wrapper.getString("Email"));
//			contact.setFirstName(wrapper.getString("FirstName"));
//			contact.setLastName(wrapper.getString("LastName"));
//			contact.setName(wrapper.getString("Name"));
//			contact.setPhone(wrapper.getString("Phone"));
//			contact.setTitle(wrapper.getString("Title"));
//
//			contactList.add(contact);
//
//		}
//
//		return contactList;
//	}
	
	public static List<Contact> parse(Iterator<XmlObject> iterator) {
		
		List<Contact> contactList = new ArrayList<Contact>();
		
		while (iterator.hasNext()) {
		
			SObjectWrapper wrapper = new SObjectWrapper(iterator.next());
			
			Contact contact = new Contact();
			contact.setId(wrapper.getId());
			contact.setContactId(wrapper.getString("ContactId"));
			contact.setDepartment(wrapper.getString("Department"));
			contact.setEmail(wrapper.getString("Email"));
			contact.setFirstName(wrapper.getString("FirstName"));
			contact.setLastName(wrapper.getString("LastName"));
			contact.setName(wrapper.getString("Name"));
			contact.setPhone(wrapper.getString("Phone"));
			contact.setTitle(wrapper.getString("Title"));

			contactList.add(contact);
		}
		
		return contactList;
	}
}