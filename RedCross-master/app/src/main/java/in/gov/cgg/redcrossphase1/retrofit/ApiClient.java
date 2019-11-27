package in.gov.cgg.redcrossphase1.retrofit;


import java.util.concurrent.TimeUnit;

import in.gov.cgg.redcrossphase1.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL).client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }

}
