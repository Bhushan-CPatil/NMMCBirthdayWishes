package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.SerializedName;

public class QCReportItem{

	@SerializedName("QC_Response")
	private String qCResponse;

	@SerializedName("UpdatedDate")
	private String UpdatedDate;

	@SerializedName("SiteName")
	private String SiteName;

	@SerializedName("cnt")
	private int cnt;

	@SerializedName("Ward_no")
	private int Ward_no;

	public String getSiteName() {
		return SiteName;
	}

	public void setSiteName(String siteName) {
		SiteName = siteName;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public int getWard_no() {
		return Ward_no;
	}

	public void setWard_no(int ward_no) {
		Ward_no = ward_no;
	}

	public void setQCResponse(String qCResponse){
		this.qCResponse = qCResponse;
	}

	public String getQCResponse(){
		return qCResponse;
	}

	public void setCnt(int cnt){
		this.cnt = cnt;
	}

	public int getCnt(){
		return cnt;
	}

	@Override
 	public String toString(){
		return 
			"QCReportItem{" + 
			"qC_Response = '" + qCResponse + '\'' + 
			",cnt = '" + cnt + '\'' + 
			",UpdatedDate = '" + UpdatedDate + '\'' +
			",SiteName = '" + SiteName + '\'' +
			",Ward_no = '" + Ward_no + '\'' +
			"}";
		}
}