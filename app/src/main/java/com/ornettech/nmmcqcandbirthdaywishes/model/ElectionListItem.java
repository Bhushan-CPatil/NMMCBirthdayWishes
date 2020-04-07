package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class ElectionListItem{

	@SerializedName("ElectionName")
	private String electionName;

	public void setElectionName(String electionName){
		this.electionName = electionName;
	}

	public String getElectionName(){
		return electionName;
	}

	@Override
 	public String toString(){
		return 
			"ElectionListItem{" + 
			"electionName = '" + electionName + '\'' + 
			"}";
		}
}