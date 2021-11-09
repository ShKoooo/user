package com.book;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/book/*")
public class BookServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		// URI에 따른 작업 구분
		if (uri.indexOf("campList.do") != -1) {
			// 캠핑장 리스트 (캠핑장 리스트 + 검색)
			campList(req,resp);
		} else if (uri.indexOf("campArticle.do") != -1) {
			// 캠핑장 글보기 (캠핑장 상세정보 + 객실리스트)
			campArticle(req,resp);
		} else if (uri.indexOf("book.do") != -1) {
			// 객실 글보기 + 예약정보 기입 (세부사항 작성)
			book(req, resp);
		} else if (uri.indexOf("book_ok.do") != -1) {
			// 예약정보 작성 완료
			bookSubmit(req, resp);
		} else if (uri.indexOf("confirm.do") != -1) {
			// 예약확인서 출력
			bookConfirm(req, resp);
		}
		// + TODO: 댓글형태의 객실리스트 추가..
		// + 마이페이지 예약 취소/수정 추가..
	}
	
	protected void campList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 리스트 (캠핑장 리스트 + 검색)
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			// String condition = req.getParameter("condition");
				// condition 미사용
			String keywordSrtDate = req.getParameter("srtDate");
			String keywordEndDate = req.getParameter("endDate");
			String keywordAddr1 = req.getParameter("addr1");
			String keywordPeople = req.getParameter("people");
			String keywordCampName = req.getParameter("campName");
			
			// GET 방식 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keywordSrtDate = URLDecoder.decode(keywordSrtDate,"utf-8");
				keywordEndDate = URLDecoder.decode(keywordEndDate,"utf-8");
				keywordAddr1 = URLDecoder.decode(keywordAddr1,"utf-8");
				keywordPeople = URLDecoder.decode(keywordPeople,"utf-8"); // 필요없음.
				keywordCampName = URLDecoder.decode(keywordCampName,"utf-8");
			}
			
			// 전체 캠핑장 개수
			int campCount;
			
			String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordAddr1,keywordPeople,keywordCampName};
			
			if (keywordSrtDate.length() == 0 
				&& keywordEndDate.length() == 0
				&& keywordAddr1.length() == 0
				&& keywordPeople.length() == 0
				&& keywordCampName.length() == 0) {
				campCount = dao.campCount();
			} else {
				campCount = dao.campCount(keyword);
			}
			
			// 전체 페이지 수
			int rows = 10;
			int total_page = util.pageCount(rows, campCount);
			
			if (current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 캠핑장 게시물 가져오기
			List<CampSiteDTO> list = null;
			if (keywordSrtDate.length() == 0 
					&& keywordEndDate.length() == 0
					&& keywordAddr1.length() == 0
					&& keywordPeople.length() == 0
					&& keywordCampName.length() == 0) {
				list = dao.listCamp(start,end);
			} else {
				list = dao.listCamp(start,end,keyword);
			}
			
			// 리스트 글번호 만들기
			int listNum, n = 0;
			for (CampSiteDTO dto : list) {
				listNum = campCount - (start+n-1);
				dto.setListNum(listNum);
				n++;
			}
			
			String query = "";
			if (keywordSrtDate.length() != 0 || keywordEndDate.length() != 0) {
				query = "srtDate="+URLEncoder.encode(keyword[0], "utf-8")
					+"&endDate="+URLEncoder.encode(keyword[1], "utf-8");
				
				if (keyword[2].length() != 0) {
					query += "&addr1="+URLEncoder.encode(keyword[2], "utf-8");
				}
				if (keyword[3].length() != 0) {									
					query += "&people="+URLEncoder.encode(keyword[3], "utf-8");
				}
				if (keyword[4].length() != 0) {					
					query += "&campName="+URLEncoder.encode(keyword[4], "utf-8");
				}
			}
			
			// 페이징 처리
			String listUrl = cp + "/book/campList.do";
			String articleUrl = cp + "/book/campArticle.do?page=" + current_page;
			
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("campCount", campCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/campingutte/book/campList.jsp");
	}
	
	protected void campArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 글보기 (캠핑장 상세정보 + 객실리스트)
		// TODO: 객실리스트
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		String query = "page="+page;
		
		try {
			// campNo (읽어와야 함!)
			String campNo = req.getParameter("campNo");
			
			// String condition = req.getParameter("condition");
			String keywordSrtDate = req.getParameter("srtDate");
			String keywordEndDate = req.getParameter("endDate");
			String keywordAddr1 = req.getParameter("addr1");
			String keywordPeople = req.getParameter("people");
			String keywordCampName = req.getParameter("campName");
			
			keywordSrtDate = URLDecoder.decode(keywordSrtDate, "utf-8");
			keywordEndDate = URLDecoder.decode(keywordEndDate, "utf-8");
			keywordAddr1 = URLDecoder.decode(keywordAddr1, "utf-8");
			keywordPeople = URLDecoder.decode(keywordPeople, "utf-8");
			keywordCampName = URLDecoder.decode(keywordCampName, "utf-8");
			
			String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordAddr1,keywordPeople,keywordCampName};
			// String query = "";
			if (keywordSrtDate.length() != 0 || keywordEndDate.length() != 0) {
				query += "srtDate="+URLEncoder.encode(keyword[0], "utf-8")
					+"&endDate="+URLEncoder.encode(keyword[1], "utf-8");
				
				if (keyword[2].length() != 0) {
					query += "&addr1="+URLEncoder.encode(keyword[2], "utf-8");
				}
				if (keyword[3].length() != 0) {									
					query += "&people="+URLEncoder.encode(keyword[3], "utf-8");
				}
				if (keyword[4].length() != 0) {					
					query += "&campName="+URLEncoder.encode(keyword[4], "utf-8");
				}
			}
			
			
			// 게시물 가져오기
			CampSiteDTO dto = dao.readCamp(campNo);
			if (dto == null) { // 게시글 없으면
				resp.sendRedirect(cp+"/book/campList.do"+query);
				return;
			}
			dto.setCampDetail(util.htmlSymbols(dto.getCampDetail()));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			// 이전글,다음글 없음
			
			// 포워딩
			forward(req, resp, "/WEB-INF/campingutte/book/campArticle.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/book/campList.do?" + query);
	}
	
	protected void roomList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실리스트 (??) : TODO
		// BoardSerlet - listReply 참고 (메인에 댓글 형태)
	}
	
	protected void book(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 글보기 + 예약정보 기입 (세부사항 작성)
	}
	
	protected void bookSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약정보 작성 완료
	}
	
	protected void bookConfirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약확인서 출력
	}
}
