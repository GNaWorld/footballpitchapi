/**
 * 
 */
package com.model.base;

import java.util.List;

import com.common.plugins.xmlsql.sqlparse.SqlParam;
import com.common.plugins.xmlsql.sqlparse.SqlParser;
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author Ô¬Ö¾»ª 283117523@qq.com
 *
 */
public class BaseModel<M extends BaseModel<M>> extends Model<M> implements IBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4702517549533289968L;

	public M findFirstEx(String sqlid, SqlParam sqlparam) {

		return findFirst(SqlParser.parser(sqlid, sqlparam));
	}

	public List<M> findEx(String sqlid, SqlParam sqlparam) {

		return find(SqlParser.parser(sqlid, sqlparam));
	}

	public List<M> findEx(String sqlid) {

		return find(SqlParser.parser(sqlid));
	}

	public Page<M> paginateEx(int pageNumber, int pageSize, String sqlid, SqlParam sqlparam) {
		String sql = SqlParser.parser(sqlid, sqlparam);
		System.out.println(SqlParser.splitFromPrefix(sql) + ":" + SqlParser.splitFromSuffix(sql));
		return paginate(pageNumber, pageSize, SqlParser.splitFromPrefix(sql), SqlParser.splitFromSuffix(sql));
	}
}
