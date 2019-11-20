package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictViewModel extends AndroidViewModel {
    private MutableLiveData<List<Top5>> districtResponseMutableLiveData, bottomDistrcitsList;

    private MutableLiveData<DashboardCountResponse> dashboardCountResponseMutableLiveData;

    public DistrictViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Top5>> getTopDistricts(String role, String did, String uid) {

        districtResponseMutableLiveData = new MutableLiveData<>();

        if (GlobalDeclaration.role.contains("S")) {
            loadTopDistricts(role, "0", uid);
        } else if (GlobalDeclaration.role.contains("G")) {
            loadTopDistricts(role, "0", uid);
        } else {
            loadTopDistricts(role, did, uid);
        }

        return districtResponseMutableLiveData;

    }

    public LiveData<List<Top5>> getBottomDistricts(String role, String did, String uid) {

        bottomDistrcitsList = new MutableLiveData<>();

        if (GlobalDeclaration.role.contains("S")) {
            loadBottomDistricts(role, "0", uid);
        } else if (GlobalDeclaration.role.contains("G")) {
            loadBottomDistricts(role, "0", uid);
        } else {
            loadBottomDistricts(role, did, uid);
        }
        return bottomDistrcitsList;

    }

    private void loadBottomDistricts(String role, String did, String uid) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DistrictResponse> call = apiInterface.Bottom5DistMandalWiseService(did, role,
                "1920", uid, "btm");

        call.enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {

                if (response.body() != null) {
                    bottomDistrcitsList.setValue(response.body().getTop5());
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

    private void loadTopDistricts(String role, String did, String uid) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DistrictResponse> call = apiInterface.Top5DistMandalWiseService(did, role, "1920", uid);

        call.enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {

                if (response.body() != null) {

                    districtResponseMutableLiveData.setValue(response.body().getTop5());
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

    public LiveData<DashboardCountResponse> getDashboardCounts(String type, String userid, String districtId) {

        //if (dashboardCountResponseMutableLiveData == null) {
        dashboardCountResponseMutableLiveData = new MutableLiveData<>();

        loadDashboardCounts(type, userid, districtId);
        //}
        return dashboardCountResponseMutableLiveData;

    }

    private void loadDashboardCounts(String type, String userid, String districtId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DashboardCountResponse> call = apiInterface.getMemberCountsForDashboard();

        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<DashboardCountResponse>() {
            @Override
            public void onResponse(Call<DashboardCountResponse> call, Response<DashboardCountResponse> response) {

                if (response.body().getStatus().equalsIgnoreCase("200")) {
                    dashboardCountResponseMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    Log.e("district view model", "onResponse: Response null");
                }

            }

            @Override
            public void onFailure(Call<DashboardCountResponse> call, Throwable t) {

                // Toast.makeText(getActivity(), "error failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("vm counts", "onFailure");

            }
        });
    }

}
