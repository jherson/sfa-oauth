package com.redhat.sforce.qb.session;

import com.redhat.sforce.qb.bean.model.SObject;

public interface SessionManager {

    public void persist(SObject sobject);
    public void remove(SObject sobject);
    public void refresh(SObject sobject);
    public SObject query(String query);
}
