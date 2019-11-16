package in.gov.cgg.redcrossphase1.ui_cgcitizen.agewise;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgewiseViewModel extends AndroidViewModel {

    private MutableLiveData<AgeResponse> ageListMutableLiveData;

    public AgewiseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<AgeResponse> getAges() {

        if (ageListMutableLiveData == null) {
            ageListMutableLiveData = new MutableLiveData<>();
            loadAges();
        }
        return ageListMutableLiveData;
    }

    private void loadAges() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AgeResponse> call = apiInterface.ageWiseService("1", "JRC", "1920", "1");

        call.enqueue(new Callback<AgeResponse>() {
            @Override
            public void onResponse(Call<AgeResponse> call, Response<AgeResponse> response) {

                if (response.body() != null) {
                    ageListMutableLiveData.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AgeResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}