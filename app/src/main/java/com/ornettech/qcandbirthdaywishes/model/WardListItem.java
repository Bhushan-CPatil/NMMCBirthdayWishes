package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class WardListItem{

	@SerializedName("Ward_No")
	private int wardNo;

	public void setWardNo(int wardNo){
		this.wardNo = wardNo;
	}

	public int getWardNo(){
		return wardNo;
	}

	@Override
 	public String toString(){
		return 
			"WardListItem{" + 
			"ward_No = '" + wardNo + '\'' + 
			"}";
		}
}