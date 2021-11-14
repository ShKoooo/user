package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class CampSiteDAO {
	private Connection conn = DBConn.getConnection();
	
	// 캠핑장 유형 데이터 추가
	public int insertCampType(CampSiteDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			sql = "INSERT INTO campType(typeNo, typeName) VALUES (?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTypeNo());
			pstmt.setString(2, dto.getTypeName());
			
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
	
	
	
	// 캠핑장 데이터 추가 (캠핑장을 입력하려면 유형을 입력해야한다. 유형만 등록할 일은 없으니까 이대로 트랜잭션 처리로 할지 고민했었는데 유형만 등록해두고, 유형에 맞게 캠핑장을 등록할거같아서 따로로 뺌)
	public int insertCampSite(CampSiteDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			sql = "INSERT INTO campSite(campNo, campName, campAddr1, campAddr2, campTel, campDetail, typeNo, campAdd)"
					+ " VALUES (?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCampNo());
			pstmt.setString(2, dto.getCampName());
			pstmt.setString(3, dto.getCampAddr1());
			pstmt.setString(4, dto.getCampAddr2());
			pstmt.setString(5, dto.getCampTel());
			pstmt.setString(6, dto.getCampDetail());
			pstmt.setString(7, dto.getTypeNo());
			pstmt.setString(8, dto.getCampAdd());
			
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
	
	// 캠핑장 이미지 추가
	public int insertCampSiteImage(CampSiteDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO campsiteImage(imgNum, imgName, campNo)"
						+ " VALUES (campImg_seq.NEXTVAL, ?, ?)";
				
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
	
	
	// 캠핑장 글보기 (캠핑장 상세 페이지 보기)
	public CampSiteDTO readCampSite(String campNo) {
		CampSiteDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT s.campNo, campName, campAddr1, campAddr2, campTel, campDetail, campAdd,"
					+ " imgName, t.typeNo, typeName"
					+ " FROM campSite s"
					+ " JOIN campType t ON s.typeNo = t.typeNo"
					+ " LEFT OUTER JOIN campsiteImage i ON s.campNo = i.campNo"
					+ " WHERE s.campNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, campNo);
			
			rs = pstmt.executeQuery();
			
			// 또는 while
			while(rs.next()) {
				dto = new CampSiteDTO();
				
				dto.setCampNo(rs.getString("campNo"));
				dto.setCampName(rs.getString("campName"));
				dto.setCampAddr1(rs.getString("campAddr1"));
				dto.setCampAddr2(rs.getString("campAddr2"));
				dto.setCampTel(rs.getString("campTel"));
				dto.setCampDetail(rs.getString("campDetail"));
				dto.setCampAdd(rs.getString("campAdd"));
				dto.setImgName(rs.getString("imgName"));
				dto.setTypeNo(rs.getString("typeNo")); // 유형삭제하려고 추가.
				dto.setTypeName(rs.getString("typeName"));
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
	
/*	유형은 삭제후 다시 등록하게 하자. -> 일단 주석
	// 등록한 캠핑장 유형이름 수정
	public int updateCampType(CampSiteDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			// 유형번호 수정을 뺀 이유는 유형번호 수정했다 소속되는 캠핑장이 붕 뜰까봐..
			sql = "UPDATE campType SET typeName = ? WHERE typeNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			//pstmt.setString(1, dto.getTypeNo());
			pstmt.setString(1, dto.getTypeName());
			pstmt.setString(2, dto.getTypeNo());
			
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
*/	
	
	
	// 등록한 캠핑장 정보 수정
	public int updateCampSite(CampSiteDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			// 캠핑장번호 수정을 뺀 이유는 캠핑장번호 수정했다 소속되는 정보들이 붕 뜰까봐..
			sql = "UPDATE campSite SET campName =?, campAddr1 = ?, campAddr2 = ?, "
					+ "campTel = ?, campDetail = ?, typeNo = ?, campAdd = ? WHERE campNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			//pstmt.setString(1, dto.getCampNo());
			pstmt.setString(1, dto.getCampName());
			pstmt.setString(2, dto.getCampAddr1());
			pstmt.setString(3, dto.getCampAddr2());
			pstmt.setString(4, dto.getCampTel());
			pstmt.setString(5, dto.getCampDetail());
			pstmt.setString(6, dto.getTypeNo()); // 유형은 소속유형 바꾸고싶을 수 있으니 수정 가능하게 둠
			pstmt.setString(7, dto.getCampAdd());
			pstmt.setString(8, dto.getCampNo());
			
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
	
	// 등록한 캠핑장 이미지 수정
	public int updateCampSiteImage(CampSiteDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO campsiteImage(imgNum, imgName, campNo)"
						+ " VALUES (campImg_seq.NEXTVAL, ?, ?)";
				
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
	
	// 캠핑장 이미지만 삭제
	public int deleteCampSiteImage(int imgNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM campsiteImage WHERE imgNum=?";
			
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
	
	// 캠핑장 유형 삭제
	public int deleteCampType(String typeNo, String memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(memberId.equals("admin")) {
				sql = "DELETE FROM campType WHERE typeNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, typeNo);
			
				result = pstmt.executeUpdate();
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
	
	
	// 등록한 캠핑장 삭제
	public int deleteCampSite(String campNo, String memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		// 캠핑장을 지우게 되면 캠핑장 아래의 객실도 없어져야 한다.
		// 자식을 먼저 지우고 아버지를 지워야 함
		try {
			if(memberId.equals("admin")) {
				// 객실 이미지 삭제
				sql = "DELETE FROM roomImage WHERE campNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, campNo);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				
				// 객실 삭제
				sql = "DELETE FROM room WHERE campNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, campNo);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				
				// 캠핑장 이미지 삭제(캠핑장 번호로 해서 전체 이미지 삭제)
				sql = "DELETE FROM campsiteImage WHERE campNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, campNo);
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				
				// 캠핑장 삭제
				sql = "DELETE FROM campSite WHERE campNo = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, campNo);
				
				result = pstmt.executeUpdate();	
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

	
	// 관리자가 등록한 캠핑장 리스트 보기
	public List<CampSiteDTO> listCampSite(int start, int end) {
		List<CampSiteDTO> list = new ArrayList<CampSiteDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT campNo, campName, s.typeNo, typeName ");
			sb.append("         FROM campSite s ");
			sb.append("         JOIN campType t ON s.typeNo = t.typeNo ");
			sb.append("         ORDER BY campNo DESC "); // campNo가 int형이 아닌데 어쩌지
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				CampSiteDTO dto = new CampSiteDTO();

				dto.setCampNo(rs.getString("campNo"));
				dto.setCampName(rs.getString("campName"));
				dto.setTypeNo(rs.getString("typeNo"));
				dto.setTypeName(rs.getString("typeName"));

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
	
	// 캠핑장 이미지파일 리스트
	public List<CampSiteDTO> listCampImgFile(String campNo) {
		List<CampSiteDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT imgNum, imgName, campNo FROM campsiteImage WHERE campNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, campNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CampSiteDTO dto = new CampSiteDTO();
				
				dto.setImgNum(rs.getInt("imgNum"));
				dto.setImgName(rs.getString("imgName"));
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
	
	
	
	// 캠핑장 데이터 개수 
		public int dataCount() {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM campSite";
				
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

		// 캠핑장 등록, 수정시 캠핑장 유형 셀렉트로 선택
		public List<CampTypeDTO> listCampType() {
			List<CampTypeDTO> list = new ArrayList<CampTypeDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT typeNo, typeName FROM campType";
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					CampTypeDTO dto = new CampTypeDTO();
					
					dto.setTypeNo(rs.getString("typeNo"));
					dto.setTypeName(rs.getString("typeName"));
					
					list.add(dto);
				}
			} catch (Exception e) {
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
		 
}
