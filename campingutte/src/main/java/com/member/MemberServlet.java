package com.member;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
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
		} else if (uri.indexOf("signup.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
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
		
		String memberId = req.getParameter("userId");
		String memberPwd = req.getParameter("userPwd");
		
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
		// 회원가입 폼
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");

		forward(req, resp, "/WEB-INF/campingutte/member/signup.jsp");
	}

	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리
		MemberDAO dao = new MemberDAO();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String message = "";
		try {
			MemberDTO dto = new MemberDTO();
			dto.setMemberId(req.getParameter("memberId"));
			dto.setMemberPwd(req.getParameter("memberPwd"));
			dto.setMemberName(req.getParameter("memberName"));

			String memberBirth = req.getParameter("memberBirth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setMemberBirth(memberBirth);

			String memberEmail = req.getParameter("memberEmail");
			dto.setMemberEmail(memberEmail);
			
			String tel = req.getParameter("memberTel");
			dto.setMemberTel(tel);
			
			dto.setMemberAddr(req.getParameter("memberAddr"));
			dto.setMemberAddr2(req.getParameter("memberAddr2"));

			dao.signupMember(dto);
			resp.sendRedirect(cp + "/");
			return;
		} catch (SQLException e) {
			if (e.getErrorCode() == 1)
				message = "아이디가 중복됩니다.";
			else if (e.getErrorCode() == 1400)
				message = "빠진 항목 없이 입력하세요.";
			else if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861)
				message = "올바른 날짜 형식을 지정하세요. ";
			else
				message = "회원 가입이 실패 했습니다.";
		} catch (Exception e) {
			message = "회원 가입이 실패 했습니다.";
			e.printStackTrace();
		}

		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		req.setAttribute("message", message);
		forward(req, resp, "/WEB-INF/campingutte/member/signup.jsp");
	}

	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { 
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			MemberDTO dto = new MemberDTO();

			dto.setMemberId(req.getParameter("memberId"));
			dto.setMemberPwd(req.getParameter("memberPwd"));
			dto.setMemberName(req.getParameter("memberName"));
			dto.setMemberBirth(req.getParameter("memberBirth"));
			dto.setMemberEmail(req.getParameter("memberEmail"));
			dto.setMemberTel(req.getParameter("memberTel"));
			dto.setMemberAddr(req.getParameter("memberAddr"));
			dto.setMemberAddr2(req.getParameter("memberAddr2"));

			dao.updateMember(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}
	
	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		String mode = req.getParameter("mode");
		if (mode.equals("update")) {
			req.setAttribute("title", "회원정보 수정");
		}
		req.setAttribute("mode", mode);
		
		forward(req, resp, "WEB-INF/views/member/update.jsp");
		
		}
	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			MemberDTO dto = dao.readMember(info.getMemberId());
			if(dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}
			
			String memberPwd = req.getParameter("memberPwd");
			String mode = req.getParameter("mode");
			if(!dto.getMemberPwd().equals(memberPwd)) {
				if(mode.equals("update")) {
					req.setAttribute("title", "회원정보 수정");
				}
			req.setAttribute("mode", mode);
			req.setAttribute("message", "패스워드가 일치하지 않습니다.");
			forward(req, resp, "/WEB-INF/views/member/update.jsp");
			return;
			}
			
			req.setAttribute("title", "회원정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
			
	}
		
}
