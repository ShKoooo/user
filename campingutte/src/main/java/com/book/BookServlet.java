package com.book;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

// 그림 첨부 시 사용..?
// 도트 연산자의 왼쪽에는 맵, 빈만 올 수 있었지만, [] 연산자의 경우에는 왼쪽에 맵, 빈 외에도 배열, 리스트가 올 수 있다.
// 또한 [] 안에 따옴표로 감싸져 있으므로 [] 안의 내용은 맵 키, 빈 프로퍼티, 리스트나 배열의 인덱스가 될 수 있다.
// 출처: https://www.4te.co.kr/557 [체리필터의 인생이야기]

@WebServlet("/book/*")
public class BookServlet extends MyServlet{
	private static final long serialVersionUID = 1L;
	private boolean standalone = false; // 세션독립여부 true -> 세션 미사용/ false -> 세션 사용

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		// URI에 따른 작업 구분
		if (uri.indexOf("campList.do") != -1) {
			// 캠핑장 리스트 (캠핑장 리스트 + 검색)
			campList(req,resp);
		} else if (uri.indexOf("campArticle.do") != -1) {
			// 캠핑장 글보기 (캠핑장 상세정보 + (x)객실리스트(x) )
			campArticle(req,resp);
				// 예약확인서 화면에서 더 예약하기 눌렀을 때 여기로...!
		} else if (uri.indexOf("book.do") != -1) {
			// 객실 글보기 
			// + 예약정보 기입 (세부사항 작성) ==> bookSubmit에서 (book_ok.do)
			book(req, resp);
		} else if (uri.indexOf("book_ok.do") != -1) {
			// 예약정보 작성/ 완료
			bookSubmit(req, resp);
		} else if (uri.indexOf("confirm.do") != -1) {
			// 예약확인서 출력
			bookConfirm(req, resp);
		} else if (uri.indexOf("roomList.do") != -1) {
			// 객실리스트 (메인 글과 "댓글" 구분과 같은..?)
			roomList(req,resp);
		} else if (uri.indexOf("book1.do") != -1) {
			// bookForm(req, resp); // (X)
			// book 연결용 (나중에 삭제)
			forward(req, resp, "/WEB-INF/campingutte/book/book.jsp"); // 정상화되면 삭제예정.
		} else if (uri.indexOf("delete_BookSession.do") != -1) {
			// 예약 세션 삭제
			deleteBookSession(req,resp);
		} else if (uri.indexOf("bookList.do") != -1) {
			// 예약 리스트
			bookList(req,resp);
		} else if (uri.indexOf("bookArticle.do") != -1) {
			// 예약 글보기 (--> 여기서 리뷰 글보기로..)
			bookArticle(req,resp);
		} else if (uri.indexOf("bookUpdate.do") != -1) {
			// 예약 수정
			bookUpdate(req,resp);
		} else if (uri.indexOf("bookDelete.do") != -1) {
			// 예약 취소
			bookDelete(req,resp);
		} 
		/*
				else if (uri.indexOf("") != -1) {
					// 리뷰 리스트 --> com.review
				} else if (uri.indexOf("") != -1) {
					// 리뷰 글보기 --> com.review
				} else if (uri.indexOf("") != -1) {
					// 리뷰 작성 --> com.review
				} else if (uri.indexOf("") != -1) {
					// 리뷰 수정 --> com.review
				} else if (uri.indexOf("") != -1) {
					// 리뷰 삭제 --> com.review
				}		
		*/
		// + 댓글형태의 객실리스트 추가.. -- DONE.
		// TODO : + 마이페이지 예약 취소/수정 추가.. (하고있음)
	}
	
	/*
	Session
	(1) ID
	(2) NAME
	(3) (ROLL)
	(4) SrtDate 시작일
	(5) EndDate 종료일
	(6) Addr1 장소
	(7) People 인원
	(8) CampName 캠프장명
	*/
	
	protected void campList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 리스트 (캠핑장 리스트 + 검색)
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		// 세션객체. SrtDate 시작일, EndDate 종료일, Addr1 장소, People 인원, CampName 캠프장명
		HttpSession session = req.getSession();
		BookSessionInfo bookInfo = (BookSessionInfo)session.getAttribute("book");
		// 검색 및 예약에 사용했던 세션 정보가 살아있으면 지움
		if (bookInfo != null) {
			session.removeAttribute("book");			
		}
		// bookInfo = (BookSessionInfo)session.getAttribute("book");
		
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
			
			if (keywordSrtDate == null) {
				keywordSrtDate = "";
			}
			if (keywordEndDate == null) {
				keywordEndDate = "";
			}
			if (keywordAddr1 == null) {
				keywordAddr1 = "";
			}
			if (keywordPeople == null) {
				keywordPeople = "";
			}
			if (keywordCampName == null) {
				keywordCampName = "";
			}
			
			
			// GET 방식 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keywordSrtDate = URLDecoder.decode(keywordSrtDate,"utf-8");
				keywordEndDate = URLDecoder.decode(keywordEndDate,"utf-8");
				keywordAddr1 = URLDecoder.decode(keywordAddr1,"utf-8");
				keywordPeople = URLDecoder.decode(keywordPeople,"utf-8"); // 필요없음.
				keywordCampName = URLDecoder.decode(keywordCampName,"utf-8");
			}
			
			// 세션에 저장할 내용: SrtDate, EndDate, Addr1, People, CampName
			// SessionInfo info = (SessionInfo)session.getAttribute("member");
						// 세션에 member이라는 이름으로 저장
						// session.setAttribute("member", info);
			// BookSessionInfo bookInfo = (BookSessionInfo)session.getAttribute("book");
			/*
				if (bookInfo.getSrtDate() != null && !bookInfo.getSrtDate().equals("") ) {	
				}
				if (bookInfo.getEndDate() != null && !bookInfo.getEndDate().equals("") ) {	
				}
				if (bookInfo.getAddr1() != null && !bookInfo.getAddr1().equals("") ) {	
				}
				if (bookInfo.getPeople() != null && !bookInfo.getPeople().equals("") ) {	
				}
				if (bookInfo.getCampName() != null && !bookInfo.getCampName().equals("") ) {	
				}
			*/
			bookInfo = new BookSessionInfo();
			
			bookInfo.setSrtDate(keywordSrtDate);
			bookInfo.setEndDate(keywordEndDate);
			bookInfo.setAddr1(keywordAddr1);
			bookInfo.setPeople(keywordPeople);
			bookInfo.setCampName(keywordCampName);
			
			session.setAttribute("book", bookInfo);
			
			/*
			// 세션변경 (SessionInfo -> BookSessionInfo)
			info.setSrtDate(keywordSrtDate);
			info.setEndDate(keywordEndDate);
			info.setAddr1(keywordAddr1);
			info.setPeople(keywordPeople);
			info.setCampName(keywordCampName);
			
			session.setAttribute("member", info);			
			*/
			
			// 전체 캠핑장 개수
			int campCount;
			
			String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordAddr1,keywordPeople,keywordCampName};
			
			System.out.println("키워드: "+Arrays.toString(keyword));
			
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
					// dao.listCamp에 그림 가져오기 포함
					// CampSiteDTO 내부에 CampsiteImageDTO 객체 가지고 있음.
			}
			
			System.out.println("list is Empty? : "+list.isEmpty());
			
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
				// page -> campArticle에서 귀환을 위한 page로 씀
			
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
		// 캠핑장 글보기 (캠핑장 상세정보 + 객실리스트(?) )
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		BookSessionInfo bookInfo = (BookSessionInfo) session.getAttribute("book");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		// 예약 세션이 넘어오지 않았을 때 --> 캠핑장 리스트로 리다이렉트 (매우 중요)
		
		if (!standalone) {
			if (bookInfo == null) { 
				resp.sendRedirect(cp+"/book/campList.do");
				// 캠핑장 리스트로 리다이렉트
				return;
			}
		}
		
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();

		// List<CampSiteDTO> list = null;
		// List<CampsiteImageDTO> campImgList = null; // 그림가져오기 - 이렇게 안할거임
		
		String page = req.getParameter("page");
		
		String query = "page="+page;
		
		try {
			// campNo (읽어와야 함!)
			String campNo = req.getParameter("campNo");
			
			// String condition = req.getParameter("condition");
			
			String keywordSrtDate = "";
			String keywordEndDate = "";
			String keywordAddr1 = "";
			String keywordPeople = "";
			String keywordCampName = "";
			
			if (standalone) {
				keywordSrtDate = req.getParameter("srtDate")==null?"":req.getParameter("srtDate");
				keywordEndDate = req.getParameter("endDate")==null?"":req.getParameter("endDate");
				keywordAddr1 = req.getParameter("addr1")==null?"":req.getParameter("addr1");
				keywordPeople = req.getParameter("people")==null?"":req.getParameter("people");
				keywordCampName = req.getParameter("campName")==null?"":req.getParameter("campName");
				
				keywordSrtDate = URLDecoder.decode(keywordSrtDate, "utf-8");
				keywordEndDate = URLDecoder.decode(keywordEndDate, "utf-8");
				keywordAddr1 = URLDecoder.decode(keywordAddr1, "utf-8");
				keywordPeople = URLDecoder.decode(keywordPeople, "utf-8");
				keywordCampName = URLDecoder.decode(keywordCampName, "utf-8");
			} else {
				keywordSrtDate = bookInfo.getSrtDate();
				keywordEndDate = bookInfo.getEndDate();
				keywordAddr1 = bookInfo.getAddr1();
				keywordPeople = bookInfo.getPeople();
				keywordCampName = bookInfo.getCampName();
			}
			
			/*
			// 아래 세션 안먹히면 이거로 바꿈.
			*/
			
			// 세션 (bookSessionInfo)에서 가져오기
			// BookSessionInfo bookInfo = (BookSessionInfo)session.getAttribute("searchNbook");

			// 위에서 null일때 캠핑장리스트로 리다이렉트했으므로 널 판별 여부 확인 불필요
				// 필요시 ==null?"":bookInfo.getXXX(); 추가
			
			
			String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordAddr1,keywordPeople,keywordCampName};
			// String query = "";
			if (keywordSrtDate.length() != 0 || keywordEndDate.length() != 0) {
				query += "&srtDate="+URLEncoder.encode(keyword[0], "utf-8")
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
			
			if (standalone) {
				campNo = "123";				
			}
			
			// 게시물 가져오기
			CampSiteDTO dto = dao.readCamp(campNo); // 그림 가져오기 포함
			if (dto == null) { // 게시글 없으면
				resp.sendRedirect(cp+"/book/campList.do?"+query);
				return;
			}
			dto.setCampDetail(util.htmlSymbols(dto.getCampDetail()));
			
			// bookInfo 세션 // JP 요청 (21-11-15-13:00)
			bookInfo.setCampNameReal(dto.getCampName());
			
			session.setAttribute("book", bookInfo);
			
			// 그림 가져오기
			// campImgList = dao.readCampImages(campNo); // 이렇게 안할거임
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			// req.setAttribute("campImgList", campImgList);
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
	
	// AJAX - Text
	protected void roomList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실리스트 (??)
		// study4/bbs/BoardSerlet - listReply 참고 (메인에 댓글 형태 or 방명록 형태..?)
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		BookSessionInfo bookInfo = (BookSessionInfo) session.getAttribute("book");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// 예약 세션이 넘어오지 않았을 때 --> 캠핑장 리스트로 리다이렉트 (매우 중요)
		if (!standalone) {
			if (bookInfo == null) {
				resp.sendRedirect(cp+"/book/campList.do");
				// 캠핑장 리스트로 리다이렉트
				return;
			}			
		}
		
		// GET 방식 거부
		/* 
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/book/campArticle.do");
			return;
		}
		 */
		
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		try {
			// int num = Integer.parseInt(req.getParameter("num"));
			String campNo = req.getParameter("campNo");
			if (standalone) {
				campNo = "123";
			}
			String roomPageNo = req.getParameter("roomPageNo"); // 객실 페이징 처리 (객실은 한 페이지당 5개씩)
			int current_page = 1;
			if (roomPageNo  != null) {
				current_page = Integer.parseInt(roomPageNo);
			}
			
			/*
			// 세션으로 대체
			*/
			
			String keywordSrtDate="";
			String keywordEndDate="";
			String keywordPeople="";
			
			if (standalone) {
				keywordSrtDate = req.getParameter("srtDate");
				keywordEndDate = req.getParameter("endDate");
				// String keywordAddr1 = req.getParameter("addr1");
				keywordPeople = req.getParameter("people");
				// String keywordCampName = req.getParameter("campName");
				
				keywordSrtDate = URLDecoder.decode(keywordSrtDate, "utf-8");
				keywordEndDate = URLDecoder.decode(keywordEndDate, "utf-8");
				// keywordAddr1 = URLDecoder.decode(keywordAddr1, "utf-8");
				keywordPeople = URLDecoder.decode(keywordPeople, "utf-8");
				// keywordCampName = URLDecoder.decode(keywordCampName, "utf-8");				
			} else {
				keywordSrtDate = bookInfo.getSrtDate();
				keywordEndDate = bookInfo.getEndDate();
				keywordPeople = bookInfo.getPeople();				
			}

			String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordPeople}; // [0,1,2]
			
			
			int rows = 5; // 객실 5개/페이지 당.
			int roomTotal_page = 0;
			int roomCount = 0;
			
			roomCount = dao.roomCount(campNo, keyword);
			roomTotal_page = util.pageCount(rows, roomTotal_page);
			
			if (current_page > roomTotal_page) {
				current_page = roomTotal_page;
			}
			
			int start = (current_page-1) * rows + 1;
			int end = current_page * rows;
			
			List<RoomDTO> listRoom = dao.listRoom(campNo, keyword, start, end);
			
			for (RoomDTO dto : listRoom) {
				dto.setRoomDetail(dto.getRoomDetail().replaceAll("\n","<br>"));
			}
			
			String roomPaging = util.pagingMethod(current_page, roomTotal_page, "listPage");
			
			req.setAttribute("listRoom", listRoom);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("roomCount", roomCount);
			req.setAttribute("roomTotal_page", roomTotal_page);
			req.setAttribute("roomPaging", roomPaging);
			
			forward(req,resp,"/WEB-INF/campingutte/book/roomList.jsp");
			// campArticle,
			// roomList 
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(405); //
	}
	
	protected void book(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 글보기
		// + 예약정보 기입 (세부사항 작성) --> bookSubmit에서..
		// 예약정보 기입? (--> bookForm)
		// forward(req, resp, "/WEB-INF/campingutte/book/book.jsp");
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		BookSessionInfo bookInfo = (BookSessionInfo) session.getAttribute("book");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		// 예약 세션이 넘어오지 않았을 때 --> 캠핑장 리스트로 리다이렉트 (매우 중요)
		if (bookInfo == null) {
			resp.sendRedirect(cp+"/book/campList.do");
				// 캠핑장 리스트로 리다이렉트
			return;
		}
		
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String page = req.getParameter("page");
		String query = "page="+page;
		
		try {
			// roomNo (읽어와야 함!)
			String roomNo = req.getParameter("roomNo");
			
			String keywordSrtDate = bookInfo.getSrtDate();
			String keywordEndDate = bookInfo.getEndDate();
			String keywordPeople = bookInfo.getPeople();
			String campNameReal = bookInfo.getCampNameReal();
			
			String [] keyword = 
				{keywordSrtDate,keywordEndDate,keywordPeople}; // [0,1,2]
			
			if (keywordSrtDate.length() != 0 || keywordEndDate.length() != 0) {
				query += "&srtDate="+URLEncoder.encode(keyword[0], "utf-8")
					+"&endDate="+URLEncoder.encode(keyword[1], "utf-8");
				
				if (keyword[2].length() != 0) {
					query += "&people="+URLEncoder.encode(keyword[2], "utf-8");
				}
			}
			
			// 객실 게시물 가져오기
			RoomDTO dto = dao.readRoom(roomNo);
			
			if (dto == null) { // 객실 게시글 없으면
				resp.sendRedirect(cp+"/book/campArticle.do"+query);
				return;
			}
			
			bookInfo.setRoomNo(roomNo);
			
			session.setAttribute("book", bookInfo);
			
			dto.setRoomDetail(util.htmlSymbols(dto.getRoomDetail()));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("campNameReal", campNameReal);
			
			// 이전글,다음글 없음
			
			// 포워딩
			forward(req, resp, "/WEB-INF/campingutte/book/book.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/book/roomList.do?" + query);
		// resp.sendRedirect(cp+"/book/roomList.do"); // 이렇게만 써도 될 듯.. (세션에 정보 있음..)
	}
	
	/*
	protected void bookForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약정보 입력
		// 예약자명, 휴대폰번호, 이메일, 요청사항
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		// 글쓰기 폼 형태 필요 없음
	}
	*/
	
	protected void bookSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약정보 작성 완료 (저장) -- 예약완료 (예약확인서 출력으로 넘어가기)
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		BookSessionInfo bookInfo = (BookSessionInfo) session.getAttribute("book");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		// 예약 세션이 넘어오지 않았을 때 --> 캠핑장 리스트로 리다이렉트 (매우 중요)
		if (bookInfo == null) {
			resp.sendRedirect(cp+"/book/campList.do");
				// 캠핑장 리스트로 리다이렉트
			return;
		}
		
		BookDAO dao = new BookDAO();
	
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/book/roomList.do"); // 객실 리스트로 리다이렉트
			return;
		}
		
		// book_seq
		try {
			BookDTO dto = new BookDTO();
			
			// bookNo
			// bookName, bookTel, bookSrtdate, bookEnddate, bookRequest
			// totalPrice, memberId, bookDate, people, roomNo
			// bookEmail
			
			// 세션에 저장된 정보 불러오기
			// memberName, srtdate, enddate, memberId, people, roomNo
			// dto.setBookName(info.getMemberName()); // 세션에서 불러올 필요 없음..
			dto.setBookSrtdate(bookInfo.getSrtDate());
			dto.setBookEnddate(bookInfo.getEndDate());
			dto.setMemberId(memberInfo.getMemberId());
			if (bookInfo.getPeople()==null || bookInfo.getPeople().equals("")) {
				dto.setPeople(0);
			} else {
				dto.setPeople(Integer.parseInt(bookInfo.getPeople()));
			}
			
			dto.setRoomNo(bookInfo.getRoomNo());
			
			// 세션에 저장되지 않은 정보 불러오기
			// bookTel,
			// 전화번호, 이메일, 예약요청사항
			// 세션에서 받은 이름: 멤버 이름!
			// jsp에서 받은 이름: 예약 이름 (멤버 이름과 다를 수 있음...)
			
			dto.setBookName(req.getParameter("name"));
			dto.setBookTel(req.getParameter("phone"));
			dto.setBookEmail(req.getParameter("email"));
			dto.setBookRequest(req.getParameter("message"));
			
			// book DB입력
			String bookNo = dao.insertBook(dto); // DB 입력 단계
			
			// 세션에 bookNo 저장
			bookInfo.setRoomNo(bookNo);
			
			session.setAttribute("book", bookInfo);
			
			resp.sendRedirect(cp+"/book/confirm.do"); // DB 입력이 성공했을 때
				// 제출한 형태이므로 예약확인서로 바로 리다이렉트
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// forward(req, resp, "/WEB-INF/campingutte/book/book_ok.jsp");
		
		// 버튼?눌렀을 때 예약확인서로...
		// 아니면 그냥?
		// forward(req, resp, "/WEB-INF/campingutte/book/confirm.jsp");
		
		// SQL Exception 등이 throw 되거나 기타 오류가 발생했을 때
		resp.sendRedirect(cp+"/book/book.do");
			// 세션에 이미 정보가 있으므로 그냥 쿠리 없이 던짐.
	}
	
	protected void bookConfirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약확인서 출력
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		BookSessionInfo bookInfo = (BookSessionInfo) session.getAttribute("book");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		// 예약 세션이 넘어오지 않았을 때 --> 캠핑장 리스트로 리다이렉트 (매우 중요)
		if (bookInfo == null) {
			resp.sendRedirect(cp+"/book/campList.do");
				// 캠핑장 리스트로 리다이렉트
			return;
		}		
		
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		try {
			/*
			keyword = URLDecoder.decode(keyword,"utf-8");
			
			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			*/
			
			// book에서 가져오기 (==> 얘도 세션으로 처리해야 할 듯)..
			// ==> 세션에서 bookNo 가져오기
			String bookNo = bookInfo.getBookNo();
			
			BookDTO dto = dao.readBook(bookNo); // 이거로 써야 함
			// BookDTO dto = dao.readBook("bookNo"); // 임시
			
			// 예약확인서가 없으면 메인으로 리턴.. 
			if (dto==null) {
				resp.sendRedirect(cp+"/");
				return;
			}
			
			dto.setBookRequest(util.htmlSymbols(dto.getBookRequest()));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			// req.setAttribute("query", query);
			
			// 예약확인서.jsp 로 포워딩
			forward(req,resp,"/WEB-INF/campingutte/book/confirm.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Redirect 처리
		resp.sendRedirect(cp+"/book/book.do");
		// resp.sendRedirect(cp+"/book/book.do?"+query);
		// 쿼리 추가 or 세션 수정 --> 했음.
	}
	
	// 예약 세션정보 지우기... (==== 예약완료 또는 예약취소 버튼 눌렀을 때 ====)
	protected void deleteBookSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		BookSessionInfo bookInfo = (BookSessionInfo) session.getAttribute("book");
		String cp = req.getContextPath();
		
		/*
			// 로그인 여부 확인
			if (memberInfo == null) { // 로그아웃 상태일 때
				resp.sendRedirect(cp+"/member/login.do");
				return;
			}
		*/
		
		if (bookInfo == null) {
			resp.sendRedirect(cp+"/"); // (cp+"/index.jsp") ?
				// 루트로 리다이렉트 (메인화면)
			return;
		}
		
		// 세션에 저장된 정보를 지움
		session.removeAttribute("book");
		// 루트로 리다이렉트
		resp.sendRedirect(cp+"/"); // (cp+"/index.jsp") ?
	}
	
	protected void bookList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약 리스트 (마이페이지에서 갑니다)
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색 없음
			
			// GET 방식인 경우 디코딩 --> 도 필요 없음
			
			// 전체 데이터 개수
			int dataCount;
			
			if (memberInfo.getMemberId() == null) {
				resp.sendRedirect(cp+"/member/login.do"); // 또는 로그아웃으로..
				return;
			}
			dataCount = dao.bookCount(memberInfo.getMemberId());
			
			// 전체 페이지 수
			int rows = 10;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 게시물 가져오기
			List<BookDTO> list = null;
			// 키워드 없음 -> 키워드 길이 비교 없음
			list = dao.listBook(start,end,memberInfo.getMemberId());
			
			// 리스트 글번호 만들기
			int listNum, n = 0;
			for (BookDTO dto : list) {
				listNum = dataCount - (start+n-1);
				dto.setBookArticleNo(listNum);
				n++;
			}
			
			// String query = ""; // X
			
			// 페이징 처리
			String listUrl = cp + "/book/bookList.do";
			String articleUrl = cp + "/book/bookArticle.do?page="+current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("ldataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			// req.setAttribute("condition", condition);
			// req.setAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포워딩
		forward(req,resp,"/WEB-INF/campingutte/book/bookList.jsp");
	}
	
	protected void bookArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약 글보기 --> (예약확인서와 비슷해요!) 이 화면에서 리뷰작성으로 넘어갑니닷
		BookDAO dao = new BookDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo)session.getAttribute("member");
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page = req.getParameter("page");
		String query = "page="+page;
		
		try {
			String bookNo = req.getParameter("bookNo"); // 예약번호 (bookNo): 받아와야 함
			
			// 예약 가져오기 (= 게시물 가져오기)
			BookDTO dto = dao.readBook(bookNo);
			
			if (dto == null) { // 예약이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/book/bookList.do?" + query);
				return;
			}
			
			dto.setBookRequest(util.htmlSymbols(dto.getBookRequest()));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			// 포워딩
			forward(req, resp, "/WEB-INF/campingutte/book/bookArticle.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 못찾았을때 리스트로 Redirect
		resp.sendRedirect(cp+"/book/bookList.do?"+query);
	}
	
	protected void bookUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약 수정 (수정 폼)
		BookDAO dao = new BookDAO();
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			String bookNo = req.getParameter("bookNo"); // 예약번호 (bookNo): 받아와야 함
			BookDTO dto = dao.readBook(bookNo);
			
			if (dto == null) {
				resp.sendRedirect(cp+"/book/BookList.do?page="+page);
				return;
			}
			
			// 예약한 사용자가 아니라면
			if (!dto.getMemberId().equals(memberInfo.getMemberId())) {
				resp.sendRedirect(cp+"/book/BookList.do?page="+page);
				return;
			}
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/campingutte/book/bookUpdate.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 수정 불가/오류일 때 리다이렉트 : 예약 리스트로...
		resp.sendRedirect(cp+"/book/BookList.do?page="+page);
	}
	
	protected void bookUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약 수정 완료
		BookDAO dao = new BookDAO();
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			BookDTO dto = new BookDTO();
			
			dto.setBookName(req.getParameter("bookName"));
			dto.setBookTel(req.getParameter("bookTel"));
			dto.setBookEmail(req.getParameter("bookEmail"));
			dto.setBookRequest(req.getParameter("bookRequest"));
			
			dao.updateBook(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 수정 오류 시 리다이렉트
		resp.sendRedirect(cp+"/book/bookList.do?page="+page);
	}
	
	
	protected void bookDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약 취소 (=게시글 삭제)
		BookDAO dao = new BookDAO();
		
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		// 로그인 여부 확인
		if (memberInfo == null) { // 로그아웃 상태일 때
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			String bookNo = req.getParameter("bookNo");
			System.out.println("delete_bookNo : " + bookNo);
			System.out.println("memberId : "+ memberInfo.getMemberId());
			
			dao.deleteBook(bookNo, memberInfo.getMemberId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/book/bookList.do?page="+page);
	}
}
