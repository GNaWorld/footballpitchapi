/**
 *
 */
package com.common.plugins.xmlsql;

import java.io.File;
import java.io.FileFilter;

import com.common.plugins.xmlsql.sqlparse.InitMapping;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.IPlugin;

/**
 * @author Hellozhihua
 *
 */
public class SqlXmlPlugin implements IPlugin {

	public SqlXmlPlugin() {
	}

	@Override
	public boolean start() {
		File file = new File(PathKit.getRootClassPath());
		file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith("sql.xml")) {
					InitMapping.instance().mapfile(pathname.getAbsolutePath());
					// System.out.println(pathname.getAbsolutepath());
					return true;
				}
				return false;
			}
		});
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
