package in.gov.cgg.redcrossphase1.ui_cgcitizen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.ActivityLoginBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    Button buttonlogin;
    EditText etName, etPwd;
    ProgressDialog progressDialog;
    private String name, password;
    private JsonObject gsonObject;
    ActivityLoginBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait");

        buttonlogin = binding.btnSubmit;
        etName = binding.nameEditText;
        etPwd = binding.passwordEditText;

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(LoginActivity.this)) {

                    name = etName.getText().toString();
                    password = etPwd.getText().toString();
                    if (validate()) {
                        callLoginRequest();

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void callLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject object = new JSONObject();
        try {
            object.put("userName", name);
            object.put("passwd", password);
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

                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else if (status.equals("100")) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            etName.setText("");
                            etPwd.setText("");
                        } else {

                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean validate() {
        if (etName.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(LoginActivity.this, "Please enter valid username", Toast.LENGTH_LONG).show();
            return false;
        }

        if (etPwd.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
