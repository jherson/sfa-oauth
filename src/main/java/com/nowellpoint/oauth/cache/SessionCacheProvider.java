package com.nowellpoint.oauth.cache;

import java.io.Serializable;

import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.SingleFileStoreConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class SessionCacheProvider implements Serializable {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 3306191209141636649L;
	
	/**
	 * 
	 */
	
	private EmbeddedCacheManager cacheManager;
	
	public SessionCacheProvider() {
		Configuration cacheConfig = new ConfigurationBuilder().persistence()
                .passivation(false)
                .addStore(SingleFileStoreConfigurationBuilder.class)
                .shared(true)
                .location(System.getenv("OPENSHIFT_DATA_DIR"))
                .purgeOnStartup(false)
                .eviction().strategy(EvictionStrategy.LIRS).maxEntries(5000)
                .build();
		
		cacheManager = new DefaultCacheManager();
        cacheManager.defineConfiguration("session-cache", cacheConfig);
	}

	public EmbeddedCacheManager getCacheManager() {
        return cacheManager;
    }
}