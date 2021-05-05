package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.DownloadCertificateLayoutBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DownloadCertificate extends LocBaseFragment {
    DownloadCertificateLayoutBinding binding;
    CustomProgressDialog progressDialog;
    String mid = "", mdob = "";
    private int selectedThemeColor;

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        // Inflate the layout for this fragment

        //View root = inflater.inflate(R.layout.membership_selection_fragment, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.download_certificate_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.download_certificate));

        if (getArguments() != null) {
            mid = getArguments().getString(getResources().getString(R.string.memId));
            mdob = getArguments().getString(getResources().getString(R.string.mdob));

            if (!(mid.isEmpty() || mdob.isEmpty())) {
                binding.DOBRes.setText(mdob);
                binding.memberIDRes.setText(mid);

                if (mid.contains("PAT") ||
                        mid.contains("LMR") ||
                        mid.contains("VPT")) {
                    binding.downloadIdCard.setVisibility(View.VISIBLE);
                    binding.downloadCertificate.setVisibility(View.VISIBLE);
                    binding.downloadReceipt.setVisibility(View.GONE);

                } else if (mid.contains("AAE") ||
                        mid.contains("AMR")) {
                    binding.downloadReceipt.setVisibility(View.VISIBLE);
                    binding.downloadCertificate.setVisibility(View.GONE);
                    binding.downloadIdCard.setVisibility(View.GONE);
                } else if (mid.length() < 1) {
                    binding.downloadReceipt.setVisibility(View.GONE);
                    binding.downloadCertificate.setVisibility(View.GONE);
                    binding.downloadIdCard.setVisibility(View.GONE);
                }
            } else if (mid.length() < 1) {
                binding.downloadReceipt.setVisibility(View.GONE);
                binding.downloadCertificate.setVisibility(View.GONE);
                binding.downloadIdCard.setVisibility(View.GONE);
            }
        }

        progressDialog = new CustomProgressDialog(getActivity());

        binding.memberIDRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 3) {
                    if (s.toString().equalsIgnoreCase("PAT") ||
                            s.toString().equalsIgnoreCase("LMR") ||
                            s.toString().equalsIgnoreCase("VPT")) {
                        binding.downloadIdCard.setVisibility(View.VISIBLE);
                        binding.downloadCertificate.setVisibility(View.VISIBLE);
                        binding.downloadReceipt.setVisibility(View.GONE);

                    } else if (s.toString().equalsIgnoreCase("AAE") ||
                            s.toString().equalsIgnoreCase("AMR")) {
                        binding.downloadReceipt.setVisibility(View.VISIBLE);
                        binding.downloadCertificate.setVisibility(View.GONE);
                        binding.downloadIdCard.setVisibility(View.GONE);
                    } else if (s.length() < 1) {
                        binding.downloadReceipt.setVisibility(View.GONE);
                        binding.downloadCertificate.setVisibility(View.GONE);
                        binding.downloadIdCard.setVisibility(View.GONE);
                    }
                } else if (s.length() < 1) {
                    binding.downloadReceipt.setVisibility(View.GONE);
                    binding.downloadCertificate.setVisibility(View.GONE);
                    binding.downloadIdCard.setVisibility(View.GONE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // binding.downloadCertificate.setVisibility(View.GONE);
        // binding.downloadIdCard.setVisibility(View.VISIBLE);
        //Datepicker and age calculation
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
                binding.DOBRes.setText(format);
            }
        };
        binding.DOBRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });


        binding.btnDownloadCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnDownloadIDCard.setBackgroundResource(R.color.white);
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.black));
                binding.btnDownloadCertificate.setBackgroundResource(R.color.redcroosbg_6);
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.downloadCertificate.setVisibility(View.VISIBLE);
                // binding.downloadIdCard.setVisibility(View.GONE);
                setThemes_1();


            }
        });

        binding.btnDownloadIDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.downloadCertificate.setVisibility(View.GONE);
                // binding.downloadIdCard.setVisibility(View.VISIBLE);
                binding.btnDownloadIDCard.setBackgroundResource(R.color.redcroosbg_6);
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackgroundResource(R.color.white);
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.black));
                setThemes();

            }
        });
        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1)
                    , -1);
            setThemes();
        } catch (Exception e) {
            e.printStackTrace();

        }

        binding.downloadIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                    binding.downloadIdCard.setEnabled(false);
                } else {
                    binding.downloadIdCard.setEnabled(true);
                    if (validateFields()) {
                        if (CheckInternet.isOnline(getActivity())) {
                            downloadIDCardNew();

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
        binding.downloadCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                    binding.downloadCertificate.setEnabled(false);
                } else {
                    binding.downloadCertificate.setEnabled(true);
                    if (validateFields()) {
                        if (CheckInternet.isOnline(getActivity())) {
                            downloadCertificate();

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
        return binding.getRoot();
    }
    //THEMES

    private void setThemes() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.MainLayout.setBackgroundResource(R.drawable.redcross1_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.MainLayout.setBackgroundResource(R.drawable.redcross2_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_2));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.MainLayout.setBackgroundResource(R.drawable.redcross3_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_3));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross4_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross5_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_5));

            } else if (selectedThemeColor == R.color.redcroosbg_6) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross7_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_7));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross8_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_8));
            } else {
                binding.MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_6));

            }

        } else {
            binding.MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
            binding.btnDownloadIDCard.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.white));
            binding.btnDownloadCertificate.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.redcroosbg_6));
        }
    }

    private void setThemes_1() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross1_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_1));

            } else if (selectedThemeColor == R.color.redcroosbg_2) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross2_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_2));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross3_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_3));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross4_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross5_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_5));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross7_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_7));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross8_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_8));
            } else {

                binding.MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
                binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_6));
            }

        } else {
            binding.MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
            binding.btnDownloadCertificate.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            binding.btnDownloadCertificate.setTextColor(getResources().getColor(R.color.white));
            binding.btnDownloadIDCard.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.btnDownloadIDCard.setTextColor(getResources().getColor(R.color.redcroosbg_6));
        }
    }

    protected boolean validateFields() {

        if (binding.memberIDRes.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_member_id), Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.DOBRes.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_date_of_birth), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

