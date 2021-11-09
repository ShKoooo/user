package com.admin;

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

@MultipartConfig
@WebServlet("/admin/*")
public class GoodServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인 되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		} else if(! info.getMemberId().equals("admin")) { // 일단 관리자 아니면 못들어오게 하려고 넣긴했는데 나중에 보고 수정하든 지우든 할듯. 왜냐면 관리자아니면 메뉴도 안보이게 할까 생각중이라
			resp.sendRedirect(cp + "/main/main.do");
			return;
		}
		
		// 이미지를 저장할 경로(pathname).
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "admin"; // 이미지 실제 경로. 여기에 저장되야 이미지를 볼수 있음 (즉 얘는 그림저장하는 경로)
		
		
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
		} else if (uri.indexOf("deleteCampImgFile") != -1) { // 수정에서 이미지파일만 삭제
			deleteCampImgFile(req, resp);
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
	
	// 등록한 유형 리스트..?
	
	// 등록한 캠핑장 리스트
	private void campList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
	private void campWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 등록 폼 (코딩완료)
		req.setAttribute("mode", "campWrite");
		forward(req, resp, "/WEB-INF/campingutte/admin/campWrite.jsp");
	}
	
	
	private void campWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 등록 완료 (코딩완료)
		CampSiteDAO dao = new CampSiteDAO();
		
		// 관리자 정보 확인
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/main/main.do"); // 경로 바꿀듯
			return;
		}
		
		try {
			MemberDTO mdto = new MemberDTO();
			CampSiteDTO dto = new CampSiteDTO();
			
			mdto.setMemberId(info.getMemberId());
			
			// 등록받을 파라미터들 (name으로 받아옴)
			dto.setCampNo(req.getParameter("campNo"));
			dto.setCampName(req.getParameter("campName"));
			dto.setCampAddr1(req.getParameter("campAddr1"));
			dto.setCampAddr2(req.getParameter("campAddr2"));
			dto.setCampTel(req.getParameter("campTel"));
			dto.setCampDetail(req.getParameter("campDetail"));
			
			dto.setTypeNo(req.getParameter("typeNo"));
			dto.setTypeName(req.getParameter("typeName"));
			
			
			// 이미지 첨부
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.insertCampType(dto);
			dao.insertCampSite(dto);
			dao.insertCampSiteImage(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/main.do"); // 우선 메인으로 해둠
	}
	
	private void campUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 정보 수정 폼
		CampSiteDAO dao = new CampSiteDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			MemberDTO mdto = new MemberDTO();
			
			String campNo = req.getParameter("campNo");
			CampSiteDTO dto = dao.readCampSite(campNo);

			if (dto == null) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			// 관리자가 아니면 메인으로
			if (! mdto.getMemberId().equals(info.getMemberId())) { // 잘되나 봐야함
				resp.sendRedirect(cp + "/main.do");
				return;
			}

			List<CampSiteDTO> listCampSiteImage = dao.listCampImgFile(campNo);

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listCampSiteImage", listCampSiteImage);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/campingutte/admin/campWrite.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
	}
	
	private void campUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		CampSiteDAO dao = new CampSiteDAO();
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/admin/campList.do");
			return;
		}

		String page = req.getParameter("page");

		try {
			CampSiteDTO dto = new CampSiteDTO();

			// 우선 입력한거 다 수정 가능하게 해둠
			// 등록받을 파라미터들 (name으로 받아옴)
			dto.setCampNo(req.getParameter("campNo"));
			dto.setCampName(req.getParameter("campName"));
			dto.setCampAddr1(req.getParameter("campAddr1"));
			dto.setCampAddr2(req.getParameter("campAddr2"));
			dto.setCampTel(req.getParameter("campTel"));
			dto.setCampDetail(req.getParameter("campDetail"));
			
			dto.setTypeNo(req.getParameter("typeNo"));
			dto.setTypeName(req.getParameter("typeName"));
			
			
			// 이미지 첨부
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.updateCampType(dto);
			dao.updateCampSite(dto);
			dao.updateCampSiteImage(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
		
	}
	
	private void campDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 정보 삭제
		CampSiteDAO dao = new CampSiteDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			MemberDTO mdto = new MemberDTO();
			
			String campNo = req.getParameter("campNo");
			CampSiteDTO dto = dao.readCampSite(campNo);
			
			if (dto == null) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (! mdto.getMemberId().equals(info.getMemberId())) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			// 캠핑장 이미지 파일 지우기
			List<CampSiteDTO> listCampSiteImage = dao.listCampImgFile(campNo);
			for (CampSiteDTO vo : listCampSiteImage) {
				FileManager.doFiledelete(pathname, vo.getImgName());
			}
			
			// 객실 이미지 파일 지우기
			
			
			
			// 테이블 데이터 삭제(객실이미지, 객실, 캠핑장이미지, 캠핑장 다 지워져야함)
			dao.deleteCampSite(campNo, info.getMemberId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
	}
	
	private void deleteCampImgFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 파일만 삭제
		CampSiteDAO dao = new CampSiteDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");

		try {
			MemberDTO mdto = new MemberDTO();
			
			String campNo = req.getParameter("campNo");
			CampSiteDTO dto = dao.readCampSite(campNo);
			
			// 얘 되나 보자☆★☆★☆★☆★☆★☆★☆★
			int imgNum = dto.getImgNum(); // 이미지 번호는 시퀀스라서 파라미터로 못가져와서 dto로 해보는데...제발됐으면...ㅎ
			

//			if (dto == null) {
//				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
//				return;
//			}

			if (! info.getMemberId().equals(mdto.getMemberId())) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}
			
			List<CampSiteDTO> listCampSiteImage = dao.listCampImgFile(campNo);

			for (CampSiteDTO vo : listCampSiteImage) {
				if (vo.getImgNum() == imgNum) { // 이거 고쳐야함..
					// 파일삭제
					FileManager.doFiledelete(pathname, vo.getImgName());
					dao.deleteCampSiteImage(imgNum);
					listCampSiteImage.remove(vo);
					break;
				}
			}

			req.setAttribute("dto", dto);
			req.setAttribute("listCampSiteImage", listCampSiteImage);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/campingutte/admin/campWrite.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
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
