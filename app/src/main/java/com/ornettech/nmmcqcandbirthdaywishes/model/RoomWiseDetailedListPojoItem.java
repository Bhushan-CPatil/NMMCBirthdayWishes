package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RoomWiseDetailedListPojoItem{

	@SerializedName("RoomNo")
	private String roomNo;

	@SerializedName("VoterDetailedData")
	private List<VoterDetailedDataItem> voterDetailedData;

	public void setRoomNo(String roomNo){
		this.roomNo = roomNo;
	}

	public String getRoomNo(){
		return roomNo;
	}

	public void setVoterDetailedData(List<VoterDetailedDataItem> voterDetailedData){
		this.voterDetailedData = voterDetailedData;
	}

	public List<VoterDetailedDataItem> getVoterDetailedData(){
		return voterDetailedData;
	}

	@Override
 	public String toString(){
		return 
			"RoomWiseDetailedListPojoItem{" + 
			"roomNo = '" + roomNo + '\'' + 
			",voterDetailedData = '" + voterDetailedData + '\'' + 
			"}";
		}
}