package in.gov.cgg.redcrossphase1.ui_officer.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DayWiseReportCountResponse;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaywiseViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    private MutableLiveData<List<DayWiseReportCountResponse>> dayListMutableLiveData;


    Context context;
    //ProgressDialog pd;


    public DaywiseViewModel(Context application) {
        this.context = application;
        // pd = new ProgressDialog(context);
        //pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<DayWiseReportCountResponse>> getDaysCount(String finanyear, String distid, String monthid) {

        dayListMutableLiveData = new MutableLiveData<>();
        if (distid.equalsIgnoreCase("0")) {
            loadDaywiseCounts(finanyear, "", monthid);
        } else {
            loadDaywiseCounts(finanyear, distid, monthid);
        }
        return dayListMutableLiveData;
    }

    private void loadDaywiseCounts(String finanyear, String distid, String monthid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DayWiseReportCountResponse>> call = apiInterface.DayWiseReportDataWS(distid, finanyear, monthid);

        Log.e(" day url", call.request().url().toString());


        call.enqueue(new Callback<List<DayWiseReportCountResponse>>() {
            @Override
            public void onResponse(Call<List<DayWiseReportCountResponse>> call, Response<List<DayWiseReportCountResponse>> response) {
                pd.dismiss();
                if (response.body() != null) {

                    //ageList.addAll(response.body().getAges());
                    dayListMutableLiveData.setValue(response.body());


                } else {
                    // Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<DayWiseReportCountResponse>> call, Throwable t) {

                pd.dismiss();
                t.printStackTrace();
            }
        });


    }


}