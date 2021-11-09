package com.book;

public class CampSiteDTO {
	private int listNum;
	private String CampNo;
	private String CampName;
	private String CampAddr1;
	private String CampAddr2;
	private String CampTel;
	private String CampDetail;
	private String TypeNo;
	private String TypeName;
	// 캠핑장 이미지? (따로?) 
	
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getCampNo() {
		return CampNo;
	}
	public void setCampNo(String campNo) {
		CampNo = campNo;
	}
	public String getCampName() {
		return CampName;
	}
	public void setCampName(String campName) {
		CampName = campName;
	}
	public String getCampAddr1() {
		return CampAddr1;
	}
	public void setCampAddr1(String campAddr1) {
		CampAddr1 = campAddr1;
	}
	public String getCampAddr2() {
		return CampAddr2;
	}
	public void setCampAddr2(String campAddr2) {
		CampAddr2 = campAddr2;
	}
	public String getCampTel() {
		return CampTel;
	}
	public void setCampTel(String campTel) {
		CampTel = campTel;
	}
	public String getCampDetail() {
		return CampDetail;
	}
	public void setCampDetail(String campDetail) {
		CampDetail = campDetail;
	}
	public String getTypeNo() {
		return TypeNo;
	}
	public void setTypeNo(String typeNo) {
		TypeNo = typeNo;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
}
