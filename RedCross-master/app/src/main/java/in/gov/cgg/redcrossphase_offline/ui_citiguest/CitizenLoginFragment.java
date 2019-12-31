package in.gov.cgg.redcrossphase_offline.ui_citiguest;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.databinding.FragmentCitizenBinding;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiClient;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.CitizenLoginRequest;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.CitizenLoginResponse;
import in.gov.cgg.redcrossphase_offline.utils.CheckInternet;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CitizenLoginFragment extends Fragment {

    FragmentCitizenBinding binding;
    TextInputEditText et_mobile;
    Button btn_login;
    CustomProgressDialog progressDialog;
    private JsonObject gsonObject;
    private int selectedThemeColor = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_citizen, container, false);
        progressDialog = new CustomProgressDialog(getActivity());
        //  progressDialog.show();


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).
                    getInt("theme_color", -1);
            if (selectedThemeColor != -1) {

                binding.btnLogincitizen.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else {
                binding.btnLogincitizen.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
            btn_login.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));

        }

        //  progressDialog.setMessage("Please wait");
        et_mobile = binding.mobileEditText;
        //   et_mobile.setText("9999999998");


        binding.btnLogincitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    if (validate()) {
                        GlobalDeclaration.guest = "";
                        callcitizenLoginRequest();

                    }
                }
            }
        });


        return binding.getRoot();
    }


    private void callcitizenLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        CitizenLoginRequest request = new CitizenLoginRequest();
        request.setMobileNumber(et_mobile.getText().toString().trim());

        Call<CitizenLoginResponse> call = apiInterface.callCitizenLogin(request);
        Log.d("Login", "callcitizenLoginRequest: " + call.request().url());
        call.enqueue(new Callback<CitizenLoginResponse>() {
            @Override
            public void onResponse(Call<CitizenLoginResponse> call, Response<CitizenLoginResponse> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    Log.d("Login", "onResponse: " + response.body().toString());
                    String message = response.body().getMessage();
                    int status = response.body().getStatus();
                    Log.d("Login", "onResponse: " + message + " " + status);


                    if (status == 200) {
                        String role = response.body().getRole();
                        String mobileNumber = response.body().getMobileNumber();
                        String name = response.body().getName();
                        String emailID = response.body().getEmailId();
                        GlobalDeclaration.citizenrole = role;
                        GlobalDeclaration.loginresponse = response.body();

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
                        getActivity().finish();
                    } else if (status == 100) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CitizenLoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validate() {
        //  String mobile = binding.mobileEditText.getText().toString();
        if (et_mobile.getText().toString().trim().equalsIgnoreCase("") ||
                et_mobile.getText().toString().startsWith("1") || et_mobile.getText().toString().startsWith("2") || et_mobile.getText().toString().startsWith("3") || et_mobile.getText().toString().startsWith("0") ||
                et_mobile.getText().toString().startsWith("4")) {
            Toast.makeText(getActivity(), "Please enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
