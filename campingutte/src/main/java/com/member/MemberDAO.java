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
			
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberPwd(rs.getString("memberPwd"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberRegdate(rs.getString("memberRegdate"));
				dto.setMemberUpdate(rs.getString("memberUpdate"));
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
}
