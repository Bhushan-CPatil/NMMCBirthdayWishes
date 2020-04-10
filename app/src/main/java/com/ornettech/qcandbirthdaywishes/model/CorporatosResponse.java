package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CorporatosResponse{

	@SerializedName("CorporatorsElectionWise")
	private List<CorporatorsElectionWiseItem> corporatorsElectionWise;

	public void setCorporatorsElectionWise(List<CorporatorsElectionWiseItem> corporatorsElectionWise){
		this.corporatorsElectionWise = corporatorsElectionWise;
	}

	public List<CorporatorsElectionWiseItem> getCorporatorsElectionWise(){
		return corporatorsElectionWise;
	}

	@Override
 	public String toString(){
		return 
			"CorporatosResponse{" + 
			"corporatorsElectionWise = '" + corporatorsElectionWise + '\'' + 
			"}";
		}
}