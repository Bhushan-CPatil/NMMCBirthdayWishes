package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QCReportResponse{

	@SerializedName("QCReport")
	private List<QCReportItem> qCReport;

	public void setQCReport(List<QCReportItem> qCReport){
		this.qCReport = qCReport;
	}

	public List<QCReportItem> getQCReport(){
		return qCReport;
	}

	@Override
 	public String toString(){
		return 
			"QCReportResponse{" + 
			"qCReport = '" + qCReport + '\'' + 
			"}";
		}
}