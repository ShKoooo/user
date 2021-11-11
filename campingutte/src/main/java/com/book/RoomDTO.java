package com.book;

import java.util.List;

public class RoomDTO {
	private String roomNo;
	private String roomName;
	private int stdPers;
	private int maxPers;
	private int stdPrice;
	private int extraPrice;
	private String campNo;
	private String roomDetail;
	private List<RoomImageDTO> images;
	// 객실이미지?
	
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
	public List<RoomImageDTO> getImages() {
		return images;
	}
	public void setImages(List<RoomImageDTO> images) {
		this.images = images;
	}
}
