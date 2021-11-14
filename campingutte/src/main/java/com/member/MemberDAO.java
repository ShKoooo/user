package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	public MemberDTO loginMember(String memberId, String memberPwd) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT memberId, memberName, memberPwd, memberRegdate, memberUpdate "
				+ " FROM member"
				+ " WHERE memberId = ? AND memberPwd = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			
			rs = pstmt.executeQuery();
			
			// System.out.println(memberId); --> null
			
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberPwd(rs.getString("memberPwd"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberRegdate(rs.getString("memberRegdate"));
				dto.setMemberUpdate(rs.getString("memberUpdate"));
				
				// test
				// System.out.println(rs.getString("memberId"));
			}
		} catch (Exception e) {
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
	public int signupMember(MemberDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO member(memberId, memberPwd, memberName, memberBirth, memberEmail, memberTel, memberAddr, memberAddr2, memberRegdate, memberUpdate) VALUES (?, ?, ?, TO_DATE(?,'YYYYMMDD'), ?, ?, ?, ?, SYSDATE, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getMemberPwd());
			pstmt.setString(3, dto.getMemberName());
			pstmt.setString(4, dto.getMemberBirth());
			pstmt.setString(5, dto.getMemberEmail());
			pstmt.setString(6, dto.getMemberTel());
			pstmt.setString(7, dto.getMemberAddr());
			pstmt.setString(8, dto.getMemberAddr2());
			
			
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}
		
		return result;
	}
	
	public int updateMember(MemberDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE member SET memberPwd=?, memberUpdate=SYSDATE MemberBirth=TO_DATE(?,'YYYYMMDD'), MemberEmail=?, MemberTel=?, MemberAddr=?, MemberAddr2=?   WHERE memberId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberPwd());
			pstmt.setString(2, dto.getMemberId());
			pstmt.setString(3, dto.getMemberBirth());
			pstmt.setString(4, dto.getMemberEmail());
			pstmt.setString(5, dto.getMemberTel());
			pstmt.setString(6, dto.getMemberAddr());
			pstmt.setString(7, dto.getMemberAddr2());
			
			result += pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	
	public MemberDTO readMember(String memberId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT m1.memberId, memberName, memberPwd,");
			sb.append("      memberRegdate, memberUpdate,");
			sb.append("      TO_CHAR(memberBirth, 'YYYY-MM-DD') memberBirth, ");
			sb.append("      memberEmail, memberTel,");
			sb.append("      memberAddr, memberAddr2");
			sb.append("  FROM member m1");
			sb.append("  WHERE m1.memberId = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberPwd(rs.getString("memberPwd"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberRegdate(rs.getString("memberRegdate"));
				dto.setMemberUpdate(rs.getString("memberUpdate"));
				dto.setMemberBirth(rs.getString("memberBirth"));
				dto.setMemberTel(rs.getString("memberTel"));
				dto.setMemberEmail(rs.getString("memberEmail"));
				dto.setMemberAddr(rs.getString("memberAddr"));
				dto.setMemberAddr2(rs.getString("memberAddr2"));
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
