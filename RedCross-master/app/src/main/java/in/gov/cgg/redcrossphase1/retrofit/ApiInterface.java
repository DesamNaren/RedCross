package in.gov.cgg.redcrossphase1.retrofit;


import com.google.gson.JsonObject;

import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgeResponse;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodResponse;
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
    Call<AgeResponse> ageWiseService(@Header("districtId") String districtId,
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
    Call<DashboardCountResponse> getMemberCountsForDashboard();

    @POST("GovPvtService ")
    Call<GovtVsPvtResponse> GovPvtService(@Header("districtId") String districtId,
                                          @Header("enrollmentType") String enrollmentType,
                                          @Header("fyId") String fyId,
                                          @Header("userId") String userId);


}
