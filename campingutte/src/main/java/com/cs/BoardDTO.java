package com.cs;

public class BoardDTO {
	private int listNum;
	private int compNo;
	private String memberId;
	private String memberName;
	private String compSubject;
	private String compContent;
	private String compDate;
	private int compHitCount;
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
	public int getCompNo() {
		return compNo;
	}
	public void setCompNo(int compNum) {
		this.compNo = compNum;
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
	public String getCompSubject() {
		return compSubject;
	}
	public void setCompSubject(String compSubject) {
		this.compSubject = compSubject;
	}
	public String getCompContent() {
		return compContent;
	}
	public void setCompContent(String compContent) {
		this.compContent = compContent;
	}
	public String getCompDate() {
		return compDate;
	}
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	public int getCompHitCount() {
		return compHitCount;
	}
	public void setCompHitCount(int compHitCount) {
		this.compHitCount = compHitCount;
	}

	
}
