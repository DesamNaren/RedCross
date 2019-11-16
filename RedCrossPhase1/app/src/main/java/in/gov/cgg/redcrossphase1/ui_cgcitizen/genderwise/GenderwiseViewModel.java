package in.gov.cgg.redcrossphase1.ui_cgcitizen.genderwise;

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

public class GenderwiseViewModel extends AndroidViewModel {

    private MutableLiveData<GenderResponse> genderResponseMutableLiveData;

    public GenderwiseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<GenderResponse> getGender() {

        if (genderResponseMutableLiveData == null) {
            genderResponseMutableLiveData = new MutableLiveData<>();
            loadGenders();
        }
        return genderResponseMutableLiveData;
    }

    private void loadGenders() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GenderResponse> call = apiInterface.GenderWiseService("1", "JRC", "1920", "1");

        call.enqueue(new Callback<GenderResponse>() {
            @Override
            public void onResponse(Call<GenderResponse> call, Response<GenderResponse> response) {

                if (response.body() != null) {
                    genderResponseMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenderResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}