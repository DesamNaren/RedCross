package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

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

public class AllDistrictsViewModel extends AndroidViewModel {

    private MutableLiveData<List<AllDistrict>> alListMutableLiveData;

    public AllDistrictsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<AllDistrict>> getAllDistrcts(String role) {

        // if (genderResponseMutableLiveData == null) {
        alListMutableLiveData = new MutableLiveData<>();
        loadAllDistricts(role);
        //}
        return alListMutableLiveData;
    }

    private void loadAllDistricts(String role) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllDistrictResponse> call = apiInterface.DistrictWiseEnrollmentsService(role);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<AllDistrictResponse>() {
            @Override
            public void onResponse(Call<AllDistrictResponse> call, Response<AllDistrictResponse> response) {

                if (response.body() != null) {
                    alListMutableLiveData.setValue(response.body().getAllDistricts());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllDistrictResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}