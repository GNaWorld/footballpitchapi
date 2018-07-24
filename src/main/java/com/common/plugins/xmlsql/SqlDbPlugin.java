/**
 * 
 */
package com.common.plugins.xmlsql;

import java.util.List;

import com.common.plugins.xmlsql.sqlparse.MappingConstant;
import com.common.plugins.xmlsql.sqlparse.SqlMapping;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.SqlPara;
import com.model.SysSql;

/**
 * @author 袁志华 283117523@qq.com
 *
 */
public class SqlDbPlugin implements IPlugin {

	public SqlDbPlugin() {
	}

	@Override
	public boolean start() {
		List<SysSql> sqls = SysSql.dao.find("select * from sys_sql where sql_sta = 0 ");
		for (SysSql sql : sqls) {
			SqlMapping sqlMap = new SqlMapping();
			sqlMap.setSqlBody(sql.getSqlText());
			sqlMap.setId(sql.getSqlId());
			sqlMap.setSqlType(sql.getSqlType());
			// sqlMap.setResultType(type);
			MappingConstant.sqlMapping.put(sqlMap.getId(), sqlMap);
		}
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
