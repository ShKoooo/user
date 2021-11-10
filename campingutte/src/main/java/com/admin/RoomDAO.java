package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class RoomDAO {
	private Connection conn = DBConn.getConnection();
	
	// 객실 정보 추가
	public int insertRoom(RoomDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			sql = "INSERT INTO room(roomNo, stdPers, maxPers, stdPrice, extraPrice, campNo, roomDetail, roomName)"
					+ " VALUES (?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getRoomNo());
			pstmt.setInt(2, dto.getStdPers());
			pstmt.setInt(3, dto.getMaxPers());
			pstmt.setInt(4, dto.getStdPrice());
			pstmt.setInt(5, dto.getExtraPrice());
			pstmt.setString(6, dto.getCampNo());
			pstmt.setString(7, dto.getRoomDetail());
			pstmt.setString(8, dto.getRoomName());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}
	
	// 객실 이미지 추가
	public int insertRoomImage(RoomDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			/*
			// imgName은 관리자가 지정하는게 아님.
			sql = "INSERT INTO campsiteImage(imgNum, imgName, campNo)"
					+ " VALUES (campImg_seq.NEXTVAL, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getImgName());
			pstmt.setString(2, dto.getCampNo());
			
			result = pstmt.executeUpdate();
			*/
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO roomImage(imgNum, imgName, roomNo, campNo)"
						+ " VALUES (campImg_seq.NEXTVAL, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setString(1, dto.getImageFiles()[i]);
					pstmt.setString(2, dto.getCampNo());
					
					pstmt.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 객실 정보 수정
	
	
	// 객실 이미지 수정
	
	
	// 객실 정보 삭제(이미지까지 삭제)
	
	
	// 객실 이미지만 삭제
	
	
	// 객실 글보기 (객실 상세 보기)
	
	
	// 객실 리스트
	
	
	// 객실 이미지파일 리스트
	
	
	// 객실 데이터 개수 -> 있어야될지 없어도될지 모르겠음
	
	
	
}
