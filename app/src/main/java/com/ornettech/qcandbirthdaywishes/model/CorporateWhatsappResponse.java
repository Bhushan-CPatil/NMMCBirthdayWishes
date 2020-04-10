package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CorporateWhatsappResponse{

	@SerializedName("CorporatorDetails")
	private List<CorporatorDetailsItem> corporatorDetails;

	@SerializedName("Note")
	private String note;

	@SerializedName("BirthdaysInSelectedWard")
	private List<BirthdaysInSelectedWardItem> birthdaysInSelectedWard;

	public void setCorporatorDetails(List<CorporatorDetailsItem> corporatorDetails){
		this.corporatorDetails = corporatorDetails;
	}

	public List<CorporatorDetailsItem> getCorporatorDetails(){
		return corporatorDetails;
	}

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return note;
	}

	public void setBirthdaysInSelectedWard(List<BirthdaysInSelectedWardItem> birthdaysInSelectedWard){
		this.birthdaysInSelectedWard = birthdaysInSelectedWard;
	}

	public List<BirthdaysInSelectedWardItem> getBirthdaysInSelectedWard(){
		return birthdaysInSelectedWard;
	}

	@Override
 	public String toString(){
		return 
			"CorporateWhatsappResponse{" + 
			"corporatorDetails = '" + corporatorDetails + '\'' + 
			",note = '" + note + '\'' + 
			",birthdaysInSelectedWard = '" + birthdaysInSelectedWard + '\'' + 
			"}";
		}
}