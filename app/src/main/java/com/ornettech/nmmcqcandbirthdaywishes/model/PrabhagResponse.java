package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PrabhagResponse{

	@SerializedName("PrabhagWiseBirthdays")
	private List<PrabhagWiseBirthdaysItem> prabhagWiseBirthdays;

	public void setPrabhagWiseBirthdays(List<PrabhagWiseBirthdaysItem> prabhagWiseBirthdays){
		this.prabhagWiseBirthdays = prabhagWiseBirthdays;
	}

	public List<PrabhagWiseBirthdaysItem> getPrabhagWiseBirthdays(){
		return prabhagWiseBirthdays;
	}

	@Override
 	public String toString(){
		return 
			"PrabhagResponse{" + 
			"prabhagWiseBirthdays = '" + prabhagWiseBirthdays + '\'' + 
			"}";
		}
}