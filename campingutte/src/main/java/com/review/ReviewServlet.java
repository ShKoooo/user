package com.review;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

public class ReviewServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		// 멤버 세션 정보
		HttpSession session = req.getSession();
		SessionInfo memberInfo = (SessionInfo) session.getAttribute("member");
		
		if (memberInfo == null) { // 로그아웃 상태에서는 로그인으로...
			forward(req, resp, "/WEB-INF/campingutte/member/login.jsp");
			return;
		}
		
		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			// 리뷰 리스트
			list(req,resp);
		} else if (uri.indexOf("write.do") != -1) {
			// 리뷰 작성 (글쓰기 폼)
			writeForm(req,resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			// 리뷰 작성 완료 (Submit)
			writeSubmit(req,resp);
		} else if (uri.indexOf("article.do") != -1) {
			// 리뷰 글보기
			article(req,resp);
		} else if (uri.indexOf("update.do") != -1) {
			// 리뷰 수정 폼
			updateForm(req,resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			// 리뷰 수정 (Submit)
			updateSubmit(req,resp);
		} else if (uri.indexOf("delete.do") != -1) {
			// 리뷰 삭제
			delete(req,resp);
		}	
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 데이터 개수
			int dataCount;
			dataCount = dao.dataCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
