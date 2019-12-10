package in.gov.cgg.redcrossphase1.retrofit;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMemberbean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodDonorResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenDonarRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DistrictBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonorregResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.HomeNursingRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MemberActivitiesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_Bean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.PaymentBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.PhotoBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBeanMainBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.locate.LocateResponse;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgeResponse;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodResponse;
import in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont.DayWiseReportCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.DrillDownResponse;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderResponse;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtVsPvtResponse;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.DistrictResponse;
import okhttp3.MultipartBody;
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


    @POST("loginService")
    Call<JsonObject> callLogin(@Body JsonObject jsonBody);

    @POST("citizenRegistration")
    Call<JsonObject> callCitizenRegistration(@Body JsonObject jsonBody);

    @POST("verifyCitizen")
    Call<CitizenLoginResponse> callCitizenLogin(@Body CitizenLoginRequest req);


    @GET("AgeWiseService")
    Call<AgeResponse> ageWiseService(@Query("districtId") String districtId,
                                     @Query("enrollmentType") String enrollmentType,
                                     @Header("fyId") String fyId,
                                     @Header("userId") String userId);

    @GET("BloodGroupWiseService")
    Call<BloodResponse> BloodGroupWiseService(@Query("districtId") String districtId,
                                              @Query("enrollmentType") String enrollmentType,
                                              @Header("fyId") String fyId,
                                              @Header("userId") String userId);

    @GET("GenderWiseService")
    Call<GenderResponse> GenderWiseService(@Query("districtId") String districtId,
                                           @Query("enrollmentType") String enrollmentType,
                                           @Header("fyId") String fyId,
                                           @Header("userId") String userId);

    @GET("Top5DistMandalWiseService")
    Call<DistrictResponse> Top5DistMandalWiseService(@Query("districtId") String districtId,
                                                     @Query("enrollmentType") String enrollmentType,
                                                     @Header("fyId") String fyId,
                                                     @Header("userId") String userId);

    @GET("Top5DistMandalWiseService")
    Call<DistrictResponse> Bottom5DistMandalWiseService(@Query("districtId") String districtId,
                                                        @Query("enrollmentType") String enrollmentType,
                                                        @Header("fyId") String fyId,
                                                        @Header("userId") String userId,
                                                        @Query("type") String type);

    @GET("getMemberCountsForDashboard")
    Call<DashboardCountResponse> getMemberCountsForDashboard(@Query("districtId") String districtId);

    @GET("GovPvtService")
    Call<GovtVsPvtResponse> GovPvtService(@Query("districtId") String districtId);

    @GET("DayWiseReportDataWS")
    Call<List<DayWiseReportCountResponse>> DayWiseReportDataWS(@Query("districtId") String districtId,
                                                               @Query("finyrId") String finyrId,
                                                               @Query("monthId") String monthId);

    @GET("getFullDrillDownDataWs")
    Call<DrillDownResponse> getFullDrillDownDataWs(@Query("enrollmentType") String enrollmentType,
                                                   @Query("fyId") String fyId,
//                                                   @Query("entryDate") String entryDate,
                                                   @Query("districtId") int districtId,
                                                   @Query("mandalId") int mandalId,
                                                   @Query("villageId") int villageId);
    //
//    @GET("DistrictWiseEnrollmentsService")
//    Call<AllDistrictResponse> DistrictWiseEnrollmentsService(@Query("role") String role);

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

    //Citizen
    @GET("getActivitiesList")
    Call<List<MemberActivitiesResponse>> getActivitiesForMemReg();

    @GET("getMembershipTypes")
    Call<List<MembershipDetails_Bean>> getMembershipTypes();

    @GET("getdistrictsBasedOnStateId/{stateId}")
    Call<List<MembersipDistResponse>> getDistrictsForMemReg(@Path("stateId") String stateId);
//    @GET
//    Call<List<MembersipDistResponse>> getDistrictsForMemReg(@Url String url);

    @GET("getmandalsBasedOnDist/{distId}")
    Call<List<MembershipMandalsResponse>> getMandalsForMemReg(@Path("distId") String DistID);

    @GET("getVillagesForphotoUpl/{mandalId}")
    Call<List<MembershipVillagesResponse>> getVillagesForMemReg(@Path("mandalId") String mandID);

    @POST("saveMembershipMemberData")
    Call<PaymentBean> SendDetails(@Body JsonObject jsonBody);
    @Multipart
    @POST("uploadPhotoMembAdd")
    Call<PhotoBean> SendPhoto(@Part MultipartBody.Part photoUpload);

    @GET("getdistrictsBasedOnStateId/1")
    Call<List<DistrictBean>> getDistrict(@Path("stateId") String stateId);

    //    @POST("saveBloodDonorData")
//    Call<JsonObject> callCitizendonorRegistration(@Body JsonObject jsonBody);

    @POST("saveBloodDonorData")
    Call<DonorregResponse> callCitizendonorRegistration(@Body CitizenDonarRequest req);

    @GET("getFullDrillDownDataWs")
    Call<DrillDownResponse> getFullDrillDownDataWsFilter(@Query("enrollmentType") String enrollmentType,
                                                         @Query("fyId") String fyId,
                                                         @Query("districtId") int districtId,
                                                         @Query("mandalId") int mandalId,
                                                         @Query("villageId") int villageId,
                                                         @Query("gender") String gender,
                                                         @Query("bloodGroup") String bloodGroup
    );


    @GET("state")
    Call<ArrayList<eRaktkoshResponseBean>> callERaktakoshInfo(@Query("state") String state, @Query("source") String source, @Query("key") String key);

    @GET("searchDonor")
    Call<ArrayList<BloodDonorResponse>> getBloodDonorsResponse();

    @POST("saveHomeNursingDetails")
    Call<ResponseBody> saveHomeNursingDetails(@Body HomeNursingRequest req);

    @POST("additionsCentersService")
    Call<LocateResponse> getAdditionsCentersService();

    @GET("additionsCentersService")
    Call<ServeBeanMainBean> getadditionsCentersService(@Query("type") String type);

    @GET("getMemberCountsForDashboard")
    Call<AbstractMemberbean> getAAbstractdata();

}

























