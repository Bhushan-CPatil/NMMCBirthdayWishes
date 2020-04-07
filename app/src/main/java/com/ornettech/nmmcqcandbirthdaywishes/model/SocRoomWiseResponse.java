package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SocRoomWiseResponse{

	@SerializedName("SocietyRoomWiseQCListPojo")
	private List<SocietyRoomWiseQCListPojoItem> societyRoomWiseQCListPojo;

	public void setSocietyRoomWiseQCListPojo(List<SocietyRoomWiseQCListPojoItem> societyRoomWiseQCListPojo){
		this.societyRoomWiseQCListPojo = societyRoomWiseQCListPojo;
	}

	public List<SocietyRoomWiseQCListPojoItem> getSocietyRoomWiseQCListPojo(){
		return societyRoomWiseQCListPojo;
	}

	@Override
 	public String toString(){
		return 
			"SocRoomWiseResponse{" + 
			"societyRoomWiseQCListPojo = '" + societyRoomWiseQCListPojo + '\'' + 
			"}";
		}
}