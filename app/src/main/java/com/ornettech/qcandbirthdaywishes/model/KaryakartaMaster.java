package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ornet on 11/23/2017.
 */

public class KaryakartaMaster {

    @SerializedName("KK_Cd")
    @Expose
    private int kKCd;
    @SerializedName("SrNo")
    @Expose
    private int srNo;
    @SerializedName("KK_Name")
    @Expose
    private String kKName;
    @SerializedName("KK_Name_Mar")
    @Expose
    private String kKNameMar;
    @SerializedName("Designation_Cd")
    @Expose
    private int designationCd;
    @SerializedName("Category_Cd")
    @Expose
    private int categoryCd;
    @SerializedName("Mobile_No_1")
    @Expose
    private String mobileNo1;
    @SerializedName("Mobile_No_2")
    @Expose
    private String mobileNo2;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("Age")
    @Expose
    private int age;
    @SerializedName("Area")
    @Expose
    private String area;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("KK_Flag")
    @Expose
    private int kKFlag;
    @SerializedName("Ac_No")
    @Expose
    private int acNo;
    @SerializedName("Corporation_Name")
    @Expose
    private String corporationName;
    @SerializedName("Election_Name")
    @Expose
    private String electionName;
    @SerializedName("Ward_No")
    @Expose
    private int wardNo;
    @SerializedName("List_No")
    @Expose
    private int listNo;
    @SerializedName("Voter_Id")
    @Expose
    private int voterId;
    @SerializedName("NewVoter_Id")
    @Expose
    private int newVoterId;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("UpdatedByUser")
    @Expose
    private String updatedByUser;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;


    public KaryakartaMaster(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public KaryakartaMaster(int kKCd, int srNo, String kKName, String kKNameMar,
                            int designationCd, int categoryCd, String mobileNo1,
                            String mobileNo2, String birthDate, int age, String area,
                            String remark, int kKFlag, int acNo, String corporationName,
                            String electionName, int wardNo, int listNo, int voterId,
                            int newVoterId, String gender, String updatedByUser,
                            String updatedDate) {
        this.kKCd = kKCd;
        this.srNo = srNo;
        this.kKName = kKName;
        this.kKNameMar = kKNameMar;
        this.designationCd = designationCd;
        this.categoryCd = categoryCd;
        this.mobileNo1 = mobileNo1;
        this.mobileNo2 = mobileNo2;
        this.birthDate = birthDate;
        this.age = age;
        this.area = area;
        this.remark = remark;
        this.kKFlag = kKFlag;
        this.acNo = acNo;
        this.corporationName = corporationName;
        this.electionName = electionName;
        this.wardNo = wardNo;
        this.listNo = listNo;
        this.voterId = voterId;
        this.newVoterId = newVoterId;
        this.gender = gender;
        this.updatedByUser = updatedByUser;
        this.updatedDate = updatedDate;
    }

    public int getkKCd() {
        return kKCd;
    }

    public int getSrNo() {
        return srNo;
    }

    public String getkKName() {
        return kKName;
    }

    public String getkKNameMar() {
        return kKNameMar;
    }

    public int getDesignationCd() {
        return designationCd;
    }

    public int getCategoryCd() {
        return categoryCd;
    }

    public String getMobileNo1() {
        return mobileNo1;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }

    public String getArea() {
        return area;
    }

    public String getRemark() {
        return remark;
    }

    public int getkKFlag() {
        return kKFlag;
    }

    public int getAcNo() {
        return acNo;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public String getElectionName() {
        return electionName;
    }

    public int getWardNo() {
        return wardNo;
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

    public String getGender() {
        return gender;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }
}
