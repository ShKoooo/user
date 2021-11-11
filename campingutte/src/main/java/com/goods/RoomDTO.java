package com.goods;

public class RoomDTO {
	private String roomNo; // 객실 번호
	private String roomName; // 객실 이름
	private int stdPers; // 기본예약인원
	private int maxPers; // 최대예약인원
	private int stdPrice; // 1박 기본요금
	private int extraPrice; // 초과인원 1박 요금
	private String campNo; // 캠핑장 번호
	private String roomDetail; /// 설명
	private int imgNum; // 이미지 번호
	private String imgName; // 이미지 파일명
	private String[] imageFiles; // 이미지 파일 받을 배열
	
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getStdPers() {
		return stdPers;
	}
	public void setStdPers(int stdPers) {
		this.stdPers = stdPers;
	}
	public int getMaxPers() {
		return maxPers;
	}
	public void setMaxPers(int maxPers) {
		this.maxPers = maxPers;
	}
	public int getStdPrice() {
		return stdPrice;
	}
	public void setStdPrice(int stdPrice) {
		this.stdPrice = stdPrice;
	}
	public int getExtraPrice() {
		return extraPrice;
	}
	public void setExtraPrice(int extraPrice) {
		this.extraPrice = extraPrice;
	}
	public String getCampNo() {
		return campNo;
	}
	public void setCampNo(String campNo) {
		this.campNo = campNo;
	}
	public String getRoomDetail() {
		return roomDetail;
	}
	public void setRoomDetail(String roomDetail) {
		this.roomDetail = roomDetail;
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
	
}
