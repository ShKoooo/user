package com.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BoardDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public int insertBoard(BoardDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO community(commNo, memberId, commSubject, commContent, commHitCount, commDate) "
					+ " VALUES (comm_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getCommSubject());
			pstmt.setString(3, dto.getCommContent());

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
			sql = "SELECT COUNT(*) FROM servCenter";
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
			sql = "SELECT COUNT(*) FROM servCenter b JOIN member m ON b.memberId = m.memberId ";
			if(condition.equals("all")) {
				sql += "  WHERE INSTR(commSubject, ?) >= 1 OR INSTR(commContent, ?) >= 1 ";
			} else if(condition.equals("commDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(commDate, 'YYYYMMDD') = ? ";
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
			sb.append("         SELECT commNo, b.memberId, memberName, commSubject, commHitCount, ");
			sb.append("               TO_CHAR(commDate, 'YYYY-MM-DD') commDate");
			sb.append("         FROM servCenter b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
			sb.append("         ORDER BY commNo DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();

				dto.setCommNo(rs.getInt("commNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCommSubject(rs.getString("commSubject"));
				dto.setCommHitCount(rs.getInt("commHitCount"));
				dto.setCommDate(rs.getString("commDate"));
				
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
			sb.append("         SELECT commNo, b.memberId, memberName, commSubject, commHitCount, ");
			sb.append("               TO_CHAR(commDate, 'YYYY-MM-DD') commDate ");
			sb.append("         FROM servCenter b ");
			sb.append("         JOIN member m ON b.memberId = m.memberId ");
			if (condition.equals("all")) {
				sb.append("     WHERE INSTR(commSubject, ?) >= 1 OR INSTR(commContent, ?) >= 1 ");
			} else if (condition.equals("commDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("     WHERE TO_CHAR(commDate, 'YYYYMMDD') = ?");
			} else {
				sb.append("     WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("         ORDER BY commNo DESC ");
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

				dto.setCommNo(rs.getInt("commNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCommSubject(rs.getString("commSubject"));
				dto.setCommHitCount(rs.getInt("commHitCount"));
				dto.setCommDate(rs.getString("commDate"));

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
	public int updateHitCount(int commNo) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE community SET commHitCount=commHitCount+1 WHERE commNo=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commNo);
			
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
	public BoardDTO readBoard(int commNo) {
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			// OriginalSql = SELECT commNo, b.memberId, memberName, commSubject, commContent, commDate, commHitCount,
			// FROM servCenter b  JOIN member m ON b.memberId=m.memberId  WHERE b.commNo = ? , Error Msg = ORA-00936:
			sql = "SELECT commNo, b.memberId, memberName, commSubject, commContent, commDate, commHitCount " 
					+ " FROM servCenter b "
					+ " JOIN member m ON b.memberId=m.memberId "
					+ " WHERE b.commNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoardDTO();
				
				dto.setCommNo(rs.getInt("commNo"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setCommSubject(rs.getString("commSubject"));
				dto.setCommContent(rs.getString("commContent"));
				dto.setCommHitCount(rs.getInt("commHitCount"));
				dto.setCommDate(rs.getString("commDate"));
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
			sql = "UPDATE community SET commSubject=?, commContent=? WHERE commNo=? AND memberId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCommSubject());
			pstmt.setString(2, dto.getCommContent());
			pstmt.setInt(3, dto.getCommNo());
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
	public int deleteBoard(int commNo, String memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (memberId.equals("admin")) {
				sql = "DELETE FROM servCenter WHERE commNo=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, commNo);
				
				result = pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM servCenter WHERE commNo=? AND memberId=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, commNo);
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
