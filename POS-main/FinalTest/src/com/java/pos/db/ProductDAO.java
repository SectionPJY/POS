package com.java.pos.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends AbstractDAO {
	
	final String FIND_ALL_QUERY = "SELECT p_id, p_name, p_price FROM products";
	public List<ProductDTO> findAll() {
		List<ProductDTO> products = new ArrayList<>();
		try {
			connect();
			pstmt = con.prepareStatement(FIND_ALL_QUERY);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				products.add(new ProductDTO(rs.getInt("p_id"),rs.getString("p_name"),rs.getInt("p_price") ));
			}
		} catch (SQLException e) {
		} finally {
			closeAll();
		}
		return products;
	}
	
	public String[] getProductsName() {
		String[] result = new String[9];
		String query = "SELECT p_name FROM products";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = Util.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			for (int i = 0; rs.next(); i++)
				result[i] = rs.getString("p_name");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}
	
	public int[] getProductPrice() {
		int[] result = new int[9];
		String query = "SELECT p_price FROM products";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			for(int i = 0; rs.next();i++)
				result[i] = rs.getInt("p_price");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
	}
	public String getProductDetail(int id) {
		String result = null;
		String query = "SELECT p_name FROM products WHERE p_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next())
				result = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}


}