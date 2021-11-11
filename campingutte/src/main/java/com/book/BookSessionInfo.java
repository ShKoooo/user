package com.book;

/*
Session
(1) ID
(2) NAME
(3) (ROLL)
(4) SrtDate 시작일
(5) EndDate 종료일
(6) Addr1 장소
(7) People 인원
(8) CampName 캠프장명
(9) bookNo 예약번호
*/

public class BookSessionInfo {
	// 이하 검색관련 값
	private String srtDate;
	private String endDate;
	private String addr1;
	private String people; // 미입력 대비 String 형으로 저장 (필요시 int로 변경)
	private String campName; // 검색	캠핑장이름
	private String roomNo;
	private String bookNo;
	
	// 이하 검색관련 getter/setter
	public String getSrtDate() {
		return srtDate;
	}
	public void setSrtDate(String srtDate) {
		this.srtDate = srtDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getCampName() {
		return campName;
	}
	public void setCampName(String campName) {
		this.campName = campName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
}
