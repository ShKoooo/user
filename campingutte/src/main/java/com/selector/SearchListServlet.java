package com.selector;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/select/*")
public class SearchListServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri=req.getRequestURI();
		
		if(uri.indexOf("searchList.do") != -1) {
			forward(req, resp, "/WEB-INF/campingutte/select/searchList.jsp");
		}
		
	}

}
