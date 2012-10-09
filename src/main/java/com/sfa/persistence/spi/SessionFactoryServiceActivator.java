package com.sfa.persistence.spi;

import java.util.logging.Logger;

import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistryException;
import org.jboss.msc.service.ServiceTarget;

import com.sfa.persistence.impl.SessionFactoryImpl;
import com.sfa.persistence.impl.SessionFactoryService;

public class SessionFactoryServiceActivator implements ServiceActivator {
	
	private static final Logger log = Logger.getLogger(SessionFactoryServiceActivator.class.getName());

	@Override
	public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
		log.info("Activating MSC service: " + getClass().getName());
		
		SessionFactoryService service = new SessionFactoryService(new SessionFactoryImpl());		
		ServiceTarget serviceTarget = context.getServiceTarget();
		ServiceName serviceName = ServiceName.of("SessionService");
        ServiceBuilder<?> serviceBuilder = serviceTarget.addService(serviceName, service);
        serviceBuilder.setInitialMode(Mode.ACTIVE);
        serviceBuilder.install();       
	}
}