package com.gn.kit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IAWMysqlKit {
	//设置手动提交事务
	// mysqlConn.setAutoCommit(false);
	private static final String Class_Name = "com.mysql.jdbc.Driver";

	public static Connection createConnection(String dburl, String username, String password) throws Exception {
		Class.forName(Class_Name);
		return DriverManager.getConnection(dburl, username, password);

	}

	public static PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setQueryTimeout(30); // set timeout to 30 sec.
		return ps;
	}

	/**
	 * CRUD中R的操�?
	 * 
	 * @param ps
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet func_R(PreparedStatement ps) throws SQLException {
		return ps.executeQuery();
	}

	/**
	 * CRUD中CUD的操�?
	 * 
	 * @param ps
	 * @return
	 * @throws SQLException
	 */
	public static int func_CUD(PreparedStatement ps) throws SQLException {
		return ps.executeUpdate();
	}

	// 关闭connection
	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭数据库发生错�?:");
			System.out.println(e);
		}
	}
}
