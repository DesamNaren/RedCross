package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentDownloadprintBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;

public class NewDownloadIDCardFragment extends Fragment {

    FragmentDownloadprintBinding binding;
    CustomProgressDialog progressDialog;
    int selectedThemeColor = -1;

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_downloadprint, container, false);

        progressDialog = new CustomProgressDialog(getActivity());
        binding.downloadCertificate.setText("Download ID Card");
//
//        if (GlobalDeclaration.globalMemDob != null) {
//            if (!GlobalDeclaration.globalMemDob.isEmpty()) {
//                binding.DOBRes.setText(GlobalDeclaration.globalMemDob);
//            }
//        }
//
//        if (GlobalDeclaration.globalMemId != null) {
//            if (!GlobalDeclaration.globalMemId.isEmpty()) {
//                binding.memberIDRes.setText(GlobalDeclaration.globalMemId);
//            }
//        }

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                binding.downloadCertificate.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.DOBTv.setTextColor(getResources().getColor(selectedThemeColor));
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.ll.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.ll.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.ll.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.ll.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.ll.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.ll.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.ll.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.ll.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    binding.ll.setBackgroundResource(R.drawable.redcross6_bg);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            binding.ll.setBackgroundResource(R.drawable.redcross6_bg);

        }

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat(getResources().getString(R.string.idMemDateformat)).format(c.getTime());
                binding.DOBRes.setText(format);
                GlobalDeclaration.globalMemDob = format;

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

        binding.downloadCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                    binding.downloadCertificate.setEnabled(false);
                } else {
                    binding.downloadCertificate.setEnabled(true);
                    if (validateFields()) {
                        if (CheckInternet.isOnline(getActivity())) {
                            //downloadIDCardNew();

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });


        return binding.getRoot();

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

//    private void downloadIDCardNew() {
//        progressDialog.show();
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        GlobalDeclaration.globalMemId = binding.memberIDRes.getText().toString();
//
//        Call<ResponseBody> call = apiInterface.getIdcardDownload(binding.memberIDRes.getText().toString(), binding.DOBRes.getText().toString());
//        Log.e("url--------", call.request().url().toString());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                progressDialog.dismiss();
//                if (response.code() == 200) {
//                    if (response.body() != null) {
//
//                        if (response.isSuccessful()) {
//
//                            writeIDCardToDisk(response.body());
//                        } else {
//                            Toast.makeText(getActivity(), "You cannot download ID Card Certificate due to unavailability of digital signature of District Chairman/Member Photo. Please contact Red Cross team.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getActivity(), "You cannot download ID Card Certificate due to unavailability of digital signature of District Chairman/Member Photo. Please contact Red Cross team.", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (response.code() == 412) {
//                    Toast.makeText(getActivity(), "Invalid Member Id or Date of Birth", Toast.LENGTH_SHORT).show();
//                } else if (response.code() == 403) {
//                    Toast.makeText(getActivity(), "You cannot download ID Card Certificate due to unavailability of digital signature of District Chairman/Member Photo. Please contact Red Cross team.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), "You cannot download ID Card Certificate due to unavailability of digital signature of District Chairman/Member Photo. Please contact Red Cross team.", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "You cannot download ID Card Certificate due to unavailability of digital signature of District Chairman/Member Photo. Please contact Red Cross team.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

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
                builder1.setTitle("Success");
                builder1.setMessage("ID Card downloaded at" + pdfIDCard.getAbsolutePath());
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

    @Override
    public void onResume() {
        super.onResume();
        if (GlobalDeclaration.globalMemDob != null) {
            if (!GlobalDeclaration.globalMemDob.isEmpty()) {
                binding.DOBRes.setText(GlobalDeclaration.globalMemDob);
            }
        }

        if (GlobalDeclaration.globalMemId != null) {
            if (!GlobalDeclaration.globalMemId.isEmpty()) {
                binding.memberIDRes.setText(GlobalDeclaration.globalMemId);
            }
        }
    }
}
