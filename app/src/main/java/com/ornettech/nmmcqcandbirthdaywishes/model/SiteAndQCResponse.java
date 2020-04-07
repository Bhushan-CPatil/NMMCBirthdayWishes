package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SiteAndQCResponse{

	@SerializedName("SiteNameListPojo")
	private List<SiteNameListPojoItem> siteNameListPojo;

	@SerializedName("ResponseListPojo")
	private List<ResponseListPojoItem> responseListPojo;

	public void setSiteNameListPojo(List<SiteNameListPojoItem> siteNameListPojo){
		this.siteNameListPojo = siteNameListPojo;
	}

	public List<SiteNameListPojoItem> getSiteNameListPojo(){
		return siteNameListPojo;
	}

	public void setResponseListPojo(List<ResponseListPojoItem> responseListPojo){
		this.responseListPojo = responseListPojo;
	}

	public List<ResponseListPojoItem> getResponseListPojo(){
		return responseListPojo;
	}

	@Override
 	public String toString(){
		return 
			"SiteAndQCResponse{" + 
			"siteNameListPojo = '" + siteNameListPojo + '\'' + 
			",responseListPojo = '" + responseListPojo + '\'' + 
			"}";
		}
}