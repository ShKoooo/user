package com.member;

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
*/

public class SessionInfo {
	private String memberId;
	private String memberName;
	private int memberRoll;
	// 이하 검색관련 값 -- 삭제
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getMemberRoll() {
		return memberRoll;
	}
	public void setMemberRoll(int memberRoll) {
		this.memberRoll = memberRoll;
	}
	
	// 이하 검색관련 getter/setter -- 삭제
}
