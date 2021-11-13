package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO roomImage(imgNum, imgName, roomNo, campNo)"
						+ " VALUES (roomImg_seq.NEXTVAL, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				for (int i=0; i<dto.getImageFiles().length; i++) {
					pstmt.setString(1, dto.getImageFiles()[i]);
					pstmt.setString(2, dto.getRoomNo());
					pstmt.setString(3, dto.getCampNo());
					
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
	public int updateRoom(RoomDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE room SET roomNo = ?, stdPers = ?, maxPers = ?, "
					+ "stdPrice = ?, extraPrice = ?, campNo = ?, "
					+ "roomDetail = ?, roomName = ? WHERE roomNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getRoomNo());
			pstmt.setInt(2, dto.getStdPers());
			pstmt.setInt(3, dto.getMaxPers());
			pstmt.setInt(4, dto.getStdPrice());
			pstmt.setInt(5, dto.getExtraPrice());
			pstmt.setString(6, dto.getCampNo());
			pstmt.setString(7, dto.getRoomDetail());
			pstmt.setString(8, dto.getRoomName());
			pstmt.setString(9, dto.getRoomNo());
			
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
	
	// 등록한 객실 이미지 수정
	public int updateRoomImage(RoomDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			/*
			sql = "UPDATE roomImage SET imgName = ? WHERE imgNum = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getImgName());
			pstmt.setInt(2, dto.getImgNum());
			
			result = pstmt.executeUpdate();
			*/
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO roomImage(imgNum, imgName, roomNo, campNo)"
						+ " VALUES (roomImg_seq.NEXTVAL, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setString(1, dto.getImageFiles()[i]);
					pstmt.setString(2, dto.getRoomNo());
					pstmt.setString(3, dto.getCampNo());
					
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
	
	// 객실 정보 삭제(이미지까지 삭제)
	public int deleteRoom(String roomNo, String memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		// 객실을 지우게 되면 객실 이미지도 없어져야 한다.
		// 자식을 먼저 지우고 아버지를 지워야 함
		try {
			if(memberId.equals("admin")) {
				// 객실 이미지 삭제
				sql = "DELETE FROM roomImage WHERE roomNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, roomNo);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				
				// 객실 삭제
				sql = "DELETE FROM room WHERE roomNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, roomNo);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return result;
	}
	
	// 객실 이미지만 삭제
	public int deleteRoomImage(int imgNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM roomImage WHERE imgNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, imgNum);
			
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
	
	// 객실 글보기 (객실 상세 보기)
	public RoomDTO readRoom(String roomNo) {
		RoomDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT r.roomNo, stdPers, maxPers, stdPrice, extraPrice,"
					+ " r.campNo, roomDetail, roomName, imgName"
					+ " FROM room r"
					+ " LEFT OUTER JOIN roomImage i ON r.roomNo = i.roomNo"
					+ " WHERE r.roomNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomNo);
			
			rs = pstmt.executeQuery();
			
			// 또는 while
			while(rs.next()) {
				dto = new RoomDTO();
				
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setStdPers(rs.getInt("stdPers"));
				dto.setMaxPers(rs.getInt("maxPers"));
				dto.setStdPrice(rs.getInt("stdPrice"));
				dto.setExtraPrice(rs.getInt("extraPrice"));
				dto.setCampNo(rs.getString("campNo"));
				dto.setRoomDetail(rs.getString("roomDetail"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setImgName(rs.getString("imgName"));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}
	
	// 객실 리스트(될지 의문)
	public List<RoomDTO> listRoom(int start, int end) {
		List<RoomDTO> list = new ArrayList<RoomDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT r.campNo, campName, roomNo, roomName, stdPers, stdPrice ");
			sb.append("         FROM room r ");
			sb.append("         LEFT OUTER JOIN campSite s ON r.campNo = s.campNo ");
			sb.append("         ORDER BY roomNo DESC "); // roomNo가 int형이 아닌데 어쩌지
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomDTO dto = new RoomDTO();
				
				dto.setCampNo(rs.getString("campNo"));
				dto.setCampName(rs.getString("campName"));
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setStdPers(rs.getInt("stdPers"));
				dto.setStdPrice(rs.getInt("stdPrice"));

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
	
	// 객실 이미지파일 리스트
	public List<RoomDTO> listRoomImgFile(String roomNo) {
		List<RoomDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT imgNum, imgName, roomNo, campNo FROM roomImage WHERE roomNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RoomDTO dto = new RoomDTO();
				
				dto.setImgNum(rs.getInt("imgNum"));
				dto.setImgName(rs.getString("imgName"));
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setCampNo(rs.getString("campNo"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
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
		
		return list;
	}
	
	// 객실 데이터 개수 -> 있어야될지 없어도될지 모르겠음
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM room";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	
}
