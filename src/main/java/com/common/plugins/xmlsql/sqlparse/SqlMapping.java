/**
 * 
 */
package com.common.plugins.xmlsql.sqlparse;

/**
 * @author Administrator
 * 
 */
public class SqlMapping {

	public static String INSERT = "insert";
	public static String UPDATE = "update";
	public static String DELETE = "delete";
	public static String SELECT = "select";

	private String id;
	private String curd;
	private String sqlType;
	private String resultType;
	private String paramType;
	private String sqlBody;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurd() {
		return curd;
	}

	public void setCurd(String curd) {
		this.curd = curd;
	}

	public String getSqlBody() {
		return sqlBody;
	}

	public void setSqlBody(String sqlBody) {
		this.sqlBody = sqlBody;
	}

	@Override
	public String toString() {
		return id + resultType + sqlBody;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamType() {
		return paramType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

}
