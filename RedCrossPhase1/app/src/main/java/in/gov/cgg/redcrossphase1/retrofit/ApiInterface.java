package in.gov.cgg.redcrossphase1.retrofit;


import com.google.gson.JsonObject;

import in.gov.cgg.redcrossphase1.ui_cgcitizen.agewise.AgeResponse;
import in.gov.cgg.redcrossphase1.ui_cgcitizen.bloodwise.BloodResponse;
import in.gov.cgg.redcrossphase1.ui_cgcitizen.genderwise.GenderResponse;
import in.gov.cgg.redcrossphase1.ui_cgcitizen.home_distrcit.DistrictResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("loginService")
    Call<JsonObject> callLogin(@Body JsonObject jsonBody);


    @POST("AgeWiseService")
    Call<AgeResponse> ageWiseService(@Query("districtId") String districtId,
                                     @Query("enrollmentType") String enrollmentType,
                                     @Query("fyId") String fyId,
                                     @Query("userId") String userId);

    @POST("BloodGroupWiseService")
    Call<BloodResponse> BloodGroupWiseService(@Query("districtId") String districtId,
                                              @Query("enrollmentType") String enrollmentType,
                                              @Query("fyId") String fyId,
                                              @Query("userId") String userId);

    @POST("GenderWiseService")
    Call<GenderResponse> GenderWiseService(@Query("districtId") String districtId,
                                           @Query("enrollmentType") String enrollmentType,
                                           @Query("fyId") String fyId,
                                           @Query("userId") String userId);

    @POST("Top5DistMandalWiseService")
    Call<DistrictResponse> Top5DistMandalWiseService(@Query("districtId") String districtId,
                                                     @Query("enrollmentType") String enrollmentType,
                                                     @Query("fyId") String fyId,
                                                     @Query("userId") String userId);




}
