package com.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.MemberDTO;
import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/goods/*")
public class GoodServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// uri에 따른 작업 구분
		if(uri.indexOf("campList.do") != -1) {
			campList(req, resp);
		} else if(uri.indexOf("campWrite.do") != -1) {
			campWriteForm(req, resp);
		} else if(uri.indexOf("campWrite_ok.do") != -1) {
			campWriteSubmit(req, resp);
		} else if(uri.indexOf("campUpdate.do") != -1) {
			campUpdateForm(req, resp);
		} else if(uri.indexOf("campUpdate_ok.do") != -1) {
			campUpdateSubmit(req, resp);
		} else if(uri.indexOf("campDelete.do") != -1) {
			campDelete(req, resp);
		} else if(uri.indexOf("campArticle.do") != -1) {
			campArticle(req, resp);
		} else if(uri.indexOf("roomList.do") != -1) {
			roomList(req, resp);
		} else if(uri.indexOf("roomWrite.do") != -1) {
			roomWriteForm(req, resp);
		} else if(uri.indexOf("roomWrite_ok.do") != -1) {
			roomWriteSubmit(req, resp);
		} else if(uri.indexOf("roomUpdate.do") != -1) {
			roomUpdateForm(req, resp);
		} else if(uri.indexOf("roomUpdate_ok.do") != -1) {
			roomUpdateSubmit(req, resp);
		} else if(uri.indexOf("roomDelete.do") != -1) {
			roomDelete(req, resp);
		} else if(uri.indexOf("roomArticle.do") != -1) {
			roomArticle(req, resp);
		}
		
	}
	
	
	private void campList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
	private void campWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 등록 폼
		req.setAttribute("mode", "campWrite");
		forward(req, resp, "/WEB-INF/campingutte/admin/campWrite.jsp");
	}
	
	
	private void campWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 등록 완료
		CampSiteDAO dao = new CampSiteDAO();
		
		// 관리자 정보 확인
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			MemberDTO mdto = new MemberDTO();
			CampSiteDTO dto = new CampSiteDTO();
			
			mdto.setMemberId(info.getMemberId());
			
			// 파라미터
			dto.setCampNo(cp);
			
			// 이미지 첨부
			Part p = req.getPart("selectFile");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/campingutte/main.do"); // 우선 메인으로 해둠
	}
	
	private void campUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void campUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void campDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void campArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void roomList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}
	
	private void roomWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 등록 폼
		req.setAttribute("mode", "roomWrite");
		forward(req, resp, "/WEB-INF/campingutte/admin/roomWrite.jsp");
	}
	
	private void roomWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void roomUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void roomUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void roomDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void roomArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}
