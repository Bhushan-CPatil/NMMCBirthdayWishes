package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class VoterDetailedDataItem{

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("RoomNo")
	private String roomNo;

	@SerializedName("UpdatedDate")
	private String updatedDate;

	@SerializedName("QC_Response")
	private String qCResponse;

	@SerializedName("Sex")
	private String sex;

	@SerializedName("MiddleName")
	private String middleName;

	@SerializedName("SMS_Flag")
	private String sMSFlag;

	@SerializedName("Name")
	private String name;

	@SerializedName("SMS_UpdatedDate")
	private String sMSUpdatedDate;

	@SerializedName("QC_Calling_UpdatedDate")
	private String qCCallingUpdatedDate;

	@SerializedName("QC_Calling_Recording")
	private String QC_Calling_Recording;

	@SerializedName("List_No")
	private int listNo;

	@SerializedName("SocietyName")
	private String societyName;

	@SerializedName("QC_Calling_Status_Cd")
	private String qCCallingStatusCd;

	@SerializedName("Voter_Cd")
	private String voterCd;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("Survey_Society_Cd")
	private int surveySocietyCd;

	@SerializedName("Ac_No")
	private int acNo;

	@SerializedName("tablename")
	private String tablename;

	@SerializedName("Voter_Id")
	private int voterId;

	@SerializedName("Ward_no")
	private int wardNo;

	@SerializedName("Surname")
	private String surname;

	@SerializedName("BirthDate")
	private String birthDate;

	@SerializedName("QC_Remark")
	private String QCRemark;

	public String getQCRemark() {
		return QCRemark;
	}

	public void setQCRemark(String QCRemark) {
		this.QCRemark = QCRemark;
	}

	public String getQC_Calling_Recording() {
		return QC_Calling_Recording;
	}

	public void setQC_Calling_Recording(String QC_Calling_Recording) {
		this.QC_Calling_Recording = QC_Calling_Recording;
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

	public void setUpdatedDate(String updatedDate){
		this.updatedDate = updatedDate;
	}

	public String getUpdatedDate(){
		return updatedDate;
	}

	public void setQCResponse(String qCResponse){
		this.qCResponse = qCResponse;
	}

	public String getQCResponse(){
		return qCResponse;
	}

	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return sex;
	}

	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public void setSMSFlag(String sMSFlag){
		this.sMSFlag = sMSFlag;
	}

	public String getSMSFlag(){
		return sMSFlag;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setSMSUpdatedDate(String sMSUpdatedDate){
		this.sMSUpdatedDate = sMSUpdatedDate;
	}

	public String getSMSUpdatedDate(){
		return sMSUpdatedDate;
	}

	public void setQCCallingUpdatedDate(String qCCallingUpdatedDate){
		this.qCCallingUpdatedDate = qCCallingUpdatedDate;
	}

	public String getQCCallingUpdatedDate(){
		return qCCallingUpdatedDate;
	}

	public void setListNo(int listNo){
		this.listNo = listNo;
	}

	public int getListNo(){
		return listNo;
	}

	public void setSocietyName(String societyName){
		this.societyName = societyName;
	}

	public String getSocietyName(){
		return societyName;
	}

	public void setQCCallingStatusCd(String qCCallingStatusCd){
		this.qCCallingStatusCd = qCCallingStatusCd;
	}

	public String getQCCallingStatusCd(){
		return qCCallingStatusCd;
	}

	public void setVoterCd(String voterCd){
		this.voterCd = voterCd;
	}

	public String getVoterCd(){
		return voterCd;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setSurveySocietyCd(int surveySocietyCd){
		this.surveySocietyCd = surveySocietyCd;
	}

	public int getSurveySocietyCd(){
		return surveySocietyCd;
	}

	public void setAcNo(int acNo){
		this.acNo = acNo;
	}

	public int getAcNo(){
		return acNo;
	}

	public void setTablename(String tablename){
		this.tablename = tablename;
	}

	public String getTablename(){
		return tablename;
	}

	public void setVoterId(int voterId){
		this.voterId = voterId;
	}

	public int getVoterId(){
		return voterId;
	}

	public void setWardNo(int wardNo){
		this.wardNo = wardNo;
	}

	public int getWardNo(){
		return wardNo;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setBirthDate(String birthDate){
		this.birthDate = birthDate;
	}

	public String getBirthDate(){
		return birthDate;
	}

	@Override
 	public String toString(){
		return 
			"VoterDetailedDataItem{" + 
			"mobileNo = '" + mobileNo + '\'' + 
			",roomNo = '" + roomNo + '\'' + 
			",updatedDate = '" + updatedDate + '\'' + 
			",qC_Response = '" + qCResponse + '\'' + 
			",sex = '" + sex + '\'' + 
			",middleName = '" + middleName + '\'' + 
			",sMS_Flag = '" + sMSFlag + '\'' + 
			",name = '" + name + '\'' + 
			",sMS_UpdatedDate = '" + sMSUpdatedDate + '\'' + 
			",QC_Calling_Recording = '" + QC_Calling_Recording + '\'' +
			",qC_Calling_UpdatedDate = '" + qCCallingUpdatedDate + '\'' +
			",list_No = '" + listNo + '\'' + 
			",societyName = '" + societyName + '\'' + 
			",qC_Calling_Status_Cd = '" + qCCallingStatusCd + '\'' + 
			",voter_Cd = '" + voterCd + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",survey_Society_Cd = '" + surveySocietyCd + '\'' + 
			",ac_No = '" + acNo + '\'' + 
			",tablename = '" + tablename + '\'' + 
			",voter_Id = '" + voterId + '\'' + 
			",ward_no = '" + wardNo + '\'' + 
			",surname = '" + surname + '\'' + 
			",QCRemark = '" + QCRemark + '\'' +
			",birthDate = '" + birthDate + '\'' +
			"}";
		}
}