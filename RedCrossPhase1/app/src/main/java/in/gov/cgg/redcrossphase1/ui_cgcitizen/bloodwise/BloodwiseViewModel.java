package in.gov.cgg.redcrossphase1.ui_cgcitizen.bloodwise;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodwiseViewModel extends AndroidViewModel {

    private MutableLiveData<BloodResponse> bloodResponseMutableLiveData;

    public BloodwiseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BloodResponse> getBlood() {

        if (bloodResponseMutableLiveData == null) {
            bloodResponseMutableLiveData = new MutableLiveData<>();
            loadBlood();
        }
        return bloodResponseMutableLiveData;
    }

    private void loadBlood() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BloodResponse> call = apiInterface.BloodGroupWiseService("1", "JRC", "1920", "1");

        call.enqueue(new Callback<BloodResponse>() {
            @Override
            public void onResponse(Call<BloodResponse> call, Response<BloodResponse> response) {

                if (response.body() != null) {
                    bloodResponseMutableLiveData.setValue(response.body());
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