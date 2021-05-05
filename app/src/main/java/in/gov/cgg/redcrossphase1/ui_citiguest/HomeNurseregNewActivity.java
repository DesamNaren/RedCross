package in.gov.cgg.redcrossphase1.ui_citiguest;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentHomenursing1Binding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDistAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipMandalAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipvillageAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.HomeNurseRegResponseBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.HomeNursingRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.PhotoBean;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class HomeNurseregNewActivity extends LocBaseActivity {
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 200;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;
    private final List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private final List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private final List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();
    public Uri fileUri;
    Uri mCropImageUri;
    String selectedFilePath;
    MembershipDistAdaptor adapter;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    FragmentHomenursing1Binding activityHomeNurseregNewBinding;
    CustomProgressDialog progressDialog;
    String mEducationId;
    String mMarriedId;
    List<String> mEducationIdsList = new ArrayList<>();
    List<String> mMarriedIdsList = new ArrayList<>();
    Integer distId = 0, manId = 0, villageID = 0;
    File imageFile;
    String FilePath;
    Bitmap bm;
    String Image_name;
    String PHOTOPATH = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    int selectedThemeColor = -1;
    String email = "";
    String pincode = "";
    Uri resultUri;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mYear, mMonth, mDay;
    private HomeNursingRequest request;
    private boolean distValidation, mandalValid, villageValid;
    private int nurseage;
    private int experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_home_nursereg_new);

        activityHomeNurseregNewBinding = DataBindingUtil.setContentView(this, R.layout.fragment_homenursing1);


        progressDialog = new CustomProgressDialog();
        activityHomeNurseregNewBinding.datePickerDateofBirth.setInputType(InputType.TYPE_NULL);

        if (CheckInternet.isOnline(this)) {
            loadEducationSpinner();
            loadMarriedstatusSpinner();
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
        }


        try {
            selectedThemeColor = this.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color), -1);

            setThemes();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Datepicker and age calculation
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                activityHomeNurseregNewBinding.datePickerDateofBirth.setText(format);
                nurseage = calculateAge(c.getTimeInMillis());
                Log.e("AGE before minus", "" + nurseage);
                nurseage = nurseage - 18;
                Log.e("AGE after minus", "" + nurseage);

                //  ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };
        activityHomeNurseregNewBinding.datePickerDateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                c.add(Calendar.YEAR, -18);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                c.add(Calendar.YEAR, -12);
                dateDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                dateDialog.show();
            }
        });


        //disrtrict spinner
        activityHomeNurseregNewBinding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (MembersipDistResponseList.size() > 0) {
                    distId = MembersipDistResponseList.get(i).getDistrictID();
                    //   District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {

                        if (CheckInternet.isOnline(HomeNurseregNewActivity.this)) {
                            callgetMandalsListRequest("" + distId);
                            activityHomeNurseregNewBinding.mandalSpinLayout.setVisibility(View.VISIBLE);
                            distValidation = true;
                        } else {
                            Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        activityHomeNurseregNewBinding.spnMandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        activityHomeNurseregNewBinding.spnVillage.setAdapter(villageadapter);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //mandal spinner
        activityHomeNurseregNewBinding.spnMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembershipMandalsResponseList.size() > 0) {
                    manId = MembershipMandalsResponseList.get(i).getMandalID();
                    //   Mandal_View.setText(MembershipMandalsResponseList.get(i).getMandalName());
                    if (distId != 0 && manId != 0) {

                        if (CheckInternet.isOnline(HomeNurseregNewActivity.this)) {

                            callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
                            activityHomeNurseregNewBinding.villageSpinLayout.setVisibility(View.VISIBLE);
                            mandalValid = true;
                            Log.e("MANDALID", "====" + MembershipMandalsResponseList.get(i).getMandalID());
                        } else {
                            Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        activityHomeNurseregNewBinding.spnVillage.setAdapter(villageadapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //village spinner
        activityHomeNurseregNewBinding.spnVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembersipVillagesResponseList.size() > 0) {
                    villageID = MembersipVillagesResponseList.get(i).getVillageID();
                    if (villageID != 0) {
                        villageValid = true;

                    }
                    //  Village_View.setText(MembersipVillagesResponseList.get(i).getVillageName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //    enablePermissions();


        activityHomeNurseregNewBinding.chooseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //    selectImage();

                    selectImageCameraandGalary();


                } catch (Exception e) {
                    Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

            }
        });

        callgetDistrictListRequest();
        activityHomeNurseregNewBinding.btnHomeNursingRegNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternet.isOnline(HomeNurseregNewActivity.this)) {

                    if (validateFields()) {

                        email = activityHomeNurseregNewBinding.etEmail.getText().toString();
                        pincode = activityHomeNurseregNewBinding.etPincode.getText().toString();
                        PhotoUpload();

                    }
                }

            }
        });
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                return shouldShowRequestPermissionRationale(permission);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), okListener)
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .create()
                .show();
    }

    //Permissions Code
    private void selectImageCameraandGalary() {
        Intent intent = CropImage.activity()
                .setAspectRatio(16, 16)
                .getIntent(this);

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //note: public, not protected.
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("APP_DEBUG", String.valueOf(requestCode));


        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                //   selectedFilePath = mCropImageUri.getPath();


                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                Intent intent = CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setFixAspectRatio(true)
                        .setAspectRatio(2, 1)
                        .getIntent(this);
                this.startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                Toast.makeText(this, getResources().getString(R.string.success), Toast.LENGTH_LONG).show();


            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //  Uri resultUri = result.getUri();
                resultUri = result.getUri();
                mCropImageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                selectedFilePath = resultUri.getPath();
                activityHomeNurseregNewBinding.chooseBt.setImageURI(resultUri);
//                Log.d("result_uri", String.valueOf(resultUri));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                Toast.makeText(this, getResources().getString(R.string.permissions_not_granted), Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = CropImage.activity(mCropImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setFixAspectRatio(true)
                        .setAspectRatio(2, 1)
                        .getIntent(this);


                this.startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else {
                Toast.makeText(this, getResources().getString(R.string.permissions_not_granted), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void selectImage() {
        final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_from_gallery), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getResources().getString(R.string.take_photo))) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        getResources().getString(R.string.camera_permission);

                    } catch (Exception e) {
                        Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }


                } else if (options[item].equals(getResources().getString(R.string.choose_from_gallery))) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } catch (Exception e) {
                        Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        Uri imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + getResources().getString(R.string.file_provider), //(use your app signature + ".provider" )
                imageFile);
        return imageUri;
    }

    private void PhotoUpload() {
        //saveHomeNursingDetails();

        if (resultUri == null) {
            Toast.makeText(this, getResources().getString(R.string.image_not_empty), Toast.LENGTH_SHORT).show();
        } else if (selectedFilePath != null) {

            File file = new File(selectedFilePath);
            Log.d("selectedFilePath", selectedFilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData(getResources().getString(R.string.photo_upload), file.getName(), requestFile);
            //TypedFile file = new TypedFile("multipart/form-data", new File(path));
            progressDialog.showProgress(this);

            if (CheckInternet.isOnline(this)) {

                calluploadPhotoMembAdd(PhotoBody);

            } else {
                Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void calluploadPhotoMembAdd(final MultipartBody.Part PhotoBody) {

        progressDialog.showProgress(this);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PhotoBean> call = apiInterface.SendPhoto(PhotoBody);
        Log.d("photouploadurl", call.request().url().toString());
        call.enqueue(new Callback<PhotoBean>() {
            @Override
            public void onResponse(Call<PhotoBean> call, Response<PhotoBean> response) {
                Log.d("photouploadrespnse", String.valueOf(response.body()));
                if (response.body() != null) {
                    Log.e("TESTsuc", "onResponse: " + response.body().getSavedFileName());
                    if (response.body().getSavedFileName().length() > 0) {
                        PHOTOPATH = response.body().getSavedFileName();
                        Log.d("photoupload", PHOTOPATH);

                        if (CheckInternet.isOnline(HomeNurseregNewActivity.this)) {

                            saveHomeNursingDetails();
                        } else {
                            Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }


                    }

                } else {
                    Toast.makeText(HomeNurseregNewActivity.this, "not uploaded......", Toast.LENGTH_SHORT).show();

                }
                //if()
                //updateLocationInterface.SubmitData(response);
            }

            @Override
            public void onFailure(Call<PhotoBean> call, Throwable t) {
                Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                //updateLocationInterface.SubmitData(null);
                Log.d("failurecase", String.valueOf(t));
                progressDialog.hideProgress();

                t.printStackTrace();
            }
        });


    }


    private void saveHomeNursingDetails() {
        progressDialog.showProgress(this);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        request = new HomeNursingRequest();
        request.setName("" + activityHomeNurseregNewBinding.etName.getText());
        request.setFatherName("" + activityHomeNurseregNewBinding.etFathersName.getText());
        request.setDateOfBirth("" + activityHomeNurseregNewBinding.datePickerDateofBirth.getText());
        request.setEducation(mEducationId);
        request.setMarried(mMarriedId);
        request.setPhoneNo("" + activityHomeNurseregNewBinding.etMobileNumber.getText());
        request.setEmail("" + email);
        request.setInstituteName("" + activityHomeNurseregNewBinding.etInstitute.getText());
        request.setAddress("" + activityHomeNurseregNewBinding.etAdress.getText());
        request.setDistricts("" + distId);
        request.setMandals("" + manId);
        request.setVillage("" + villageID);
        request.setPincode("" + pincode);
        request.setPrevWorkYears("" + activityHomeNurseregNewBinding.etNoofpreviousExperians.getText());
        request.setPhotoPath("" + PHOTOPATH);


        Call<HomeNurseRegResponseBean> call = apiInterface.saveHomeNursingDetails(request);

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.d("homenurse", "================: " + json);

        call.enqueue(new Callback<HomeNurseRegResponseBean>() {
            @Override
            public void onResponse(Call<HomeNurseRegResponseBean> call, Response<HomeNurseRegResponseBean> response) {
                progressDialog.hideProgress();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {

                        callsuccess();

                    } else if (response.body() != null && response.body().getStatus().contains("Failed")) {
                        //Toast.makeText(this, "Response" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        /*final PrettyDialog dialog = new PrettyDialog(this);
                        dialog
                                .setTitle("Response")
                                .setMessage("Registration Failed")
                                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                                .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        dialog.dismiss();
                                    }
                                });

                        dialog.show();*/

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeNurseregNewActivity.this);
                        builder1.setMessage(getResources().getString(R.string.reg_failed));
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                    }

                }
            }


            @Override
            public void onFailure(Call<HomeNurseRegResponseBean> call, Throwable t) {
                progressDialog.hideProgress();
                Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callsuccess() {

        /*final PrettyDialog dialog = new PrettyDialog(this);
        dialog
                .setTitle("Success")
                .setMessage("Registered successfully")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Intent i = new Intent(this, CitiGuestMainActivity.class);
                        startActivity(i);


                    }
                });

        dialog.show();*/
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(getResources().getString(R.string.reg_successfully));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent i = new Intent(HomeNurseregNewActivity.this, CitiGuestMainActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public Bitmap saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return selectedBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    int calculateAge(long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/"
                        + "Files/" + IMAGE_DIRECTORY_NAME;

                Image_name = 1 + ".jpg";
                FilePath = FilePath + "/" + Image_name;

                bm = saveBitmapToFile(imageFile);

                activityHomeNurseregNewBinding.chooseBt.setImageBitmap(bm);
                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor currsor = this.getContentResolver().query(selectedImage, filePath, null, null, null);
                currsor.moveToFirst();
                int columnIndex = currsor.getColumnIndex(filePath[0]);
                String picturePath = currsor.getString(columnIndex);
                currsor.close();
                bm = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                activityHomeNurseregNewBinding.chooseBt.setImageBitmap(bm);
                FilePath = picturePath;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this.getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }*/

    //DIRECTORY CREATION CODE
    private File getOutputMediaFile(int type) {

        //File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Android File Upload");

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }

        /*timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date()) + ".jpg";*/

        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "1.jpg");


        } else {
            return null;
        }

        return mediaFile;
    }

    private void loadMarriedstatusSpinner() {

        mMarriedIdsList = Arrays.asList(getResources().getStringArray(R.array.Married));
        activityHomeNurseregNewBinding.spnMarried.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMarriedId = mMarriedIdsList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadEducationSpinner() {

        mEducationIdsList = Arrays.asList(getResources().getStringArray(R.array.Education));
        activityHomeNurseregNewBinding.spnEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (activityHomeNurseregNewBinding.spnEducation.getSelectedItem().toString().contains("Select Education")) {
                    mEducationId = "";
                } else {
                    mEducationId = mEducationIdsList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    //SERVICE CALL OF DISTRICTS
    private void callgetDistrictListRequest() {
        try {
            progressDialog.showProgress(this);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            MembersipDistResponseList.clear();
            MembersipDistResponse membersipDistResponse = new MembersipDistResponse();
            membersipDistResponse.setDistrictID(0);
            membersipDistResponse.setDistrictName("Select District");
            MembersipDistResponseList.add(membersipDistResponse);

            Call<List<MembersipDistResponse>> call = apiInterface.getDistrictsForMemReg("1");
            Log.e("url", call.request().url().toString());

            call.enqueue(new Callback<List<MembersipDistResponse>>() {
                @Override
                public void onResponse(Call<List<MembersipDistResponse>> call, Response<List<MembersipDistResponse>> response) {

                    progressDialog.hideProgress();
                    if (response.body() != null) {
                        MembersipDistResponseList.addAll(response.body());
                        Log.d("Activity ", "Response = " + MembersipDistResponseList.size());
                        adapter = new MembershipDistAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        activityHomeNurseregNewBinding.spnDistrict.setAdapter(adapter);

                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        activityHomeNurseregNewBinding.spnMandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        activityHomeNurseregNewBinding.spnVillage.setAdapter(villageadapter);

                    }

                }

                @Override
                public void onFailure(Call<List<MembersipDistResponse>> call, Throwable t) {
                    Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                    progressDialog.hideProgress();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callgetVillagesListRequest(String MandID) {

        progressDialog.showProgress(this);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MembershipVillagesResponse>> call = apiInterface.getVillagesForMemReg(MandID);
        Log.e("  url", call.request().url().toString());

        MembersipVillagesResponseList.clear();
        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
        membershipVillagesResponse.setVillageID(0);
        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
        MembersipVillagesResponseList.add(membershipVillagesResponse);

        call.enqueue(new Callback<List<MembershipVillagesResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipVillagesResponse>> call, Response<List<MembershipVillagesResponse>> response) {

                progressDialog.hideProgress();
                if (response.body() != null) {
                    Log.d("Activity ", "Response = " + response.body().toString());
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                    MembersipVillagesResponseList.addAll(response.body());
                    villageadapter = new MembershipvillageAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    activityHomeNurseregNewBinding.spnVillage.setAdapter(villageadapter);
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<MembershipVillagesResponse>> call, Throwable t) {
                Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                progressDialog.hideProgress();
                t.printStackTrace();
            }
        });


    }

//SERVICE CALL OF MANDALS

    private void callgetMandalsListRequest(String DistID) {

        progressDialog.showProgress(this);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MembershipMandalsResponse>> call = apiInterface.getMandalsForMemReg(DistID);

        MembershipMandalsResponseList.clear();
        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
        membershipmandResponse.setMandalID(0);
        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
        MembershipMandalsResponseList.add(membershipmandResponse);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<MembershipMandalsResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipMandalsResponse>> call, Response<List<MembershipMandalsResponse>> response) {

                progressDialog.hideProgress();
                if (response.body() != null) {
                    MembershipMandalsResponseList.addAll(response.body());
                    Log.d("Activity ", "Response = " + MembershipMandalsResponseList.size());
                    mandaladapter = new MembershipMandalAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                    activityHomeNurseregNewBinding.spnMandal.setAdapter(mandaladapter);
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    MembersipVillagesResponseList.clear();
                    MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                    membershipVillagesResponse.setVillageID(0);
                    membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                    MembersipVillagesResponseList.add(membershipVillagesResponse);
                    villageadapter = new MembershipvillageAdaptor(HomeNurseregNewActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    activityHomeNurseregNewBinding.spnVillage.setAdapter(villageadapter);
                }
            }

            @Override
            public void onFailure(Call<List<MembershipMandalsResponse>> call, Throwable t) {
                Toast.makeText(HomeNurseregNewActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                progressDialog.hideProgress();
                t.printStackTrace();
            }
        });
    }

    protected boolean validateFields() {
        try {
            experience = Integer.parseInt(activityHomeNurseregNewBinding.etNoofpreviousExperians.getText().toString());
            Log.e("experience", "experience===" + experience);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activityHomeNurseregNewBinding.etName.getText().toString().trim().length() == 0) {
            activityHomeNurseregNewBinding.etName.setError(getResources().getString(R.string.enter_your_name));
            activityHomeNurseregNewBinding.etName.requestFocus();
            return false;
        } else if (activityHomeNurseregNewBinding.etFathersName.getText().toString().trim().length() == 0) {
            activityHomeNurseregNewBinding.etFathersName.setError(getResources().getString(R.string.enter_father_s_husband_s_name));
            activityHomeNurseregNewBinding.etFathersName.requestFocus();
            return false;

        } else if (activityHomeNurseregNewBinding.datePickerDateofBirth.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.enter_date_of_birth), Toast.LENGTH_LONG).show();
            return false;
        } else if (activityHomeNurseregNewBinding.spnEducation.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.enter_edcation), Toast.LENGTH_LONG).show();


            return false;
        } else if (activityHomeNurseregNewBinding.spnMarried.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.enter_marital_status), Toast.LENGTH_LONG).show();

            return false;

        } else if (activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().length() == 0) {
            activityHomeNurseregNewBinding.etMobileNumber.setError("");
            activityHomeNurseregNewBinding.etMobileNumber.requestFocus();
            return false;
        } else if (activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().length() < 10) {
            activityHomeNurseregNewBinding.etMobileNumber.setError(getResources().getString(R.string.enter_validmno));
            activityHomeNurseregNewBinding.etMobileNumber.requestFocus();
            return false;
        } else if (!(activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().startsWith("9") || activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().startsWith("8") || activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().startsWith("7") || activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().startsWith("6") || activityHomeNurseregNewBinding.etMobileNumber.getText().toString().trim().startsWith("5"))) {
            activityHomeNurseregNewBinding.etMobileNumber.setError(getResources().getString(R.string.enter_validmno));
            activityHomeNurseregNewBinding.etMobileNumber.requestFocus();
            return false;
        } else if (activityHomeNurseregNewBinding.etInstitute.getText().toString().trim().length() == 0) {

            activityHomeNurseregNewBinding.etInstitute.setError(getResources().getString(R.string.enter_institte_name));
            activityHomeNurseregNewBinding.etInstitute.requestFocus();

            return false;
        } else if (activityHomeNurseregNewBinding.etAdress.getText().toString().trim().length() == 0) {

            activityHomeNurseregNewBinding.etAdress.setError(getResources().getString(R.string.enter_address));
            activityHomeNurseregNewBinding.etAdress.requestFocus();

            return false;
        } else if (activityHomeNurseregNewBinding.spnDistrict.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.Select_District), Toast.LENGTH_SHORT).show();
            return false;
        } else if (activityHomeNurseregNewBinding.spnMandal.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.Select_Mandal), Toast.LENGTH_SHORT).show();
            return false;
        } else if (activityHomeNurseregNewBinding.spnVillage.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.Select_Village), Toast.LENGTH_SHORT).show();
            return false;
        } else if (activityHomeNurseregNewBinding.etNoofpreviousExperians.getText().toString().trim().length() == 0) {

            activityHomeNurseregNewBinding.etNoofpreviousExperians.setError(getResources().getString(R.string.no_prev_experience));
            activityHomeNurseregNewBinding.etNoofpreviousExperians.requestFocus();

            return false;
        } else if (experience > nurseage) {
            activityHomeNurseregNewBinding.etNoofpreviousExperians.setError(getResources().getString(R.string.exp_not_greater_than_age));
            activityHomeNurseregNewBinding.etNoofpreviousExperians.requestFocus();
            return false;
        }

        return true;
    }

    protected void errorSpinner(Spinner mySpinner, String errorMsg) {
        TextView errorText = (TextView) mySpinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText(errorMsg);

    }

    protected void setFocus(final View view, String errorMsg) {
        view.requestFocus();
        ((TextView) view).setError(errorMsg);
    }


    private void setThemes() {
        if (selectedThemeColor != -1) {


            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(selectedThemeColor)));
            }

            if (selectedThemeColor == R.color.redcroosbg_1) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));

//
//                if (getSupportActionBar() != null) {
//                    //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross1_bg));
//
//
//                }
                //   activityHomeNurseregNewBinding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross2_bg));
//                }
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross3_bg));
//                }
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross4_bg));
//                }
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross5_bg));
//                }
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross6_bg));
//                }
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross7_bg));
//                }
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross8_bg));
//                }
            } else {
                activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(R.color.colorPrimary));
                activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(R.color.colorPrimary));
                activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                if (getSupportActionBar() != null) {
//                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross6_bg));
//                }

            }

        } else {
            activityHomeNurseregNewBinding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
            activityHomeNurseregNewBinding.tvHomenurseDeatils.setTextColor(getResources().getColor(R.color.colorPrimary));
            activityHomeNurseregNewBinding.tvHomenurseAdress.setTextColor(getResources().getColor(R.color.colorPrimary));
            activityHomeNurseregNewBinding.tvTvGomenurseInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
            activityHomeNurseregNewBinding.view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            activityHomeNurseregNewBinding.view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            activityHomeNurseregNewBinding.view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.redcross6_bg));
//            }

        }
    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder1.setMessage(getResources().getString(R.string.exitmsg));
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}

