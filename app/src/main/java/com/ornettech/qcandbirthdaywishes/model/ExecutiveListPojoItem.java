package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class ExecutiveListPojoItem{

	@SerializedName("ExecutiveName")
	private String executiveName;

	public void setExecutiveName(String executiveName){
		this.executiveName = executiveName;
	}

	public String getExecutiveName(){
		return executiveName;
	}

	@Override
 	public String toString(){
		return 
			"ExecutiveListPojoItem{" + 
			"executiveName = '" + executiveName + '\'' + 
			"}";
		}
}