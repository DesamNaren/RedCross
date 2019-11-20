package in.gov.cgg.redcrossphase1.ui_officer.bloodwise;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodwiseViewModel extends AndroidViewModel {

    private MutableLiveData<List<BloodGroups>> bloodResponseMutableLiveData;

    public BloodwiseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<BloodGroups>> getBlood(String role, String districtsid, String userid) {

//        if (bloodResponseMutableLiveData == null) {
        bloodResponseMutableLiveData = new MutableLiveData<>();
        loadBlood(districtsid, role, userid);
        // }
        return bloodResponseMutableLiveData;
    }

    private void loadBlood(String districtsid, String role, String userid) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BloodResponse> call = apiInterface.BloodGroupWiseService(districtsid, role, "1920", userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<BloodResponse>() {
            @Override
            public void onResponse(Call<BloodResponse> call, Response<BloodResponse> response) {

                if (response.body() != null) {
                    bloodResponseMutableLiveData.setValue(response.body().getBloodGroups());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BloodResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}