package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.DownloadCertificateLayoutBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
public class DownloadCertificate extends Fragment {
    DownloadCertificateLayoutBinding binding;
    CustomProgressDialog progressDialog;
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
        // Inflate the layout for this fragment

        //View root = inflater.inflate(R.layout.membership_selection_fragment, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.download_certificate_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Download Certificate");
        progressDialog = new CustomProgressDialog(getActivity());
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

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_1));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_1));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_2));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_2));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_3));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_3));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_4));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_4));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_5));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_5));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_6));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_6));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_7));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_7));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_8));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.redcroosbg_8));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.downloadCertificate.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    binding.downloadIdCard.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }

            }
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
                    downloadIDCard();
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
                    downloadCertificate();
                }
            }
        });
        return binding.getRoot();
    }

    private void downloadIDCard() {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.getIdcardDownload(binding.memberIDRes.getText().toString(), binding.DOBRes.getText().toString());
        Log.e("url--------", call.request().url().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.isSuccessful()) {

                        writeIDCardToDisk(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void downloadCertificate() {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.getcertificateDownload(binding.memberIDRes.getText().toString(), binding.DOBRes.getText().toString());
        Log.e("url--------", call.request().url().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.isSuccessful()) {

                        writeCertificateToDisk(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "ID Card Successfully Downloded", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "ID Card Successfully Downloded", Toast.LENGTH_SHORT).show();
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
