package in.gov.cgg.redcrossphase1.ui_officer.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Top5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictViewModel extends ViewModel {
    //  private final CustomProgressDialog customProgressDialog;
    private MutableLiveData<List<Top5>> districtResponseMutableLiveData, bottomDistrcitsList;

    Context context;
    //ProgressDialog pd;
    private MutableLiveData<DashboardCountResponse> dashboardCountResponseMutableLiveData;


    public DistrictViewModel(Context application) {
        this.context = application;
        // pd = new ProgressDialog(context);
        //pd.setMessage("Loading ,Please wait");
        //customProgressDialog = new CustomProgressDialog(context);


    }


//    public LiveData<List<Top5>> getTopDistricts(String role, String did, String uid) {
//
//        districtResponseMutableLiveData = new MutableLiveData<>();
//
//        if (GlobalDeclaration.role.contains("S")) {
//            loadTopDistricts(role, "0", uid);
//        } else if (GlobalDeclaration.role.contains("G")) {
//            loadTopDistricts(role, "0", uid);
//        } else {
//            loadTopDistricts(role, did, uid);
//        }
//
//        return districtResponseMutableLiveData;
//
//    }
//
//    public LiveData<List<Top5>> getBottomDistricts(String role, String did, String uid) {
//
//        bottomDistrcitsList = new MutableLiveData<>();
//
//        if (GlobalDeclaration.role.contains("S")) {
//            loadBottomDistricts(role, "0", uid);
//        } else if (GlobalDeclaration.role.contains("G")) {
//            loadBottomDistricts(role, "0", uid);
//        } else {
//            loadBottomDistricts(role, did, uid);
//        }
//        return bottomDistrcitsList;
//
//    }

//    private void loadBottomDistricts(String role, String did, String uid) {
//        customProgressDialog.show();
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<DistrictResponse> call = apiInterface.Bottom5DistMandalWiseService(did, role,
//                "1920", uid, "btm");
//
//        call.enqueue(new Callback<DistrictResponse>() {
//            @Override
//            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
//
//                if (response.body() != null) {
//                    bottomDistrcitsList.setValue(response.body().getTop5());
//                    customProgressDialog.dismiss();
//                } else {
//                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//                    Log.e("district view model", "onResponse: Response null");
//                    customProgressDialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DistrictResponse> call, Throwable t) {
//
//                // Toast.makeText(getActivity(), "error failure", Toast.LENGTH_SHORT).show();
//                t.printStackTrace();
//                Log.e("district view model", "onFailure");
//                customProgressDialog.dismiss();
//
//            }
//        });
//    }
//
//    private void loadTopDistricts(String role, String did, String uid) {
//
//        customProgressDialog.show();
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<DistrictResponse> call = apiInterface.Top5DistMandalWiseService(did, role, "1920", uid);
//
//        call.enqueue(new Callback<DistrictResponse>() {
//            @Override
//            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
//
//                if (response.body() != null) {
//                    districtResponseMutableLiveData.setValue(response.body().getTop5());
//                    customProgressDialog.dismiss();
//                } else {
//                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//                    Log.e("district view model", "onResponse: Response null");
//                    customProgressDialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DistrictResponse> call, Throwable t) {
//
//                // Toast.makeText(getActivity(), "error failure", Toast.LENGTH_SHORT).show();
//                customProgressDialog.dismiss();
//                t.printStackTrace();
//                Log.e("district view model", "onFailure");
//
//            }
//        });
//
//    }

    public LiveData<DashboardCountResponse> getDashboardCounts(String districtId) {

        if (dashboardCountResponseMutableLiveData == null) {
            dashboardCountResponseMutableLiveData = new MutableLiveData<>();
            if (GlobalDeclaration.counts == null) {
                loadDashboardCounts(districtId);
            } else {
                dashboardCountResponseMutableLiveData.setValue(GlobalDeclaration.counts);
            }
        }
        return dashboardCountResponseMutableLiveData;

    }

    private void loadDashboardCounts(String districtId) {
        // customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DashboardCountResponse> call = apiInterface.getMemberCountsForDashboard(districtId);

        Log.e("counts url", call.request().url().toString());

        call.enqueue(new Callback<DashboardCountResponse>() {
            @Override
            public void onResponse(Call<DashboardCountResponse> call, Response<DashboardCountResponse> response) {

                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        // customProgressDialog.dismiss();
                        dashboardCountResponseMutableLiveData.setValue(response.body());
                        GlobalDeclaration.counts = response.body();
                    } else {
                        //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        Log.e("district view model", "onResponse: Response null");
                        //  customProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCountResponse> call, Throwable t) {

                // Toast.makeText(getActivity(), "error failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                GlobalDeclaration.failedcounts = true;
                Log.e("vm counts", "onFailure");
                //    customProgressDialog.dismiss();

            }
        });
    }

}
