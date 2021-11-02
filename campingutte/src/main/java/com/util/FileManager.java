package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

public class FileManager {
	/**
	 * 파일 다운로드 메소드
	 * @param saveFilename		서버에저장된파일명
	 * @param originalFilename	클라이언트가업로드한파일명
	 * @param pathname			서버에저장된경로
	 * @param resp				HttpServletResponse 객체
	 * @return					다운로드성공여부
	 */
	public static boolean doFiledownload(String saveFilename, String originalFilename, String pathname, HttpServletResponse resp) {
		boolean flag=false;
		
		BufferedInputStream bis = null; // 서버의 담긴 파일을 읽어내기 위해 사용.
		OutputStream os = null;
		
		try {
			originalFilename = new String(originalFilename.getBytes("euc-kr"), "8859_1"); // "euc-kr" : 8859_1로 변경할 목적으로 사용.   / "8859_1" : 네트워크로 송수신할 수 있게 함
			pathname = pathname + File.separator + saveFilename;
			File f = new File(pathname);
			if(! f.exists()) { // 만약 파일이 존재하지 않으면 빠져나간다.
				return flag;
			}
			
			// 클라이언트에게 전송 할 문서타입을 스트림으로 설정(파일을 전송할 때 application/octet-stream로 타입을 설정)
			resp.setContentType("application/octet-stream");
			
			// 파일명은 헤더에 실어서 전송 (브라우저에게 파일을 전송)
			resp.setHeader("Content-disposition", "attachment;filename=" + originalFilename); // "attachment;filename=" : 자체적인 형식이라 써줘야 함
			
			// 클라이언트에게 파일의 내용을 전송(파일은 문자스트림으로 보내면 인코딩되서, 절대로 바이트스트림으로 보내야 한다.)
			byte[] b = new byte[2048];
			bis = new BufferedInputStream(new FileInputStream(f)); // 서버에 저장된 파일을 읽어들임 
			
			// 클라이언트에게 전송할 출력 스트림
			os = resp.getOutputStream(); // 바이트스트림으로 전달. resp안에 다 있음
			
			int n;
			while((n = bis.read(b)) != -1) {
				os.write(b, 0, n); // 읽어 들여서 읽어들인만큼 전송. write(이 안의 내용을 출력한다, 이 위치부터, 읽어들인 길이만큼 전송해라) - 자바의 file에 자세한 설명이 더 있음
			}
			os.flush();
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (Exception e2) {
				}
			}
			
			if(os != null) {
				try {
					os.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return flag;
	}
	
	/**
	 * 파일 이름 변경(파일명을 "년월일시분초나노초.확장자" 로 변경)
	 * @param pathname	파일이저장된 경로
	 * @param filename	변경할 파일명
	 * @return			새로운파일명
	 */
	public static String doFilerename(String pathname, String filename) {
		String newname = "";
		
    	String fileExt = filename.substring(filename.lastIndexOf("."));
    	String s = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
				 Calendar.getInstance());
    	s += System.nanoTime();
    	s += fileExt;
    	
    	try {
	    	File f1 = new File(pathname+File.separator+filename);
	    	File f2 = new File(pathname+File.separator+s);
	    	f1.renameTo(f2);
	    	
	    	newname = s;
    	} catch(Exception e) {
    	}
		
		return newname;
	}
	
	/**
	 * 파일 삭제
	 * @param pathname	파일이 저장된 경로
	 * @param filename	삭제할 파일명
	 * @return			파일 삭제 성공 여부
	 */
	public static boolean doFiledelete(String pathname, String filename) {
		String path = pathname + File.separator + filename;
		
		return doFiledelete(path);
	}

	/**
	 * 파일 삭제
	 * @param pathname	파일이 저장된 경로 및 삭제할 파일명
	 * @return			파일 삭제 성공 여부
	 */
	public static boolean doFiledelete(String pathname) {
		try {
			File f = new File(pathname);
			
			if(! f.exists()) { // 파일이 없으면
				return false;
			}
			
			f.delete();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
