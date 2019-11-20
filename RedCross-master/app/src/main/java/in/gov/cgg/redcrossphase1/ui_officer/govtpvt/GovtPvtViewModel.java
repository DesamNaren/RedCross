package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;

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

public class GovtPvtViewModel extends AndroidViewModel {

    private MutableLiveData<List<GovType>> goListMutableLiveData;

    public GovtPvtViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<GovType>> getGender(String role, String districtid, String userid) {

        // if (genderResponseMutableLiveData == null) {
        goListMutableLiveData = new MutableLiveData<>();
        loadGovtPvt(userid, districtid, role);
        //}
        return goListMutableLiveData;
    }

    private void loadGovtPvt(String userid, String districtid, String role) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GovtVsPvtResponse> call = apiInterface.GovPvtService(districtid, role, "1920", userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<GovtVsPvtResponse>() {
            @Override
            public void onResponse(Call<GovtVsPvtResponse> call, Response<GovtVsPvtResponse> response) {

                if (response.body() != null) {
                    goListMutableLiveData.setValue(response.body().getTypes());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GovtVsPvtResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}