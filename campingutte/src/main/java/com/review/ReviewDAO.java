package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReviewDAO {
	private Connection conn = DBConn.getConnection();
	
	// 리플 리스트 (해당 예약번호) --> 한개만 뜸.
	public List<ReviewDTO> listReview1(String bookNo) {
		List<ReviewDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT reviewNo, reviewComment, reviewDate, memberId, bookNo, reviewStar");
			sb.append("	FROM review");
			sb.append("	WHERE bookNo = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, bookNo);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				
				dto.setReviewNo(rs.getString("reviewNo"));
				dto.setReviewComment(rs.getString("reviewComment"));
				dto.setReviewDate(rs.getString("reviewDate"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setBookNo(rs.getString("bookNo"));
				dto.setReviewStar(rs.getString("reviewStar"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	// 리뷰 DB에 입력
	public int insertReview(ReviewDTO dto) {
		int result = 0;
		
		// TODO
		
		return result;
	}
	
	public int dataCountReview(String campNo) {
		int result = 0;
		
		// TODO
		
		return result;
	}
	
	// 리뷰 리스트 (해당 캠핑장)
	public List<ReviewDTO> listReview (String campNo, int start, int end) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			// TODO
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				
				dto.setReviewNo(rs.getString("reviewNo"));
				dto.setReviewComment(rs.getString("reviewComment"));
				dto.setReviewDate(rs.getString("reviewDate"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setBookNo(rs.getString("bookNo"));
				dto.setReviewStar(rs.getString("reviewStar"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	// 리뷰 삭제
	public int deleteReview(String reviewNo, String memberId) {
		int result = 0;
		
		// TODO
		
		return result;
	}
	
	// 데이터 개수 (나중에 삭제해도 될거같음)
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM review";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
}
