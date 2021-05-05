package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.BuildConfig;
import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.databinding.FragmentHomenursing1Binding;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiClient;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.MembershipDistAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.MembershipMandalAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.MembershipvillageAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.HomeNursingRequest;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.PhotoBean;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase_offline.utils.CheckInternet;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


public class HomeNursingRegistrationFragment extends Fragment {
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
    MembershipDistAdaptor adapter;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    FragmentHomenursing1Binding binding;
    CustomProgressDialog progressDialog;
    String mEducationId;
    String mMarriedId;
    List<String> mEducationIdsList;
    List<String> mMarriedIdsList;
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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mYear, mMonth, mDay;
    private HomeNursingRequest request;
    private boolean distValidation, mandalValid, villageValid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homenursing1, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Home Nursing Registration");
        progressDialog = new CustomProgressDialog(getActivity());

        binding.datePickerDateofBirth.setInputType(InputType.TYPE_NULL);


        loadEducationSpinner();
        loadMarriedstatusSpinner();


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
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


                Calendar userAge = new GregorianCalendar(year, month, day);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(userAge)) {
                    Toast.makeText(getActivity(), "minimum age should be 18 years", Toast.LENGTH_SHORT).show();
                    binding.datePickerDateofBirth.setText("");
                } else {
                    binding.datePickerDateofBirth.setText(format);
                    //  SHOW_ERROR_MESSAGE;
                }

                //  ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };
        binding.datePickerDateofBirth.setOnClickListener(new View.OnClickListener() {
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


        //disrtrict spinner
        binding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (MembersipDistResponseList.size() > 0) {
                    distId = MembersipDistResponseList.get(i).getDistrictID();
                    //   District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {
                        callgetMandalsListRequest("" + distId);
                        binding.mandalSpinLayout.setVisibility(View.VISIBLE);
                        distValidation = true;
                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName("Select Mandal");
                        MembershipMandalsResponseList.add(membershipmandResponse);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //mandal spinner
        binding.spnMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembershipMandalsResponseList.size() > 0) {
                    manId = MembershipMandalsResponseList.get(i).getMandalID();
                    //   Mandal_View.setText(MembershipMandalsResponseList.get(i).getMandalName());
                    if (distId != 0 && manId != 0) {
                        callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
                        binding.villageSpinLayout.setVisibility(View.VISIBLE);
                        mandalValid = true;
                        Log.e("MANDALID", "====" + MembershipMandalsResponseList.get(i).getMandalID());
                    } else {
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName("Select Village");
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //village spinner
        binding.spnVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        enablePermissions();


        binding.chooseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        callgetDistrictListRequest();
        binding.btnHomeNursingRegNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternet.isOnline(getActivity())) {

                    if (validateFields()) {

                        email = binding.etEmail.getText().toString();
                        pincode = binding.etPincode.getText().toString();
                        PhotoUpload(bm);

                    }
                }

            }
        });
        return binding.getRoot();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                return shouldShowRequestPermissionRationale(permission);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //Permissions Code
    private boolean enablePermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Access Read Phone State");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Access Write External Storage");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Access Camera");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                }
                            }
                        });
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return false;
        } else {
            return true;
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        Uri imageUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", //(use your app signature + ".provider" )
                imageFile);
        return imageUri;
    }

    private void PhotoUpload(Bitmap bmp) {
        //saveHomeNursingDetails();

        if (bmp == null) {
            Toast.makeText(getActivity(), "Image can't be empty", Toast.LENGTH_SHORT).show();
        } else {

            //FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;


            File file = new File(FilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData("photoUpload", file.getName(), requestFile);
            //TypedFile file = new TypedFile("multipart/form-data", new File(path));
            progressDialog.show();


            if (CheckInternet.isOnline(getActivity())) {

                calluploadPhotoMembAdd(PhotoBody);

            } else {
                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void calluploadPhotoMembAdd(final MultipartBody.Part PhotoBody) {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PhotoBean> call = apiInterface.SendPhoto(PhotoBody);
        Log.i("LOGIN_URL", call.request().url().toString());
        call.enqueue(new Callback<PhotoBean>() {
            @Override
            public void onResponse(Call<PhotoBean> call, Response<PhotoBean> response) {
                if (response.body() != null) {
                    Log.e("TESTsuc", "onResponse: " + response.body().getSavedFileName());
                    if (response.body().getSavedFileName().length() > 0) {
                        PHOTOPATH = response.body().getSavedFileName();


                        saveHomeNursingDetails();
                    }

                }
                //if()
                //updateLocationInterface.SubmitData(response);
            }

            @Override
            public void onFailure(Call<PhotoBean> call, Throwable t) {
                Log.e("TESTfail", "onResponse: " + t.getMessage());
                //updateLocationInterface.SubmitData(null);
                t.printStackTrace();
            }
        });


    }


    private void saveHomeNursingDetails() {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        request = new HomeNursingRequest();
        request.setName("" + binding.etName.getText());
        request.setFatherName("" + binding.etFathersName.getText());
        request.setDateOfBirth("" + binding.datePickerDateofBirth.getText());
        request.setEducation(mEducationId);
        request.setMarried(mMarriedId);
        request.setPhoneNo("" + binding.etMobileNumber.getText());
        request.setEmail("" + email);
        request.setInstituteName("" + binding.etInstitute.getText());
        request.setAddress("" + binding.etAdress.getText());
        request.setDistricts("" + distId);
        request.setMandals("" + manId);
        request.setVillage("" + villageID);
        request.setPincode("" + pincode);
        request.setPrevWorkYears("" + binding.etNoofpreviousExperians.getText());
        request.setPhotoPath("" + PHOTOPATH);
        Call<ResponseBody> call = apiInterface.saveHomeNursingDetails(request);

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.d("homenurse", "================: " + json);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.isSuccessful()) {

                        callsuccess();

                    } else {
                        Toast.makeText(getActivity(), "Unable to register", Toast.LENGTH_SHORT).show();

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

    private void callsuccess() {

        final PrettyDialog dialog = new PrettyDialog(getActivity());
        dialog
                .setTitle("Success")
                .setMessage("Registered successfully")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), CitiGuestMainActivity.class);
                        startActivity(i);


                    }
                });

        dialog.show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/"
                        + "Files/" + IMAGE_DIRECTORY_NAME;

                Image_name = 1 + ".jpg";
                FilePath = FilePath + "/" + Image_name;

                bm = saveBitmapToFile(imageFile);

                binding.chooseBt.setImageBitmap(bm);
                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor currsor = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                currsor.moveToFirst();
                int columnIndex = currsor.getColumnIndex(filePath[0]);
                String picturePath = currsor.getString(columnIndex);
                currsor.close();
                bm = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                binding.chooseBt.setImageBitmap(bm);
                FilePath = picturePath;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

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
        binding.spnMarried.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        binding.spnEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mEducationId = mEducationIdsList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    //SERVICE CALL OF DISTRICTS
    private void callgetDistrictListRequest() {
        try {
            progressDialog.show();
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

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        MembersipDistResponseList.addAll(response.body());
                        Log.d("Activity ", "Response = " + MembersipDistResponseList.size());
                        adapter = new MembershipDistAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        binding.spnDistrict.setAdapter(adapter);

                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName("Select Mandal");
                        MembershipMandalsResponseList.add(membershipmandResponse);
                    }

                }

                @Override
                public void onFailure(Call<List<MembersipDistResponse>> call, Throwable t) {

                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callgetVillagesListRequest(String MandID) {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MembershipVillagesResponse>> call = apiInterface.getVillagesForMemReg(MandID);
        Log.e("  url", call.request().url().toString());

        MembersipVillagesResponseList.clear();
        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
        membershipVillagesResponse.setVillageID(0);
        membershipVillagesResponse.setVillageName("Select Village");
        MembersipVillagesResponseList.add(membershipVillagesResponse);

        call.enqueue(new Callback<List<MembershipVillagesResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipVillagesResponse>> call, Response<List<MembershipVillagesResponse>> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("Activity ", "Response = " + response.body().toString());
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                    MembersipVillagesResponseList.addAll(response.body());
                    villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    binding.spnVillage.setAdapter(villageadapter);
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<MembershipVillagesResponse>> call, Throwable t) {

                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }

//SERVICE CALL OF MANDALS

    private void callgetMandalsListRequest(String DistID) {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MembershipMandalsResponse>> call = apiInterface.getMandalsForMemReg(DistID);

        MembershipMandalsResponseList.clear();
        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
        membershipmandResponse.setMandalID(0);
        membershipmandResponse.setMandalName("Select Mandal");
        MembershipMandalsResponseList.add(membershipmandResponse);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<MembershipMandalsResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipMandalsResponse>> call, Response<List<MembershipMandalsResponse>> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    MembershipMandalsResponseList.addAll(response.body());
                    Log.d("Activity ", "Response = " + MembershipMandalsResponseList.size());
                    mandaladapter = new MembershipMandalAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                    binding.spnMandal.setAdapter(mandaladapter);
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MembershipMandalsResponse>> call, Throwable t) {

                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    protected boolean validateFields() {

        if (binding.etName.getText().toString().trim().length() == 0) {
            binding.etName.setError("enter your  name");
            binding.etName.requestFocus();
            return false;
        } else if (binding.etFathersName.getText().toString().trim().length() == 0) {
            binding.etFathersName.setError("enter  father or husband name");
            binding.etFathersName.requestFocus();
            return false;

        } else if (binding.datePickerDateofBirth.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Enter your Date of birth", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.spnEducation.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "select education", Toast.LENGTH_LONG).show();


            return false;
        } else if (binding.spnMarried.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "select marital status", Toast.LENGTH_LONG).show();

            return false;

        } else if (binding.etMobileNumber.getText().toString().trim().length() == 0) {
            binding.etMobileNumber.setError("Enter Mobile number");
            binding.etMobileNumber.requestFocus();
            return false;
        } else if (binding.etMobileNumber.getText().toString().trim().length() < 10) {
            binding.etMobileNumber.setError("Enter valid Mobile number");
            binding.etMobileNumber.requestFocus();
            return false;
        } else if (!(binding.etMobileNumber.getText().toString().trim().startsWith("9") || binding.etMobileNumber.getText().toString().trim().startsWith("8") || binding.etMobileNumber.getText().toString().trim().startsWith("7") || binding.etMobileNumber.getText().toString().trim().startsWith("6") || binding.etMobileNumber.getText().toString().trim().startsWith("5"))) {
            binding.etMobileNumber.setError("Enter valid Mobile number");
            binding.etMobileNumber.requestFocus();
            return false;
        } else if (binding.etInstitute.getText().toString().trim().length() == 0) {

            binding.etInstitute.setError("enter institute name");
            binding.etInstitute.requestFocus();

            return false;
        } else if (binding.etAdress.getText().toString().trim().length() == 0) {

            binding.etAdress.setError("enter address");
            binding.etAdress.requestFocus();

            return false;
        } else if (!distValidation) {
            Toast.makeText(getActivity(), "select district ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mandalValid) {
            Toast.makeText(getActivity(), "select mandal ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!villageValid) {
            Toast.makeText(getActivity(), "select village", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etNoofpreviousExperians.getText().toString().trim().length() == 0) {

            binding.etNoofpreviousExperians.setError("enter no of previous experience");
            binding.etNoofpreviousExperians.requestFocus();

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
            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //   binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //   binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //  binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //   binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //  binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //  binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //  binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //   binding.chooseBt.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else {
                binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                binding.tvHomenurseDeatils.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHomenurseAdress.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //    binding.chooseBt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


            }

        } else {
            binding.llHomenurseRegistartion.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
            binding.tvHomenurseDeatils.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvHomenurseAdress.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvTvGomenurseInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            //   binding.chooseBt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        }
    }
}
