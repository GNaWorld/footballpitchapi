package com.gn.kit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.gn.interceptor.LogKitInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;

public class IAWLogKit {
	private static final boolean SAVEDB = true;

	public static void info(Object msg) {
		log(Level.INFO, msg, SAVEDB);
	}

	public static void error(Object msg) {
		log(Level.ERROR, msg, SAVEDB);
	}

	public static void debug(Object msg) {
		log(Level.DEBUG, msg, SAVEDB);
	}

	public static void warn(Object msg) {
		log(Level.WARN, msg, SAVEDB);
	}

	public static void info(Object msg, boolean saveDb) {
		log(Level.INFO, msg, saveDb);
	}

	public static void error(Object msg, boolean saveDb) {
		log(Level.ERROR, msg, saveDb);
	}

	public static void debug(Object msg, boolean saveDb) {
		log(Level.DEBUG, msg, saveDb);
	}

	public static void warn(Object msg, boolean saveDb) {
		log(Level.WARN, msg, saveDb);
	}

	private static void log(Level level, Object msg, boolean saveDb) {
		IAWLogKit logKit = Duang.duang(IAWLogKit.class);
		String className = Thread.currentThread().getStackTrace()[3].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		logKit.log(level, className, methodName, lineNumber, msg, saveDb);
	}

	/**
	 * 不能写private 否则拦截器不生效
	 * 
	 * @param level
	 * @param className
	 * @param methodName
	 * @param lineNumber
	 * @param msg
	 */
	@Before(LogKitInterceptor.class)
	protected void log(Level level, String className, String methodName, int lineNumber, Object msg, boolean saveDb) {
		Logger log = Logger.getLogger(IAWLogKit.class);
		log.log(level, msg);
	}

}
