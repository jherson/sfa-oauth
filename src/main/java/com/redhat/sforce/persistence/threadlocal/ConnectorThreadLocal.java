package com.redhat.sforce.persistence.threadlocal;

import com.sforce.ws.ConnectorConfig;

public class ConnectorThreadLocal {

	public static final ThreadLocal<ConnectorConfig> threadLocal = new ThreadLocal<ConnectorConfig>();

	public static void set(ConnectorConfig config) {
		threadLocal.set(config);
	}

	public static void unset() {
		threadLocal.remove();
	}

	public static ConnectorConfig get() {
		return threadLocal.get();
	}
}