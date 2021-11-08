package com.community;

public class BoardDTO {
	private int listNum;
	private int commNo;
	private String memberId;
	private String memberName;
	private String commSubject;
	private String commContent;
	private String commDate;
	private int commHitCount;
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getCommNo() {
		return commNo;
	}
	public void setCommNo(int commNum) {
		this.commNo = commNum;
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
	public String getCommSubject() {
		return commSubject;
	}
	public void setCommSubject(String commSubject) {
		this.commSubject = commSubject;
	}
	public String getCommContent() {
		return commContent;
	}
	public void setCommContent(String commContent) {
		this.commContent = commContent;
	}
	public String getCommDate() {
		return commDate;
	}
	public void setCommDate(String commDate) {
		this.commDate = commDate;
	}
	public int getCommHitCount() {
		return commHitCount;
	}
	public void setCommHitCount(int commHitCount) {
		this.commHitCount = commHitCount;
	}

	
}
