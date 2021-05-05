package in.gov.cgg.redcrossphase1.ui_citiguest;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

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
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class HomeNursingActivity extends AppCompatActivity {
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 200;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
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
    private int mYear, mMonth, mDay;
    private HomeNursingRequest request;
    private boolean distValidation, mandalValid, villageValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_homenursing1);
        //Objects.requireNonNull(getActivity()).setTitle("Home Nursing");
        progressDialog = new CustomProgressDialog(HomeNursingActivity.this);

        enablePermissions();

        loadEducationSpinner();
        loadMarriedstatusSpinner();


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
                    Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.min_age_validation), Toast.LENGTH_SHORT).show();
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
                        if (CheckInternet.isOnline(HomeNursingActivity.this)) {
                            callgetMandalsListRequest("" + distId);
                            distValidation = true;

                        } else {
                            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
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

                        if (CheckInternet.isOnline(HomeNursingActivity.this)) {

                            callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
                            mandalValid = true;
                            Log.e("MANDALID", "====" + MembershipMandalsResponseList.get(i).getMandalID());

                        } else {
                            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
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


        binding.chooseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectImage();


                } catch (Exception e) {
                    Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        });

        if (CheckInternet.isOnline(HomeNursingActivity.this)) {

            callgetDistrictListRequest();

        } else {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


        binding.btnHomeNursingRegNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternet.isOnline(HomeNursingActivity.this)) {
                    if (validateFields()) {
                        PhotoUpload(bm);

                    }
                }

            }
        });
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                return shouldShowRequestPermissionRationale(permission);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeNursingActivity.this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), okListener)
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .create()
                .show();
    }

    //Permissions Code
    private boolean enablePermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add(getResources().getString(R.string.access_read_phonestate));
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add(getResources().getString(R.string.write_external_storage));
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add(getResources().getString(R.string.access_camera));

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = getResources().getString(R.string.grant_access) + permissionsNeeded.get(0);
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
        final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_from_gallery), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeNursingActivity.this);
        builder.setTitle(getResources().getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getResources().getString(R.string.take_photo))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                } else if (options[item].equals(getResources().getString(R.string.choose_from_gallery))) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        Uri imageUri = FileProvider.getUriForFile(HomeNursingActivity.this, BuildConfig.APPLICATION_ID + getResources().getString(R.string.file_provider), //(use your app signature + ".provider" )
                imageFile);
        return imageUri;
    }

    private void PhotoUpload(Bitmap bmp) {
        //saveHomeNursingDetails();

        if (bmp == null) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.image_not_empty), Toast.LENGTH_SHORT).show();
        } else {

            //FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;


            File file = new File(FilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData(getResources().getString(R.string.photo_upload), file.getName(), requestFile);
            //TypedFile file = new TypedFile("multipart/form-data", new File(path));
            progressDialog.show();


            if (CheckInternet.isOnline(HomeNursingActivity.this)) {

                calluploadPhotoMembAdd(PhotoBody);

            } else {
                Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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

                        if (CheckInternet.isOnline(HomeNursingActivity.this)) {

                            saveHomeNursingDetails();

                        } else {
                            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

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
        request.setEmail("" + binding.etEmail.getText());
        request.setInstituteName("" + binding.etInstitute.getText());
        request.setAddress("" + binding.etAdress.getText());
        request.setDistricts("" + distId);
        request.setMandals("" + manId);
        request.setVillage("" + villageID);
        request.setPincode("" + binding.etPincode.getText());
        request.setPrevWorkYears("" + binding.etNoofpreviousExperians.getText());
        //request.setPhotoPath("f1Hn2YbtKEAaGjjN_9Dec2019175839GMT_1575914319596.PNG");
        request.setPhotoPath("" + PHOTOPATH);
        Call<HomeNurseRegResponseBean> call = apiInterface.saveHomeNursingDetails(request);

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.d("Donor", "================: " + json);

        call.enqueue(new Callback<HomeNurseRegResponseBean>() {
            @Override
            public void onResponse(Call<HomeNurseRegResponseBean> call, Response<HomeNurseRegResponseBean> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        Intent i = new Intent(HomeNursingActivity.this, CitiGuestMainActivity.class);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeNurseRegResponseBean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(HomeNursingActivity.this,
                        getResources().getString(R.string.canceled_img), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(HomeNursingActivity.this,
                        getResources().getString(R.string.failed_to_capture), Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor currsor = getContentResolver().query(selectedImage, filePath, null, null, null);
                currsor.moveToFirst();
                int columnIndex = currsor.getColumnIndex(filePath[0]);
                String picturePath = currsor.getString(columnIndex);
                currsor.close();
                bm = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                binding.chooseBt.setImageBitmap(bm);
                FilePath = picturePath;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(HomeNursingActivity.this.getApplicationContext(),
                        getResources().getString(R.string.user_cancelled_imgcapture), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(HomeNursingActivity.this,
                        getResources().getString(R.string.failed_to_capture), Toast.LENGTH_SHORT)
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
            membersipDistResponse.setDistrictName(getResources().getString(R.string.Select_District));
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
                        adapter = new MembershipDistAdaptor(HomeNursingActivity.this, R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        binding.spnDistrict.setAdapter(adapter);

                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
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
        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
        MembersipVillagesResponseList.add(membershipVillagesResponse);

        call.enqueue(new Callback<List<MembershipVillagesResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipVillagesResponse>> call, Response<List<MembershipVillagesResponse>> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("Activity ", "Response = " + response.body().toString());
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                    MembersipVillagesResponseList.addAll(response.body());
                    villageadapter = new MembershipvillageAdaptor(HomeNursingActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
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
        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
        MembershipMandalsResponseList.add(membershipmandResponse);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<MembershipMandalsResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipMandalsResponse>> call, Response<List<MembershipMandalsResponse>> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    MembershipMandalsResponseList.addAll(response.body());
                    Log.d("Activity ", "Response = " + MembershipMandalsResponseList.size());
                    mandaladapter = new MembershipMandalAdaptor(HomeNursingActivity.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
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
            setFocus(binding.etName, getResources().getString(R.string.enter_your_name));
            return false;
        } else if (binding.etFathersName.getText().toString().trim().length() == 0) {
            setFocus(binding.etFathersName, getResources().getString(R.string.enter_father_s_husband_s_name));
            return false;

        } else if (binding.datePickerDateofBirth.getText().toString().trim().length() == 0) {
            setFocus(binding.datePickerDateofBirth, getResources().getString(R.string.enter_date_of_birth));
            return false;
        }
       /* else if (spn_gender.getSelectedItemPosition() == 0) {
            errorSpinner(spn_gender, "select gender");
            return false;
        }*/
        else if (binding.spnEducation.getSelectedItemPosition() == 0) {
            errorSpinner(binding.spnEducation, getResources().getString(R.string.enter_edcation));
            return false;
        } else if (binding.spnMarried.getSelectedItemPosition() == 0) {
            errorSpinner(binding.spnMarried, getResources().getString(R.string.enter_marital_status));
            return false;

        } else if (binding.etMobileNumber.getText().toString().trim().length() == 0) {
            //  Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_LONG).show();
            setFocus(binding.etMobileNumber, getResources().getString(R.string.enter_mobile_number));
            return false;
        } else if (!(binding.etMobileNumber.getText().toString().trim().startsWith("9") || binding.etEmail.getText().toString().trim().startsWith("8") || binding.etEmail.getText().toString().trim().startsWith("7") || binding.etEmail.getText().toString().trim().startsWith("6") || binding.etEmail.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.enter_validmno), Toast.LENGTH_LONG).show();
            return false;

        } else if (binding.etEmail.getText().toString().trim().length() == 0) {
            setFocus(binding.etEmail, getResources().getString(R.string.enter_email_id));
            return false;

        } else if (!binding.etEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.etInstitute.getText().toString().trim().length() == 0) {
            setFocus(binding.etInstitute, getResources().getString(R.string.enter_institte_name));
            return false;
        } else if (binding.etAdress.getText().toString().trim().length() == 0) {
            setFocus(binding.etAdress, getResources().getString(R.string.enter_address));
            return false;
        }
//        else if (spn_district.getSelectedItem().toString().contains("select")) {
//            errorSpinner(spn_district, "select district");
//            return false;
//        }

        else if (!distValidation) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.Select_District), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mandalValid) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.Select_Mandal), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!villageValid) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.Select_Village), Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPincode.getText().toString().trim().length() == 0) {
            setFocus(binding.etPincode, getResources().getString(R.string.enter_pincode));
            return false;
        } else if (binding.etPincode.getText().toString().length() < 6) {
            Toast.makeText(HomeNursingActivity.this, getResources().getString(R.string.enter_valid_pincode), Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.etNoofpreviousExperians.getText().toString().trim().length() == 0) {
            setFocus(binding.etNoofpreviousExperians, getResources().getString(R.string.enter_donation_type));
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
}
