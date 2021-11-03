package com.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

public class MemberServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		}
	}
	
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path = "/WEB-INF/campingutte/member/login.jsp";
		forward(req,resp,path);
	}
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리
		// 세션객체. 세션 정보는 서버에 저장(로그인 정보, 권한등을 저장)
		HttpSession session = req.getSession();
		
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		String memberId = req.getParameter("memberId");
		String memberPwd = req.getParameter("memberPwd");
		
		MemberDTO dto = dao.loginMember(memberId, memberPwd);
		
		if (dto != null) {
			session.setMaxInactiveInterval(20*60); // 20분
			
			// 세션에 저장할 내용 (id,name,roll)
			SessionInfo info = new SessionInfo();
			info.setMemberId(dto.getMemberId());
			info.setMemberName(dto.getMemberName());
			
			// 세션에 member이라는 이름으로 저장
			session.setAttribute("member", info);
			
			// 메인으로 리다이렉트
			resp.sendRedirect(cp+"/");
			return;
		}
		
		// 로그인 실패
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);
		
		forward(req,resp,"/WEB-INF/campingutte/member/login.jsp");
	}
	
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		// 세션 지움
		session.removeAttribute("member");
		
		// 세션 초기화
		session.invalidate();
		
		// root로 리다이렉트
		resp.sendRedirect(cp+"/");
	}
	
	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
