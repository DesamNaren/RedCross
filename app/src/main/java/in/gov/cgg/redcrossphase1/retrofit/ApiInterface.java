package in.gov.cgg.redcrossphase1.retrofit;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.HomeNurseReqResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMainBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AllScreenMain;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodDonorResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenDonarRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLanguagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonateMoneyRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonateMoneyResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonorregResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.HomeNurseRegResponseBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.HomeNursingRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MemberActivitiesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_Bean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_spinner_Bean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyMembership;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyOnline;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.PaymentBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.PhotoBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBeanMainBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.locate.LocateResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.AgeResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.BloodResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DayWiseReportCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DrillDownResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.GenderResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.GovtVsPvtResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.MainFinance;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.MembershipCounts;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UploadResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    //URL : http://uat2.cgg.gov.in:8081/redcross/

    //Officer

    @POST("loginService")
    Call<JsonObject> callLogin(@Body JsonObject jsonBody);

    @GET("getCurrentVersionNumber")
    Call<JsonObject> getCurrentVersionNumber();

    @GET("AgeWiseService")
    Call<AgeResponse> ageWiseService(@Query("districtId") String districtId,
                                     @Query("enrollmentType") String enrollmentType,
                                     @Query("fyId") String fyId,
                                     @Header("userId") String userId);

    @GET("BloodGroupWiseService")
    Call<BloodResponse> BloodGroupWiseService(@Query("districtId") String districtId,
                                              @Query("enrollmentType") String enrollmentType,
                                              @Query("fyId") String fyId,
                                              @Header("userId") String userId);

    @GET("GenderWiseService")
    Call<GenderResponse> GenderWiseService(@Query("districtId") String districtId,
                                           @Query("enrollmentType") String enrollmentType,
                                           @Query("fyId") String fyId,
                                           @Header("userId") String userId);

    @GET("GovPvtService")
    Call<GovtVsPvtResponse> GovPvtService(@Query("districtId") String districtId);


    @GET("financialYears")
    Call<MainFinance> financialYears();

    @GET("DayWiseReportDataWS")
    Call<List<DayWiseReportCountResponse>> DayWiseReportDataWS(@Query("districtId") String districtId,
//                                                               @Query("finyrId") String finyrId,
                                                               @Query("finyrId") String finyrId,
                                                               @Query("monthId") String monthId);

    @GET("getInstitutesWiseCount")
    Call<List<UserTypesList>> getInstitutesWiseCount(@Query("fyId") String fyId);

    //Fetches dashboard counts (JRC,YRC,MEMBERSHIP)
    @GET("getMemberCountsForDashboard")
    Call<DashboardCountResponse> getMemberCountsForDashboard(@Query("districtId") String districtId,
                                                             @Query("fyId") String finyrId);

    @GET("checkPassword")
    Call<JsonObject> checkPassword(@Query("psw") String psw,
                                   @Query("userName") String userName,
                                   @Query("type") String type);

    @POST("forgotPassword")
    Call<JsonObject> forgotPassword(@Query("userName") String userName);

    @POST("checkotp")
    Call<JsonObject> checkotp(@Query("otp") String otp,
                              @Query("userName") String userName);


    @POST("savePassword")
    Call<JsonObject> savePasswordforForgot(@Query("newPassword") String newPassword,
                                           @Query("confirmPassword") String confirmPassword,
                                           @Query("userName") String userName);


    @GET("saveNewPassword")
    Call<JsonObject> saveNewPassword(@Query("psw") String psw,
                                     @Query("userName") String userName,
                                     @Query("type") String type);


    //Fetches data of enrollment list
    @GET("getFullDrillDownDataWs")
    Call<DrillDownResponse> getFullDrillDownDataWs(@Query("enrollmentType") String enrollmentType,
                                                   @Query("fyId") String fyId,
                                                   @Query("entryDate") String entryDate,
                                                   @Query("districtId") int districtId,
                                                   @Query("mandalId") int mandalId,
                                                   @Query("villageId") int villageId);


    //Fetches dashboard counts (MEMBERSHIP)
    @GET("getMemberCountsForDashboardMobile")
    Call<MembershipCounts> getMemberCountsForDashboardMobile(@Query("memberShipTypeId") String memberShipTypeId);


    //Fetches district, mandals in dashboard
    @GET("getDrillDownCountLevelWiseWs")
    //State level will get district count
    Call<List<StatelevelDistrictViewCountResponse>> getDrillDownCountLevelWiseWs1(@Query("level") String level,
                                                                                  @Query("fyId") String fyId);

    @GET("getDrillDownCountLevelWiseWs")
        //District level will get manal count
    Call<List<StatelevelDistrictViewCountResponse>> getDrillDownCountLevelWiseWs2(@Query("level") String level,
                                                                                  @Query("fyId") String fyId,
                                                                                  @Query("districtId") int districtId);

    @GET("getDrillDownCountLevelWiseWs")
        //Mandal Level will get village count
    Call<List<StatelevelDistrictViewCountResponse>> getDrillDownCountLevelWiseWs3(@Query("level") String level,
                                                                                  @Query("fyId") String fyId,
                                                                                  @Query("mandalId") int mandalId);

    @GET("getFullDrillDownDataWs")
    Call<DrillDownResponse> getFullDrillDownDataWsFilter(@Query("enrollmentType") String enrollmentType,
                                                         @Query("fyId") String fyId,
                                                         @Query("districtId") int districtId,
                                                         @Query("mandalId") int mandalId,
                                                         @Query("villageId") int villageId,
                                                         @Query("gender") String gender,
                                                         @Query("bloodGroup") String bloodGroup
    );

    @GET("getFullDrillDownDataWs")
    Call<DrillDownResponse> getFullDrillDownDataWsFilterMember(@Query("enrollmentType") String enrollmentType,
                                                               @Query("fyId") String fyId,
                                                               @Query("districtId") int districtId,
                                                               @Query("gender") String gender,
                                                               @Query("bloodGroup") String bloodGroup,
                                                               @Query("memberShipTypeId") String memberShipTypeId
    );

    @Multipart
    @POST("updateDetails")
    Call<UploadResponse> updateDetails(@Part MultipartBody.Part photoUpload,
                                       @Query("memberId") String memberId,
                                       @Query("phoneNo") String phoneNo,
                                       @Query("dateOfBirth") String dateOfBirth,
                                       @Query("bloodGroup") String bloodGroup,
                                       @Query("email") String email);


    //Citizen

