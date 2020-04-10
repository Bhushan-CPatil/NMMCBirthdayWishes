package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class SocietyRoomWiseQCListPojoItem{

	@SerializedName("roomcnt")
	private int roomcnt;

	@SerializedName("SocietyName")
	private String societyName;

	@SerializedName("Survey_Society_Cd")
	private int surveySocietyCd;

	@SerializedName("SubLocation_Cd")
	private int subLocationCd;

	public void setRoomcnt(int roomcnt){
		this.roomcnt = roomcnt;
	}

	public int getRoomcnt(){
		return roomcnt;
	}

	public void setSocietyName(String societyName){
		this.societyName = societyName;
	}

	public String getSocietyName(){
		return societyName;
	}

	public void setSurveySocietyCd(int surveySocietyCd){
		this.surveySocietyCd = surveySocietyCd;
	}

	public int getSurveySocietyCd(){
		return surveySocietyCd;
	}

	public void setSubLocationCd(int subLocationCd){
		this.subLocationCd = subLocationCd;
	}

	public int getSubLocationCd(){
		return subLocationCd;
	}

	@Override
 	public String toString(){
		return 
			"SocietyRoomWiseQCListPojoItem{" + 
			"roomcnt = '" + roomcnt + '\'' + 
			",societyName = '" + societyName + '\'' + 
			",survey_Society_Cd = '" + surveySocietyCd + '\'' + 
			",subLocation_Cd = '" + subLocationCd + '\'' + 
			"}";
		}
}