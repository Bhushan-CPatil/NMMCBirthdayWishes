package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ornet on 11/21/2017.
 */

public class DesignationMaster {
    @SerializedName("Designation_Cd")
    @Expose
    private int designationCd;
    @SerializedName("SrNo")
    @Expose
    private int srNo;
    @SerializedName("Designation_Name")
    @Expose
    private String designationName;
    @SerializedName("Designation_Name_Mar")
    @Expose
    private String designationNameMar;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("UpdatedByUser")
    @Expose
    private String updatedByUser;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;

    public DesignationMaster(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public DesignationMaster(int designationCd, int srNo, String designationName,
                             String designationNameMar, String remark, String updatedByUser,
                             String updatedDate) {
        this.designationCd = designationCd;
        this.srNo = srNo;
        this.designationName = designationName;
        this.designationNameMar = designationNameMar;
        this.remark = remark;
        this.updatedByUser = updatedByUser;
        this.updatedDate = updatedDate;
    }

    public int getDesignationCd() {
        return designationCd;
    }

    public int getSrNo() {
        return srNo;
    }

    public String getDesignationName() {
        return designationName;
    }

    public String getDesignationNameMar() {
        return designationNameMar;
    }

    public String getRemark() {
        return remark;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }
}
