package com.java.pos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

abstract public class AbstractDAO {
	private String url = "jdbc:mariadb://localhost:3307/pos";
	private String uid = "root";
	private String upw = "vudrkd1004";

	protected Connection con;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	protected void connect() {
		try {
			con = DriverManager.getConnection(url, uid, upw);
		} catch (Exception e) {
		}
	}

	protected void closeAll() {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}
}
