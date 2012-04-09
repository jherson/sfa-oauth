package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.model.Contact;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class ContactFactory {

	public static List<Contact> deserialize(JSONArray jsonArray)
			throws JSONException, ParseException {
		List<Contact> contactList = new ArrayList<Contact>();

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray
					.getJSONObject(i).getJSONObject("Contact"));

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