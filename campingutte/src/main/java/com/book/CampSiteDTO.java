package com.book;

import java.util.List;

public class CampSiteDTO {
	private int listNum;
	private String campNo;
	private String campName;
	private String campAddr1;
	private String campAddr2;
	private String campTel;
	private String campDetail;
	private String typeNo;
	private String typeName;
	private String campAdd;
	private List<CampsiteImageDTO> images;
	// 캠핑장 이미지? (따로?) 
	
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
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
	public List<CampsiteImageDTO> getImages() {
		return images;
	}
	public void setImages(List<CampsiteImageDTO> images) {
		this.images = images;
	}
	public String getCampAdd() {
		return campAdd;
	}
	public void setCampAdd(String campAdd) {
		this.campAdd = campAdd;
	}
}