//    private void downloadIDCard() {
//        progressDialog.show();
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//
//        Call<ResponseBody> call = apiInterface.getIdcardDownload(binding.memberIDRes.getText().toString(), binding.DOBRes.getText().toString());
//        Log.e("url--------", call.request().url().toString());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                progressDialog.dismiss();
//                if (response.body() != null) {
//                    if (response.code() == 200) {
//                        if (response.body() != null) {
//                            if (response.isSuccessful()) {
//                                writeIDCardToDisk(response.body());
//                            } else {
//                                Toast.makeText(getActivity(), "Failed to Download ID Card, please contact support team", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "Failed to Download ID Card, please contact support team", Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (response.code() == 412) {
//                        Toast.makeText(getActivity(), "Invalid Member Id or Date of Birth", Toast.LENGTH_SHORT).show();
//                    } else if (response.code() == 403) {
//                        Toast.makeText(getActivity(), "File Not Found , failed to download", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "Failed to Download ID Card, please contact support team", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "Failed to Download ID Card, please contact support team", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void downloadCertificate() {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.getcertificateDownload(binding.memberIDRes.getText().toString(),
                binding.DOBRes.getText().toString(), getResources().getString(R.string.Mobile));
        Log.e("url--------", call.request().url().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {

                        if (response.isSuccessful()) {

                            writeCertificateToDisk(response.body());
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 412) {
                    String mesage = "";
                    try {
                        String responseCard = response.errorBody().string();
                        Log.e("TAG", "response 412: " + responseCard);
                        JSONObject jsonObj = new JSONObject(responseCard);
                        mesage = jsonObj.getString("msg");
                        Log.e("TAG1", "response 412: " + mesage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), mesage, Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    String mesage = "";
                    try {
                        String responseCard = response.errorBody().string();
                        Log.e("TAG", "response 403: " + responseCard);
                        JSONObject jsonObj = new JSONObject(responseCard);
                        mesage = jsonObj.getString("msg");
                        Log.e("TAG1", "response 403: " + mesage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), mesage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void downloadIDCardNew() {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.getIdcardDownload(binding.memberIDRes.getText().toString(),
                binding.DOBRes.getText().toString(), getResources().getString(R.string.Mobile));
        Log.e("url--------", call.request().url().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {

                        if (response.isSuccessful()) {

                            writeIDCardToDisk(response.body());
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 412) {
                    String mesage = "";
                    try {
                        String responseCard = response.errorBody().string();
                        Log.e("TAG", "response 412: " + responseCard);
                        JSONObject jsonObj = new JSONObject(responseCard);
                        mesage = jsonObj.getString("msg");
                        Log.e("TAG1", "response 412: " + mesage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), mesage, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    String mesage = "";
                    try {
                        String responseCard = response.errorBody().string();
                        Log.e("TAG", "response 403: " + responseCard);
                        JSONObject jsonObj = new JSONObject(responseCard);
                        mesage = jsonObj.getString("msg");
                        Log.e("TAG1", "response 403: " + mesage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), mesage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_todownload_certi), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean writeIDCardToDisk(ResponseBody body) {
        try {
            File pdfIDCard = new File(Environment.getExternalStorageDirectory() + "/Download/" + "RedCross/");
            if (!pdfIDCard.exists()) {
                pdfIDCard.mkdirs();
            }
            pdfIDCard = new File(pdfIDCard.getPath() + File.separator + binding.memberIDRes.getText().toString() + ".pdf");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(pdfIDCard);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                // Toast.makeText(getActivity(), "ID Card Successfully Downloded", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle(getResources().getString(R.string.success));
                builder1.setMessage(getResources().getString(R.string.idcard_download_at) + pdfIDCard.getAbsolutePath());
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private boolean writeCertificateToDisk(ResponseBody body) {
        try {
            File pdfIDCard = new File(Environment.getExternalStorageDirectory() + "/Download/" + "RedCross/");
            if (!pdfIDCard.exists()) {
                pdfIDCard.mkdirs();
            }
            pdfIDCard = new File(pdfIDCard.getPath() + File.separator + binding.memberIDRes.getText().toString() + "_Certificate.pdf");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(pdfIDCard);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle(getResources().getString(R.string.success));
                builder1.setMessage(getResources().getString(R.string.certificate_download_at) + pdfIDCard.getAbsolutePath());
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
//                Toast.makeText(getActivity(), "Certificate Successfully Downloded"+pdfIDCard.getAbsolutePath(),
//                        Toast.LENGTH_SHORT).show();
                // Log.e("certificate path",pdfIDCard.getAbsolutePath());

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
