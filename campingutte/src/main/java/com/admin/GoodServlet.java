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
import com.util.MyUtil;

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
		if(uri.indexOf("campList.do") != -1) { // 캠핑장 리스트(관리자가 봄)
			campList(req, resp);
		} else if(uri.indexOf("campTypeWrite.do") != -1) { // 캠핑장 유형 등록
			campTypeWriteForm(req, resp);
		} else if(uri.indexOf("campTypeWrite_ok.do") != -1) { // 캠핑장 유형 등록
			campTypeWriteSubmit(req, resp);
		} else if(uri.indexOf("campWrite.do") != -1) { // 캠핑장 정보 등록
			campWriteForm(req, resp);
		} else if(uri.indexOf("campWrite_ok.do") != -1) { // 캠핑장 정보 등록
			campWriteSubmit(req, resp);
		} else if(uri.indexOf("campUpdate.do") != -1) { // 캠핑장 정보 수정
			campUpdateForm(req, resp);
		} else if(uri.indexOf("campUpdate_ok.do") != -1) { // 캠핑장 정보 수정
			campUpdateSubmit(req, resp);
		} else if(uri.indexOf("campTypeDelete.do") != -1) { // 캠핑장 유형 삭제
			campTypeDelete(req, resp);
		} else if(uri.indexOf("campDelete.do") != -1) { // 캠핑장 정보 삭제 (캠핑장, 캠핑장 이미지, 객실, 객실 이미지)
			campDelete(req, resp);
		} else if (uri.indexOf("deleteCampImgFile") != -1) { // 수정에서 이미지파일만 삭제
			deleteCampImgFile(req, resp);
		} else if(uri.indexOf("campDetail.do") != -1) { // 캠핑장 글보기(클라이언트).
			campDetail(req, resp);
		}
		
				
		  else if(uri.indexOf("roomList.do") != -1) { // 객실리스트
			roomList(req, resp);
		} else if(uri.indexOf("roomWrite.do") != -1) { // 객실 등록
			roomWriteForm(req, resp);
		} else if(uri.indexOf("roomWrite_ok.do") != -1) { // 객실 등록
			roomWriteSubmit(req, resp);
		} else if(uri.indexOf("roomUpdate.do") != -1) { // 객실 수정
			roomUpdateForm(req, resp);
		} else if(uri.indexOf("roomUpdate_ok.do") != -1) { // 객실 수정
			roomUpdateSubmit(req, resp);
		} else if(uri.indexOf("roomDelete.do") != -1) { // 객실 삭제
			roomDelete(req, resp);
		} else if (uri.indexOf("deleteRoomImgFile") != -1) { // 수정에서 이미지파일만 삭제
			deleteRoomImgFile(req, resp);
		} else if(uri.indexOf("roomArticle.do") != -1) { // 객실상세 글보기(클라이언트)
			roomArticle(req, resp);
		}
		
	}
	
	// 등록한 유형 리스트..? (없어도 될거같아서 일단 보류)
	
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
	

	private void campTypeWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 유형 등록 폼
		forward(req, resp, "/WEB-INF/campingutte/admin/typeWrite.jsp");
	}
	
	
	private void campTypeWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 유형 등록 완료
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
			dto.setTypeNo(req.getParameter("typeNo"));
			dto.setTypeName(req.getParameter("typeName"));
					
			dao.insertCampType(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/admin/campList.do");
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
			dto.setTypeNo(req.getParameter("typeNo")); // 셀렉트 옵션으로 받기
			dto.setCampAdd(req.getParameter("campAdd"));
			
//			dto.setTypeName(req.getParameter("typeName")); // 잘 되면 지우기
			
			
			// 이미지 첨부
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
//			dao.insertCampType(dto); // 잘 되면 지우기 
			dao.insertCampSite(dto);
			dao.insertCampSiteImage(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/admin/campList.do");
	}
	
	
	private void campUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 정보 수정 폼
		CampSiteDAO dao = new CampSiteDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			String campNo = req.getParameter("campNo");
			CampSiteDTO dto = dao.readCampSite(campNo);

			if (dto == null) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			// 관리자가 아니면 메인으로
			if (! info.getMemberId().equals("admin")) { // 잘되나 봐야함 -> 안됨. 나중에 지울것임.
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
			dto.setCampAdd(req.getParameter("campAdd"));
			
//			dto.setTypeName(req.getParameter("typeName"));
			
			
			// 이미지 첨부
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
//			dao.updateCampType(dto);
			dao.updateCampSite(dto);
			dao.updateCampSiteImage(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
		
	}

	
	private void campTypeDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 유형 삭제
		CampSiteDAO dao = new CampSiteDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			MemberDTO mdto = new MemberDTO();
			
			String typeNo = req.getParameter("typeNo");
			CampSiteDTO dto = dao.readCampSite(typeNo); // 같은 스트링이니까 찾아지지 않을까..되는지 봐야한다.
			
			if (dto == null) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면(관리자가 아니면)
			// if (! mdto.getMemberId().equals(info.getMemberId())) {
			if (! mdto.getMemberId().equals("admin")) {	
				//resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				resp.sendRedirect(cp + "/main.do"); // 관리자아니면 여기 접근하면 안되니까 메인으로..? 
				return;
			}

			// 객실 유형 테이블 데이터 삭제
			dao.deleteCampType(typeNo, info.getMemberId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
	}
	
	
	private void campDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑장 정보 삭제
		CampSiteDAO dao = new CampSiteDAO();
		RoomDAO rdao = new RoomDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {			
			String campNo = req.getParameter("campNo");
			CampSiteDTO dto = dao.readCampSite(campNo);
			
			// 이게 될까...1
			String roomNo = req.getParameter("roomNo");
			RoomDTO rdto = rdao.readRoom(roomNo);
			
			if (dto == null) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (! info.getMemberId().equals("admin")) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}
			

			// 캠핑장 이미지 파일 지우기
			List<CampSiteDTO> listCampSiteImage = dao.listCampImgFile(campNo);
			for (CampSiteDTO vo : listCampSiteImage) {
				FileManager.doFiledelete(pathname, vo.getImgName());
			}
			
			
			// 이게 될까...2
			// 객실 이미지 파일 지우기
			List<RoomDTO> listRoomImage = rdao.listRoomImgFile(roomNo);
			for (RoomDTO vo : listRoomImage) {
				FileManager.doFiledelete(pathname, vo.getImgName());
			}
			
			
			// 테이블 데이터 삭제(객실이미지, 객실, 캠핑장이미지, 캠핑장 다 지워져야함)
			dao.deleteCampSite(campNo, info.getMemberId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
	}
	
	private void deleteCampImgFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 캠핑장 이미지 파일만 삭제
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
//			int imgNum = dto.getImgNum(); // 이미지 번호는 시퀀스라서 파라미터로 못가져와서 dto로 해보는데...제발됐으면...ㅎ
			int imgNum = Integer.parseInt(req.getParameter("imgNum"));

			if (dto == null) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}

			if (! info.getMemberId().equals(mdto.getMemberId())) {
				resp.sendRedirect(cp + "/admin/campList.do?page=" + page);
				return;
			}
			
			List<CampSiteDTO> listCampSiteImage = dao.listCampImgFile(campNo);

			for (CampSiteDTO vo : listCampSiteImage) {
				if (vo.getImgNum() == imgNum) {
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
			if (dto == null) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/selector/searchList.do?" + query);
//				resp.sendRedirect(cp + "/main.do");
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
	
	private void roomWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 등록 폼
		req.setAttribute("mode", "roomWrite");
		forward(req, resp, "/WEB-INF/campingutte/admin/roomWrite.jsp");
	}
	
	private void roomWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 등록 완료
		RoomDAO dao = new RoomDAO();
		
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
			RoomDTO dto = new RoomDTO();
			
			mdto.setMemberId(info.getMemberId());
			
			// 등록받을 파라미터들 (name으로 받아옴)
			dto.setRoomNo(req.getParameter("roomNo"));
			dto.setStdPers(Integer.parseInt(req.getParameter("stdPers")));
			dto.setMaxPers(Integer.parseInt(req.getParameter("maxPers")));
			dto.setStdPrice(Integer.parseInt(req.getParameter("stdPrice")));
			dto.setExtraPrice(Integer.parseInt(req.getParameter("extraPrice")));
			dto.setCampNo(req.getParameter("campNo"));
			dto.setRoomDetail(req.getParameter("roomDetail"));
			dto.setRoomName(req.getParameter("roomName"));
			
			
			// 이미지 첨부
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.insertRoom(dto);
			dao.insertRoomImage(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/admin/roomList.do");
	}
	
	private void roomUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 정보 수정 폼
		RoomDAO dao = new RoomDAO();
		
//		HttpSession session = req.getSession();
//		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
//			MemberDTO mdto = new MemberDTO();
			
			String roomNo = req.getParameter("roomNo");
			RoomDTO dto = dao.readRoom(roomNo);

			if (dto == null) {
				resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
				return;
			}

//			// 관리자가 아니면 메인으로
//			if (! mdto.getMemberId().equals(info.getMemberId())) { // 잘되나 봐야함 -> 안됨. 나중에 지울것임.
//				resp.sendRedirect(cp + "/main.do");
//				return;
//			}

			List<RoomDTO> listRoomImage = dao.listRoomImgFile(roomNo);

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listRoomImage", listRoomImage);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/campingutte/admin/roomWrite.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);		
	}
	
	private void roomUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		RoomDAO dao = new RoomDAO();
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/admin/roomList.do");
			return;
		}

		String page = req.getParameter("page");

		try {
			RoomDTO dto = new RoomDTO();

			// 우선 입력한거 다 수정 가능하게 해둠
			// 등록받을 파라미터들 (name으로 받아옴)
			dto.setRoomNo(req.getParameter("roomNo"));
			dto.setStdPers(Integer.parseInt(req.getParameter("stdPers")));
			dto.setMaxPers(Integer.parseInt(req.getParameter("maxPers")));
			dto.setStdPrice(Integer.parseInt(req.getParameter("stdPrice")));
			dto.setExtraPrice(Integer.parseInt(req.getParameter("extraPrice")));
			dto.setCampNo(req.getParameter("campNo"));
			dto.setRoomDetail(req.getParameter("roomDetail"));
			dto.setRoomName(req.getParameter("roomName"));
			
			
			// 이미지 첨부
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.updateRoom(dto);
			dao.updateRoomImage(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
	}
	
	private void roomDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 객실 정보 삭제
		RoomDAO dao = new RoomDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			// MemberDTO mdto = new MemberDTO();
			
			String roomNo = req.getParameter("roomNo");
			RoomDTO dto = dao.readRoom(roomNo);
			
			if (dto == null) {
				resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (! info.getMemberId().equals("admin")) {
				resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
				return;
			}

			// 객실 이미지 파일 지우기
			List<RoomDTO> listRoomImage = dao.listRoomImgFile(roomNo);
			for (RoomDTO vo : listRoomImage) {
				FileManager.doFiledelete(pathname, vo.getImgName());
			}	
			
			
			// 테이블 데이터 삭제(객실이미지, 객실 다 지워져야함)
			dao.deleteRoom(roomNo, info.getMemberId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);		
	}
	
	private void deleteRoomImgFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 객실 이미지 파일만 삭제
		RoomDAO dao = new RoomDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");

		try {
			MemberDTO mdto = new MemberDTO();
			
			String roomNo = req.getParameter("roomNo");
			RoomDTO dto = dao.readRoom(roomNo);
			
			// 얘 되나 보자☆★☆★☆★☆★☆★☆★☆★
//			int imgNum = dto.getImgNum(); // 이미지 번호는 시퀀스라서 파라미터로 못가져와서 dto로 해보는데...제발됐으면...ㅎ
			int imgNum = Integer.parseInt(req.getParameter("imgNum"));

			if (dto == null) {
				resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
				return;
			}

			if (! info.getMemberId().equals(mdto.getMemberId())) {
				resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
				return;
			}
			
			List<RoomDTO> listRoomImage = dao.listRoomImgFile(roomNo);

			for (RoomDTO vo : listRoomImage) {
				if (vo.getImgNum() == imgNum) {
					// 파일삭제
					FileManager.doFiledelete(pathname, vo.getImgName());
					dao.deleteRoomImage(imgNum);
					listRoomImage.remove(vo);
					break;
				}
			}

			req.setAttribute("dto", dto);
			req.setAttribute("listRoomImage", listRoomImage);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/campingutte/admin/roomWrite.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/roomList.do?page=" + page);
	}	
	
	private void roomArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
				resp.sendRedirect(cp + "/admin/campDetail.do?" + query);
//				resp.sendRedirect(cp + "/main.do");
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

		resp.sendRedirect(cp + "/admin/campDetail.do?" + query); // 경로 어디로 하지..?
//		resp.sendRedirect(cp + "/main.do");		
	}
	
}
