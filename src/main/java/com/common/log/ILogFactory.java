package com.common.log;

/**
 * ILogFactory.
 */
public interface ILogFactory {
	
	Log getLog(Class<?> clazz);
	
	Log getLog(String name);
}
