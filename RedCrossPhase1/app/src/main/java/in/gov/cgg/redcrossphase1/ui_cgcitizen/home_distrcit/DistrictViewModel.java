package in.gov.cgg.redcrossphase1.ui_cgcitizen.home_distrcit;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictViewModel extends AndroidViewModel {
    private MutableLiveData<DistrictResponse> districtResponseMutableLiveData;

    public DistrictViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<DistrictResponse> getDistricts() {

        if (districtResponseMutableLiveData == null) {
            districtResponseMutableLiveData = new MutableLiveData<>();

            loadDistricts();
        }
        return districtResponseMutableLiveData;

    }

    private void loadDistricts() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DistrictResponse> call = apiInterface.Top5DistMandalWiseService("1", "JRC", "1920", "1");

        call.enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {

                if (response.body() != null) {

                    districtResponseMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    Log.e("district view model", "onResponse: Response null");
                }

            }

            @Override
            public void onFailure(Call<DistrictResponse> call, Throwable t) {

                // Toast.makeText(getActivity(), "error failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("district view model", "onFailure");

            }
        });

    }

}
