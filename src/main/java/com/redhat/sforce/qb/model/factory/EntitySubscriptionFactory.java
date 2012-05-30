package com.redhat.sforce.qb.model.factory;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.model.EntitySubscription;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class EntitySubscriptionFactory {

	public static List<EntitySubscription> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<EntitySubscription> entitySubscriptionList = new ArrayList<EntitySubscription>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
			
			EntitySubscription entitySubscription = new EntitySubscription();
			entitySubscription.setId(wrapper.getId());
			entitySubscription.setParentId(wrapper.getString("ParentId"));
			entitySubscription.setSubscriberId(wrapper.getString("SubscriberId"));
			entitySubscriptionList.add(entitySubscription);
		}	
		
        return entitySubscriptionList;
	}
}