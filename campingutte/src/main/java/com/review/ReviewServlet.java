package com.review;

import java.io.IOException;
import java.util.List;

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
		if (uri.indexOf("listMyBookReview.do") != -1) {
			// 예약 글보기에서 리뷰 리스트 (참고: BoardServlet - listReply)
				// 해당 예약번호에 해당하는 한개의 리뷰만 가져오기
				// AJAX - Text
			listMyBookReview(req, resp);
		} else if (uri.indexOf("insertReview.do") != -1) {
			// 리뷰 작성 (참고: BoardServlet - insertReply)
				// AJAX - JSON
			insertReview(req, resp);
		} else if (uri.indexOf("listCampReview.do")!= -1) {
			// 캠핑장에서의 리뷰 리스트 (참고: BoardServlet - listReply)	
				// 해당 캠핑장의 모든 리뷰 가져오기
				// AJAX - Text
			listCampReview(req, resp);
		} else if (uri.indexOf("deleteReview.do")!= -1) {
			// 캠핑장에서의 리뷰 삭제 (참고: BoardServlet - deleteReview)
				// AJAX - JSON
			deleteReview(req, resp);
		}
	}
	
	// 예약 글보기에서 리뷰 리스트 (참고: BoardServlet - listReply)
	// AJAX - Text
	protected void listMyBookReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();
		
		try {
			// 하나만 받아오므로 페이징처리 필요없음.
			
			String bookNo = req.getParameter("bookNoR"); // 파라미터값에 유의
					
			List<ReviewDTO> listReview = dao.listReview1(bookNo);
			
			for(ReviewDTO dto : listReview) {
				dto.setReviewComment(dto.getReviewComment().replaceAll("\n", "<br>"));
			}
			
			req.setAttribute("listReview", listReview);
			
			forward(req, resp, "/WEB-INF/campingutte/review/listMyBookReview.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(405);
	}
	
	// 리뷰 작성 (참고: BoardServlet - insertReply)
	// AJAX : JSON
	protected void insertReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String state = "false";
		
		try {
			ReviewDTO dto = new ReviewDTO();
			
			String bookNo = req.getParameter("bookNoR");
			dto.setReviewNo(bookNo+info.getMemberId());
			dto.setBookNo(bookNo);
			dto.setMemberId(info.getMemberId());
			dto.setReviewComment(req.getParameter("comment"));
			dto.setReviewStar(req.getParameter("star"));
			
			// dao.insertReview(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 캠핑장에서의 리뷰 리스트 (참고: BoardServlet - listReply)	
	protected void listCampReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	// 캠핑장에서의 리뷰 삭제 (참고: BoardServlet - deleteReview)
	// 해당 작성한 사람 또는 어드민만 (--> jsp에서 차단)
	protected void deleteReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
