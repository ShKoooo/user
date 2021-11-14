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
		// List<CampsiteImageDTO> list2 = null;
		List<CampsiteImageDTO> list2 = new ArrayList<CampsiteImageDTO>();
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
				
				list2 = readCampImages(rs.getString("campNo"));
				dto.setImages(list2);
				
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
		// List<CampsiteImageDTO> list2 = null;
		List<CampsiteImageDTO> list2 = new ArrayList<CampsiteImageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		// String sql;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
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
				dto.setTypeName(rs.getString("typeName"));
				
				list2 = readCampImages(rs.getString("campNo"));
				dto.setImages(list2);
				
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
			
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	
	// 게시물 보기 (캠핑장)
	public CampSiteDTO readCamp (String campNo) {
		// List<CampsiteImageDTO> list2 = null;
		List<CampsiteImageDTO> list2 = new ArrayList<CampsiteImageDTO>();
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
				
				list2 = readCampImages(rs.getString("campNo"));
				dto.setImages(list2);
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
		// List<RoomImageDTO> list2 = null;
		List<RoomImageDTO> list2 = new ArrayList<RoomImageDTO>();
		
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
			pstmt.setString(1, CampNo);
			pstmt.setString(2, keyword[1]);
			pstmt.setString(3, keyword[0]);
			
			if (!keyword[2].equals("")) {
				if (Integer.parseInt(keyword[2]) > 0) {
					pstmt.setInt(4, Integer.parseInt(keyword[2]));
				} else {
					pstmt.setInt(4, 0);
				}				
			} else {
				pstmt.setInt(4, 0);
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
				
				// 객실 그림 가져오기
				list2 = readRoomImages(rs.getString("roomNo"));
				dto.setImages(list2);
				
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
		
		// List<RoomImageDTO> list2 = null;
		List<RoomImageDTO> list2 = new ArrayList<RoomImageDTO>();
		
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
				
				// 객실 그림 가져오기
				list2 = readRoomImages(rs.getString("roomNo"));
				dto.setImages(list2);
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
	public String insertBook(BookDTO dto) throws SQLException{
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		String bookNo = "";
		
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
			
			/*
			// SQL 쿼리 테스트
			INSERT INTO book(bookNo, bookName, bookTel, bookSrtdate, bookEnddate,bookRequest, totalPrice, memberId, bookDate, people, roomNo)
			VALUES (book_seq.NEXTVAL, '이이이','070','2021-12-01','2021-12-10', '없어',217000,'kim',SYSDATE,2,'c001r001');
			
			commit;
			
			select book_seq.currval from dual;
			*/
			
			sql = "INSERT INTO book(bookNo, bookName, bookTel, bookSrtdate, bookEnddate,"
				+ " bookRequest, totalPrice, memberId, bookDate, people, roomNo)"
				+ " VALUES (book_seq.NEXTVAL, ?,		?,		?,			?,"
				+ " ?,			?,			?,			SYSDATE,		?,		?)";
			
			// bookNo = dto.getMemberId()+
			
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
			
			if (result == 0) {
				throw new SQLException("BOOK 테이블 쿼리입력 실패");
			} else {
				pstmt.close();
			}
			
			// SELECT book_seq.CURRVAL FROM DUAL;
			sql = "SELECT book_seq.CURRVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bookNo = rs.getString(1);
			}
			
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
		
		return bookNo;
	}
	
	// 예약확인서 출력 (예약정보 읽어오기)
	public BookDTO readBook(String bookNo) {
		BookDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT bookNo, bookName, bookTel, bookSrtdate, bookEnddate"
				+ " bookRequest, totalPrice, memberId, bookDate, people, "
				+ " roomNo, bookEmail"
				+ " FROM book"
				+ " WHERE bookNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookNo);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new BookDTO();
				
				dto.setBookNo(bookNo);
				dto.setBookName(rs.getString("bookName"));
				dto.setBookTel(rs.getString("bookTel"));
				dto.setBookSrtdate(rs.getString("bookSrtdate"));
				dto.setBookEnddate(rs.getString("bookEnddate"));
				
				dto.setBookRequest(rs.getString("bookRequest"));
				dto.setTotalPrice(rs.getInt("totalPrice"));
				dto.setMemberId(rs.getString("MemberId"));
				dto.setBookDate(rs.getString("bookDate"));
				dto.setPeople(rs.getInt("people"));
				
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setBookEmail(rs.getString("bookEmail"));
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
		
		return dto;
	}
	
	// 캠핑장 이미지 가져오기 (TEMP)
	public List<CampsiteImageDTO> readCampImages(String campNo) {
		List<CampsiteImageDTO> list = new ArrayList<CampsiteImageDTO>();
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql;
		
		try {
			// 그림 가져와서 dto에 저장하기
			sql = "SELECT imgNum, imgName, campNo"
				+ " FROM campsiteImage"
				+ " WHERE campNo = ?";
			
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, campNo);
			
			rs2 = pstmt2.executeQuery();
			
			// List<CampsiteImageDTO> list2 = new ArrayList<CampsiteImageDTO>();
			while (rs2.next()) {
				CampsiteImageDTO dto2 = new CampsiteImageDTO();
				
				dto2.setImgNum(rs2.getInt("imgNum"));
				dto2.setImgName(rs2.getString("imgName"));
				dto2.setCampNo(rs2.getString("campNo"));
				
				list.add(dto2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	
	// 객실 이미지 가져오기
	public List<RoomImageDTO> readRoomImages(String roomNo) {
		List<RoomImageDTO> list = new ArrayList<RoomImageDTO>();
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql;
		
		try {
			// 그림 가져와서 dto에 저장하기
			sql = "SELECT imgNum, imgName, roomNo, campNo"
				+ " FROM roomImage"
				+ " WHERE roomNo = ?";
			
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, roomNo);
			
			rs2 = pstmt2.executeQuery();
			
			// List<CampsiteImageDTO> list2 = new ArrayList<CampsiteImageDTO>();
			while (rs2.next()) {
				RoomImageDTO dto2 = new RoomImageDTO();
				
				dto2.setImgNum(rs2.getInt("imgNum"));
				dto2.setImgName(rs2.getString("imgName"));
				dto2.setRoomNo(rs2.getString("roomNo"));
				dto2.setCampNo(rs2.getString("campNo"));
				
				list.add(dto2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	
	// 예약 갯수
	public int bookCount(String memberId) {
		int result  = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM book";
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
	
	// 예약 리스트
	public List<BookDTO> listBook(int start, int end, String memberId) {
		List<BookDTO> list = new ArrayList<BookDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT bookNo, bookName, bookTel, bookSrtdate, bookEnddate,");
			sb.append(" 			bookRequest, totalPrice, memberId, bookDate, people,");
			sb.append(" 			roomNo, bookEmail");
			sb.append(" 		FROM book");
			if (!memberId.equalsIgnoreCase("admin")) {
				sb.append("WHERE memberId = "+memberId);
			}
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			/*			
			sql = "SELECT bookNo, bookName, bookTel, bookSrtdate, bookEnddate,"
				+ " bookRequest, totalPrice, memberId, bookDate, people,"
				+ " roomNo, bookEmail "
				+ " FROM book";
			*/
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BookDTO dto = new BookDTO();
				
				dto.setBookNo(rs.getString("bookNo"));
				dto.setBookName(rs.getString("bookName"));
				dto.setBookTel(rs.getString("bookTel"));
				dto.setBookSrtdate(rs.getString("bookSrtdate"));
				dto.setBookEnddate(rs.getString("bookEnddate"));
				dto.setBookRequest(rs.getString("bookRequest"));
				dto.setTotalPrice(rs.getInt("totalPrice"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setBookDate(rs.getString("bookDate"));
				dto.setPeople(rs.getInt("people"));
				dto.setRoomNo(rs.getString("roomNo"));
				dto.setBookEmail(rs.getString("bookEmail"));
				
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
	
	// 예약 수정
	public int updateBook(BookDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE book SET bookName = ?, bookTel = ?, bookEmail = ?, bookRequest = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBookName());
			pstmt.setString(2, dto.getBookTel());
			pstmt.setString(3, dto.getBookEmail());
			pstmt.setString(4, dto.getBookRequest());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 예약 취소
	public int deleteBook(String bookNo, String memberId) throws SQLException{
		int result1 = 0, result2 = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			if (memberId.equalsIgnoreCase("admin")) {
				sql = "DELETE FROM review WHERE bookNo = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bookNo);
				
				result1 = pstmt.executeUpdate();
				
				pstmt.close();
				
				sql = "DELETE FROM book WHERE bookNo = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bookNo);
				
				result2 = pstmt.executeUpdate();
				
				if (result1 != 0 && result2 != 0) {
					conn.commit();					
				} else {
					conn.rollback();
				}
			} else {
				sql = "DELETE FROM review WHERE bookNo = ? AND memberId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bookNo);
				pstmt.setString(2, memberId);
				
				result1 = pstmt.executeUpdate();
				
				pstmt.close();
				
				sql = "DELETE FROM book WHERE bookNo = ? AND memberId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bookNo);
				pstmt.setString(2, memberId);
				
				result2 = pstmt.executeUpdate();
				
				if (result1 != 0 && result2 != 0) {
					conn.commit();					
				} else {
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		
		return result1 + result2;
	}
}
