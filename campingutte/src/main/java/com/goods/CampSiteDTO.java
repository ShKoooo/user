package com.goods;

public class CampSiteDTO {
	private String campNo; // 캠핑장 번호
	private String campName; // 캠핑장 이름
	private String campAddr1; // 기본주소
	private String campAddr2; // 상세주소
	private String campTel; // 전화번호
	private String campDetail; // 설명
	private String typeNo; // 유형번호
	private String typeName; // 유형이름
	private int imgNum; // 이미지 번호
	private String imgName; // 이미지 파일명
	private String[] imageFiles; // 이미지 파일 받을 배열
	private String campAdd; // 부대시설
	
	public String getCampNo() {
		return campNo;
	}
	public void setCampNo(String campNo) {
		this.campNo = campNo;
	}
	public String getCampName() {
		return campName;
	}
	public void setCampName(String campName) {
		this.campName = campName;
	}
	public String getCampAddr1() {
		return campAddr1;
	}
	public void setCampAddr1(String campAddr1) {
		this.campAddr1 = campAddr1;
	}
	public String getCampAddr2() {
		return campAddr2;
	}
	public void setCampAddr2(String campAddr2) {
		this.campAddr2 = campAddr2;
	}
	public String getCampTel() {
		return campTel;
	}
	public void setCampTel(String campTel) {
		this.campTel = campTel;
	}
	public String getCampDetail() {
		return campDetail;
	}
	public void setCampDetail(String campDetail) {
		this.campDetail = campDetail;
	}
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getImgNum() {
		return imgNum;
	}
	public void setImgNum(int imgNum) {
		this.imgNum = imgNum;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String[] getImageFiles() {
		return imageFiles;
	}
	public void setImageFiles(String[] imageFiles) {
		this.imageFiles = imageFiles;
	}
	public String getCampAdd() {
		return campAdd;
	}
	public void setCampAdd(String campAdd) {
		this.campAdd = campAdd;
	}
	
}
