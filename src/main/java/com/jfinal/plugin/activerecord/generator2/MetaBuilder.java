/**
 * Copyright (c) 2011-2017, James Zhan ղ�� (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.plugin.activerecord.generator2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.sql.DataSource;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;

/**
 * MetaBuilder
 */
public class MetaBuilder {

    protected DataSource dataSource;
    protected Dialect dialect = new MysqlDialect();
    protected Set<String> excludedTables = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);

    protected Connection conn = null;
    protected DatabaseMetaData dbMeta = null;

    protected String[] removedTableNamePrefixes = null;

    protected TypeMapping typeMapping = new TypeMapping();

    public MetaBuilder(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource can not be null.");
        }
        this.dataSource = dataSource;
    }

    public void setDialect(Dialect dialect) {
        if (dialect != null) {
            this.dialect = dialect;
        }
    }

    public void addExcludedTable(String... excludedTables) {
        if (excludedTables != null) {
            for (String table : excludedTables) {
                this.excludedTables.add(table);
            }
        }
    }

    /**
     * ������Ҫ���Ƴ��ı���ǰ׺������������ modelName ��  baseModelName
     * �������  "osc_account"���Ƴ�ǰ׺ "osc_" ���Ϊ "account"
     */
    public void setRemovedTableNamePrefixes(String... removedTableNamePrefixes) {
        this.removedTableNamePrefixes = removedTableNamePrefixes;
    }

    public void setTypeMapping(TypeMapping typeMapping) {
        if (typeMapping != null) {
            this.typeMapping = typeMapping;
        }
    }

    public List<TableMeta> build() {
        System.out.println("Build TableMeta ...");
        try {
            conn = dataSource.getConnection();
            dbMeta = conn.getMetaData();

            List<TableMeta> ret = new ArrayList<TableMeta>();
            buildTableNames(ret);
            for (TableMeta tableMeta : ret) {
                buildPrimaryKey(tableMeta);
                buildColumnMetas(tableMeta);
            }
            return ret;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * ͨ���̳в����Ǵ˷���������һЩ��ϣ������� table�����Ƹ������� table ���˹���
     * @return ���� true ʱ��������ǰ tableName �Ĵ���
     */
    protected boolean isSkipTable(String tableName) {
        return false;
    }

    /**
     * ���� modelName��mysql �� tableName ����ʹ��Сд��ĸ���൥�ʱ���ʹ���»��߷ָ���������ʹ���շ�����
     * oracle ֮�µ� tableName ����ʹ���»��߷ָ��൥���������� mysql���� oralce��tableName ��������ʹ���շ�����
     */
    protected String buildModelName(String tableName) {
        // �Ƴ�����ǰ׺���������� modelName��baseModelName���� tableMeta.name ������������Ӱ��
        if (removedTableNamePrefixes != null) {
            for (String prefix : removedTableNamePrefixes) {
                if (tableName.startsWith(prefix)) {
                    tableName = tableName.replaceFirst(prefix, "");
                    break;
                }
            }
        }

        // �� oralce ��д�� tableName ת��Сд�������� modelName
        if (dialect instanceof OracleDialect) {
            tableName = tableName.toLowerCase();
        }

        return StrKit.firstCharToUpperCase(StrKit.toCamelCase(tableName));
    }

    /**
     * ʹ�� modelName ���� baseModelName
     */
    protected String buildBaseModelName(String modelName) {
        return "Base" + modelName;
    }

    /**
     * ��ͬ���ݿ� dbMeta.getTables(...) �� schemaPattern �������岻ͬ
     * 1��oracle ���ݿ������������ dbMeta.getUserName()
     * 2��postgresql ���ݿ�����Ҫ�� jdbcUrl������ schemaPatter�����磺
     *   jdbc:postgresql://localhost:15432/djpt?currentSchema=public,sys,app
     *   ���Ĳ�����������schema��˳��DruidPlugin �²��Գɹ�
     * 3�������������������з��ֹ�������������ͨ���̳� MetaBuilder�����Ǵ˷�����ʵ�ֹ���
     */
    protected ResultSet getTablesResultSet() throws SQLException {
        String schemaPattern = dialect instanceof OracleDialect ? dbMeta.getUserName() : null;
        // return dbMeta.getTables(conn.getCatalog(), schemaPattern, null, new String[]{"TABLE", "VIEW"});
        return dbMeta.getTables(conn.getCatalog(), schemaPattern, null, new String[]{"TABLE"});    // ��֧�� view ����
    }

    protected void buildTableNames(List<TableMeta> ret) throws SQLException {
        ResultSet rs = getTablesResultSet();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");

            if (excludedTables.contains(tableName)) {
                System.out.println("Skip table :" + tableName);
                continue;
            }
            if (isSkipTable(tableName)) {
                System.out.println("Skip table :" + tableName);
                continue;
            }

            TableMeta tableMeta = new TableMeta();
            tableMeta.name = tableName;
            tableMeta.remarks = rs.getString("REMARKS");

            tableMeta.modelName = buildModelName(tableName);
            tableMeta.baseModelName = buildBaseModelName(tableMeta.modelName);
            ret.add(tableMeta);
        }
        rs.close();
    }

    protected void buildPrimaryKey(TableMeta tableMeta) throws SQLException {
        ResultSet rs = dbMeta.getPrimaryKeys(conn.getCatalog(), null, tableMeta.name);

        String primaryKey = "";
        int index = 0;
        while (rs.next()) {
            if (index++ > 0) {
                primaryKey += ",";
            }
            primaryKey += rs.getString("COLUMN_NAME");
        }
        if (StrKit.isBlank(primaryKey)) {
            throw new RuntimeException("primaryKey of table \"" + tableMeta.name + "\" required by active record pattern");
        }
        tableMeta.primaryKey = primaryKey;
        rs.close();
    }

    /**
     * �ĵ��ο���
     * http://dev.mysql.com/doc/connector-j/en/connector-j-reference-type-conversions.html
     *
     * JDBC ��ʱ���й�����ת������mysql ���͵� java �������¶�Ӧ��ϵ��
     * DATE				java.sql.Date
     * DATETIME			java.sql.Timestamp
     * TIMESTAMP[(M)]	java.sql.Timestamp
     * TIME				java.sql.Time
     *
     * �����ݿ�� DATE��DATETIME��TIMESTAMP��TIME ��������ע�� new java.util.Date()���󱣴浽���Ժ���Դﵽ���뾫�ȡ�
     * Ϊ�˱���ԣ�getter��setter �����ж����������ֶ����Ͳ��� java.util.Date����ͨ������ TypeMapping �ı��ӳ�����
     */
    protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
        String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            ColumnMeta cm = new ColumnMeta();
            cm.name = rsmd.getColumnName(i);

            String typeStr = null;
            if (dialect.isKeepByteAndShort()) {
                int type = rsmd.getColumnType(i);
                if (type == Types.TINYINT) {
                    typeStr = "java.lang.Byte";
                } else if (type == Types.SMALLINT) {
                    typeStr = "java.lang.Short";
                }
            }

            if (typeStr == null) {
                String colClassName = rsmd.getColumnClassName(i);
                typeStr = typeMapping.getType(colClassName);
            }

            if (typeStr == null) {
                int type = rsmd.getColumnType(i);
                if (type == Types.BINARY || type == Types.VARBINARY || type == Types.LONGVARBINARY || type == Types.BLOB) {
                    typeStr = "byte[]";
                } else if (type == Types.CLOB || type == Types.NCLOB) {
                    typeStr = "java.lang.String";
                }
                // ֧�� oracle �� TIMESTAMP��DATE �ֶ����ͣ����� Types.DATE ֵ���������
                // ������ Types.DATE ���жϣ�һ��Ϊ���߼��ϵ���ȷ�ԡ��걸�ԣ������������͵����ݿ�����õ���
                else if (type == Types.TIMESTAMP || type == Types.DATE) {
                    typeStr = "java.util.Date";
                } else {
                    typeStr = "java.lang.String";
                }
            }
            cm.javaType = typeStr;

            // �����ֶζ�Ӧ�������� attrName
            cm.attrName = buildAttrName(cm.name);

            tableMeta.columnMetas.add(cm);
        }

        rs.close();
        stm.close();
    }

    /**
     * ���� colName ����Ӧ�� attrName��mysql ���ݿ⽨��ʹ��Сд�ֶ��������շ��ֶ���
     * Oralce ���佫�õ���д�ֶ��������Բ�����ʹ���շ�����������ʹ���»��߷ָ�����������
     */
    protected String buildAttrName(String colName) {
        if (dialect instanceof OracleDialect) {
            colName = colName.toLowerCase();
        }
        return StrKit.toCamelCase(colName);
    }
}







