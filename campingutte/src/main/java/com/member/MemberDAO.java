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
			
			sql = "INSERT ALL "
		    	+ " INTO member1(memberId, memberPwd, memberName, enabled, register_date, modify_date) VALUES(?, ?, ?, 1, SYSDATE, SYSDATE) "
		    	+ " INTO member2(memberId, memberBirth, memberEmail, memberTel, memberAddr, memberAddr2) VALUES (?, TO_DATE(?,'YYYYMMDD'), ?, ?, ?, ?) "
		    	+ " SELECT * FROM dual";
		    
		    pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getMemberPwd());
			pstmt.setString(3, dto.getMemberName());            
			pstmt.setString(4, dto.getMemberId());
			pstmt.setString(5, dto.getMemberBirth());
			pstmt.setString(6, dto.getMemberEmail());
			pstmt.setString(7, dto.getMemberTel());
			pstmt.setString(8, dto.getMemberAddr());
			pstmt.setString(9, dto.getMemberAddr2());
			
			result = pstmt.executeUpdate();
			conn.commit();

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
	
}
