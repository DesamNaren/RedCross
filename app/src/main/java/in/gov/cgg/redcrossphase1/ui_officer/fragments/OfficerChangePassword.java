package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentChangepasswordBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import in.gov.cgg.redcrossphase1.utils.ExceptionHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OfficerChangePassword extends Fragment {


    public static final String MyPREFERENCES = "MyPrefs";
    FragmentChangepasswordBinding binding;
    private String currentPswd, newPswd, confirmPswd;
    private CustomProgressDialog progressDialog;
    private boolean proceed;
    private int selectedThemeColor = -1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_changepassword, container, false);


        progressDialog = new CustomProgressDialog(getActivity());
        getActivity().setTitle("Change Password");


        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                    getInt(getResources().getString(R.string.theme_color1), -1);
            if (selectedThemeColor != -1) {

                binding.btnSubmit.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else {
                binding.btnSubmit.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));


            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.btnSubmit.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));


        }


        binding.nameCurrentpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCurrentPassword(binding.nameCurrentpassword.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (proceed) {
                        callChangePassword();
                    } else {
                        binding.btnSubmit.setVisibility(View.GONE);
                        binding.nameCurrentpassword.setError("Please enter correct password");
                    }

                }
            }
        });


        return binding.getRoot();

    }

    private void checkCurrentPassword(String currentPswdString) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {

            Call<JsonObject> call = apiInterface.checkPassword(currentPswdString, GlobalDeclaration.username, "mobile");
            Log.e("check current url", "checkCurrentPassword: " + call.request().url());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.body() != null) {
                        String status = response.body().get("status").toString();


                        if (status.contains("uccess")) {
                            //Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            binding.btnSubmit.setVisibility(View.VISIBLE);
                            binding.til.setError(null);
                            proceed = true;


                        } else {
                            //Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            binding.btnSubmit.setVisibility(View.GONE);
                            binding.til.setError("Please enter correct password");
                            proceed = false;
                        }
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }

    }

    private void callChangePassword() {


        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {

            Call<JsonObject> call = apiInterface.saveNewPassword(confirmPswd, GlobalDeclaration.username, "mobile");
            Log.e("change url", "checkCurrentPassword: " + call.request().url());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.body() != null) {
                        String status = response.body().get("status").toString();


                        if (status.contains("success")) {
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();

                            try {
                                SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("un", GlobalDeclaration.username);
                                editor.putString("pw", confirmPswd);
                                editor.apply();

                                binding.passwordConfirm.setText("");
                                binding.nameCurrentpassword.setText("");
                                binding.passwordNewpassword.setText("");

                            } catch (Exception e) {

                            }

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage(status);
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    getResources().getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        } else {
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                        }
                    }

                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    if (progressDialog.isShowing()) {

                        progressDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }

    }

    private boolean validate() {

        currentPswd = binding.nameCurrentpassword.getText().toString();
        newPswd = binding.passwordNewpassword.getText().toString();
        confirmPswd = binding.passwordConfirm.getText().toString();

        if (binding.nameCurrentpassword.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter Current Password", Toast.LENGTH_LONG).show();
            binding.nameCurrentpassword.requestFocus();
            return false;
        }

        if (binding.passwordNewpassword.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter New Password", Toast.LENGTH_LONG).show();
            binding.passwordNewpassword.requestFocus();
            return false;
        }
        if (binding.passwordConfirm.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please Confirm Password", Toast.LENGTH_LONG).show();
            binding.passwordConfirm.requestFocus();
            return false;
        }
        if (!newPswd.equals(confirmPswd)) {
            Toast.makeText(getActivity(), "Passwords doesn't match", Toast.LENGTH_LONG).show();
            binding.passwordConfirm.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_backpress, menu);
        MenuItem logout = menu.findItem(R.id.home);
        logout.setIcon(R.drawable.ic_home_white_48dp);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
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
