package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class BirthdaysInWardItem{

	@SerializedName("tablename")
	private String tablename;

	@SerializedName("MobileNo")
	private String mobileNo;

	@SerializedName("RoomNo")
	private String roomNo;

	@SerializedName("SiteName")
	private String siteName;

	@SerializedName("PocketName")
	private String PocketName;

	@SerializedName("visible")
	private String visible;

	@SerializedName("UpdatedDate")
	private String updatedDate;

	@SerializedName("Sex")
	private String sex;

	@SerializedName("MiddleName")
	private String middleName;

	@SerializedName("QC_Birthday_Wish_updatedDate")
	private String qCBirthdayWishUpdatedDate;

	@SerializedName("Name")
	private String name;

	@SerializedName("List_No")
	private int listNo;

	@SerializedName("SocietyName")
	private String societyName;

	@SerializedName("Voter_Cd")
	private String voterCd;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("QC_Birthday_Wish_Status")
	private String qCBirthdayWishStatus;

	@SerializedName("QC_Birthday_Recording")
	private String QC_Birthday_Recording;

	@SerializedName("QC_Calling_UpdatedByUser")
	private String qCCallingUpdatedByUser;

	@SerializedName("Ac_No")
	private int acNo;

	@SerializedName("UpdateByUser")
	private String updateByUser;

	@SerializedName("Voter_Id")
	private int voterId;

	@SerializedName("Surname")
	private String surname;

	@SerializedName("Ward_no")
	private int wardNo;

	@SerializedName("Age")
	private int age;

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

	public String getQC_Birthday_Recording() {
		return QC_Birthday_Recording;
	}

	public void setQC_Birthday_Recording(String QC_Birthday_Recording) {
		this.QC_Birthday_Recording = QC_Birthday_Recording;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getPocketName() {
		return PocketName;
	}

	public void setPocketName(String pocketName) {
		PocketName = pocketName;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
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

	public void setSiteName(String siteName){
		this.siteName = siteName;
	}

	public String getSiteName(){
		return siteName;
	}

	public void setUpdatedDate(String updatedDate){
		this.updatedDate = updatedDate;
	}

	public String getUpdatedDate(){
		return updatedDate;
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

	public void setQCBirthdayWishUpdatedDate(String qCBirthdayWishUpdatedDate){
		this.qCBirthdayWishUpdatedDate = qCBirthdayWishUpdatedDate;
	}

	public String getQCBirthdayWishUpdatedDate(){
		return qCBirthdayWishUpdatedDate;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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

	public void setQCBirthdayWishStatus(String qCBirthdayWishStatus){
		this.qCBirthdayWishStatus = qCBirthdayWishStatus;
	}

	public String getQCBirthdayWishStatus(){
		return qCBirthdayWishStatus;
	}

	public void setQCCallingUpdatedByUser(String qCCallingUpdatedByUser){
		this.qCCallingUpdatedByUser = qCCallingUpdatedByUser;
	}

	public String getQCCallingUpdatedByUser(){
		return qCCallingUpdatedByUser;
	}

	public void setAcNo(int acNo){
		this.acNo = acNo;
	}

	public int getAcNo(){
		return acNo;
	}

	public void setUpdateByUser(String updateByUser){
		this.updateByUser = updateByUser;
	}

	public String getUpdateByUser(){
		return updateByUser;
	}

	public void setVoterId(int voterId){
		this.voterId = voterId;
	}

	public int getVoterId(){
		return voterId;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setWardNo(int wardNo){
		this.wardNo = wardNo;
	}

	public int getWardNo(){
		return wardNo;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
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
			"BirthdaysInWardItem{" + 
			"tablename = '" + tablename + '\'' +
			",mobileNo = '" + mobileNo + '\'' + 
			",QC_Birthday_Recording = '" + QC_Birthday_Recording + '\'' +
			",roomNo = '" + roomNo + '\'' +
			",siteName = '" + siteName + '\'' + 
			",PocketName = '" + PocketName + '\'' +
			",visible = '" + visible + '\'' +
			",updatedDate = '" + updatedDate + '\'' +
			",sex = '" + sex + '\'' + 
			",middleName = '" + middleName + '\'' + 
			",qC_Birthday_Wish_updatedDate = '" + qCBirthdayWishUpdatedDate + '\'' + 
			",name = '" + name + '\'' + 
			",list_No = '" + listNo + '\'' + 
			",societyName = '" + societyName + '\'' + 
			",voter_Cd = '" + voterCd + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",qC_Birthday_Wish_Status = '" + qCBirthdayWishStatus + '\'' + 
			",qC_Calling_UpdatedByUser = '" + qCCallingUpdatedByUser + '\'' + 
			",ac_No = '" + acNo + '\'' + 
			",updateByUser = '" + updateByUser + '\'' + 
			",voter_Id = '" + voterId + '\'' + 
			",surname = '" + surname + '\'' + 
			",ward_no = '" + wardNo + '\'' + 
			",age = '" + age + '\'' +
			",QCRemark = '" + QCRemark + '\'' +
			",birthDate = '" + birthDate + '\'' + 
			"}";
		}
}