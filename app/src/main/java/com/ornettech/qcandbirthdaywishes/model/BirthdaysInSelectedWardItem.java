package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class BirthdaysInSelectedWardItem{

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("QC_Birthday_Wish_Status")
	private String qCBirthdayWishStatus;

	@SerializedName("MiddleName")
	private String middleName;

	@SerializedName("Surname")
	private String surname;

	@SerializedName("Name")
	private String name;

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setQCBirthdayWishStatus(String qCBirthdayWishStatus){
		this.qCBirthdayWishStatus = qCBirthdayWishStatus;
	}

	public String getQCBirthdayWishStatus(){
		return qCBirthdayWishStatus;
	}

	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"BirthdaysInSelectedWardItem{" + 
			"mobileNo = '" + mobileNo + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",qC_Birthday_Wish_Status = '" + qCBirthdayWishStatus + '\'' + 
			",middleName = '" + middleName + '\'' + 
			",surname = '" + surname + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}