package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.RegisBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CustomProgressDialog progressDialog;
    RegisBinding binding;
    TextInputEditText et_name;
    TextInputEditText et_email;
    TextInputEditText et_mobile;
    private JsonObject gsonObject;
    private int selectedThemeColor = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.regis);

        et_name = binding.nameEditText;
        et_email = binding.emailEditText;
        et_mobile = binding.mobileEditText;


        try {
            selectedThemeColor = this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).
                    getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                binding.tvSignUp.setTextColor(getResources().getColor(selectedThemeColor));
                binding.btnRegister.setBackgroundColor(getResources().getColor(selectedThemeColor));

                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));

                } else {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                    binding.tvSignUp.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                }
            } else {
                binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                binding.tvSignUp.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                binding.btnRegister.setTextColor(getResources().getColor(R.color.redcroosbg_2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
            binding.tvSignUp.setTextColor(getResources().getColor(R.color.redcroosbg_2));
            binding.btnRegister.setTextColor(getResources().getColor(R.color.redcroosbg_2));
        }



        progressDialog = new CustomProgressDialog(RegisterActivity.this);
        //   progressDialog = new ProgressDialog(RegisterActivity.this);
        //   progressDialog.setMessage("Please wait");


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(RegisterActivity.this)) {
                    if (validate()) {
                        callLoginRequest();

                    }
                }
            }
        });
    }


    private void callLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject object = new JSONObject();
        try {
            object.put("name", et_name.getText().toString());
            object.put("mobileNumber", et_mobile.getText().toString());
            object.put("emailId", et_email.getText().toString());

            GlobalDeclaration.unamefromReg = et_name.getText().toString();

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
            Log.e("sent_json", object.toString());

            Call<JsonObject> call = apiInterface.callCitizenRegistration(gsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {

                        String message = response.body().get("message").toString();
                        String status = response.body().get("status").toString();


                        if (status.equals("200")) {
                            String role = response.body().get("role").toString();
                            GlobalDeclaration.citizenrole = role;
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, CitiGuestMainActivity.class));
                            finish();
                        } else if (status.equals("100")) {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private boolean validate() {

        if (et_name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, "Please valid enter name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_mobile.getText().toString().trim().equalsIgnoreCase("") ||
                et_mobile.getText().toString().startsWith("1") || et_mobile.getText().toString().startsWith("2") || et_mobile.getText().toString().startsWith("3") || et_mobile.getText().toString().startsWith("0") ||
                et_mobile.getText().toString().startsWith("4")) {
            Toast.makeText(RegisterActivity.this, "Please valid enter mobile number", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_email.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, "Please valid enter email", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}