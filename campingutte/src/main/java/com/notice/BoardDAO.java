package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.notice.BoardDTO;
import com.util.DBConn;

public class BoardDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertBoard(BoardDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO notice(notiNo, memberId, notiSubject, notiContent, notiHitCount, notiDate) "
					+ " VALUES (noti_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getNotiSubject());
			pstmt.setString(3, dto.getNotiContent());

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
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM notice";
			pstmt = conn.prepareStatement(sql);

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
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM notice b JOIN member m ON b.memberId = m.memberId ";
			if(condition.equals("all")) {
				sql += "  WHERE INSTR(notiSubject, ?) >= 1 OR INSTR(notiContent, ?) >= 1 ";
			} else if(condition.equals("notiDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(notiDate, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
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

	// 게시물 리스트
	public List<BoardDTO> listBoard(int start, int end) {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT notiNo, b.memberId, memberName, notiSubject, notiHitCount, ");
			sb.append("               TO_CHAR(notiDate, 'YYYY-MM-DD') notiDate");
			sb.append("         FROM notice b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
			sb.append("         ORDER BY notiNo DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();

				dto.setNotiNo(rs.getInt("notiNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setNotiSubject(rs.getString("notiSubject"));
				dto.setNotiHitCount(rs.getInt("notiHitCount"));
				dto.setNotiDate(rs.getString("notiDate"));
				
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

	public List<BoardDTO> listBoard(int start, int end, String condition, String keyword) {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT notiNo, b.memberId, memberName, notiSubject, notiHitCount, ");
			sb.append("               TO_CHAR(notiDate, 'YYYY-MM-DD') notiDate ");
			sb.append("         FROM notice b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
			if (condition.equals("all")) {
				sb.append("     WHERE INSTR(notiSubject, ?) >= 1 OR INSTR(notiContent, ?) >= 1 ");
			} else if (condition.equals("notiDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("     WHERE TO_CHAR(notiDate, 'YYYYMMDD') = ?");
			} else {
				sb.append("     WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("         ORDER BY notiNo DESC ");
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

				dto.setNotiNo(rs.getInt("notiNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setNotiSubject(rs.getString("notiSubject"));
				dto.setNotiHitCount(rs.getInt("notiHitCount"));
				dto.setNotiDate(rs.getString("notiDate"));

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
	public int updateHitCount(int notiNo) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice SET notiHitCount=notiHitCount+1 WHERE notiNo=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, notiNo);
			
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
	public BoardDTO readBoard(int notiNo) {
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			// OriginalSql = SELECT notiNo, b.memberId, memberName, notiSubject, notiContent, notiDate, notiHitCount,
			// FROM notice b  JOIN member m ON b.memberId=m.memberId  WHERE b.notiNo = ? , Error Msg = ORA-00936:
			sql = "SELECT notiNo, b.memberId, memberName, notiSubject, notiContent, notiDate, notiHitCount " 
					+ " FROM notice b "
					+ " JOIN member m ON b.memberId=m.memberId "
					+ " WHERE b.notiNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, notiNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoardDTO();
				
				dto.setNotiNo(rs.getInt("notiNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setNotiSubject(rs.getString("notiSubject"));
				dto.setNotiContent(rs.getString("notiContent"));
				dto.setNotiHitCount(rs.getInt("notiHitCount"));
				dto.setNotiDate(rs.getString("notiDate"));
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
			sql = "UPDATE notice SET notiSubject=?, notiContent=? WHERE notiNo=? AND memberId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getNotiSubject());
			pstmt.setString(2, dto.getNotiContent());
			pstmt.setInt(3, dto.getNotiNo());
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
	public int deleteBoard(int notiNo, String memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (memberId.equals("admin")) {
				sql = "DELETE FROM notice WHERE notiNo=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, notiNo);
				
				result = pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM notice WHERE notiNo=? AND memberId=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, notiNo);
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
}
