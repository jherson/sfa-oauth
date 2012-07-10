package com.redhat.sforce.persistence.threadlocal;

import com.sforce.ws.ConnectorConfig;

public final class ConnectionThreadLocal {

	private static final ThreadLocal<ConnectorConfig> THREAD_LOCAL = new ThreadLocal<ConnectorConfig>();
	
	private ConnectionThreadLocal() {}

	public static void set(ConnectorConfig config) {
		THREAD_LOCAL.set(config);
	}

	public static void close() {
		THREAD_LOCAL.remove();
	}

	public static ConnectorConfig get() {
		return THREAD_LOCAL.get();
	}
}