package in.gov.cgg.redcrossphase1.ui_officer.agewise;

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

public class AgewiseViewModel extends AndroidViewModel {

    private MutableLiveData<List<Age>> ageListMutableLiveData;

    //List<Age>  ageList=new ArrayList<>();

    public AgewiseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Age>> getAges(String role, String distid, String uid) {

        ageListMutableLiveData = new MutableLiveData<>();
        loadAges(role, distid, uid);
        return ageListMutableLiveData;
    }

    private void loadAges(String role, String distid, String uid) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AgeResponse> call = apiInterface.ageWiseService(distid, role, "1920", uid);

        Log.e("  url", call.request().url().toString());


        call.enqueue(new Callback<AgeResponse>() {
            @Override
            public void onResponse(Call<AgeResponse> call, Response<AgeResponse> response) {

                if (response.body() != null) {

                    //ageList.addAll(response.body().getAges());
                    ageListMutableLiveData.setValue(response.body().getAges());


                } else {
                    Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AgeResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }


}