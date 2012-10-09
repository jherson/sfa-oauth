package com.sfa.persistence.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

import com.sfa.persistence.SessionFactory;

public class SessionFactoryService implements Service<SessionFactory> {

	private static final Logger log = Logger.getLogger(SessionFactoryService.class.getName());
	private SessionFactory sessionFactory;
	
	public SessionFactoryService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SessionFactory getValue() throws IllegalStateException, IllegalArgumentException {
		return sessionFactory;
	}

	@Override
	public void start(final StartContext context) throws StartException {
		log.log(Level.INFO, "start context");
		
        context.asynchronous();        
        context.execute(new Runnable() {
            public void run() {
                try {
                	SessionFactoryService.this.getValue().getSession();
                    context.complete();
                } catch (Exception e) {
                    context.failed( new StartException( e ) );
                }
            }
        });
		
	}

	@Override
	public void stop(StopContext context) {
		log.log(Level.INFO, "stop context");
		
	}
}
