package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KKHIResponse{

	@SerializedName("KaryakartaHitachintakList")
	private List<KaryakartaHitachintakListItem> karyakartaHitachintakList;

	@SerializedName("DesignationMaster")
	private List<DesignationMasterItem> designationMaster;

	public void setKaryakartaHitachintakList(List<KaryakartaHitachintakListItem> karyakartaHitachintakList){
		this.karyakartaHitachintakList = karyakartaHitachintakList;
	}

	public List<KaryakartaHitachintakListItem> getKaryakartaHitachintakList(){
		return karyakartaHitachintakList;
	}

	public void setDesignationMaster(List<DesignationMasterItem> designationMaster){
		this.designationMaster = designationMaster;
	}

	public List<DesignationMasterItem> getDesignationMaster(){
		return designationMaster;
	}

	@Override
 	public String toString(){
		return 
			"KKHIResponse{" + 
			"karyakartaHitachintakList = '" + karyakartaHitachintakList + '\'' + 
			",designationMaster = '" + designationMaster + '\'' + 
			"}";
		}
}