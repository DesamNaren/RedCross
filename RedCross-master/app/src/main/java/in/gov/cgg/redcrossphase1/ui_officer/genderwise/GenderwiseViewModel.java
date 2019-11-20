package in.gov.cgg.redcrossphase1.ui_officer.genderwise;

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

public class GenderwiseViewModel extends AndroidViewModel {

    private MutableLiveData<List<Genders>> genderResponseMutableLiveData;

    public GenderwiseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Genders>> getGender(String role, String districtid, String userid) {

        // if (genderResponseMutableLiveData == null) {
        genderResponseMutableLiveData = new MutableLiveData<>();
        loadGenders(userid, districtid, role);
        //}
        return genderResponseMutableLiveData;
    }

    private void loadGenders(String userid, String districtid, String role) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GenderResponse> call = apiInterface.GenderWiseService(districtid, role, "1920", userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<GenderResponse>() {
            @Override
            public void onResponse(Call<GenderResponse> call, Response<GenderResponse> response) {

                if (response.body() != null) {
                    genderResponseMutableLiveData.setValue(response.body().getGenders());
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