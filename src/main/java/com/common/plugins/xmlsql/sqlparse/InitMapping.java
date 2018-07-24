/**
 * 
 */
package com.common.plugins.xmlsql.sqlparse;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author willian
 * 
 */
public class InitMapping {

	private static InitMapping ka = null;

	private InitMapping() {
	}

	public static InitMapping instance() {
		if (ka == null) {
			ka = new InitMapping();
		}
		return ka;
	}

	/**
	 * @param xmlFile
	 */
	public void xml2obj(String xmlFile) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			File f = new File(xmlFile);
			document = reader.read(f);
			Element root = document.getRootElement();
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				Element element = i.next();
				SqlMapping sqlMap = new SqlMapping();
				if (element.getName() != null) {
					if (element.getName().equalsIgnoreCase("select")
							|| element.getName().equalsIgnoreCase("update")
							|| element.getName().equalsIgnoreCase("insert")
							|| element.getName().equalsIgnoreCase("delete")) {
						sqlMap.setSqlBody(element.getTextTrim());
						String id = element.attributeValue("id");
						String type = element.attributeValue("resultType");
						sqlMap.setId(id);
						sqlMap.setSqlType(element.getName());
						sqlMap.setResultType(type);
						MappingConstant.sqlMapping.put(sqlMap.getId(), sqlMap);
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void mapfile(String mapfile) {
		xml2obj(mapfile);
	}

	public void map(String map) {
		String files[] = map.split(",");
		for (String jsonOrXmlFile : files) {
			StringBuffer sb = new StringBuffer();
			if (jsonOrXmlFile.endsWith(".xml")) {
				xml2obj(jsonOrXmlFile);
			}
		}
	}
}
