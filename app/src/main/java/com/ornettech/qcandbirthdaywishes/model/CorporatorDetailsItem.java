package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class CorporatorDetailsItem{

	@SerializedName("Designation_Name")
	private String designationName;

	@SerializedName("Corporator_Name_Mar")
	private String corporatorNameMar;

	@SerializedName("MobileNo1")
	private String mobileNo1;

	@SerializedName("MobileNo2")
	private String mobileNo2;

	@SerializedName("Designation_Name_Mar")
	private String designationNameMar;

	@SerializedName("Corporator_Name")
	private String corporatorName;

	public void setDesignationName(String designationName){
		this.designationName = designationName;
	}

	public String getDesignationName(){
		return designationName;
	}

	public void setCorporatorNameMar(String corporatorNameMar){
		this.corporatorNameMar = corporatorNameMar;
	}

	public String getCorporatorNameMar(){
		return corporatorNameMar;
	}

	public void setMobileNo1(String mobileNo1){
		this.mobileNo1 = mobileNo1;
	}

	public String getMobileNo1(){
		return mobileNo1;
	}

	public void setMobileNo2(String mobileNo2){
		this.mobileNo2 = mobileNo2;
	}

	public String getMobileNo2(){
		return mobileNo2;
	}

	public void setDesignationNameMar(String designationNameMar){
		this.designationNameMar = designationNameMar;
	}

	public String getDesignationNameMar(){
		return designationNameMar;
	}

	public void setCorporatorName(String corporatorName){
		this.corporatorName = corporatorName;
	}

	public String getCorporatorName(){
		return corporatorName;
	}

	@Override
 	public String toString(){
		return 
			"CorporatorDetailsItem{" + 
			"designation_Name = '" + designationName + '\'' + 
			",corporator_Name_Mar = '" + corporatorNameMar + '\'' + 
			",mobileNo1 = '" + mobileNo1 + '\'' + 
			",mobileNo2 = '" + mobileNo2 + '\'' + 
			",designation_Name_Mar = '" + designationNameMar + '\'' + 
			",corporator_Name = '" + corporatorName + '\'' + 
			"}";
		}
}