package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KKHIResponse{

	@SerializedName("KaryakartaHitachintakList")
	private List<KaryakartaHitachintakListItem> karyakartaHitachintakList;

	public void setKaryakartaHitachintakList(List<KaryakartaHitachintakListItem> karyakartaHitachintakList){
		this.karyakartaHitachintakList = karyakartaHitachintakList;
	}

	public List<KaryakartaHitachintakListItem> getKaryakartaHitachintakList(){
		return karyakartaHitachintakList;
	}

	@Override
 	public String toString(){
		return 
			"KKHIResponse{" + 
			"karyakartaHitachintakList = '" + karyakartaHitachintakList + '\'' + 
			"}";
		}
}