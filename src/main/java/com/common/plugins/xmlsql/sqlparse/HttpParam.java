/**
 * 
 */
package com.common.plugins.xmlsql.sqlparse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hellozhihua
 * 
 */
public class HttpParam extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1698207404340203868L;

	public HttpParam addparam(String key, Object value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	public Map[] toArray() {
		return new Map[] { this };
	}
}
