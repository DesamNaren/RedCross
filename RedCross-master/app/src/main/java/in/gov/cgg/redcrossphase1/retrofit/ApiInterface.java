package in.gov.cgg.redcrossphase1.retrofit;


import com.google.gson.JsonObject;

import java.util.List;

import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgeResponse;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodResponse;
import in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont.DayWiseReportCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.DrillDownResponse;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderResponse;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtVsPvtResponse;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.DistrictResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("loginService")
    Call<JsonObject> callLogin(@Body JsonObject jsonBody);


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
                                                   @Query("districtId") String districtId,
                                                   @Query("mandalId") String mandalId);
//
//    @GET("DistrictWiseEnrollmentsService")
//    Call<AllDistrictResponse> DistrictWiseEnrollmentsService(@Query("role") String role);

    @GET("getDrillDownCountLevelWiseWs")
        //State level will get village count
    Call<List<StatelevelDistrictViewCountResponse>> getDrillDownCountLevelWiseWs1(@Query("level") String level,
                                                                                  @Query("fyId") String fyId);

    @GET("getDrillDownCountLevelWiseWs")
        //District level will get manal count
    Call<List<StatelevelDistrictViewCountResponse>> getDrillDownCountLevelWiseWs2(@Query("level") String level,
                                                                                  @Query("fyId") String fyId,
                                                                                  @Query("districtId") String districtId);

    @GET("getDrillDownCountLevelWiseWs")
        //Mandal Level will get village count
    Call<List<StatelevelDistrictViewCountResponse>> getDrillDownCountLevelWiseWs3(@Query("level") String level,
                                                                                  @Query("fyId") String fyId,
                                                                                  @Query("mandalId") String mandalId);

}
