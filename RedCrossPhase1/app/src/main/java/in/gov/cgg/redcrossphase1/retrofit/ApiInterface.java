package in.gov.cgg.redcrossphase1.retrofit;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("loginService")
    Call<JsonObject> callLogin(@Body JsonObject jsonBody);

}
