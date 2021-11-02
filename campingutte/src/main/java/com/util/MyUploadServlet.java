package com.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
	// location = "c:/temp",			// 파일을 임시로 저장할 경로(생략가능. 기본값 ""), 지정된 경로가 없으면 업로드가 안됨
	fileSizeThreshold = 1024*1024,	// 업로드된 파일이 임시로 서버에 저장되지 않고 메모리에서 스트림으로 바로 전달되는 크기
	maxFileSize = 1024*1024*5,		// 업로드된 하나의 파일 크기. 기본 용량 제한 없음 (막지 않으면 무한루프에 빠짐. 여기선 5메가로 막음
	maxRequestSize = 1024*1024*10	// 폼 전체 용량 (여기도 파일 용량 제한 줘야함. 10메가로 해놓음)
)
public abstract class MyUploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	/**
	 * 단일 파일 업로드
	 * @param p			Part 객체
	 * @param pathname	서버에 파일을 저장할 경로
	 * @return			서버에 저장된 파일명, 클라이언트가 업로드한 파일명
	 */
	protected Map<String, String> doFileUpload(Part p, String pathname) throws ServletException, IOException {
		Map<String, String> map = null;
		
		try {
			File f=new File(pathname);
			if(! f.exists()) { // 폴더가 존재하지 않으면,
				f.mkdirs(); // 폴더를 만듬
			}
			
			// 클라이언트가 올린 파일이름에 공백같은게 있으면 웹에서 볼수없는 등 문제가 생긴다. 때문에 거의 대부분 서버에 저장할 때는 숫자로 파일이름을 바꿔서 저장한다.
			String originalFilename=getOriginalFilename(p);
			if(originalFilename==null || originalFilename.length()==0) // 파일 이름이 null이거나 길이가 0일때 파일 업로드 안한다.
				return null; // 첨부파일이 없는 것.
			
			String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
			String saveFilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
				 Calendar.getInstance());
			saveFilename += System.nanoTime();
			saveFilename += fileExt;
			
			// 서버에 파일 저장하기 : 디비에는 파일 이름만 저장하는 것이고, 파일의 내용 자체는 서버컴퓨터에만 저장해놓는다. 아주 특별한 이유가 있지 않는 이상 디비에 파일을 저장하면 부하생긴다.
			// 여기서 pathname은 BoardServlet의 Map<String, String> map = doFileUpload(p, pathname); 에서 넘어온다.
			String fullpath = pathname+File.separator+saveFilename; // File.separator : 파일 구분자. 경로를 설정해주기 위해 필요 /같은 것
			p.write(fullpath); // write : 단순하게 파일 저장
			// part는 파일 5개 보내면 part가 5개
			
			map = new HashMap<>();
			map.put("originalFilename", originalFilename);
			map.put("saveFilename", saveFilename);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 다중 파일 업로드
	 * @param parts		클라이언트가 서버로 전송한 모든 Part 객체
	 * @param pathname	서버에 파일을 저장할 경로 
	 * @return			서버에 저장된 파일명, 클라이언트가 올린 파일명
	 */
	protected Map<String, String[]> doFileUpload(Collection<Part> parts, String pathname) throws ServletException, IOException {
		Map<String, String[]> map = null;
		try {
			File f=new File(pathname);
			if(! f.exists()) { // 폴더가 존재하지 않으면
				f.mkdirs();
			}
			
			String original, save, ext;
			List<String> listOriginal=new ArrayList<String>();
			List<String> listSave=new ArrayList<String>();
			
			for(Part p : parts) {
				String contentType = p.getContentType();
/*				
			      if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			         // multipart
			      }				
*/
				// contentType 가 null 인 경우는 파일이 아닌 경우이다.(<input type="text"... 등)
				if(contentType != null) { // 파일이면
					original = getOriginalFilename(p);
					if(original == null || original.length() == 0 ) continue;
					
					ext = original.substring(original.lastIndexOf("."));
					save = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
						 Calendar.getInstance());
					save += System.nanoTime();
					save += ext;
					
					String fullpath = pathname+File.separator+save;
					p.write(fullpath);
					
					listOriginal.add(original);
					listSave.add(save);
					// Long size = p.getSize()); // 파일 크기
				}
			}		
			
			if(listOriginal.size() != 0) {
				String [] originals = listOriginal.toArray(new String[listOriginal.size()]);
				String [] saves = listSave.toArray(new String[listSave.size()]);
				
				map = new HashMap<>();
				
				map.put("originalFilenames", originals);
				map.put("saveFilenames", saves);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private String getOriginalFilename(Part p) {
		try {
			for(String s: p.getHeader("content-disposition").split(";")) {
				if(s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=")+1).trim().replace("\"","");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
