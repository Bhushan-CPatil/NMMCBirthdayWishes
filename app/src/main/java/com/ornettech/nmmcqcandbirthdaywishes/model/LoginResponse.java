package com.ornettech.nmmcqcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("Designation")
	private String designation;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("Message")
	private String message;

	@SerializedName("Executive_Cd")
	private String executiveCd;

	@SerializedName("ExecutiveName")
	private String executiveName;

	@SerializedName("error")
	private boolean error;

	public void setDesignation(String designation){
		this.designation = designation;
	}

	public String getDesignation(){
		return designation;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setExecutiveCd(String executiveCd){
		this.executiveCd = executiveCd;
	}

	public String getExecutiveCd(){
		return executiveCd;
	}

	public void setExecutiveName(String executiveName){
		this.executiveName = executiveName;
	}

	public String getExecutiveName(){
		return executiveName;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"designation = '" + designation + '\'' + 
			",userName = '" + userName + '\'' + 
			",message = '" + message + '\'' + 
			",executive_Cd = '" + executiveCd + '\'' + 
			",executiveName = '" + executiveName + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}