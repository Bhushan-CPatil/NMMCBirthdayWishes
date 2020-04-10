package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QCResposeWiseReportResponse{

	@SerializedName("QCResposeWiseReport")
	private List<QCResposeWiseReportItem> qCResposeWiseReport;

	public void setQCResposeWiseReport(List<QCResposeWiseReportItem> qCResposeWiseReport){
		this.qCResposeWiseReport = qCResposeWiseReport;
	}

	public List<QCResposeWiseReportItem> getQCResposeWiseReport(){
		return qCResposeWiseReport;
	}

	@Override
 	public String toString(){
		return 
			"QCResposeWiseReportResponse{" + 
			"qCResposeWiseReport = '" + qCResposeWiseReport + '\'' + 
			"}";
		}
}