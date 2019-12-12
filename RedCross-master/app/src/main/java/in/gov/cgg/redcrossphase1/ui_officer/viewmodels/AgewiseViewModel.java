package in.gov.cgg.redcrossphase1.ui_officer.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Age;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.AgeResponse;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgewiseViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    private MutableLiveData<List<Age>> ageListMutableLiveData;

    //List<Age>  ageList=new ArrayList<>();
    Context context;
    //ProgressDialog pd;

    public AgewiseViewModel(Context application) {
        this.context = application;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<Age>> getAges(String role, String distid, String uid) {

        ageListMutableLiveData = new MutableLiveData<>();
        loadAges(role, distid, uid);
        return ageListMutableLiveData;
    }

    private void loadAges(String role, String distid, String uid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AgeResponse> call = apiInterface.ageWiseService(distid, role, "1920", uid);

        Log.e("  url", call.request().url().toString());


        call.enqueue(new Callback<AgeResponse>() {
            @Override
            public void onResponse(Call<AgeResponse> call, Response<AgeResponse> response) {

                if (response.body() != null) {

                    //ageList.addAll(response.body().getAges());
                    ageListMutableLiveData.setValue(response.body().getAges());
                    pd.dismiss();


                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<AgeResponse> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }


}