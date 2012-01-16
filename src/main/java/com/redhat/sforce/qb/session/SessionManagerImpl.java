package com.redhat.sforce.qb.session;

import com.redhat.sforce.qb.bean.model.SObject;

public class SessionManagerImpl implements SessionManager {

    @Override
    public void persist(SObject sobject) {
        return;
    }

    @Override
    public void remove(SObject sobject) {
        return;
    }

    @Override
    public void refresh(SObject sobject) {
        return;
    }

    @Override
    public SObject query(String query) {
        return null;
    }
}
