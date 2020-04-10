package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class CorporatorsElectionWiseItem{

	@SerializedName("Corporator_Cd")
	private int corporatorCd;

	@SerializedName("BirthDayWishImage")
	private String birthDayWishImage;

	@SerializedName("Designation_Name_Mar")
	private String designationNameMar;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Corporator_Photo")
	private String corporatorPhoto;

	@SerializedName("Party")
	private String party;

	@SerializedName("Designation_Name")
	private String designationName;

	@SerializedName("Corporator_Name_Mar")
	private String corporatorNameMar;

	@SerializedName("Mobile_No_1")
	private String mobileNo1;

	@SerializedName("Ac_No")
	private int acNo;

	@SerializedName("Ward_No")
	private int wardNo;

	@SerializedName("Corporation_Name")
	private String corporationName;

	@SerializedName("Corporator_Name")
	private String corporatorName;

	public void setCorporatorCd(int corporatorCd){
		this.corporatorCd = corporatorCd;
	}

	public int getCorporatorCd(){
		return corporatorCd;
	}

	public void setBirthDayWishImage(String birthDayWishImage){
		this.birthDayWishImage = birthDayWishImage;
	}

	public String getBirthDayWishImage(){
		return birthDayWishImage;
	}

	public void setDesignationNameMar(String designationNameMar){
		this.designationNameMar = designationNameMar;
	}

	public String getDesignationNameMar(){
		return designationNameMar;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setCorporatorPhoto(String corporatorPhoto){
		this.corporatorPhoto = corporatorPhoto;
	}

	public String getCorporatorPhoto(){
		return corporatorPhoto;
	}

	public void setParty(String party){
		this.party = party;
	}

	public String getParty(){
		return party;
	}

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

	public void setCorporationName(String corporationName){
		this.corporationName = corporationName;
	}

	public String getCorporationName(){
		return corporationName;
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
			"CorporatorsElectionWiseItem{" + 
			"corporator_Cd = '" + corporatorCd + '\'' + 
			",birthDayWishImage = '" + birthDayWishImage + '\'' + 
			",designation_Name_Mar = '" + designationNameMar + '\'' + 
			",gender = '" + gender + '\'' + 
			",corporator_Photo = '" + corporatorPhoto + '\'' + 
			",party = '" + party + '\'' + 
			",designation_Name = '" + designationName + '\'' + 
			",corporator_Name_Mar = '" + corporatorNameMar + '\'' + 
			",mobile_No_1 = '" + mobileNo1 + '\'' + 
			",ac_No = '" + acNo + '\'' + 
			",ward_No = '" + wardNo + '\'' + 
			",corporation_Name = '" + corporationName + '\'' + 
			",corporator_Name = '" + corporatorName + '\'' + 
			"}";
		}
}