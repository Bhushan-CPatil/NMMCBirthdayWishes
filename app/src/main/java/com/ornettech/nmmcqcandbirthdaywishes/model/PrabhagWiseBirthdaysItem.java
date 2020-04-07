package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class PrabhagWiseBirthdaysItem{

	@SerializedName("Designation_Name")
	private String designationName;

	@SerializedName("Corporator_Name_Mar")
	private String corporatorNameMar;

	@SerializedName("MobileNo1")
	private String mobileNo1;

	@SerializedName("MobileNo2")
	private String mobileNo2;

	@SerializedName("TotalBirthDays")
	private int totalBirthDays;

	@SerializedName("Designation_Name_Mar")
	private String designationNameMar;

	@SerializedName("Ac_No")
	private int acNo;

	@SerializedName("Ward_No")
	private int wardNo;

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

	public void setTotalBirthDays(int totalBirthDays){
		this.totalBirthDays = totalBirthDays;
	}

	public int getTotalBirthDays(){
		return totalBirthDays;
	}

	public void setDesignationNameMar(String designationNameMar){
		this.designationNameMar = designationNameMar;
	}

	public String getDesignationNameMar(){
		return designationNameMar;
	}

	public void setAcNo(int acNo){
		this.acNo = acNo;
	}

	public int getAcNo(){
		return acNo;
	}

	public void setWardNo(int wardNo){
		this.wardNo = wardNo;
	}

	public int getWardNo(){
		return wardNo;
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
			"PrabhagWiseBirthdaysItem{" + 
			"designation_Name = '" + designationName + '\'' + 
			",corporator_Name_Mar = '" + corporatorNameMar + '\'' + 
			",mobileNo1 = '" + mobileNo1 + '\'' + 
			",mobileNo2 = '" + mobileNo2 + '\'' + 
			",totalBirthDays = '" + totalBirthDays + '\'' + 
			",designation_Name_Mar = '" + designationNameMar + '\'' + 
			",ac_No = '" + acNo + '\'' + 
			",ward_No = '" + wardNo + '\'' + 
			",corporator_Name = '" + corporatorName + '\'' + 
			"}";
		}
}