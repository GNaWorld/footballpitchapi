/**
 * 
 */
package com.common.plugins.xmlsql.sqlparse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hellozhihua
 * 
 */
public class SqlParser {

	private static Logger log = LoggerFactory.getLogger(SqlParser.class);

	private static String parseSql(String sql, Object paramObject) {
		try {
			sql = sql.replace("}", "@#");
			sql = sql.replace("{", "#@");
			sql = sql.replace("$", "!#");
			Pattern p = Pattern.compile("#@.*?@#");
			Pattern pp = Pattern.compile("!#.*?!#");
			Matcher m = p.matcher(sql);

			while (m.find()) {
				Matcher mm = pp.matcher(m.group());
				if (mm.find()) {
					String tempsql = null;
					if (paramObject != null) {
						tempsql = regexSql(sql, mm.group(), paramObject, true);
					}
					if (tempsql == null) {
						if (sql.trim().startsWith("update")
								|| sql.trim().startsWith("UPDATE")) {
						}
						sql = sql.replace(m.group(), " 1=1 ");
					}
				}
			}

			if (paramObject != null) {

				Map<String, String> sqlParam = new HashMap<String, String>();

				boolean isOkPre = true;
				Matcher main = pp.matcher(sql);
				while (main.find()) {
					sql = regexSql(sql, main.group(), paramObject, false);
				}
			}
			sql = sql.replace("\\", "\\\\");
			sql = sql.replace("@#", " ");
			sql = sql.replace("#@", " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}

	public static String splitFromSuffix(String sqlidorbody) {
		SqlMapping sqlMap = MappingConstant.sqlMapping.get(sqlidorbody);
		String sqlbody = sqlidorbody;
		if (sqlMap != null) {
			sqlbody = sqlMap.getSqlBody();
		}
		if (sqlbody == null || sqlbody.equals("")) {
			log.error("空的sql解析");
			return "";
		}
		if (sqlbody.indexOf("FROM") != -1) {
			sqlbody = sqlbody.substring(sqlbody.indexOf("FROM"));
		} else {
			sqlbody = sqlbody.substring(sqlbody.toUpperCase().indexOf("FROM"));
		}

		return sqlbody;
	}

	public static String splitFromPrefix(String sqlidorbody) {
		SqlMapping sqlMap = MappingConstant.sqlMapping.get(sqlidorbody);
		String sqlbody = sqlidorbody;
		if (sqlMap != null) {
			sqlbody = sqlMap.getSqlBody();
		}
		if (sqlbody == null || sqlbody.equals("")) {
			log.error("空的sql解析");
			return "";
		}
		if (sqlbody.indexOf("FROM") != -1) {
			sqlbody = sqlbody.substring(0, sqlbody.indexOf("FROM"));
		} else {
			sqlbody = sqlbody.substring(0, sqlbody.toUpperCase()
					.indexOf("FROM"));
		}

		return sqlbody;
	}

	public static String parser(String sqlidorbody) {
		SqlMapping sqlMap = MappingConstant.sqlMapping.get(sqlidorbody);
		String sqlbody = sqlidorbody;
		if (sqlMap != null) {
			sqlbody = sqlMap.getSqlBody();
		}
		if (sqlbody == null || sqlbody.equals("")) {
			log.error("空的sql解析");
			return "";
		}

		String sql = parseSql(sqlbody, new HashMap());
		return sql;
	}

	public static void main(String[] args) {
		String sql = "select * from 2 select * from 344";
		System.out.println(SqlParser.splitFromPrefix(sql));
		System.out.println(SqlParser.splitFromSuffix(sql));
	}

	public static String parser(String sqlidorbody, Object paramObject) {
		SqlMapping sqlMap = MappingConstant.sqlMapping.get(sqlidorbody);
		String sqlbody = sqlidorbody;
		if (sqlMap != null) {
			sqlbody = sqlMap.getSqlBody();
		}
		if (sqlbody == null || sqlbody.equals("")) {
			log.error("空的sql解析");
			return "";
		}

		String sql = parseSql(sqlMap.getSqlBody(), paramObject);

		System.out.println("SQL:" + sql);
		return sql;
	}

	public static String parser(String sqlidorbody, Object paramObject,
			boolean isforupdate) {
		String sql = parser(sqlidorbody, paramObject);
		if (isforupdate == true && sql.length() > 0) {
			sql += " for UPDATE";
		}
		return sql;
	}

	private static String regexSql(String sql, String findStr,
			Object paramObject, boolean isAllowEmpty) {
		if (paramObject instanceof String || paramObject instanceof Long
				|| paramObject instanceof Integer) {
			sql = sql.replace(findStr, String.valueOf(paramObject));
		} else if (paramObject instanceof Map
				|| paramObject instanceof HttpParam) {
			Map<String, String> map = (Map<String, String>) paramObject;
			String valKey = findStr.replace("!#", "").trim();
			Object val = map.get(valKey);
			if (val == null || val.equals("")) {
				if (!isAllowEmpty) {
					sql = sql.replace(findStr, "");
				} else {
					return null;
				}
			} else {
				if (val.getClass().isArray()) {
					val = ((String[]) val)[0];
					if (val == null || val.equals("")) {
						if (!isAllowEmpty) {
							sql = sql.replace(findStr, "");
						} else {
							return null;
						}
					}
				}
				//update by 徐文兵  2018-04-23 解决 sql  in 字符串的问题 一个单引号，变成双引号
				sql = sql.replace(findStr,
						String.valueOf(val));
//				sql = sql.replace(findStr,
//						String.valueOf(val).replace("'", "''"));
			}
		} else {
			Object obj = ObjectUtils.executeGetMethod(paramObject, findStr
					.replace("!#", "").trim());//
			if (obj == null || obj.equals("")) {
				if (!isAllowEmpty) {
					sql = sql.replace(findStr, "");
				} else {
					return null;
				}
			} else {
				//update by 徐文兵  2018-04-23 解决 sql  in 字符串的问题 一个单引号，变成双引号
				sql = sql.replace(findStr,
						String.valueOf(obj));//
//				sql = sql.replace(findStr,
//						String.valueOf(obj).replace("'", "''"));//
			}
		}
		return sql;
	}

}
