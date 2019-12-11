package in.gov.cgg.redcrossphase1.ui_officer.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.BloodGroups;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.BloodResponse;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodwiseViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    private MutableLiveData<List<BloodGroups>> bloodResponseMutableLiveData;
    Context context;
    // ProgressDialog pd;

    public BloodwiseViewModel(Context context) {
        this.context = context;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }


    public LiveData<List<BloodGroups>> getBlood(String role, String districtsid, String userid) {

//        if (bloodResponseMutableLiveData == null) {
        bloodResponseMutableLiveData = new MutableLiveData<>();
        loadBlood(districtsid, role, userid);
        // }
        return bloodResponseMutableLiveData;
    }

    private void loadBlood(String districtsid, String role, String userid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BloodResponse> call = apiInterface.BloodGroupWiseService(districtsid, role, "1920", userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<BloodResponse>() {
            @Override
            public void onResponse(Call<BloodResponse> call, Response<BloodResponse> response) {

                if (response.body() != null) {
                    bloodResponseMutableLiveData.setValue(response.body().getBloodGroups());
                    pd.dismiss();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<BloodResponse> call, Throwable t) {

                pd.dismiss();
                t.printStackTrace();
            }
        });


    }


}