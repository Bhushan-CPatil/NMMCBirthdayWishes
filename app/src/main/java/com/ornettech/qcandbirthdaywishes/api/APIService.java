package com.ornettech.qcandbirthdaywishes.api;

import com.ornettech.qcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.qcandbirthdaywishes.model.BirthdayInWardResponse;
import com.ornettech.qcandbirthdaywishes.model.CallingQCMainResponse;
import com.ornettech.qcandbirthdaywishes.model.ClientCallResponse;
import com.ornettech.qcandbirthdaywishes.model.CorporateWhatsappResponse;
import com.ornettech.qcandbirthdaywishes.model.CorporatosResponse;
import com.ornettech.qcandbirthdaywishes.model.LoginResponse;
import com.ornettech.qcandbirthdaywishes.model.MainResponse;
import com.ornettech.qcandbirthdaywishes.model.PrabhagResponse;
import com.ornettech.qcandbirthdaywishes.model.QCReportResponse;
import com.ornettech.qcandbirthdaywishes.model.QCResposeWiseReportResponse;
import com.ornettech.qcandbirthdaywishes.model.Result;
import com.ornettech.qcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.qcandbirthdaywishes.model.SocRoomWiseResponse;
import com.ornettech.qcandbirthdaywishes.model.SpinnerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ornet on 11/20/2017.
 */

public interface APIService {
//    @FormUrlEncoded
//    @POST("authenticateUser")
//    Call<Result> authenticateUser(
//            @Field("mobile") String mobile,
//            @Field("apkpwd") String apkpwd,
//            @Field("appname") String appname,
//            @Field("expdate") String expdate
//    );

//    @FormUrlEncoded
//    @POST("updateUserInstallationFlag")
//    Call<Result> updateUserInstallationFlag(
//            @Field("username") String username,
//            @Field("mobileno") String mobileno,
//            @Field("installationflag") String installationflag);

    @FormUrlEncoded
    @POST("designationMaster")
    Call<Result> getDesignationMasters(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate
    );

//    @FormUrlEncoded
//    @POST("assemblyMaster")
//    Call<Result> getAssemblyMasters(
//            @Field("username") String username,
//            @Field("appname") String appname,
//            @Field("updatedDate") String updatedDate
//    );

    @FormUrlEncoded
    @POST("aadharGhatakMaster")
    Call<Result> getAadharGhatakMasters(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate
    );

//    @FormUrlEncoded
//    @POST("committeeCatMaster")
//    Call<Result> getCommitteeCatMasters(
//            @Field("username") String username,
//            @Field("appname") String appname,
//            @Field("updatedDate") String updatedDate
//    );

    @FormUrlEncoded
    @POST("karyakartaMaster")
    Call<Result> getKaryakartaMasterMasters(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate
    );

//    @FormUrlEncoded
//    @POST("corporationElectionMaster")
//    Call<Result> getCorporationElectionMasters(
//            @Field("username") String username,
//            @Field("appname") String appname,
//            @Field("updatedDate") String updatedDate
//    );

    @FormUrlEncoded
    @POST("aadharGhatakDetail")
    Call<Result> getAadharGhatakDetails(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate
    );

    @FormUrlEncoded
    @POST("corporatorMaster")
    Call<Result> getCorporatorMasters(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate
    );

//    @FormUrlEncoded
//    @POST("subLocationMaster")
//    Call<Result> getSocietyMaster(
//            @Field("username") String username,
//            @Field("appname") String appname,
//            @Field("updatedDate") String updatedDate
//    );

//    @FormUrlEncoded
//    @POST("allTableDateTime")
//    Call<Result> getAllTableDateTimes(
//            @Field("username") String username,
//            @Field("appname") String appname
//    );

//    @FormUrlEncoded
//    @POST("reviewMeeting")
//    Call<Result> getBOMeetingReviewEntries(
//            @Field("username") String username,
//            @Field("appname") String appname,
//            @Field("updatedDate") String updatedDate);

    @FormUrlEncoded
    @POST("voterforbirthday")
    Call<Result> getVoterData(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate);

    @FormUrlEncoded
    @POST("nonvoterforbirthday")
    Call<Result> getNonVoterData(
            @Field("username") String username,
            @Field("appname") String appname,
            @Field("updatedDate") String updatedDate);

//    @FormUrlEncoded
//    @POST("lockroom")
//    Call<Result> getLockRoomData(
//            @Field("username") String username,
//            @Field("appname") String appname,
//            @Field("updatedDate") String updatedDate);

