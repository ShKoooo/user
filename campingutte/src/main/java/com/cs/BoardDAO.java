package com.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// import com.cs.ReplyDTO;
import com.util.DBConn;

public class BoardDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertBoard(BoardDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO SERVCENTER(compNo, memberId, compSubject, compContent, compHitCount, compDate) "
					+ " VALUES (serv_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getCompSubject());
			pstmt.setString(3, dto.getCompContent());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}

		return result;
	}
	
	// 데이터 개수
	public int dataCount(String memberId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM SERVCENTER";
			if (!memberId.equalsIgnoreCase("admin")) {
				sql += " WHERE memberId = ?";
			}
			pstmt = conn.prepareStatement(sql);
			if (!memberId.equalsIgnoreCase("admin")) {
				pstmt.setString(1, memberId);
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	// 검색에서의 데이터 개수
	public int dataCount(String memberId, String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM SERVCENTER b JOIN member m ON b.memberId = m.memberId ";
			if(condition.equals("all")) {
				sql += "  WHERE INSTR(compSubject, ?) >= 1 OR INSTR(compContent, ?) >= 1 ";
			} else if(condition.equals("compDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(compDate, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1";
			}
			if (!memberId.equalsIgnoreCase("admin")) {
				sql += " AND b.memberId = ?";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
				if (!memberId.equalsIgnoreCase("admin")) {
					pstmt.setString(3, memberId);
				}
			} else {
				if (!memberId.equalsIgnoreCase("admin")) {
					pstmt.setString(2, memberId);
				}
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	
	// CS의 게시물 보기는 관리자랑 ID가 일치하는 본인만 볼 수 있게 해야한다.
	// 1:1 고객센터에 공지사항을 띄우자!
	
	// 게시물 리스트
	public List<BoardDTO> listBoard(String memberId, int start, int end) {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
/*
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT compNo, b.memberId, memberName, compSubject, compHitCount, ");
			sb.append("               TO_CHAR(compDate, 'YYYY-MM-DD') compDate");
			sb.append("         FROM SERVCENTER b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
			sb.append("         ORDER BY compNo DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
*/

			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT compNo, b.memberId, memberName, compSubject, compHitCount, ");
			sb.append("               TO_CHAR(compDate, 'YYYY-MM-DD') compDate");
			sb.append("         FROM SERVCENTER b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
				if (!memberId.equalsIgnoreCase("admin")) {
					sb.append(" and m.memberId = '"+memberId+"'");
				}			
			sb.append("         ORDER BY compNo DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();

				dto.setCompNo(rs.getInt("compNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCompSubject(rs.getString("compSubject"));
				dto.setCompHitCount(rs.getInt("compHitCount"));
				dto.setCompDate(rs.getString("compDate"));
				
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}

	public List<BoardDTO> listBoard(String memberId,int start, int end, String condition, String keyword) {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT compNo, b.memberId, memberName, compSubject, compHitCount, ");
			sb.append("               TO_CHAR(compDate, 'YYYY-MM-DD') compDate ");
			sb.append("         FROM SERVCENTER b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
			if (condition.equals("all")) {
				sb.append("     WHERE INSTR(compSubject, ?) >= 1 OR INSTR(compContent, ?) >= 1 ");
			} else if (condition.equals("compDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("     WHERE TO_CHAR(compDate, 'YYYYMMDD') = ?");
			} else {
				sb.append("     WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			/*
			sb.append(" WHERE memberId = ?");
			{
				if( id == admin && id equals("admin")) {
					
				}
			}
			 */
			
			if (!memberId.equalsIgnoreCase("admin")) {
				sb.append(" and memberId = "+memberId);
			}
			
			sb.append("         ORDER BY compNo DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();

				dto.setCompNo(rs.getInt("compNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCompSubject(rs.getString("compSubject"));
				dto.setCompHitCount(rs.getInt("compHitCount"));
				dto.setCompDate(rs.getString("compDate"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	// 조회수 증가하기
	public int updateHitCount(int compNo) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE SERVCENTER SET compHitCount=compHitCount+1 WHERE compNo=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, compNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return result;
	}


	// 해당 게시물 보기
	public BoardDTO readBoard(int compNo) {
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT compNo, b.memberId, memberName, compSubject, compContent, compDate, compHitCount " 
					+ " FROM SERVCENTER b "
					+ " JOIN member m ON b.memberId=m.memberId "
					+ " WHERE b.compNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, compNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoardDTO();
				
				dto.setCompNo(rs.getInt("compNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCompSubject(rs.getString("compSubject"));
				dto.setCompContent(rs.getString("compContent"));
				dto.setCompHitCount(rs.getInt("compHitCount"));
				dto.setCompDate(rs.getString("compDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return dto;
	}

	// 게시물 수정
	public int updateBoard(BoardDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE SERVCENTER SET compSubject=?, compContent=? WHERE compNo=? AND memberId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCompSubject());
			pstmt.setString(2, dto.getCompContent());
			pstmt.setInt(3, dto.getCompNo());
			pstmt.setString(4, dto.getMemberId());
			
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}

	// 게시물 삭제
	public int deleteBoard(int compNo, String memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (memberId.equals("admin")) {
				sql = "DELETE FROM SERVCENTER WHERE compNo=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, compNo);
				
				result = pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM SERVCENTER WHERE compNo=? AND memberId=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, compNo);
				pstmt.setString(2, memberId);
				
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public int insertReply(ReplyDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO servReply(compNo, memberId, compReplyContent, compReplyDate) "
					+ " VALUES (?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getCompNo());			
			pstmt.setString(2, dto.getMemberId());
			pstmt.setString(3, dto.getCompReplyContent());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public ReplyDTO readReply(int compNo) {
		ReplyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT r.memberId, memberName, compNo, compReplyContent, r.compReplyDate ");
			sb.append(" FROM servReply r ");
			sb.append(" JOIN member m ON r.memberId = m.memberId ");
			sb.append("	WHERE compNo = ?");			

		
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, compNo);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ReplyDTO();
				
				dto.setCompNo(rs.getInt("CompNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCompReplyContent(rs.getString("compReplyContent"));
				dto.setCompReplyDate(rs.getString("compReplyDate"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}

}
