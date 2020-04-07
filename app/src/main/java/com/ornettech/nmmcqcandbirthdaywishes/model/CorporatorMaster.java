package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ornet27 on 23-Nov-17.
 */

public class CorporatorMaster
{
    @SerializedName("Corporator_Cd")
    @Expose
    private int corporatorCd;
    @SerializedName("Corporator_Name")
    @Expose
    private String corporatorName;
    @SerializedName("Corporator_Name_Mar")
    @Expose
    private String corporatorNameMar;
    @SerializedName("Ward_No")
    @Expose
    private int wardNo;
    @SerializedName("Corporation_Name")
    @Expose
    private String corporationName;
    @SerializedName("Ward_Name")
    @Expose
    private String wardName;
    @SerializedName("Ward_Name_Mar")
    @Expose
    private String wardNameMar;
    @SerializedName("Designation_Cd")
    @Expose
    private int designationCd;
    @SerializedName("Ac_No")
    @Expose
    private int acNo;
    @SerializedName("List_No")
    @Expose
    private int listNo;
    @SerializedName("Voter_Id")
    @Expose
    private int voterId;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("Anniversary_Date")
    @Expose
    private String anniversaryDate;
    @SerializedName("Mobile_No_1")
    @Expose
    private String mobileNo1;
    @SerializedName("Mobile_No_2")
    @Expose
    private String mobileNo2;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("Corporator_Photo")
    @Expose
    private String corporatorPhoto;
    @SerializedName("UpdatedByUser")
    @Expose
    private String updatedByUser;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("Ward_No_1")
    @Expose
    private String wardNo1;

    public CorporatorMaster(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public CorporatorMaster(int corporatorCd, String corporatorName, String corporatorNameMar,
                            int wardNo, String corporationName, String wardName, String wardNameMar,
                            int designationCd, int acNo, int listNo, int voterId, String gender,
                            String birthDate, String anniversaryDate, String mobileNo1, String mobileNo2,
                            String remark, String corporatorPhoto, String updatedByUser, String updatedDate, String wardNo1) {
        this.corporatorCd = corporatorCd;
        this.corporatorName = corporatorName;
        this.corporatorNameMar = corporatorNameMar;
        this.wardNo = wardNo;
        this.corporationName = corporationName;
        this.wardName = wardName;
        this.wardNameMar = wardNameMar;
        this.designationCd = designationCd;
        this.acNo = acNo;
        this.listNo = listNo;
        this.voterId = voterId;
        this.gender = gender;
        this.birthDate = birthDate;
        this.anniversaryDate = anniversaryDate;
        this.mobileNo1 = mobileNo1;
        this.mobileNo2 = mobileNo2;
        this.remark = remark;
        this.corporatorPhoto = corporatorPhoto;
        this.updatedByUser = updatedByUser;
        this.updatedDate = updatedDate;
        this.wardNo1 = wardNo1;
    }

    public int getCorporatorCd() {
        return corporatorCd;
    }

    public void setCorporatorCd(int corporatorCd) {
        this.corporatorCd = corporatorCd;
    }

    public String getCorporatorName() {
        return corporatorName;
    }

    public void setCorporatorName(String corporatorName) {
        this.corporatorName = corporatorName;
    }

    public String getCorporatorNameMar() {
        return corporatorNameMar;
    }

    public void setCorporatorNameMar(String corporatorNameMar) {
        this.corporatorNameMar = corporatorNameMar;
    }

    public int getWardNo() {
        return wardNo;
    }

    public void setWardNo(int wardNo) {
        this.wardNo = wardNo;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardNameMar() {
        return wardNameMar;
    }

    public void setWardNameMar(String wardNameMar) {
        this.wardNameMar = wardNameMar;
    }

    public int getDesignationCd() {
        return designationCd;
    }

    public void setDesignationCd(int designationCd) {
        this.designationCd = designationCd;
    }

    public int getAcNo() {
        return acNo;
    }

    public void setAcNo(int acNo) {
        this.acNo = acNo;
    }

    public int getListNo() {
        return listNo;
    }

    public void setListNo(int listNo) {
        this.listNo = listNo;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(String anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public String getMobileNo1() {
        return mobileNo1;
    }

    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCorporatorPhoto() {
        return corporatorPhoto;
    }

    public void setCorporatorPhoto(String corporatorPhoto) {
        this.corporatorPhoto = corporatorPhoto;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getWardNo1() {
        return wardNo1;
    }

    public void setWardNo1(String wardNo1) {
        this.wardNo1 = wardNo1;
    }
}
