package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

//@WebFilter("/*")
public class LoginFilter implements Filter {

	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info == null && isExcludeUri(req) == false) {
			if(isAjaxRequest(req)) {
				resp.sendError(403);
			} else {
				String uri = req.getRequestURI();
				String queryString = req.getQueryString();
				if(queryString != null) {
					uri += "?" + queryString;
				}
				session.setAttribute("preLoginURI", uri);
				resp.sendRedirect(cp+"/member/login.do");
			}
			return;
			
		}
		chain.doFilter(request, response);
	}
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	// 잘모르겠지만 AJAX 인지 꼭 확인해야한다.
	private boolean isAjaxRequest(HttpServletRequest req) {
		String h = req.getHeader("AJAX");
		
		return h != null && h.equals("true");
	}
	
	
	// 로그인 체크 
	private boolean isExcludeUri(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		uri = uri.substring(cp.length());
		
		//여기에 로그인이 필요없는걸 적는다. 회원가입을 하기위해 로그인을 요구하면 안되므로 login,signup을 넣음 
		String uris[] = {
				"/index.jsp", "/main.do",
				"/member/login_ok.do", "/member/login.do",
				"/member/signup_ok.do", "/member/signup.do",
				"/cs/faq.do", "/cs/help.do",
				"/resource/**", "/css/**",
				"/startbootstrap/**"
		};
		
		if(uri.length() <= 1) {
			return true;
		}
		
		for(String s : uris) {
			if(s.lastIndexOf("**") != -1) {
				s = s.substring(0, s.lastIndexOf("**"));
				if(uri.indexOf(s) == 0) {
					return true;
				}
			} else if(uri.equals(s)) {
				return true;
			}
		}
		
		return false;
	}
	

	
}
