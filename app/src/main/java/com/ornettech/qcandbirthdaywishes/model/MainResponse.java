package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MainResponse{

	@SerializedName("RoomWiseDetailedListPojo")
	private List<RoomWiseDetailedListPojoItem> roomWiseDetailedListPojo;

	public void setRoomWiseDetailedListPojo(List<RoomWiseDetailedListPojoItem> roomWiseDetailedListPojo){
		this.roomWiseDetailedListPojo = roomWiseDetailedListPojo;
	}

	public List<RoomWiseDetailedListPojoItem> getRoomWiseDetailedListPojo(){
		return roomWiseDetailedListPojo;
	}

	@Override
 	public String toString(){
		return 
			"MainResponse{" + 
			"roomWiseDetailedListPojo = '" + roomWiseDetailedListPojo + '\'' + 
			"}";
		}
}