package com.java.pos.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//pstmt.setTimestamp(4, valueOf(mDTO.getRegistryDate()));	// 날짜 가져오는 부분
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AccountDAO {
	// 회원 추가 메서드
	public int memberRegister(String id, String pw, String name, String position) throws Exception {
		String query = "INSERT INTO accounts VALUE(?, ?, ?, ?, NOW(), NOW());";
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			con = Util.getConnection();

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, position);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e2) {
					}
				}
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}

		return result;
	}

	public AccountDTO memberLogin(String id, String pw) {
		String query1 = "SELECT * FROM accounts WHERE m_id=? AND m_pw=?";
		String query2 = "UPDATE accounts SET access_date=NOW() WHERE m_id=?";
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		AccountDTO mDTO = null;

		try {
			con = Util.getConnection();

			// 로그인을 위한 SELECT문 처리
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, id);
			pstmt1.setString(2, pw);
			rs = pstmt1.executeQuery();

			if (rs.next()) { // id와 pw가 일치 -> 로그인 성공
				// 최종접속일 UPDATE
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, id);
				pstmt2.executeUpdate();

				mDTO = new AccountDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				return mDTO;
			} else {
				mDTO = null;
				return mDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				if (pstmt1 != null) {
					try {
						pstmt1.close();
					} catch (Exception e2) {
					}
				}
				if (pstmt2 != null) {
					try {
						pstmt2.close();
					} catch (Exception e2) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e2) {
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return mDTO;
	}

	public int memberUpdate(String id, String modifyId, String pw, String name) {
		String query = "UPDATE accounts SET m_id=?, m_pw=?, m_name=? WHERE m_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // 쿼리문 결과값 받는 변수

		try {
			con = Util.getConnection();

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, modifyId);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public int memberDelete(String id) {
		String query = "DELETE FROM accounts WHERE m_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // 쿼리문 결과값 받는 변수

		try {
			con = Util.getConnection();

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			try {
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
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

	public boolean checkId(String id) {
		String query = "SELECT * FROM accounts WHERE m_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			con = Util.getConnection();

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.", "회원등록", JOptionPane.ERROR_MESSAGE);
				result = false;
			} else {
				result = true;
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

	public boolean checkPw(String pw, String pwCheck) {
		Boolean result = false;
		return result;
	}

	public ArrayList<AccountDTO> memberList() {
		ArrayList<AccountDTO> mDTOs = new ArrayList<AccountDTO>();
		String query = "SELECT m_id, m_name, registry_date, access_date FROM accounts WHERE m_position='직원'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountDTO mDTO = null;

		try {
			con = Util.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mDTO = new AccountDTO(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(),
						rs.getTimestamp(4).toLocalDateTime());
				mDTOs.add(mDTO);
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
		return mDTOs;
	}
}