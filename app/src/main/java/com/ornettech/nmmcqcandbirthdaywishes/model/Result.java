package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ornet on 11/21/2017.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("commoncd")
    private Integer commoncd;

    @SerializedName("designationmaster")
    private DesignationMaster designationMaster;

    @SerializedName("designationMasters")
    @Expose
    private ArrayList<DesignationMaster> designationMasters;

    @SerializedName("aadharghatakmaster")
    private AadharGhatakMaster aadharGhatakMaster;

    @SerializedName("aadharGhatakMasters")
    @Expose
    private ArrayList<AadharGhatakMaster> aadharGhatakMasters;

    @SerializedName("karyakartaMaster")
    private KaryakartaMaster karyakartaMaster;

    @SerializedName("karyakartaMasters")
    @Expose
    private ArrayList<KaryakartaMaster> karyakartaMasters;

    @Expose
    public ArrayList<AadharGhatakDetail> aadharGhatakDetails;

    @SerializedName("corporatormaster")
    private CorporatorMaster corporatorMaster;

    private ArrayList<CorporatorMaster> corporatorMasters;

    public Integer getCommoncd() {
        return commoncd;
    }

    public void setCommoncd(Integer commoncd) {
        this.commoncd = commoncd;
    }

    @SerializedName("ward")
    private Ward ward;

    private ArrayList<Ward> wards;

    @SerializedName("voters")
    @Expose
    private ArrayList<Voter> voters;

    @SerializedName("nonvoters")
    @Expose
    private ArrayList<NonVoter> nonVoters;

    public ArrayList<Voter> getVoters() {
        return voters;
    }

    public void setVoters(ArrayList<Voter> voters) {
        this.voters = voters;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DesignationMaster getDesignationMaster() {
        return designationMaster;
    }

    public void setDesignationMaster(DesignationMaster designationMaster) {
        this.designationMaster = designationMaster;
    }

    public ArrayList<DesignationMaster> getDesignationMasters() {
        return designationMasters;
    }

    public void setDesignationMasters(ArrayList<DesignationMaster> designationMasters) {
        this.designationMasters = designationMasters;
    }

    public AadharGhatakMaster getAadharGhatakMaster() {
        return aadharGhatakMaster;
    }

    public void setAadharGhatakMaster(AadharGhatakMaster aadharGhatakMaster) {
        this.aadharGhatakMaster = aadharGhatakMaster;
    }

    public ArrayList<AadharGhatakMaster> getAadharGhatakMasters() {
        return aadharGhatakMasters;
    }

    public void setAadharGhatakMasters(ArrayList<AadharGhatakMaster> aadharGhatakMasters) {
        this.aadharGhatakMasters = aadharGhatakMasters;
    }

    public KaryakartaMaster getKaryakartaMaster() {
        return karyakartaMaster;
    }

    public void setKaryakartaMaster(KaryakartaMaster karyakartaMaster) {
        this.karyakartaMaster = karyakartaMaster;
    }

    public ArrayList<KaryakartaMaster> getKaryakartaMasters() {
        return karyakartaMasters;
    }

    public void setKaryakartaMasters(ArrayList<KaryakartaMaster> karyakartaMasters) {
        this.karyakartaMasters = karyakartaMasters;
    }

    public ArrayList<AadharGhatakDetail> getAadharGhatakDetails() {
        return aadharGhatakDetails;
    }

    public void setAadharGhatakDetails(ArrayList<AadharGhatakDetail> aadharGhatakDetails) {
        this.aadharGhatakDetails = aadharGhatakDetails;
    }

    public CorporatorMaster getCorporatorMaster() {
        return corporatorMaster;
    }

    public void setCorporatorMaster(CorporatorMaster corporatorMaster) {
        this.corporatorMaster = corporatorMaster;
    }

    public ArrayList<CorporatorMaster> getCorporatorMasters() {
        return corporatorMasters;
    }

    public void setCorporatorMasters(ArrayList<CorporatorMaster> corporatorMasters) {
        this.corporatorMasters = corporatorMasters;
    }

    public ArrayList<NonVoter> getNonVoters() {
        return nonVoters;
    }

    public void setNonVoters(ArrayList<NonVoter> nonVoters) {
        this.nonVoters = nonVoters;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public ArrayList<Ward> getWards() {
        return wards;
    }

    public void setWards(ArrayList<Ward> wards) {
        this.wards = wards;
    }
}
