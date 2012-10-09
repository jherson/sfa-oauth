package com.sfa.persistence.spi;

import java.util.ServiceLoader;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sfa.persistence.SessionFactory;


@WebListener
public class SessionInitializer implements ServletContextListener {
	
	private static final Logger log = Logger.getLogger(SessionInitializer.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("SessionInitializer...stopping");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("SessionInitializer...starting");
		init();
		log.info("SessionInitializer...initialized");
	}
	
	private void init() {	
		ServiceLoader<SessionFactory> sessionFactoryImpl = ServiceLoader.load(SessionFactory.class);
		for (SessionFactory sessionFactory : sessionFactoryImpl) {
			sessionFactory.getSession();
		}
	}
}