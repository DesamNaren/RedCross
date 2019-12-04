package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDistrictsViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    Context context;
    //ProgressDialog pd;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData;



    public AllDistrictsViewModel(Context application) {
        this.context = application;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllDistrcts(String level, String fyid) {

        // if (genderResponseMutableLiveData == null) {
        alListMutableLiveData = new MutableLiveData<>();
        loadAllDistricts(level, fyid);
        //}
        return alListMutableLiveData;
    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllMandals(String level, String fyid, String districtId) {

        // if (genderResponseMutableLiveData == null) {
        alListMutableLiveData = new MutableLiveData<>();
        loadAllMandals(level, fyid, districtId);
        //}
        return alListMutableLiveData;
    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllVillages(String level, String fyid, String mandalId) {

        // if (genderResponseMutableLiveData == null) {
        alListMutableLiveData = new MutableLiveData<>();
        loadAllVillages(level, fyid, mandalId);
        //}
        return alListMutableLiveData;
    }

    private void loadAllDistricts(String role, String fyid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs1(role, fyid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {
            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call,
                                   Response<List<StatelevelDistrictViewCountResponse>> response) {

                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }

    private void loadAllMandals(String role, String fyid, String districtId) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs2(role, fyid, districtId);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {
            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call,
                                   Response<List<StatelevelDistrictViewCountResponse>> response) {

                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }

    private void loadAllVillages(String role, String fyid, String mandalId) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs3(role, fyid, mandalId);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {
            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call,
                                   Response<List<StatelevelDistrictViewCountResponse>> response) {

                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }


}