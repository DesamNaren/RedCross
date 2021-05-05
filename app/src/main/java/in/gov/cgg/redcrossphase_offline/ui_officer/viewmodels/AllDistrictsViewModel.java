package in.gov.cgg.redcrossphase_offline.ui_officer.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase_offline.retrofit.ApiClient;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.UserTypesList;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class AllDistrictsViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    Context context;
    //ProgressDialog pd;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData1;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData2;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData3;
    private MutableLiveData<List<UserTypesList>> alListInsti;


    public AllDistrictsViewModel(Context application) {
        this.context = application;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllDistrcts(String level, String fyid) {

        if (alListMutableLiveData1 == null) {
            alListMutableLiveData1 = new MutableLiveData<>();
            if (GlobalDeclaration.districtData == null) {
                loadAllDistricts(level, fyid);
            } else {
                alListMutableLiveData1.setValue(GlobalDeclaration.districtData);
            }
        }
        return alListMutableLiveData1;
    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllMandals(String level, String fyid, int districtId) {

        if (alListMutableLiveData2 == null) {
            alListMutableLiveData2 = new MutableLiveData<>();
            if (GlobalDeclaration.mandalData == null) {
                loadAllMandals(level, fyid, districtId);
            } else {
                alListMutableLiveData2.setValue(GlobalDeclaration.mandalData);
            }
        }
        return alListMutableLiveData2;
    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllVillages(String level, String fyid, int mandalId) {

        if (alListMutableLiveData3 == null) {
            alListMutableLiveData3 = new MutableLiveData<>();
            if (GlobalDeclaration.villageData == null) {
                loadAllVillages(level, fyid, mandalId);
            } else {
                alListMutableLiveData3.setValue(GlobalDeclaration.villageData);
            }
        }
        return alListMutableLiveData3;
    }

    public LiveData<List<UserTypesList>> getInstitutionCounts() {

        if (alListInsti == null) {
            alListInsti = new MutableLiveData<>();
            if (GlobalDeclaration.instiCounts == null) {
                loadInstitutionCounts();
            } else {
                alListInsti.setValue(GlobalDeclaration.instiCounts);
            }
        }
        return alListInsti;
    }

    private void loadInstitutionCounts() {
        //pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UserTypesList>> call = apiInterface.
                getInstitutesWiseCount();
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<UserTypesList>>() {


            @Override
            public void onResponse(Call<List<UserTypesList>> call,
                                   retrofit2.Response<List<UserTypesList>> response) {
                // pd.dismiss();
                if (response.body() != null) {
                    alListInsti.setValue(response.body());
                    GlobalDeclaration.instiCounts = response.body();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserTypesList>> call, Throwable t) {

                t.printStackTrace();
                //pd.dismiss();
            }
        });
    }

    private void loadAllDistricts(String role, String fyid) {

        //  pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs1(role, fyid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {

            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call, retrofit2.Response<List<StatelevelDistrictViewCountResponse>> response) {
                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData1.setValue(response.body());
                    GlobalDeclaration.districtData = response.body();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                // pd.dismiss();
            }
        });


    }

    private void loadAllMandals(String role, String fyid, int districtId) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs2(role, fyid, districtId);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {

            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call, retrofit2.Response<List<StatelevelDistrictViewCountResponse>> response) {

                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData2.setValue(response.body());
                    GlobalDeclaration.mandalData = response.body();
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

    private void loadAllVillages(String role, String fyid, int mandalId) {

        //  pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs3(role, fyid, mandalId);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {


            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call, retrofit2.Response<List<StatelevelDistrictViewCountResponse>> response) {
                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData3.setValue(response.body());
                    GlobalDeclaration.villageData = response.body();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                //pd.dismiss();
            }
        });


    }


}