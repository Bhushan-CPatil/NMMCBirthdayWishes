package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class SiteNameListPojoItem{

	@SerializedName("SiteName")
	private String siteName;

	@SerializedName("Ward_No")
	private int wardNo;

	public void setSiteName(String siteName){
		this.siteName = siteName;
	}

	public String getSiteName(){
		return siteName;
	}

	public void setWardNo(int wardNo){
		this.wardNo = wardNo;
	}

	public int getWardNo(){
		return wardNo;
	}

	@Override
 	public String toString(){
		return 
			"SiteNameListPojoItem{" + 
			"siteName = '" + siteName + '\'' + 
			",ward_No = '" + wardNo + '\'' + 
			"}";
		}
}