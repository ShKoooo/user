package com.book;

public class RoomDTO {
	private String RoomNo;
	private String RoomName;
	private int stdPers;
	private int maxPers;
	private int stdPrice;
	private int extraPrice;
	private String campNo;
	private String roomDetail;
	// 객실이미지?
	
	public String getRoomNo() {
		return RoomNo;
	}
	public void setRoomNo(String roomNo) {
		RoomNo = roomNo;
	}
	public String getRoomName() {
		return RoomName;
	}
	public void setRoomName(String roomName) {
		RoomName = roomName;
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
}
