package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;

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

public class GovtPvtViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    private MutableLiveData<List<Last10day>> goListMutableLiveData;

    Context context;
    // ProgressDialog pd;

    public GovtPvtViewModel(Context context) {
        this.context = context;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<Last10day>> getGovtPvt(String districtid) {

        // if (genderResponseMutableLiveData == null) {
        goListMutableLiveData = new MutableLiveData<>();
        loadGovtPvt(districtid);
        //}
        return goListMutableLiveData;
    }

    private void loadGovtPvt(String districtid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GovtVsPvtResponse> call = apiInterface.GovPvtService(districtid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<GovtVsPvtResponse>() {
            @Override
            public void onResponse(Call<GovtVsPvtResponse> call, Response<GovtVsPvtResponse> response) {

                pd.dismiss();
                if (response.body() != null) {

                    goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GovtVsPvtResponse> call, Throwable t) {

                pd.dismiss();
                t.printStackTrace();
            }
        });


    }


}