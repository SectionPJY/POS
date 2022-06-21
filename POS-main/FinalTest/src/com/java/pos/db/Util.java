package com.java.pos.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
	static String driver = "org.mariadb.jdbc.Driver";
	static String url = "jdbc:mariadb://localhost:3307/pos";
	static String uid = "root";
	static String upw = "vudrkd1004";

	public static Connection getConnection() throws Exception {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, upw);
		return con;
	}
}