//
//    @Multipart
//    @POST("citizenRegistration")
//    Call<JsonObject> callCitizenRegistration(
//            //@Header("Content-Type") String content_type,
//            @Part MultipartBody.Part file,
//            @Header("mobileNumber") String mobileNumber,
//            @Header("emailId") String emailId,
//            @Header("name") String name);


    @Multipart
    @POST("citizenRegistration")
    Call<JsonObject> citizenRegistration(@Part("name") RequestBody name,
                                         @Part("emailId") RequestBody emailId,
                                         @Part("mobileNumber") RequestBody mobileNumber,
                                         @Part MultipartBody.Part file);

    @Multipart
    @POST("citizenRegistration")
    Call<JsonObject> citizenRegistrationwithout(@Part("name") RequestBody name,
                                                @Part("emailId") RequestBody emailId,
                                                @Part("mobileNumber") RequestBody mobileNumber);


    @POST("verifyCitizen")
    Call<CitizenLoginResponse> callCitizenLogin(@Body CitizenLoginRequest req);

    @GET("languages")
    Call<CitizenLanguagesResponse> getLanguages();

   /* @POST("langType")
    Call<AllScreenMain> callCitizenAllScreen(@Body CitizenAllScreenRequest req);*/


    @GET("redcrossAppLangType.jsp")
    Call<AllScreenMain> callCitizenAllScreen(@Query("langType") String type);

    @GET("getActivitiesList")
    Call<List<MemberActivitiesResponse>> getActivitiesForMemReg();

    @GET("getMembershipTypes")
    Call<List<MembershipDetails_Bean>> getMembershipTypes();

    @GET("getdistrictsBasedOnStateId/{stateId}")
    Call<List<MembersipDistResponse>> getDistrictsForMemReg(@Path("stateId") String stateId);


    @GET("getmandalsBasedOnDist/{distId}")
    Call<List<MembershipMandalsResponse>> getMandalsForMemReg(@Path("distId") String DistID);

    @GET("getVillagesForphotoUpl/{mandalId}")
    Call<List<MembershipVillagesResponse>> getVillagesForMemReg(@Path("mandalId") String mandID);


    @POST("saveMembershipMemberData")
    Call<PaymentBean> SendDetails(@Body JsonObject jsonBody);

    @Multipart
    @POST("uploadPhotoMembAdd")
    Call<PhotoBean> SendPhoto(@Part MultipartBody.Part photoUpload);


    @POST("saveBloodDonorData")
    Call<DonorregResponse> callCitizendonorRegistration(@Body CitizenDonarRequest req);


    @POST("saveDonationDetails")
    Call<DonateMoneyResponse> saveMoneyDonationDetails(@Body DonateMoneyRequest req);


    @GET("state")
    Call<ArrayList<eRaktkoshResponseBean>> callERaktakoshInfo(@Query("state") String state,
                                                              @Query("source") String source, @Query("key") String key);

    @GET("searchDonor")
    Call<ArrayList<BloodDonorResponse>> getBloodDonorsResponse();


    @GET("additionsCentersService")
    Call<ContactBean> additionsCentersServiceContact(@Query("type") String type);

    @GET("getMemberships")
    Call<MyMembership> getMemberships(@Query("mobile") String mobile);

    @GET("getMemberOnlineDonations")
    Call<MyOnline> getMemberOnlineDonations(@Query("mobile") String mobile);

    @POST("saveHomeNursingDetails")
    Call<HomeNurseRegResponseBean> saveHomeNursingDetails(@Body HomeNursingRequest req);

    @POST("additionsCentersService")
    Call<LocateResponse> getAdditionsCentersService();

    @GET("additionsCentersService")
    Call<ServeBeanMainBean> getadditionsCentersService(@Query("type") String type);

    @GET("getMemberCountsForDashboardMobile")
    Call<AbstractMainBean> getAAbstractdata();


    @GET("getidcardDownload/{memberId}/{DOB}")
    Call<ResponseBody> getIdcardDownload(@Path("memberId") String memberId,
                                         @Path("DOB") String DOB,
                                         @Query("type") String mobile);

    @GET("getcertificateDownload/{memberId}/{DOB}")
    Call<ResponseBody> getcertificateDownload(@Path("memberId") String memberId, @Path("DOB") String DOB, @Query("type") String mobile);


    @POST("submitHomeNurseRequest")
    Call<HomeNurseReqResponse> submitHomeNurseRequest(@Body JsonObject req);

    @GET("getMembershipTypes")
    Call<List<MembershipDetails_spinner_Bean>> getMembershipTypesforSpin();

    @GET("getidcardDownload/{memberId}/{DOB}")
    Call<ResponseBody> getIdcardDownloadNew(@Path("memberId") String memberId, @Path("DOB") String DOB);

    @GET("getcertificateDownload/{memberId}/{DOB}")
    Call<ResponseBody> getcertificateDownloadNew(@Path("memberId") String memberId, @Path("DOB") String DOB);
}