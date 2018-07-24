/**
 * 
 */
package com.common.plugins.xmlsql.sqlparse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 袁志华
 * 
 */
public class ObjectUtils {
	private static Logger log = LoggerFactory.getLogger(ObjectUtils.class);

	public static String getGetMethod(String property) {
		String method = property;
		method = method.substring(0, 1).toUpperCase() + method.substring(1);
		return "get" + method;
	}

	public static String getSetMethod(String property) {
		String method = property;
		method = method.substring(0, 1).toUpperCase() + method.substring(1);
		return "set" + method;
	}

	public static Object executeGetMethod(Object obj, String property) {
		Class<?> cls = obj.getClass();
		Method getMethod = null;
		Object retObject = null;
		try {
			getMethod = cls.getMethod(ObjectUtils.getGetMethod(property));
			retObject = getMethod.invoke(obj);
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return retObject;
	}

	public static void executeSetMethod(Object obj, String property,
			Object setObj) {
		Class<?> cls = obj.getClass();
		Method setMethod = null;
		Field field = null;
		try {
			for (Class<?> clazz = cls; clazz != Object.class; clazz = clazz
					.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(property);
					break;
				} catch (Exception e) {
				}
			}
			if (field == null) {
				return;
			}
			setMethod = cls.getMethod(ObjectUtils.getSetMethod(property),
					field.getType());
			if (field != null && setMethod != null) {
				if (setObj != null) {
					if (!field.getType().isPrimitive()) {
						if (field.getType() == Integer.class) {
							setMethod.invoke(obj,
									Integer.valueOf(setObj.toString()));
						} else if (field.getType() == Date.class) {
							setMethod.invoke(obj,
									Date.valueOf(setObj.toString()));
						} else if (field.getType() == Long.class) {
							setMethod.invoke(obj,
									Long.valueOf(setObj.toString()));
						} else if (field.getType() == Float.class) {
							setMethod.invoke(obj,
									Float.valueOf(setObj.toString()));
						} else if (field.getType() == Time.class) {
							setMethod.invoke(obj,
									Time.valueOf(setObj.toString()));
						} else if (field.getType() == Boolean.class) {
							setMethod.invoke(obj,
									Boolean.valueOf(setObj.toString()));
						} else {
							setMethod.invoke(obj, setObj.toString());
						}
					} else {
						if (field.getType() == boolean.class) {
							if (setObj.getClass() == String.class) {
								setMethod.invoke(obj,
										Boolean.valueOf(setObj.toString()));
							} else if (setObj.getClass() == Integer.class
									|| setObj.getClass() == int.class
									|| setObj.getClass() == Long.class
									|| setObj.getClass() == long.class) {
								if (Integer.valueOf(setObj.toString()) != 0) {
									setMethod.invoke(obj, true);
								} else {
									setMethod.invoke(obj, false);
								}
							} else {
								setMethod.invoke(obj, setObj);
							}

						} else {
							setMethod.invoke(obj, setObj);
						}
					}
				} else {
					setMethod.invoke(obj, setObj);
				}
			}
		} catch (NoSuchMethodException e) {
			log.error("Please set " + field.getName() + " for " + cls.getName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// System.out.println(setMethod.toGenericString() + setObj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public static Method parseSetMethod(String property, Class<?> cls) {
		Method setMethod = null;
		Field field = null;
		try {
			field = cls.getDeclaredField(property.toLowerCase());
			setMethod = cls.getMethod(ObjectUtils.getSetMethod(property),
					field.getType());
		} catch (NoSuchFieldException e) {
		} catch (NoSuchMethodException e) {
			log.error("Please set " + field.getName() + " for class "
					+ cls.getName());
		}
		return setMethod;
	}

	public static Object instance(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> cls = Class.forName(className);
		Object obj = cls.newInstance();
		log.info("create class " + obj);
		return obj;
	}

}
