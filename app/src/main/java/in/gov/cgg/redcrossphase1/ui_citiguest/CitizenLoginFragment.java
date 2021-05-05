package in.gov.cgg.redcrossphase1.ui_citiguest;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentCitizenBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.LangSpinnerCustomAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AllScreen;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AllScreenMain;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLanguagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.Language;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CitizenLoginFragment extends Fragment {

    TextInputEditText et_mobile;
    Button btn_login;
    CustomProgressDialog progressDialog;
    FragmentCitizenBinding binding;
    Spinner spinner_language_sel;
    List<Language> languageBeanList = new ArrayList<>();
    LangSpinnerCustomAdapter langspinnerCustomAdapter;
    String LanguageShortCode = "";
    String LanguageShortid = "";
    SharedPreferences languages;
    SharedPreferences.Editor editor;
    List<AllScreen> myallScreens;
    private JsonObject gsonObject;
    private int selectedThemeColor = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

//        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(
//                inflater, R.layout.fragment_citizen, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_citizen, container, false);

        progressDialog = new in.gov.cgg.redcrossphase1.utils.CustomProgressDialog(getActivity());
        //  progressDialog.show();


        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                    getInt(getResources().getString(R.string.theme_color1), -1);
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
        spinner_language_sel = binding.spinnerLanguageSel;
        //   et_mobile.setText("9999999998");


        if (CheckInternet.isOnline(getActivity())) {

            //callLanguagesSpinnerRequest();
            callLangSpinnerRequest();

        } else {
            Toast.makeText(getActivity(), R.string.error_occur, Toast.LENGTH_SHORT).show();
        }

        spinner_language_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LanguageShortCode = languageBeanList.get(i).getLanguageShortCode();
                LanguageShortid = languageBeanList.get(i).getLanguageId();

                GlobalDeclaration.LangShortCode = LanguageShortCode;
                GlobalDeclaration.LangShortId = LanguageShortid;


                languages = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE);
                editor = languages.edit();
                editor.putString(getResources().getString(R.string.langShortCode), GlobalDeclaration.LangShortCode);
                editor.putString(getResources().getString(R.string.langShortId), GlobalDeclaration.LangShortId);
                editor.commit();

                Log.d("LID", LanguageShortCode + "===" + LanguageShortid);


                if (CheckInternet.isOnline(getActivity())) {

                    // callcitizenAllScreenRequest(GlobalDeclaration.LangShortId);

                    callcitizenAllScreenRequest(GlobalDeclaration.LangShortId);


                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnLogincitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (CheckInternet.isOnline(getActivity())) {

                    if (validate()) {
                        GlobalDeclaration.guest = "";
                        callcitizenLoginRequest();

                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    private void callcitizenAllScreenRequest(String langShortId) {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //   CitizenAllScreenRequest request = new CitizenAllScreenRequest();
        //   request.setLangType(GlobalDeclaration.LangShortId);

        Call<AllScreenMain> call = apiInterface.callCitizenAllScreen(langShortId);
        Log.d("AllScreenreq", "callcitizenLoginRequesturl: " + call.request().url());

        call.enqueue(new Callback<AllScreenMain>() {
            @Override
            public void onResponse(Call<AllScreenMain> call, Response<AllScreenMain> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AllScreenMain> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.error_occur, Toast.LENGTH_SHORT).show();

            }
        });

/*
        call.enqueue(new Callback<AllScreenMain>() {
            @Override
            public void onResponse(Call<AllScreenMain> call, Response<AllScreenMain> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    Log.d("AllScreenRes", "onResponse: " + response.body().toString());
                  //  String message = response.body().getAllScreens().toString();


                }

            }

            @Override
            public void onFailure(Call<AllScreenMain> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.error_occur, Toast.LENGTH_SHORT).show();
            }
        });
*/


    }


    private void callLangSpinnerRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CitizenLanguagesResponse> call = apiInterface.getLanguages();
        Log.d("Languages", "callcitizenLangRequesturl: " + call.request().url());

        call.enqueue(new Callback<CitizenLanguagesResponse>() {
            @Override
            public void onResponse(Call<CitizenLanguagesResponse> call, Response<CitizenLanguagesResponse> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    Log.d("Lang", "onResponse: " + response.body().toString());
                    String message = response.body().getMessage();
                    String status = response.body().getStatus();
                    Log.d("Lang", "onResponse: " + message + " " + status);

                    if (status.equalsIgnoreCase("200")) {

                        languageBeanList = response.body().getLanguages();

                       /* Language lang_bean = new Language();
                        lang_bean.setLanguageId("0");
                        lang_bean.setLanguageName("Select Language");
                        lang_bean.setLanguageShortCode("Select");*/

                        // languageBeanList.add(lang_bean);


                        langspinnerCustomAdapter = new LangSpinnerCustomAdapter(languageBeanList, getActivity());
                        binding.spinnerLanguageSel.setAdapter(langspinnerCustomAdapter);
                        langspinnerCustomAdapter.notifyDataSetChanged();


                      /*  binding.spinnerLanguageSel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LanguageShortCode = languageBeanList.get(position).getLanguageShortCode();
                                LanguageShortid = languageBeanList.get(position).getLanguageId();

                                Log.d("LID",LanguageShortCode+"==="+LanguageShortid);


                            }
                        });*/
                    } else if (status.equalsIgnoreCase("100")) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<CitizenLanguagesResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

/*
        call.enqueue(new Callback<List<CitizenLanguagesResponse>>() {

                @Override
            public void onResponse(Call<List<CitizenLanguagesResponse>> call, Response<List<CitizenLanguagesResponse>> response) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "Status"+response.body().get(0).getStatus(), Toast.LENGTH_SHORT).show();



*/
/*
                if (response.body() != null) {
                    Log.d("Lang", "onResponse: " + response.body().toString());
                    String message = response.body().get(0).getMessage();
                    String status = response.body().get(0).getStatus();
                    Log.d("Lang", "onResponse: " + message + " " + status)



                 *//*

*

            }

            @Override
            public void onFailure(Call<List<CitizenLanguagesResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
            }


        });
*/


    }


    private void callcitizenLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        GlobalDeclaration.citizenMobile = et_mobile.getText().toString().trim();
        CitizenLoginRequest request = new CitizenLoginRequest();
        request.setMobileNumber(et_mobile.getText().toString().trim());

        Call<CitizenLoginResponse> call = apiInterface.callCitizenLogin(request);
        Log.d("Login", "callcitizenLoginRequesturl: " + call.request().url());
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
                        if (response.body().getPhotoPath() != null) {
                            if (response.body().getPhotoPath().isEmpty()) {
                                GlobalDeclaration.profilePic = response.body().getPhotoPath();
                            }
                        }

                        Log.e("pathurl", GlobalDeclaration.profilePic);

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
                Toast.makeText(getActivity(), R.string.error_occur, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validate() {
        //  String mobile = binding.mobileEditText.getText().toString();
        if (et_mobile.getText().toString().trim().equalsIgnoreCase("") ||
                et_mobile.getText().toString().startsWith("1") || et_mobile.getText().toString().startsWith("2") || et_mobile.getText().toString().startsWith("3") || et_mobile.getText().toString().startsWith("0") ||
                et_mobile.getText().toString().startsWith("4")) {
            Toast.makeText(getActivity(), R.string.enter_mobile_number, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
