package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

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

public class DrilldownViewmodel extends AndroidViewModel {

    private MutableLiveData<DrillDownResponse> drillDownList;

    public DrilldownViewmodel(@NonNull Application application) {
        super(application);
    }

    public LiveData<DrillDownResponse> getDrillDownList(String jrc, String fyear, String entrydate, String did) {

        // if (genderResponseMutableLiveData == null) {
        drillDownList = new MutableLiveData<>();
        loadDrilldown(jrc, fyear, entrydate, did);
        //}
        return drillDownList;
    }

//    public LiveData<List<List<String>>> getStudentList() {
//
//        // if (genderResponseMutableLiveData == null) {
//        studentlist = new MutableLiveData<>();
//        loadDrilldown();
//        //}
//        return studentlist;
//    }

    private void loadDrilldown(String jrc, String fyear, String entrydate, String did) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DrillDownResponse> call = apiInterface.getFullDrillDownDataWs(jrc, fyear, entrydate, did);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<DrillDownResponse>() {
            @Override
            public void onResponse(Call<DrillDownResponse> call, Response<DrillDownResponse> response) {

                if (response.body() != null) {
                    drillDownList.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DrillDownResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}