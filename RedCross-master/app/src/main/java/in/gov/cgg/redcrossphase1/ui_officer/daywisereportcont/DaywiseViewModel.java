package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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

public class DaywiseViewModel extends AndroidViewModel {

    private MutableLiveData<List<DayWiseReportCountResponse>> dayListMutableLiveData;


    public DaywiseViewModel(@NonNull Application application) {
        super(application);
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

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DayWiseReportCountResponse>> call = apiInterface.DayWiseReportDataWS(distid, finanyear, "11");

        Log.e("  url", call.request().url().toString());


        call.enqueue(new Callback<List<DayWiseReportCountResponse>>() {
            @Override
            public void onResponse(Call<List<DayWiseReportCountResponse>> call, Response<List<DayWiseReportCountResponse>> response) {

                if (response.body() != null) {

                    //ageList.addAll(response.body().getAges());
                    dayListMutableLiveData.setValue(response.body());


                } else {
                    Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<DayWiseReportCountResponse>> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}