     /* Save or Upload Data to Server Start */

    @FormUrlEncoded
    @POST("saveBirthDate")
    Call<Result> saveBirthDate(
            @Field("commoncd") int commoncd,
            @Field("birthdate") String birthdate,
            @Field("updatedbyuser") String updatedbyuser, //qc Updated by usr
            @Field("qcUpdatedDate") String qcUpdatedDate,
            @Field("tableName") String tableName,
            @Field("appname") String appname,
            @Field("voterid") int voterid,
            @Field("listno") int listno
            );

    @FormUrlEncoded
    @POST("siteAresponseList.php")
    Call<SiteAndQCResponse> siteNameAndQCResList(
            @Field("elecname") String elecname
    );

    @FormUrlEncoded
    @POST("fetchCallingData.php")
    Call<CallingQCMainResponse> fetchCallingData(
            @Field("fromdate") String fromdate,
            @Field("todate") String todate,
            @Field("elecname") String elecname,
            @Field("sitename") String sitename,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("getSocietyListOfQC.php")
    Call<SocRoomWiseResponse> getSocietyListOfQC(
            @Field("fromdate") String fromdate,
            @Field("todate") String todate,
            @Field("elecname") String elecname,
            @Field("sitename") String sitename,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("SocietyListOfOtherQC.php")
    Call<SocRoomWiseResponse> getSocietyListOfOtherQC(
            @Field("fromdate") String fromdate,
            @Field("todate") String todate,
            @Field("elecname") String elecname,
            @Field("wardno") String wardno,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("getRoomWiseDetailedListQC_2.php")
    Call<MainResponse> getRoomWiseDetailedListQC(
            @Field("fromdate") String fromdate,
            @Field("todate") String todate,
            @Field("elecname") String elecname,
            @Field("sitename") String sitename,
            @Field("subloccd") int subloccd,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("getRoomWiseDetailedListOtherQC_2.php")
    Call<MainResponse> getRoomWiseDetailedListOtherQC(
            @Field("fromdate") String fromdate,
            @Field("todate") String todate,
            @Field("elecname") String elecname,
            @Field("wardno") String ward,
            @Field("subloccd") int subloccd,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("updateCallingQC_u.php")
    Call<DefaultResponse> updateCallingQC(
            @Field("votercd") String votercd,
            @Field("mobileno") String mobileno,
            @Field("elecname") String elecname,
            @Field("sitename") String sitename,
            @Field("acno") int acno,
            @Field("username") String username,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("updateBirthDateQC.php")
    Call<DefaultResponse> updateBirthDateQC(
            @Field("votercd") String votercd,
            @Field("tablename") String tablename,
            @Field("elecname") String elecname,
            @Field("acno") int acno,
            @Field("username") String username,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("updateBDAndMobileNo_u.php")
    Call<DefaultResponse> updateBirthDateQC(
            @Field("votercd") String votercd,
            @Field("mobileno") String mobileno,
            @Field("elecname") String elecname,
            @Field("sitename") String sitename,
            @Field("acno") int acno,
            @Field("birthdate1") String birthdate1,
            @Field("birthdate2") String birthdate2,
            @Field("username") String username,
            @Field("tablename") String tablename,
            @Field("vfname") String voterfname,
            @Field("vmname") String votermname,
            @Field("vlname") String voterlname,
            @Field("remark") String remark
    );

    @FormUrlEncoded
    @POST("updateBDAndMobileNo_uOtherQC.php")
    Call<DefaultResponse> updateBDAndMobileNo_uOtherQC(
            @Field("votercd") String votercd,
            @Field("mobileno") String mobileno,
            @Field("elecname") String elecname,
            @Field("wardno") String wardno,
            @Field("acno") int acno,
            @Field("birthdate1") String birthdate1,
            @Field("birthdate2") String birthdate2,
            @Field("username") String username,
            @Field("tablename") String tablename,
            @Field("vfname") String voterfname,
            @Field("vmname") String votermname,
            @Field("vlname") String voterlname,
            @Field("remark") String remark
    );

    @FormUrlEncoded
    @POST("qc_report_u.php")
    Call<QCReportResponse> qc_report(
            @Field("reportdate") String repdate,
            @Field("elecname") String elecname,
            @Field("username") String username,
            @Field("reptype") String reptype,
            @Field("designation") String designation,
            @Field("sitename") String sitename
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("mobno") String mobilenumber,
            @Field("elecname") String elecname
    );

    @FormUrlEncoded
    @POST("spinnerOptionForRep_1.php")
    Call<SpinnerResponse> spinnerOptionForRep(
            @Field("username") String username,
            @Field("elecname") String electionname
    );

    @FormUrlEncoded
    @POST("QCResponseWiseReport_new1.php")
    Call<QCResposeWiseReportResponse> QCResponseWiseReport(
            @Field("reportdate") String repdate,
            @Field("elecname") String elecname,
            @Field("username") String username,
            @Field("qcresponse") String qcresponse,
            @Field("reptype") String reptype,
            @Field("designation") String designation,
            @Field("ward") String ward,
            @Field("executivename") String executivename
    );

    @FormUrlEncoded
    @POST("getBirthDaysWard_new.php")
    Call<PrabhagResponse> getBirthDaysWard(
            @Field("status") String status,
            @Field("elecname") String elecname,
            @Field("username") String username,
            @Field("acno") String acno,
            @Field("day") String day,
            @Field("month") String month,
            @Field("year") String year
    );

    @FormUrlEncoded
    @POST("birthdaysOfWard_new_4.php")
    Call<BirthdayInWardResponse> birthdaysOfWard(
            @Field("status") String status,
            @Field("elecname") String elecname,
            @Field("username") String username,
            @Field("acno") String acno,
            @Field("wardno") String wardno,
            @Field("day") String day,
            @Field("month") String month,
            @Field("year") String year
    );

    @FormUrlEncoded
    @POST("getAllBirthdateDateWiseForPDF.php")
    Call<BirthdayInWardResponse> getAllBirthdateDateWiseForPDF(
            @Field("elecname") String elecname,
            @Field("day") String day,
            @Field("month") String month,
            @Field("year") String year
    );

    @FormUrlEncoded
    @POST("getCorporatorDetailsAndBirthDays.php")
    Call<CorporateWhatsappResponse> birthdaysOfWardForCorporator(
            @Field("status") String status,
            @Field("elecname") String elecname,
            @Field("username") String username,
            @Field("acno") int acno,
            @Field("wardno") int wardno,
            @Field("day") String day,
            @Field("month") String month,
            @Field("year") String year
    );

    @FormUrlEncoded
    @POST("CorporatorWithWishImage.php")
    Call<CorporatosResponse> getCorporatorsList(
            @Field("elecname") String elecname
    );

    @FormUrlEncoded
    @POST("getSocietyListOfIConnectQC.php")
    Call<SocRoomWiseResponse> getSocietyListOfIConnectQC(
            @Field("elecname") String elecname,
            @Field("wardno") String wardno,
            @Field("callingresponse") String callingresponse
    );

    @FormUrlEncoded
    @POST("getRoomWiseDetailedListIconnectQC_1.php")
    Call<MainResponse> getRoomWiseDetailedListIConnectQC(
            @Field("elecname") String elecname,
            @Field("wardno") String ward,
            @Field("subloccd") int subloccd,
            @Field("callingresponse") String callingresponse
    );


    @FormUrlEncoded
    @POST("login.php")
    Call<DefaultResponse> CheckUserStatus(
            @Field("username") String username,
            @Field("mobno") String executiveCd,
            @Field("isdeactive") String status
    );

    @FormUrlEncoded
    @POST("getSocietyListOfRoundsQC.php")
    Call<SocRoomWiseResponse> getSocietyListOfRoundsQC(
            @Field("elecname") String elecname,
            @Field("wardno") String wardno,
            @Field("callingresponse") String callingresponse,
            @Field("roundcnt") String round
    );

    @FormUrlEncoded
    @POST("getRoomWiseDetailedListRoundsQC_1.php")
    Call<MainResponse> getRoomWiseDetailedListRoundsQC(
            @Field("elecname") String elecname,
            @Field("wardno") String ward,
            @Field("subloccd") int subloccd,
            @Field("callingresponse") String callingresponse,
            @Field("roundcnt") String round
    );

    @FormUrlEncoded
    @POST("ClientList.php")
    Call<ClientCallResponse> getClientListForQC(
            @Field("fromdate") String fromdate,
            @Field("elecname") String elecname,
            @Field("callingresponse") String callingresponse
    );

}

