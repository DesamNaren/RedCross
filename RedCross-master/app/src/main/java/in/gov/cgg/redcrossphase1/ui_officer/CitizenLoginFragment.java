package in.gov.cgg.redcrossphase1.ui_officer;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentCitizenBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitizenLoginFragment extends Fragment {

    FragmentCitizenBinding binding;
    TextInputEditText et_mobile;
    Button btn_login;
    ProgressDialog progressDialog;
    private JsonObject gsonObject;

    public CitizenLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_citizen, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_citizen);
        et_mobile = v.findViewById(R.id.mobile_edit_text);
        btn_login = v.findViewById(R.id.btn_logincitizen);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    if (validate()) {
                        //callLoginRequest();
                        startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
                        getActivity().finish();
                    }
                }
            }
        });


        return v;
    }

    private boolean validate() {
        String mobile = et_mobile.getText().toString();
        if (mobile.trim().equalsIgnoreCase("") ||
                mobile.startsWith("1") || mobile.startsWith("2") || mobile.startsWith("3") || mobile.startsWith("0") ||
                mobile.startsWith("4")) {
            Toast.makeText(getActivity(), "Please enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void callLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject object = new JSONObject();
        try {
            object.put("userName", "RCADLB");
            object.put("passwd", "RCADLB");
            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
            Log.e("sent_json", object.toString());

            Call<JsonObject> call = apiInterface.callLogin(gsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {

                        //Log.d("Login", "onResponse: "+response.body().getAsString());
                        String message = response.body().get("message").toString();
                        String status = response.body().get("status").toString();


                        if (status.equals("200")) {

                            String role = response.body().get("role").toString();
                            String districtId = response.body().get("districtId").toString();
                            String userID = response.body().get("userID").toString();
                            GlobalDeclaration.role = role;
                            GlobalDeclaration.districtId = districtId;
                            GlobalDeclaration.userID = userID;

                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), OfficerMainActivity.class));
                            getActivity().finish();
                        } else if (status.equals("100")) {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            // etName.setText("");
                            //etPwd.setText("");
                        } else {

                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
