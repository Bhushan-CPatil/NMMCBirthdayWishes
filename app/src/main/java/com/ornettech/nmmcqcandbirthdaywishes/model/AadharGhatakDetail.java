package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by New on 11/23/2017.
 */

public class AadharGhatakDetail {

    @SerializedName("AadharGhatak_Details_Cd")
    @Expose
    private int aadharGhatakDetailsCd;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("FullName_Mar")
    @Expose
    private String fullNameMar;
    @SerializedName("Designation_Cd")
    @Expose
    private int designationCd;
    @SerializedName("KK_Cd")
    @Expose
    private int kKCd;
    @SerializedName("AadharGhatak_Cd")
    @Expose
    private int aadharGhatakCd;
    @SerializedName("Mobile_No_1")
    @Expose
    private String mobileNo1;
    @SerializedName("Mobile_No_2")
    @Expose
    private String mobileNo2;
    @SerializedName("Area")
    @Expose
    private String area;
    @SerializedName("Area_M")
    @Expose
    private String areamar;
    @SerializedName("Age")
    @Expose
    private int age;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("Anniversary_Date")
    @Expose
    private String anniversaryDate;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("Ac_No")
    @Expose
    private int acNo;
    @SerializedName("Ward_No")
    @Expose
    private int wardNo;
    @SerializedName("Site_Name")
    @Expose
    private String siteName;
    @SerializedName("Site_Cd")
    @Expose
    private int siteCd;
    @SerializedName("List_No")
    @Expose
    private int listNo;
    @SerializedName("Voter_Id")
    @Expose
    private int voterId;
    @SerializedName("NewVoter_Id")
    @Expose
    private int newVoterId;
    @SerializedName("Corporation_Name")
    @Expose
    private String corporationName;
    @SerializedName("ElectionName")
    @Expose
    private String electionName;
    @SerializedName("UpdatedByUser")
    @Expose
    private String updatedByUser;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("Gender")
    @Expose
    private String gender;

    public AadharGhatakDetail(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public AadharGhatakDetail(int aadharGhatakDetailsCd, String fullName, String fullNameMar,
                              int designationCd, int kKCd, int aadharGhatakCd, String mobileNo1,
                              String mobileNo2, String area, int age, String birthDate,
                              String anniversaryDate, String remark, int acNo, int wardNo,
                              String siteName, int siteCd, int listNo, int voterId, int newVoterId,
                              String corporationName, String electionName, String updatedByUser,
                              String updatedDate, String gender) {
        this.aadharGhatakDetailsCd = aadharGhatakDetailsCd;
        this.fullName = fullName;
        this.fullNameMar = fullNameMar;
        this.designationCd = designationCd;
        this.kKCd = kKCd;
        this.aadharGhatakCd = aadharGhatakCd;
        this.mobileNo1 = mobileNo1;
        this.mobileNo2 = mobileNo2;
        this.area = area;
        this.age = age;
        this.birthDate = birthDate;
        this.anniversaryDate = anniversaryDate;
        this.remark = remark;
        this.acNo = acNo;
        this.wardNo = wardNo;
        this.siteName = siteName;
        this.siteCd = siteCd;
        this.listNo = listNo;
        this.voterId = voterId;
        this.newVoterId = newVoterId;
        this.corporationName = corporationName;
        this.electionName = electionName;
        this.updatedByUser = updatedByUser;
        this.updatedDate = updatedDate;
        this.gender = gender;
    }

    public int getAadharGhatakDetailsCd() {
        return aadharGhatakDetailsCd;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFullNameMar() {
        return fullNameMar;
    }

    public int getDesignationCd() {
        return designationCd;
    }

    public int getkKCd() {
        return kKCd;
    }

    public int getAadharGhatakCd() {
        return aadharGhatakCd;
    }

    public String getMobileNo1() {
        return mobileNo1;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public String getArea() {
        return area;
    }

    public int getAge() {
        return age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public String getRemark() {
        return remark;
    }

    public int getAcNo() {
        return acNo;
    }

    public int getWardNo() {
        return wardNo;
    }

    public String getSiteName() {
        return siteName;
    }

    public int getSiteCd() {
        return siteCd;
    }

    public int getListNo() {
        return listNo;
    }

    public int getVoterId() {
        return voterId;
    }

    public int getNewVoterId() {
        return newVoterId;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public String getElectionName() {
        return electionName;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getGender() {
        return gender;
    }

    public String getAreamar() {
        return areamar;
    }

    public void setAreamar(String areamar) {
        this.areamar = areamar;
    }
}
