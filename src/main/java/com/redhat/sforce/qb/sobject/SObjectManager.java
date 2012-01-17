package com.redhat.sforce.qb.sobject;

import com.redhat.sforce.qb.bean.model.SObject;
import com.redhat.sforce.qb.exception.SforceServiceException;

public interface SObjectManager {

    public void persist(SObject sobject) throws SforceServiceException;
    public void remove(SObject sobject);
    public void refresh(SObject sobject);
    public SObject query(String query);
    public SObject find(SObject sobject, String id);
}
