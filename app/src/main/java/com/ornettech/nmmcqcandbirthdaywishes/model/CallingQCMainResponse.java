package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CallingQCMainResponse{

	@SerializedName("VoterCallingQCPojo")
	private List<VoterCallingQCPojoItem> voterCallingQCPojo;

	public void setVoterCallingQCPojo(List<VoterCallingQCPojoItem> voterCallingQCPojo){
		this.voterCallingQCPojo = voterCallingQCPojo;
	}

	public List<VoterCallingQCPojoItem> getVoterCallingQCPojo(){
		return voterCallingQCPojo;
	}

	@Override
 	public String toString(){
		return 
			"CallingQCMainResponse{" + 
			"voterCallingQCPojo = '" + voterCallingQCPojo + '\'' + 
			"}";
		}
}