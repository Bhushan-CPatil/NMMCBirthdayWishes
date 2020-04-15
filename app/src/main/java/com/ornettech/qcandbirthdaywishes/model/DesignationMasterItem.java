package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class DesignationMasterItem{

	@SerializedName("Designation_Name")
	private String designationName;

	public void setDesignationName(String designationName){
		this.designationName = designationName;
	}

	public String getDesignationName(){
		return designationName;
	}

	@Override
 	public String toString(){
		return 
			"DesignationMasterItem{" + 
			"designation_Name = '" + designationName + '\'' + 
			"}";
		}
}