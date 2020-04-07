package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ornet on 11/23/2017.
 */

public class AadharGhatakMaster {

    @SerializedName("AadharGhatak_Cd")
    @Expose
    private Integer aadharGhatakCd;
    @SerializedName("AadharGhatak_Name")
    @Expose
    private String aadharGhatakName;
    @SerializedName("AadharGhatak_Name_Mar")
    @Expose
    private String aadharGhatakNameMar;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("UpdatedByUser")
    @Expose
    private String updatedByUser;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;

    public AadharGhatakMaster(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public AadharGhatakMaster(Integer aadharGhatakCd, String aadharGhatakName, String aadharGhatakNameMar,
                              String remark, String updatedByUser, String updatedDate) {
        this.aadharGhatakCd = aadharGhatakCd;
        this.aadharGhatakName = aadharGhatakName;
        this.aadharGhatakNameMar = aadharGhatakNameMar;
        this.remark = remark;
        this.updatedByUser = updatedByUser;
        this.updatedDate = updatedDate;
    }

    public Integer getAadharGhatakCd() {
        return aadharGhatakCd;
    }

    public String getAadharGhatakName() {
        return aadharGhatakName;
    }

    public String getAadharGhatakNameMar() {
        return aadharGhatakNameMar;
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
