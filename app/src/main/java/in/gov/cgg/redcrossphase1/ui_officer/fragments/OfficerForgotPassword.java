package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.databinding.FragmentOfficerforgotpswdBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfficerForgotPassword extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    FragmentOfficerforgotpswdBinding binding;
    private String otp, newPswd, confirmPswd;
    private CustomProgressDialog progressDialog;
    private boolean proceed;
    private int selectedThemeColor = -1;
    private boolean proceedotp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler(this));

        binding = DataBindingUtil.setContentView(OfficerForgotPassword.this, R.layout.fragment_officerforgotpswd);


        progressDialog = new CustomProgressDialog(OfficerForgotPassword.this);


        try {
            selectedThemeColor = getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                    getInt("theme_color", -1);

            // getSupportActionBar().setB
            if (selectedThemeColor != -1) {

                binding.btnSubmit.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.btnSendotp.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else {
                binding.btnSubmit.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                binding.btnSendotp.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));


            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.btnSubmit.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            binding.btnSendotp.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
        }


        binding.etUname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUsername(binding.etUname.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkOtp(binding.etOtp.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        binding.btnSendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (proceed) {
                    callAlert();

                } else {
                    binding.llChangeHere.setVisibility(View.GONE);
                    binding.llSendotp.setVisibility(View.VISIBLE);
                    binding.btnSubmit.setVisibility(View.GONE);
                    binding.tilUname.setError("Please enter correct username");

                }
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (proceedotp) {

                        callChangePassword();

                    } else {
                        binding.btnSubmit.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private void callAlert() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OfficerForgotPassword.this);
        builder1.setMessage("OTP sent to mobilenumber and email will expire in 15 minutes");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        binding.llChangeHere.setVisibility(View.VISIBLE);
                        binding.llSendotp.setVisibility(View.GONE);
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void checkUsername(final String uname) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {

            Call<JsonObject> call = apiInterface.forgotPassword(uname);
            Log.e("check uname url", "uname: " + call.request().url());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.body() != null) {
                        String status = response.body().get("status").toString();


                        if (status.contains("uccess")) {
                            // Toast.makeText(OfficerForgotPassword.this, status, Toast.LENGTH_SHORT).show();
                            binding.btnSendotp.setVisibility(View.VISIBLE);
                            binding.tilUname.setError(null);
                            proceed = true;
                            GlobalDeclaration.forgotuname = uname;
                        } else {
                            // Toast.makeText(OfficerForgotPassword.this, status, Toast.LENGTH_SHORT).show();
                            binding.btnSendotp.setVisibility(View.GONE);
                            binding.tilUname.setError("Please enter correct username");
                            proceed = false;
                        }
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Toast.makeText(OfficerForgotPassword.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }
    }

    private void checkOtp(String otp) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {

            Call<JsonObject> call = apiInterface.checkotp(otp, GlobalDeclaration.forgotuname);
            Log.e("check otp url", "checkotp: " + call.request().url());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.body() != null) {
                        String status = response.body().get("status").toString();


                        if (status.contains("uccess")) {
                            //Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            binding.btnSubmit.setVisibility(View.VISIBLE);
                            binding.tilOtp.setError(null);
                            proceedotp = true;


                        } else {
                            //Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            binding.btnSubmit.setVisibility(View.GONE);
                            binding.tilOtp.setError("Invalid OTP");
                            proceedotp = false;
                        }
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Toast.makeText(OfficerForgotPassword.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }

    }

    private void callChangePassword() {


        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {

            Call<JsonObject> call = apiInterface.savePasswordforForgot(newPswd, confirmPswd, GlobalDeclaration.forgotuname);
            Log.e("changeforgot url", "changepswd: " + call.request().url());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.body() != null) {
                        String status = response.body().get("status").toString();

                        try {
                            SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("un", GlobalDeclaration.username);
                            editor.putString("pw", confirmPswd);
                            editor.apply();

                            binding.etConfirm.setText("");
                            binding.etNewpassword.setText("");
                            binding.etOtp.setText("");


                        } catch (Exception e) {

                        }

                        if (status.contains("uccess")) {
                            Toast.makeText(OfficerForgotPassword.this, status, Toast.LENGTH_SHORT).show();


                            AlertDialog.Builder builder1 = new AlertDialog.Builder(OfficerForgotPassword.this);
                            builder1.setMessage(status);
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    getResources().getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            startActivity(new Intent(OfficerForgotPassword.this, TabLoginActivity.class));
                                            finish();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();


                        } else {
                            Toast.makeText(OfficerForgotPassword.this, status, Toast.LENGTH_SHORT).show();
                        }
                    }

                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    if (progressDialog.isShowing()) {

                        progressDialog.dismiss();
                    }
                    Toast.makeText(OfficerForgotPassword.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }

    }

    private boolean validate() {

        newPswd = binding.etNewpassword.getText().toString();
        confirmPswd = binding.etConfirm.getText().toString();


        if (binding.etNewpassword.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(OfficerForgotPassword.this, "Please enter New Password", Toast.LENGTH_LONG).show();
            binding.etNewpassword.requestFocus();
            return false;
        }
        if (binding.etConfirm.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(OfficerForgotPassword.this, "Please Confirm Password", Toast.LENGTH_LONG).show();
            binding.etConfirm.requestFocus();
            return false;
        }
        if (!newPswd.equals(confirmPswd)) {
            Toast.makeText(OfficerForgotPassword.this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
            binding.etConfirm.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_backpress, menu);

        MenuItem logout = menu.findItem(R.id.home);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(OfficerForgotPassword.this);
                builder1.setMessage("Do you want to exit ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                }
                            }
                        });

                builder1.setNegativeButton(
                        getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
