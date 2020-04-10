package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SpinnerResponse{

	@SerializedName("ResponseListPojo")
	private List<ResponseListPojoItem> responseListPojo;

	@SerializedName("ElectionList")
	private List<ElectionListItem> electionList;

	@SerializedName("WardList")
	private List<WardListItem> wardList;

	@SerializedName("ExecutiveListPojo")
	private List<ExecutiveListPojoItem> executiveListPojo;

	public void setResponseListPojo(List<ResponseListPojoItem> responseListPojo){
		this.responseListPojo = responseListPojo;
	}

	public List<ResponseListPojoItem> getResponseListPojo(){
		return responseListPojo;
	}

	public void setElectionList(List<ElectionListItem> electionList){
		this.electionList = electionList;
	}

	public List<ElectionListItem> getElectionList(){
		return electionList;
	}

	public void setWardList(List<WardListItem> wardList){
		this.wardList = wardList;
	}

	public List<WardListItem> getWardList(){
		return wardList;
	}

	public void setExecutiveListPojo(List<ExecutiveListPojoItem> executiveListPojo){
		this.executiveListPojo = executiveListPojo;
	}

	public List<ExecutiveListPojoItem> getExecutiveListPojo(){
		return executiveListPojo;
	}

	@Override
 	public String toString(){
		return 
			"SpinnerResponse{" + 
			"responseListPojo = '" + responseListPojo + '\'' + 
			",electionList = '" + electionList + '\'' + 
			",wardList = '" + wardList + '\'' + 
			",executiveListPojo = '" + executiveListPojo + '\'' + 
			"}";
		}
}