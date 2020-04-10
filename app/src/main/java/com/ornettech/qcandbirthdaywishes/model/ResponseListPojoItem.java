package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class ResponseListPojoItem{

	@SerializedName("QC_Response")
	private String qCResponse;

	@SerializedName("QC_Response_Cd")
	private int qCResponseCd;

	public void setQCResponse(String qCResponse){
		this.qCResponse = qCResponse;
	}

	public String getQCResponse(){
		return qCResponse;
	}

	public void setQCResponseCd(int qCResponseCd){
		this.qCResponseCd = qCResponseCd;
	}

	public int getQCResponseCd(){
		return qCResponseCd;
	}

	@Override
 	public String toString(){
		return 
			"ResponseListPojoItem{" + 
			"qC_Response = '" + qCResponse + '\'' + 
			",qC_Response_Cd = '" + qCResponseCd + '\'' + 
			"}";
		}
}