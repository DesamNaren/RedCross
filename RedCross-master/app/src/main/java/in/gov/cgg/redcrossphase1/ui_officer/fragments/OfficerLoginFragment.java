package in.gov.cgg.redcrossphase1.ui_officer.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentOfficerBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OfficerLoginFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    FragmentOfficerBinding binding;
    TextInputEditText et_name, et_pswd;
    Button btn_login;
    LinearLayout ll_rememberme;
    CheckBox check_rememberme;
    TextView TV_CHECKREMMEBR;
    //ProgressDialog progressDialog;
    private JsonObject gsonObject;
    private String name, pwd;
    private SharedPreferences sharedpreferences;
    private boolean locaisChecked;
    private String shareuname, sharepswd;
    private boolean isSharedCheked;
    private CustomProgressDialog progressDialog;
    private int selectedThemeColor;

    public OfficerLoginFragment() {
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

        progressDialog = new CustomProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait");
        View v = inflater.inflate(R.layout.fragment_officer, container, false);
        et_name = v.findViewById(R.id.name_edit_text);
        et_pswd = v.findViewById(R.id.password_edit_text);
        ll_rememberme = v.findViewById(R.id.ll_rememberme);
        check_rememberme = v.findViewById(R.id.checkremember);
        TV_CHECKREMMEBR = v.findViewById(R.id.tv_checkremember);
        btn_login = v.findViewById(R.id.btn_loginofficer);


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).
                    getInt("theme_color", -1);
            if (selectedThemeColor != -1) {

                TV_CHECKREMMEBR.setTextColor(getResources().getColor(selectedThemeColor));
                btn_login.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else {
                TV_CHECKREMMEBR.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                btn_login.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            TV_CHECKREMMEBR.setTextColor(getResources().getColor(R.color.redcroosbg_2));
            btn_login.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));

        }


        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        shareuname = prefs.getString("un", "");
        sharepswd = prefs.getString("pw", "");
        isSharedCheked = prefs.getBoolean("is", false);



        if (shareuname != null && sharepswd != null && isSharedCheked) {
            et_name.setText(shareuname);
            et_pswd.setText(sharepswd);
            check_rememberme.setChecked(true);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    if (validate()) {
                        callLoginRequest();
                    }
                }
            }
        });

//        check_rememberme.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(getActivity(), "cHECKED", Toast.LENGTH_SHORT).show();
//                // Toast.makeText(getActivity(), "UN", Toast.LENGTH_SHORT).show();
//                isChecked = check_rememberme.isChecked();
//            }
//        });

        return v;
    }

    private void storeinSharedPrefs() {

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        editor.putString("un", name);
        editor.putString("pw", pwd);
        editor.putBoolean("is", locaisChecked);
        editor.apply();
    }

    private void callLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject object = new JSONObject();
        try {
            object.put("userName", name);
            object.put("passwd", pwd);
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
                            if (response.body().get("designation") != null) {
                                String designation = String.valueOf(response.body().get("designation"));
                                designation = designation.replace("\"", "");
                                GlobalDeclaration.designation = designation;

                            }
                            GlobalDeclaration.role = role;
                            GlobalDeclaration.districtId = districtId;
                            GlobalDeclaration.userID = userID;
                            GlobalDeclaration.username = name;
                            GlobalDeclaration.guest = "";

                            if (locaisChecked) {
                                storeinSharedPrefs();
                            } else {
                                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("un", "");
                                editor.putString("pw", "");
                                editor.putBoolean("is", false);
                                editor.apply();
                            }

                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
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

    private boolean validate() {

        name = et_name.getText().toString();
        pwd = et_pswd.getText().toString();
        locaisChecked = check_rememberme.isChecked();

        if (et_name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter valid username", Toast.LENGTH_LONG).show();
            et_name.requestFocus();
            return false;
        }

        if (et_pswd.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
            et_pswd.requestFocus();
            return false;
        }
        return true;
    }
}
