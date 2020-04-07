package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class QCResposeWiseReportItem{

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("VType")
	private String vtype;

	@SerializedName("QCByUser")
	private String QCByUser;

	@SerializedName("QCResponse")
	private String QCResponse;

	@SerializedName("RoomNo")
	private String roomNo;

	@SerializedName("SocietyName")
	private String societyName;

	@SerializedName("SurveyDate")
	private String surveyDate;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("ExecutiveName")
	private String executiveName;

	@SerializedName("QC_Calling_UpdatedDate")
	private String QC_Calling_UpdatedDate;

	@SerializedName("Ward_No")
	private int wardNo;

	public String getQC_Calling_UpdatedDate() {
		return QC_Calling_UpdatedDate;
	}

	public void setQC_Calling_UpdatedDate(String QC_Calling_UpdatedDate) {
		this.QC_Calling_UpdatedDate = QC_Calling_UpdatedDate;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getQCByUser() {
		return QCByUser;
	}

	public void setQCByUser(String QCByUser) {
		this.QCByUser = QCByUser;
	}

	public String getQCResponse() {
		return QCResponse;
	}

	public void setQCResponse(String QCResponse) {
		this.QCResponse = QCResponse;
	}

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setRoomNo(String roomNo){
		this.roomNo = roomNo;
	}

	public String getRoomNo(){
		return roomNo;
	}

	public void setSocietyName(String societyName){
		this.societyName = societyName;
	}

	public String getSocietyName(){
		return societyName;
	}

	public void setSurveyDate(String surveyDate){
		this.surveyDate = surveyDate;
	}

	public String getSurveyDate(){
		return surveyDate;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setExecutiveName(String executiveName){
		this.executiveName = executiveName;
	}

	public String getExecutiveName(){
		return executiveName;
	}

	public void setWardNo(int wardNo){
		this.wardNo = wardNo;
	}

	public int getWardNo(){
		return wardNo;
	}

	@Override
 	public String toString(){
		return 
			"QCResposeWiseReportItem{" + 
			"mobileNo = '" + mobileNo + '\'' + 
			",roomNo = '" + roomNo + '\'' +
			",QCByUser = '" + QCByUser + '\'' +
			",QCResponse = '" + QCResponse + '\'' +
			",societyName = '" + societyName + '\'' + 
			",surveyDate = '" + surveyDate + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",executiveName = '" + executiveName + '\'' + 
			",ward_No = '" + wardNo + '\'' + 
			",QC_Calling_UpdatedDate = '" + QC_Calling_UpdatedDate + '\'' +
			",vtype = '" + vtype + '\'' +
			"}";
		}
}