package com.notice;

public class BoardDTO {
	private int listNum;
	private int notiNo;
	private String memberId;
	private String memberName;
	private String notiSubject;
	private String notiContent;
	private String notiDate;
	private int notiHitCount;
	private int replyCount;
	
	
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getNotiNo() {
		return notiNo;
	}
	public void setNotiNo(int notiNum) {
		this.notiNo = notiNum;
	}
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
	public String getNotiSubject() {
		return notiSubject;
	}
	public void setNotiSubject(String notiSubject) {
		this.notiSubject = notiSubject;
	}
	public String getNotiContent() {
		return notiContent;
	}
	public void setNotiContent(String notiContent) {
		this.notiContent = notiContent;
	}
	public String getNotiDate() {
		return notiDate;
	}
	public void setNotiDate(String notiDate) {
		this.notiDate = notiDate;
	}
	public int getNotiHitCount() {
		return notiHitCount;
	}
	public void setNotiHitCount(int notiHitCount) {
		this.notiHitCount = notiHitCount;
	}

	
}
