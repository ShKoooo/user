package com.cs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/cs/*")
public class BoardServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		
		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);		
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("faq.do") != -1) {
			faq(req, resp);
		} else if (uri.indexOf("insertReply.do") != -1) {
			insertReply(req, resp);
		} else if (uri.indexOf("help.do") != -1) {
			callPage(req, resp);
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if (info == null) { // 로그인 x
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}

			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 전체 데이터 개수 --> dataCount (+memberId)
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount(info.getMemberId());
				System.out.println("no");
			} else {
				dataCount = dao.dataCount(info.getMemberId(),condition, keyword);
				System.out.println("yes");
			}
			
			// 전체 페이지 수
			int rows = 10;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;

			System.out.println(info.getMemberId());
			// 게시물 가져오기
			List<BoardDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listBoard(info.getMemberId(),start, end);
			} else {
				list = dao.listBoard(info.getMemberId(),start, end, condition, keyword);
			}

			// 리스트 글번호 만들기
			int listNum, n = 0;
			for (BoardDTO dto : list) {
				listNum = dataCount - (start + n - 1);
				dto.setListNum(listNum);
				n++;
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/cs/list.do";
			String articleUrl = cp + "/cs/article.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/campingutte/cs/list.jsp");
	}

	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/campingutte/cs/write.jsp");
	}

	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		BoardDAO dao = new BoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			BoardDTO dto = new BoardDTO();
			dto.setMemberId(info.getMemberId());
			dto.setCompSubject(req.getParameter("compSubject"));
			dto.setCompContent(req.getParameter("compContent"));

			dao.insertBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/cs/list.do");
	}

	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		BoardDAO dao = new BoardDAO();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int compNo = Integer.parseInt(req.getParameter("compNo"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			// 조회수 증가
			//dao.updateHitCount(compNo);

			// 게시물 가져오기
			BoardDTO dto = dao.readBoard(compNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/cs/list.do?" + query);
				return;
			}
			
			ReplyDTO rdto = dao.readReply(compNo);

			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("rdto", rdto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);

			forward(req, resp, "/WEB-INF/campingutte/cs/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/cs/list.do?" + query);
	}

	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		BoardDAO dao = new BoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			int compNo = Integer.parseInt(req.getParameter("compNo"));
			BoardDTO dto = dao.readBoard(compNo);

			if (dto == null) {
				resp.sendRedirect(cp + "/cs/list.do?page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (! dto.getMemberId().equals(info.getMemberId())) {
				resp.sendRedirect(cp + "/cs/list.do?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/campingutte/cs/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/cs/list.do?page=" + page);
	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		BoardDAO dao = new BoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/cs/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			BoardDTO dto = new BoardDTO();
			dto.setCompNo(Integer.parseInt(req.getParameter("compNo")));
			dto.setCompSubject(req.getParameter("compSubject"));
			dto.setCompContent(req.getParameter("compContent"));

			dto.setMemberId(info.getMemberId());

			dao.updateBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/cs/list.do?page=" + page);
	}


	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		BoardDAO dao = new BoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int compNo = Integer.parseInt(req.getParameter("compNo"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			dao.deleteBoard(compNo, info.getMemberId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/cs/list.do?" + query);
	}
	
	private void faq(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		forward(req, resp, "/WEB-INF/campingutte/cs/faq.jsp");
	}
	
	private void callPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward(req, resp, "/WEB-INF/campingutte/cs/help.jsp");
	}
	
	
	private void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		try {
			ReplyDTO dto = new ReplyDTO();

			int compNo = Integer.parseInt(req.getParameter("compNo"));
			dto.setCompNo(compNo);
			dto.setMemberId(info.getMemberId());
			dto.setCompReplyContent(req.getParameter("compReplyContent"));
		
			
			dao.insertReply(dto);
			
			state = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
}
