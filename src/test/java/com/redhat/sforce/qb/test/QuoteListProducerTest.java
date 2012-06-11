package com.redhat.sforce.qb.test;

import static org.junit.Assert.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.sforce.qb.data.QuoteListProducer;
import com.redhat.sforce.qb.data.QuoteProducer;
import com.redhat.sforce.qb.data.UserProducer;

public class QuoteListProducerTest {

	public static final String WEBAPP_SRC = "src/main/webapp";
	

	public static Archive<?> createTestArchive() {	
	    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadReposFromPom("pom.xml");
	    
	    return ShrinkWrap.create(WebArchive.class, "test.war")
	            .addClasses(QuoteListProducer.class, QuoteProducer.class, UserProducer.class)
	            .addAsLibraries(resolver.artifact("org.jboss.seam.solder:seam-solder").resolveAsFiles())
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	

	public void testRegister() throws Exception {
		String id = "lskjdkfjakljsdfkjd";
	    assertNotNull(id);	
	}
}