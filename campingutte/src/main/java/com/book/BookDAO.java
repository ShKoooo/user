package com.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BookDAO {
	private Connection conn = DBConn.getConnection();
	
	// 캠핑장 개수 1
	public int campCount() {
		int result = 0; 
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM campSite";
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
	
	// 캠핑장 개수 2 - 검색조건 추가..
	/*
	 검색조건
	 	(1) 숙박 일정 (시작일, 종료일 중복 X)
	 	(2) 숙박 장소 (주소)
	 	(3) 숙박 인원
	 	(4) 캠핑장 이름
	 	String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordAddr1,keywordPeople,keywordCampName};
	 */
	public int campCount(String[] keyword) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		if (keyword[0].equals("") || keyword[1].equals("")) { // 날짜미입력시 리턴 0
			return result;
		}
		
		keyword[0] = keyword[0].replaceAll("(\\-|\\/|\\.)", ""); // start date
		keyword[1] = keyword[1].replaceAll("(\\-|\\/|\\.)", ""); // end date
		
		try {
			// (1) 예약일자와 중복이 아닌 roomNo 리스트를 찾는다.
			// (2) roomNo 를 갖는 캠핑장 (campSiteNo) 리스트를 찾는다.
			// (3) 캠핑장 숫자를 센다.
			// 기타 중간중간.. (if문~조건)
			sb.append("SELECT NVL(COUNT(*),0) cnt FROM campSite");
			sb.append("	WHERE campNo IN (");
			sb.append("		SELECT DISTINCT campNo FROM ROOM");
			sb.append("		WHERE roomNo IN (");
			sb.append("			SELECT roomNo FROM room ");
			sb.append("			WHERE roomNo NOT IN (");
			sb.append("				SELECT distinct roomNo");
			sb.append("				FROM book");
			sb.append("				WHERE bookSrtdate <= TO_DATE(?,'YYYYMMDD')");
			sb.append("				AND");
			sb.append("				bookEnddate >= TO_DATE(?,'YYYYMMDD')");
			sb.append("			)");
			if (!keyword[3].equals("")) {
				sb.append("			AND maxPers >= '");
				sb.append(keyword[3]);
				sb.append("'");
			}
			sb.append("		)");
			sb.append("	)");
			if (!keyword[2].equals("")) {
				sb.append("	AND INSTR(campAddr1,'");
				sb.append(keyword[2]);
				sb.append("') > 0");
			}
			if (!keyword[4].equals("")) {
				sb.append("	AND INSTR(campName,'");
				sb.append(keyword[4]);
				sb.append("') > 0");
			}
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword[1]);
			pstmt.setString(2, keyword[0]);
			
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
	
	// 캠핑장 리스트
	public List<CampSiteDTO> listCamp(int start, int end) {
		List<CampSiteDTO> list = new ArrayList<CampSiteDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT * FROM ( "
				+ "	SELECT ROWNUM rnum, tb.* FROM ( "
				+ " SELECT campNo, campName, campAddr1, campAddr2, campTel, campDetail, typeName"
				+ " FROM campSite s, campType t"
				+ " WHERE s.typeNo = t.typeNo"
				+ " ) tb WHERE ROWNUM <="+end
				+ " ) WHERE rnum >="+start;
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CampSiteDTO dto = new CampSiteDTO();
				
				dto.setCampNo(rs.getString("campNo"));
				dto.setCampName(rs.getString("campName"));
				dto.setCampAddr1(rs.getString("campAddr1"));
				dto.setCampAddr2(rs.getString("campAddr2"));
				dto.setCampTel(rs.getString("campTel"));
				dto.setCampDetail(rs.getString("campDetail"));
				dto.setTypeName(rs.getString("typeName"));
				
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
	
	// 캠핑장 리스트 2 (검색조건추가..)
	/*
	 검색조건..
	 String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordAddr1,keywordPeople,keywordCampName};
	 */
	public List<CampSiteDTO> listCamp(int start, int end, String[] keyword) {
		List<CampSiteDTO> list = new ArrayList<CampSiteDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		if (keyword[0].equals("") || keyword[1].equals("")) { // 날짜미입력시 리턴 널
			return list;
		}
		
		keyword[0] = keyword[0].replaceAll("(\\-|\\/|\\.)", ""); // start date
		keyword[1] = keyword[1].replaceAll("(\\-|\\/|\\.)", ""); // end date
		
		try {
			sb.append(" SELECT * FROM (");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM(");
			sb.append("	SELECT campNo, campName, campAddr1, campAddr2, campTel, campDetail, typeName ");
			sb.append("	FROM campSite s, campType t");
			sb.append("	WHERE campNo IN (");
			sb.append("		SELECT DISTINCT campNo FROM ROOM");
			sb.append("		WHERE roomNo IN (");
			sb.append("			SELECT roomNo FROM room ");
			sb.append("			WHERE roomNo NOT IN (");
			sb.append("				SELECT distinct roomNo");
			sb.append("				FROM book");
			sb.append("				WHERE bookSrtdate <= TO_DATE(?,'YYYYMMDD')");
			sb.append("				AND");
			sb.append("				bookEnddate >= TO_DATE(?,'YYYYMMDD')");
			sb.append("			)");
			if (!keyword[3].equals("")) {
				sb.append("			AND maxPers >= '");
				sb.append(keyword[3]);
				sb.append("'");
			}
			sb.append("		)");
			sb.append("	)");
			if (!keyword[2].equals("")) {
				sb.append("	AND INSTR(campAddr1,'");
				sb.append(keyword[2]);
				sb.append("') > 0");
			}
			if (!keyword[4].equals("")) {
				sb.append("	AND INSTR(campName,'");
				sb.append(keyword[4]);
				sb.append("') > 0");
			}
			sb.append("	AND s.typeNo = t.typeNo");
			sb.append("	) tb WHERE ROWNUM <="+end); // start,end
			sb.append("	) WHERE rnum >= "+start);
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword[1]);
			pstmt.setString(2, keyword[0]);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CampSiteDTO dto = new CampSiteDTO();
				
				dto.setCampNo(rs.getString("campNo"));
				dto.setCampName(rs.getString("campName"));
				dto.setCampAddr1(rs.getString("campAddr1"));
				dto.setCampAddr2(rs.getString("campAddr2"));
				dto.setCampTel(rs.getString("campTel"));
				dto.setCampDetail(rs.getString("campDetail"));
				dto.setTypeName(rs.getString("campTypeName"));
				
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
	
	// 게시물 보기 (캠핑장)
	public CampSiteDTO readCamp (String campNo) {
		CampSiteDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT campNo, campName, campAddr1, campAddr2, campTel, campDetail, typeName"
				+ "	FROM campSite s"
				+ " JOIN campType t"
				+ " ON s.typeNo = t.typeNo"
				+ " WHERE campNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, campNo);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new CampSiteDTO();
				
				dto.setCampNo(rs.getString("campNo"));
				dto.setCampName(rs.getString("campName"));
				dto.setCampAddr1(rs.getString("campAddr1"));
				dto.setCampAddr2(rs.getString("campAddr2"));
				dto.setCampTel(rs.getString("campTel"));
				dto.setCampDetail(rs.getString("campDetail"));
				dto.setTypeName(rs.getString("typeName"));
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
		
		return dto;
	}
	
	// 캠핑장의 객실 갯수
	public int roomCount (String CampNo, String[] keyword) {
		/*
		 String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordPeople};
		 */
		
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		keyword[0] = keyword[0].replaceAll("(\\-|\\/|\\.)", ""); // start date
		keyword[1] = keyword[1].replaceAll("(\\-|\\/|\\.)", ""); // end date
		
		try {
			sb.append("SELECT NVL(COUNT(DISTINCT roomNo),0) cnt FROM room ");
			sb.append("	WHERE ");
			sb.append("	campNo = ?");
			sb.append("	AND");
			sb.append("	roomNo NOT IN (");
			sb.append("		SELECT distinct roomNo");
			sb.append("		FROM book");
			sb.append("		WHERE bookSrtdate <= TO_DATE(?,'YYYYMMDD')");
			sb.append("		AND");
			sb.append("		bookEnddate >= TO_DATE(?,'YYYYMMDD')");
			sb.append("	)");
			sb.append("	AND maxPers >= ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword[1]);
			pstmt.setString(2, keyword[0]);
			
			if (!keyword[2].equals("")) {
				if (Integer.parseInt(keyword[2]) > 0) {
					pstmt.setInt(3, Integer.parseInt(keyword[2]));
				} else {
					pstmt.setInt(3, 0);
				}				
			} else {
				pstmt.setInt(3, 0);
			}
			
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
	
	// 객실 리스트 (검색조건추가된 상태)
	// 검색조건 최신화 요망..
	public List<RoomDTO> listRoom(String CampNo, String[] keyword, int start, int end) {
		List<RoomDTO> roomList = new ArrayList<RoomDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		keyword[0] = keyword[0].replaceAll("(\\-|\\/|\\.)", ""); // start date
		keyword[1] = keyword[1].replaceAll("(\\-|\\/|\\.)", ""); // end date
		
		try {
			sb.append("SELECT roomNo, roomName, stdPers, maxPers, stdPrice, extraPrice, campNo, roomDetail");
			sb.append("	FROM room");
			sb.append("	WHERE roomNo IN (");
			sb.append("		SELECT DISTINCT roomNo FROM room ");
			sb.append("		WHERE ");
			sb.append("		campNo = ?");
			sb.append("		AND");
			sb.append("		roomNo NOT IN (");
			sb.append("			SELECT distinct roomNo");
			sb.append("			FROM book");
			sb.append("			WHERE bookSrtdate <= TO_DATE(?,'YYYYMMDD')");
			sb.append("			AND");
			sb.append("			bookEnddate >= TO_DATE(?,'YYYYMMDD')");
			sb.append("		)");
			sb.append("	AND maxPers >= ?");
			sb.append("	)");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword[1]);
			pstmt.setString(2, keyword[0]);
			
			if (!keyword[2].equals("")) {
				if (Integer.parseInt(keyword[2]) > 0) {
					pstmt.setInt(3, Integer.parseInt(keyword[2]));
				} else {
					pstmt.setInt(3, 0);
				}				
			} else {
				pstmt.setInt(3, 0);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RoomDTO dto = new RoomDTO();
				
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setStdPers(rs.getInt("stdPers"));
				dto.setMaxPers(rs.getInt("maxPers"));
				dto.setStdPrice(rs.getInt("stdPrice"));
				dto.setExtraPrice(rs.getInt("extraPrice"));
				dto.setCampNo(rs.getString("campNo"));
				dto.setRoomDetail(rs.getString("roomDetail"));
				
				roomList.add(dto);
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
		
		return roomList;
	}
	
	// 게시물 보기 (객실)
	public RoomDTO readRoom(String roomNo) {
		RoomDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT roomNo, roomName, stdPers, maxPers, stdPrice, extraPrice, campNo, roomDetail"
				+ " FROM room"
				+ "	WHERE roomNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomNo);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new RoomDTO();
				
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setStdPers(rs.getInt("stdPers"));
				dto.setMaxPers(rs.getInt("maxPers"));
				dto.setStdPrice(rs.getInt("stdPrice"));
				dto.setExtraPrice(rs.getInt("extraPrice"));
				dto.setCampNo(rs.getString("campNo"));
				dto.setRoomDetail(rs.getString("roomDetail"));
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
		
		return dto;
	}
	
	// 예약정보 입력
	public int insertBook(BookDTO dto) throws SQLException{
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// 이름, 전화번호, 이메일
			sql = "SELECT memberName, memberTel, memberEmail"
				+ " FROM member"
				+ " WHERE memberId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getMemberId());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto.setBookName((dto.getBookName()==null||dto.getBookName().equals(""))?rs.getString("memberName"):dto.getBookName());
				dto.setBookTel((dto.getBookTel()==null||dto.getBookTel().equals(""))?rs.getString("memberTel"):dto.getBookTel());
				dto.setBookEmail((dto.getBookEmail()==null||dto.getBookEmail().equals(""))?rs.getString("memberEmail"):dto.getBookEmail());				
			}
			
			rs.close();
			pstmt.close();
			
			sql = "INSERT INTO book(bookNo, bookName, bookTel, bookSrtdate, bookEnddate,"
				+ " bookRequest, totalPrice, memberId, bookDate, people, roomNo)"
				+ " VALUES (book_seq.NEXTVAL, ?,		?,		?,			?"
				+ " ?,			?,			?,			SYSDATE,		?,		?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBookName());
			pstmt.setString(2, dto.getBookTel());
			pstmt.setString(3, dto.getBookSrtdate());
			pstmt.setString(4, dto.getBookEnddate());
			pstmt.setString(5, dto.getBookRequest());
			pstmt.setInt(6, dto.getTotalPrice());
			pstmt.setString(7, dto.getMemberId());
			pstmt.setInt(8, dto.getPeople());
			pstmt.setString(9, dto.getRoomNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
	
	// 예약 수정
	
	// 예약 취소
}
