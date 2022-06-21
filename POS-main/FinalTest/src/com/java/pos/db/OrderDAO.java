package com.java.pos.db;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO {
	int result = 0;
	int orderResult = 0;
	int o_id = 0;
	int[] detailResult = null;
	int[] orderMenuId = null;

	public ArrayList<OrderDTO> orderList() {
		ArrayList<OrderDTO> result = new ArrayList<OrderDTO>();
		String query = "SELECT o_id,payType, m_id, o_price, o_date FROM orders";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderDTO oDTO = null;

		try {
			con = Util.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				oDTO = new OrderDTO(rs.getInt("o_id"), PayType.valueOf(rs.getString("payType")),rs.getString("m_id"), rs.getLong("o_price"), null);
				result.add(oDTO);
			}
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

	public ArrayList<OrderDetailDTO> orderDetailList(String orderId) {
		ArrayList<OrderDetailDTO> result = new ArrayList<OrderDetailDTO>();
		String query = "SELECT * FROM order_detail WHERE o_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderDetailDTO odDTO = null;

		try {
			con = Util.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, orderId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				odDTO = new OrderDetailDTO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				result.add(odDTO);
			}
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

	private final String INSERT_ORDER_QUERY = "INSERT INTO orders VALUES (NULL, NOW(), ?, ?, ?)";
	private final String INSERT_ORDER_DETAIL_QUERY = "INSERT INTO order_detail VALUES (NULL, ?, ?, ?, ?)";

	public OrderDTO save(OrderDTO order) {
		// orders 테이블 삽입문
		try {
			connect();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_ORDER_QUERY, RETURN_GENERATED_KEYS);
			pstmt.setString(1, order.getM_id());
			pstmt.setLong(2, order.getO_price());
			pstmt.setString(3, order.getPayType().toString());
			result = pstmt.executeUpdate();

			// AUTO_INCREMENT 가져오기
			rs = pstmt.getGeneratedKeys();
			int orderId = 0;
			if (rs.next()) {
				orderId = rs.getInt(1);
				order.setO_id(orderId);
			}
			pstmt = con.prepareStatement(INSERT_ORDER_DETAIL_QUERY);
			List<OrderDetailDTO> orderDetails = order.getOrderDetails();
			for(OrderDetailDTO orderDetail : orderDetails) {
				orderDetail.setO_id(orderId);
				pstmt.setInt(1, orderDetail.getO_id());
				pstmt.setInt(2, orderDetail.getP_id());
				pstmt.setInt(3, orderDetail.getQuatity());
				pstmt.setInt(4, orderDetail.getPrice());
				pstmt.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			}catch (Exception e1) {}
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return order;
	}

	public OrderDTO findById(int orderId) {
		OrderDTO order = null;
		
		StringBuilder FIND_ORDER_BY_ID_QUERY = new StringBuilder();
		FIND_ORDER_BY_ID_QUERY.append("SELECT o_id, payType, o_date, m_id, o_price ");
		FIND_ORDER_BY_ID_QUERY.append("FROM orders ");
		FIND_ORDER_BY_ID_QUERY.append("WHERE o_id = ? ");
		try {
			connect();
			pstmt = con.prepareStatement(FIND_ORDER_BY_ID_QUERY.toString());
			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new OrderDTO(PayType.valueOf(rs.getString("payType")), rs.getString("m_id"), rs.getLong("o_price"), null);
				List<OrderDetailDTO> orderDetails = new ArrayList<>();
				
				StringBuilder FIND_ORDER_DETAIL_BY_ID_QUERY = new StringBuilder();
				FIND_ORDER_DETAIL_BY_ID_QUERY.append("SELECT od_id, o_id, p_id, od_quantity, p_price ");
				FIND_ORDER_DETAIL_BY_ID_QUERY.append("FROM order_detail ");
				FIND_ORDER_DETAIL_BY_ID_QUERY.append("WHERE o_id = ? ");
				
				pstmt = con.prepareStatement(FIND_ORDER_DETAIL_BY_ID_QUERY.toString());
				pstmt.setInt(1, orderId);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					orderDetails.add(new OrderDetailDTO(rs.getInt("od_id"), orderId, rs.getInt("p_id"), rs.getInt("od_quantity"), rs.getInt("p_price")));
				}
				order.setOrderDetails(orderDetails);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeAll();
		}
		return order;
	}

	private final String FIND_ALL_QUERY = "SELECT o_id, o_date, m_id, o_price, payType FROM orders ORDER BY o_date DESC";
	public List<OrderDTO> findAll() {
		List<OrderDTO> orders = new ArrayList<>();
		try {
			connect();
			pstmt = con.prepareStatement(FIND_ALL_QUERY);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				orders.add(new OrderDTO(rs.getInt("o_id"), PayType.valueOf(rs.getString("payType")), rs.getString("m_id"), rs.getInt("o_price"), null));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return orders;
	}
}