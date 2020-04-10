package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BirthdayInWardResponse{

	@SerializedName("BirthdaysInWard")
	private List<BirthdaysInWardItem> birthdaysInWard;

	@SerializedName("BirthdayWishMsgEng")
	private String BirthdayWishMsgEng;

	@SerializedName("BirthdayWishMsgMar")
	private String BirthdayWishMsgMar;

	@SerializedName("WishImage")
	private String WishImage;

	public String getBirthdayWishMsgEng() {
		return BirthdayWishMsgEng;
	}

	public void setBirthdayWishMsgEng(String birthdayWishMsgEng) {
		BirthdayWishMsgEng = birthdayWishMsgEng;
	}

	public String getBirthdayWishMsgMar() {
		return BirthdayWishMsgMar;
	}

	public void setBirthdayWishMsgMar(String birthdayWishMsgMar) {
		BirthdayWishMsgMar = birthdayWishMsgMar;
	}

	public void setBirthdaysInWard(List<BirthdaysInWardItem> birthdaysInWard){
		this.birthdaysInWard = birthdaysInWard;
	}

	public List<BirthdaysInWardItem> getBirthdaysInWard(){
		return birthdaysInWard;
	}


	public String getWishImage() {
		return WishImage;
	}

	public void setWishImage(String wishImage) {
		WishImage = wishImage;
	}

	@Override
 	public String toString(){
		return 
			"BirthdayInWardResponse{" + 
			"birthdaysInWard = '" + birthdaysInWard + '\'' +
			"WishImage = '" + WishImage + '\'' +
			",BirthdayWishMsgEng = '" + BirthdayWishMsgEng + '\'' +
			",BirthdayWishMsgMar = '" + BirthdayWishMsgMar + '\'' +
			"}";
		}
}