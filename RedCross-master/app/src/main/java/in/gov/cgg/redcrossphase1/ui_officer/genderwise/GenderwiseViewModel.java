package in.gov.cgg.redcrossphase1.ui_officer.genderwise;

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

public class GenderwiseViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    private MutableLiveData<List<Genders>> genderResponseMutableLiveData;

    Context context;
    // ProgressDialog pd;

    public GenderwiseViewModel(Context context) {
        this.context = context;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<Genders>> getGender(String role, String districtid, String userid) {

        // if (genderResponseMutableLiveData == null) {
        genderResponseMutableLiveData = new MutableLiveData<>();
        loadGenders(userid, districtid, role);
        //}
        return genderResponseMutableLiveData;
    }

    private void loadGenders(String userid, String districtid, String role) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GenderResponse> call = apiInterface.GenderWiseService(districtid, role, "1920", userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<GenderResponse>() {
            @Override
            public void onResponse(Call<GenderResponse> call, Response<GenderResponse> response) {

                if (response.body() != null) {
                    genderResponseMutableLiveData.setValue(response.body().getGenders());
                    pd.dismiss();

                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GenderResponse> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }


}