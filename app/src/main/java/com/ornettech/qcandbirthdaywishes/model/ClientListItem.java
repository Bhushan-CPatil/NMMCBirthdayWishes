package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class ClientListItem{

	@SerializedName("Corporator_Cd")
	private int corporatorCd;

	@SerializedName("Client_Cd")
	private int clientCd;

	@SerializedName("Client_QC_Cd")
	private int clientQCCd;

	@SerializedName("QC_Calling_Recording")
	private String qCCallingRecording;

	@SerializedName("QC_Response")
	private String qCResponse;

	@SerializedName("Designation_Name_Mar")
	private String designationNameMar;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Corporator_Photo")
	private String corporatorPhoto;

	@SerializedName("QC_Calling_UpdatedDate")
	private String qCCallingUpdatedDate;

	@SerializedName("Party")
	private String party;

	@SerializedName("Designation_Name")
	private String designationName;

	@SerializedName("Remark2")
	private String remark2;

	@SerializedName("Remark3")
	private String remark3;

	@SerializedName("Corporator_Name_Mar")
	private String corporatorNameMar;

	@SerializedName("QC_Calling_Status_Cd")
	private int qCCallingStatusCd;

	@SerializedName("Remark1")
	private String remark1;

	@SerializedName("QC_Calling_UpdatedByUser")
	private String qCCallingUpdatedByUser;

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

	@SerializedName("selecteddate")
	private String selecteddate;

	public String getSelecteddate() {
		return selecteddate;
	}

	public void setSelecteddate(String selecteddate) {
		this.selecteddate = selecteddate;
	}

	public void setCorporatorCd(int corporatorCd){
		this.corporatorCd = corporatorCd;
	}

	public int getCorporatorCd(){
		return corporatorCd;
	}

	public void setClientCd(int clientCd){
		this.clientCd = clientCd;
	}

	public int getClientCd(){
		return clientCd;
	}

	public void setClientQCCd(int clientQCCd){
		this.clientQCCd = clientQCCd;
	}

	public int getClientQCCd(){
		return clientQCCd;
	}

	public void setQCCallingRecording(String qCCallingRecording){
		this.qCCallingRecording = qCCallingRecording;
	}

	public String getQCCallingRecording(){
		return qCCallingRecording;
	}

	public void setQCResponse(String qCResponse){
		this.qCResponse = qCResponse;
	}

	public String getQCResponse(){
		return qCResponse;
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

	public void setQCCallingUpdatedDate(String qCCallingUpdatedDate){
		this.qCCallingUpdatedDate = qCCallingUpdatedDate;
	}

	public String getQCCallingUpdatedDate(){
		return qCCallingUpdatedDate;
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

	public void setRemark2(String remark2){
		this.remark2 = remark2;
	}

	public String getRemark2(){
		return remark2;
	}

	public void setRemark3(String remark3){
		this.remark3 = remark3;
	}

	public String getRemark3(){
		return remark3;
	}

	public void setCorporatorNameMar(String corporatorNameMar){
		this.corporatorNameMar = corporatorNameMar;
	}

	public String getCorporatorNameMar(){
		return corporatorNameMar;
	}

	public void setQCCallingStatusCd(int qCCallingStatusCd){
		this.qCCallingStatusCd = qCCallingStatusCd;
	}

	public int getQCCallingStatusCd(){
		return qCCallingStatusCd;
	}

	public void setRemark1(String remark1){
		this.remark1 = remark1;
	}

	public String getRemark1(){
		return remark1;
	}

	public void setQCCallingUpdatedByUser(String qCCallingUpdatedByUser){
		this.qCCallingUpdatedByUser = qCCallingUpdatedByUser;
	}

	public String getQCCallingUpdatedByUser(){
		return qCCallingUpdatedByUser;
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
			"ClientListItem{" + 
			"corporator_Cd = '" + corporatorCd + '\'' + 
			",client_Cd = '" + clientCd + '\'' + 
			",client_QC_Cd = '" + clientQCCd + '\'' + 
			",qC_Calling_Recording = '" + qCCallingRecording + '\'' + 
			",qC_Response = '" + qCResponse + '\'' + 
			",designation_Name_Mar = '" + designationNameMar + '\'' + 
			",gender = '" + gender + '\'' + 
			",corporator_Photo = '" + corporatorPhoto + '\'' + 
			",qC_Calling_UpdatedDate = '" + qCCallingUpdatedDate + '\'' + 
			",party = '" + party + '\'' + 
			",designation_Name = '" + designationName + '\'' + 
			",remark2 = '" + remark2 + '\'' + 
			",remark3 = '" + remark3 + '\'' + 
			",corporator_Name_Mar = '" + corporatorNameMar + '\'' + 
			",qC_Calling_Status_Cd = '" + qCCallingStatusCd + '\'' + 
			",remark1 = '" + remark1 + '\'' + 
			",qC_Calling_UpdatedByUser = '" + qCCallingUpdatedByUser + '\'' + 
			",mobile_No_1 = '" + mobileNo1 + '\'' + 
			",ac_No = '" + acNo + '\'' + 
			",ward_No = '" + wardNo + '\'' + 
			",corporation_Name = '" + corporationName + '\'' + 
			",selecteddate = '" + selecteddate + '\'' +
			",corporator_Name = '" + corporatorName + '\'' +
			"}";
		}
}