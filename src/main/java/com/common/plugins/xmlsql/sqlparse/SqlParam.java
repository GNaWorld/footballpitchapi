/**
 * 
 */
package com.common.plugins.xmlsql.sqlparse;

import java.util.HashMap;

/**
 * @author 袁志华  283117523@qq.com
 *
 */
public class SqlParam extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1832097637166320079L;

	public static SqlParam Init(String key, Object value) {
		SqlParam sp = new SqlParam();
		sp.put(key, value);
		return sp;
	}

	public SqlParam Add(String key, Object value) {
		this.put(key, value);
		return this;
	}
}
