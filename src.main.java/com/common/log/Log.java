package com.common.log;

import com.jfinal.log.ILogFactory;
import com.jfinal.log.JdkLogFactory;

/**
 * The five logging levels used by Log are (in order):
 * 1. DEBUG (the least serious)
 * 2. INFO
 * 3. WARN
 * 4. ERROR
 * 5. FATAL (the most serious)
 */
public abstract class Log {
	
	private static ILogFactory defaultLogFactory = null;
	
	static {
		init();
	}
	
	static void init() {
		if (defaultLogFactory == null) {
			try {
				Class.forName("org.apache.log4j.Logger");
				Class<?> log4jLogFactoryClass = Class.forName("com.jfinal.log.Log4jLogFactory");
				defaultLogFactory = (ILogFactory)log4jLogFactoryClass.newInstance();	// return new Log4jLogFactory();
			} catch (Exception e) {
				defaultLogFactory = new JdkLogFactory();
			}
		}
	}
	
	static void setDefaultLogFactory(ILogFactory defaultLogFactory) {
		if (defaultLogFactory == null) {
			throw new IllegalArgumentException("defaultLogFactory can not be null.");
		}
		Log.defaultLogFactory = defaultLogFactory;
	}
	
//	public static Log getLog(Class<?> clazz) {
//		return defaultLogFactory.getLog(clazz);
//	}
//	
//	public static Log getLog(String name) {
//		return defaultLogFactory.getLog(name);
//	}
	
	public abstract void debug(String message);
	
	public abstract void debug(String message, Throwable t);
	
	public abstract void info(String message);
	
	public abstract void info(String message, Throwable t);
	
	public abstract void warn(String message);
	
	public abstract void warn(String message, Throwable t);
	
	public abstract void error(String message);
	
	public abstract void error(String message, Throwable t);
	
	public abstract void fatal(String message);
	
	public abstract void fatal(String message, Throwable t);
	
	public abstract boolean isDebugEnabled();

	public abstract boolean isInfoEnabled();

	public abstract boolean isWarnEnabled();

	public abstract boolean isErrorEnabled();
	
	public abstract boolean isFatalEnabled();
}
