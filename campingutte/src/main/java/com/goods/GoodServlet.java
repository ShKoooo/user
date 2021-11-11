package com.goods;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDTO;
import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/goods/*")
public class GoodServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();

// 캠핑장, 객실 상세 페이지는 비회원도 볼 수 있어야 하므로 세션처리 뺌.
//		HttpSession session = req.getSession();
//		SessionInfo info = (SessionInfo) session.getAttribute("member");
//		if (info == null) { // 로그인 되지 않은 경우
//			resp.sendRedirect(cp + "/member/login.do");
//			return;
//		} else if(! info.getMemberId().equals("admin")) { // 일단 관리자 아니면 못들어오게 하려고 넣긴했는데 나중에 보고 수정하든 지우든 할듯. 왜냐면 관리자아니면 메뉴도 안보이게 할까 생각중이라
//			resp.sendRedirect(cp + "/main/main.do");
//			return;
//		}	
//		// 이미지를 저장할 경로(pathname).
//		String root = session.getServletContext().getRealPath("/");
//		pathname = root + "uploads" + File.separator + "admin"; // 이미지 실제 경로. 여기에 저장되야 이미지를 볼수 있음 (즉 얘는 그림저장하는 경로)
		

		
		// uri에 따른 작업 구분
		if(uri.indexOf("campList.do") != -1) { // 캠핑장 리스트 (혹시 몰라서 일단 넣어두었다.)
			campList(req, resp);
		}else if(uri.indexOf("campDetail.do") != -1) { // 캠핑장 글보기(클라이언트).
			campDetail(req, resp);
		} else if(uri.indexOf("roomList.do") != -1) { // 객실리스트 (혹시 몰라서 일단 넣어두었다.)
			roomList(req, resp);
		}  else if(uri.indexOf("roomDetail.do") != -1) { // 객실상세 글보기(클라이언트)
			roomDetail(req, resp);
		}
		
	}
	
	
	// 등록한 캠핑장 리스트
	private void campList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampSiteDAO dao = new CampSiteDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int dataCount;
			dataCount = dao.dataCount();
			
			// 전체 페이지 수
			int rows = 10;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;

			// 게시물 가져오기
			List<CampSiteDTO> list = null;
			list = dao.listCampSite(start, end);

			// 페이징 처리
			String listUrl = cp + "/admin/campList.do";
			String articleUrl = cp + "/admin/campArticle.do?page=" + current_page;

			
			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/campingutte/admin/campList.jsp");
	}
	

	private void campDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 글보기 (클라이언트)
		CampSiteDAO dao = new CampSiteDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
//		HttpSession session = req.getSession();
//		SessionInfo info = (SessionInfo) session.getAttribute("member");

		// 검색 리스트로??		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			//int num = Integer.parseInt(req.getParameter("num"));
			String campNo = req.getParameter("campNo");
			
			// 게시물 가져오기
			CampSiteDTO dto = dao.readCampSite(campNo);
			if (dto == null) { // 게시물이 없으면 다시 검색리스트로
				resp.sendRedirect(cp + "/selector/searchList.do?" + query);
				return;
			}
			
			dto.setCampDetail(util.htmlSymbols(dto.getCampDetail()));

			
			// 캠핑장 이미지 파일 리스트
			List<CampSiteDTO> listFile = dao.listCampImgFile(campNo);
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("listFile", listFile);
			
			
			// 포워딩
			forward(req, resp, "/WEB-INF/campingutte/goods/campDetail.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/selector/searchList.do?" + query);
//		resp.sendRedirect(cp + "/main.do");
	}


	
	
	
	// 객실 리스트
	private void roomList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao = new RoomDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int dataCount;
			dataCount = dao.dataCount();
			
			// 전체 페이지 수
			int rows = 10;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;

			// 게시물 가져오기
			List<RoomDTO> list = null;
			list = dao.listRoom(start, end);

			// 페이징 처리
			String listUrl = cp + "/admin/roomList.do";
			String articleUrl = cp + "/admin/roomArticle.do?page=" + current_page;

			
			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/campingutte/admin/roomList.jsp"); // 경로는 룸리스트로???
	}
	

	
	private void roomDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 글보기 (클라이언트)
		RoomDAO dao = new RoomDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
//		HttpSession session = req.getSession();
//		SessionInfo info = (SessionInfo) session.getAttribute("member");

		// 검색 리스트로??		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			//int num = Integer.parseInt(req.getParameter("num"));
			String roomNo = req.getParameter("roomNo");
			
			// 게시물 가져오기
			RoomDTO dto = dao.readRoom(roomNo);
			if (dto == null) { // 게시물이 없으면 다시 캠핑상세로(주소 article인지 detail인지 확인하기)
				resp.sendRedirect(cp + "/goods/campDetail.do?" + query);
				return;
			}		
			dto.setRoomDetail(util.htmlSymbols(dto.getRoomDetail()));

			
			// 객실 이미지 파일 리스트
			List<RoomDTO> listFile = dao.listRoomImgFile(roomNo);
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("listFile", listFile);
			
			
			// 포워딩
			forward(req, resp, "/WEB-INF/campingutte/goods/roomDetail.jsp"); // 객실상세 주소 알아보고 넣기.
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/goods/campDetail.do?" + query); // 되나?
//		resp.sendRedirect(cp + "/main.do");		
	}
	
}
