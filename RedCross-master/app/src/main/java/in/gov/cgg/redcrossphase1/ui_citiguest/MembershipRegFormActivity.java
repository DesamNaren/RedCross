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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipActivityAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDistAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipMandalAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipvillageAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MemberActivitiesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.PaymentBean;
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

public class MembershipRegFormActivity extends AppCompatActivity {

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 100;
    public Uri fileUri;
    Button Preview, Edit, Next;
    LinearLayout memberRegistration_edit, memberRegistration_view;
    EditText Full_name, fatherHusbName, DOB, mob_num, email, occupation, house_no, locatlity, street, pincode, hours;
    RadioGroup rg;
    TextView ageResult;
    ImageView Photo;
    String PHOTOPATH = "";
    Spinner Bloodgroup, education, district, mandal, village, activities;
    String Selected_gender = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    String EMailResult;
    String FilePath;
    TextView Full_name_View, fatherHusbName_View, Gender_View, DOB_View, Age_View;
    TextView mob_num_View, email_View, BloodGroup_View, Education_View, occupation_View;
    TextView house_no_View, locatlity_View, street_View, District_View, Mandal_View;
    TextView Village_View, pincode_View, Activities_View, hours_View;
    ImageView image_view_Res;
    File imageFile;
    CustomProgressDialog progressDialog;
    MembershipDistAdaptor adapter;
    MembershipActivityAdaptor activityAdaptor;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    Toolbar toolbar;
    int selectedThemeColor = -1;
    Integer distId = 0, manId = 0, villageID = 0, activityID = 0;
    private JsonObject gsonObject;
    private List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private List<MemberActivitiesResponse> MembersipActivityResponseList = new ArrayList<>();
    private List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();


    Bitmap bm;
    String Image_name;
    //CONVERT IMAGE TO BASE 64 CODE
    /*public static String encodeToBase64(Bitmap image,
                                        Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_reg_form);

        progressDialog = new CustomProgressDialog(this);
        Intent mIntent = getIntent();
        //String membershipType = mIntent.getStringExtra("membershipType", );

        memberRegistration_view = findViewById(R.id.memberRegistration_view);
        memberRegistration_edit = findViewById(R.id.memberRegistration_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //IDS of Editing Form

        Full_name = findViewById(R.id.FullName_res);
        fatherHusbName = findViewById(R.id.FathersHusbandName_res);
        DOB = findViewById(R.id.DOB_res);
        mob_num = findViewById(R.id.Mob_num_res);
        email = findViewById(R.id.Email_res);
        occupation = findViewById(R.id.Occupation_res);
        house_no = findViewById(R.id.House_no_res);
        locatlity = findViewById(R.id.Locality_res);
        street = findViewById(R.id.Street_area_res);
        pincode = findViewById(R.id.Pincode_res);
        hours = findViewById(R.id.No_of_hours_res);
        rg = findViewById(R.id.radio_group);
        ageResult = findViewById(R.id.Age_res);
        Photo = findViewById(R.id.image_res);
        Bloodgroup = findViewById(R.id.blood_group_spin_res);
        education = findViewById(R.id.Education_spin_res);
        district = findViewById(R.id.District_spin_res);
        mandal = findViewById(R.id.Mandal_spin_res);
        village = findViewById(R.id.Town_village_spin_res);
        activities = findViewById(R.id.Activities_interested_spin_res);

        Preview = findViewById(R.id.preview_bt);

        //IDS of View Form
        Full_name_View = findViewById(R.id.FullName_res_view);
        fatherHusbName_View = findViewById(R.id.FathersHusbandName_res_view);
        Gender_View = findViewById(R.id.Gender_res_view);
        DOB_View = findViewById(R.id.DOB_res_view);
        Age_View = findViewById(R.id.Age_res_view);
        mob_num_View = findViewById(R.id.Mob_num_res_view);
        email_View = findViewById(R.id.Email_res_view);
        BloodGroup_View = findViewById(R.id.Blood_group_res_view);
        Education_View = findViewById(R.id.Education_res_view);
        occupation_View = findViewById(R.id.Occupation_res_view);
        house_no_View = findViewById(R.id.House_no_res_view);
        locatlity_View = findViewById(R.id.Locality_res_view);
        street_View = findViewById(R.id.Street_area_res_view);
        District_View = findViewById(R.id.District_res_view);
        Mandal_View = findViewById(R.id.mandal_res_view);
        Village_View = findViewById(R.id.Town_village_res_view);
        pincode_View = findViewById(R.id.Pincode_res_view);
        Activities_View = findViewById(R.id.Activities_res_view);
        hours_View = findViewById(R.id.No_of_hours_res_view);
        image_view_Res = findViewById(R.id.image_view_Res);
        Edit = findViewById(R.id.Edit);
        Next = findViewById(R.id.Next);

        try {
            selectedThemeColor = this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);

        } catch (Exception e) {
            e.printStackTrace();

        }
        enablePermissions();
        callgetActivitiesListRequest();
        callgetDistrictListRequest();
        //Preview button click listener
        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EMailResult = email.getText().toString().trim();
                if (CheckInternet.isOnline(MembershipRegFormActivity.this)) {
                    if (validateFields()) {

                        Full_name_View.setText(Full_name.getText().toString().trim());
                        fatherHusbName_View.setText(fatherHusbName.getText().toString().trim());
                        Gender_View.setText(Selected_gender);
                        DOB_View.setText(DOB.getText().toString().trim());
                        Age_View.setText(ageResult.getText().toString().trim());
                        mob_num_View.setText(mob_num.getText().toString().trim());
                        email_View.setText(email.getText().toString().trim());
                        BloodGroup_View.setText(Bloodgroup.getSelectedItem().toString().trim());
                        Education_View.setText(education.getSelectedItem().toString().trim());
                        occupation_View.setText(occupation.getText().toString().trim());
                        house_no_View.setText(house_no.getText().toString().trim());
                        locatlity_View.setText(locatlity.getText().toString().trim());
                        street_View.setText(street.getText().toString().trim());
                        pincode_View.setText(pincode.getText().toString().trim());
                        hours_View.setText(hours.getText().toString().trim());
                        memberRegistration_edit.setVisibility(View.GONE);
                        memberRegistration_view.setVisibility(View.VISIBLE);
                        image_view_Res.setImageBitmap(bm);

                    }
                } else {
                    Toast.makeText(MembershipRegFormActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Edit Button Click listener
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberRegistration_edit.setVisibility(View.VISIBLE);
                memberRegistration_view.setVisibility(View.GONE);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMailResult = email.getText().toString().trim();
                if (CheckInternet.isOnline(MembershipRegFormActivity.this)) {
                    if (validateFields()) {
                        PhotoUpload(bm);
                    } else {

                    }
                }

                //callSetMembershipDetails();
            }
        });


        //Photo selection code
        Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });
        //radio button click listener
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radiobutton1 = findViewById(checkedId);
                Selected_gender = radiobutton1.getText().toString();
            }
        });
        //spinner code
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (MembersipDistResponseList.size() > 0) {
                    distId = MembersipDistResponseList.get(i).getDistrictID();
                    District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {
                        callgetMandalsListRequest("" + distId);
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
        mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembershipMandalsResponseList.size() > 0) {
                    manId = MembershipMandalsResponseList.get(i).getMandalID();
                    Mandal_View.setText(MembershipMandalsResponseList.get(i).getMandalName());
                    if (distId != 0 && manId != 0) {
                        callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
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
        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembersipVillagesResponseList.size() > 0) {
                    villageID = MembersipVillagesResponseList.get(i).getVillageID();
                    Village_View.setText(MembersipVillagesResponseList.get(i).getVillageName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        activities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityID = MembersipActivityResponseList.get(i).getId();
                Activities_View.setText(MembersipActivityResponseList.get(i).getActivityName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Datepicker and age calculation
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                DOB.setText(format);
                ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };
        DOB.setOnClickListener(new View.OnClickListener() {
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

    }
    //SERVICE CALL FOR PHOTO UPLOAD

    private void PhotoUpload(Bitmap bmp) {


        if (bmp == null) {
            Toast.makeText(this, "Image can't be empty", Toast.LENGTH_SHORT).show();
        } else {

            //FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;


            File file = new File(FilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData("photoUpload", file.getName(), requestFile);
            //TypedFile file = new TypedFile("multipart/form-data", new File(path));
            progressDialog.show();


            if (CheckInternet.isOnline(MembershipRegFormActivity.this)) {
                //updateLocationPresenter.UploadImageServiceCall(body);
                calluploadPhotoMembAdd(PhotoBody);
            } else {
                Toast.makeText(MembershipRegFormActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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
                        callSetMembershipDetails();
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
    //SERVICE CALL OF VILLAGES

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
                    villageadapter = new MembershipvillageAdaptor(MembershipRegFormActivity.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    village.setAdapter(villageadapter);
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
                    mandaladapter = new MembershipMandalAdaptor(MembershipRegFormActivity.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                    mandal.setAdapter(mandaladapter);
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
                        adapter = new MembershipDistAdaptor(MembershipRegFormActivity.this, R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        district.setAdapter(adapter);


                    } else {
                        //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
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

    //SERVICE CALL OF ACTIVITIES
    private void callgetActivitiesListRequest() {

        try {
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            MembersipActivityResponseList.clear();
            MemberActivitiesResponse membersipActvityResponse = new MemberActivitiesResponse();
            membersipActvityResponse.setId(0);
            membersipActvityResponse.setActivityName("Select Activity");
            MembersipActivityResponseList.add(membersipActvityResponse);
            Call<List<MemberActivitiesResponse>> call = apiInterface.getActivitiesForMemReg();
            Log.e("  url", call.request().url().toString());

            call.enqueue(new Callback<List<MemberActivitiesResponse>>() {
                @Override
                public void onResponse(Call<List<MemberActivitiesResponse>> call, Response<List<MemberActivitiesResponse>> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        Log.d("Activity ", "Response = " + response.body());
                        MembersipActivityResponseList.addAll(response.body());
                        activityAdaptor = new MembershipActivityAdaptor(MembershipRegFormActivity.this, R.layout.listitems_layout, R.id.title, MembersipActivityResponseList);
                        activities.setAdapter(activityAdaptor);
                        //goListMutableLiveData.setValue(response.body().getLast10days());
                    } else {
                        //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<MemberActivitiesResponse>> call, Throwable t) {

                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //SERVICE CALL FOR SUBMITTING DATA

    private void callSetMembershipDetails() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject object = new JSONObject();
        try {

            object.put("membershipType", GlobalDeclaration.Selection_MEMbership_type);
            object.put("studentName", Full_name.getText().toString().trim());
            object.put("fatherName", fatherHusbName.getText().toString().trim());
            object.put("gender", Selected_gender);
            object.put("dateOfBirth", DOB.getText().toString().trim());
            object.put("studentContactNumber", mob_num.getText().toString());
            object.put("email", email.getText().toString());
            object.put("bloodGroup", Bloodgroup.getSelectedItem().toString().trim());
            object.put("className", education.getSelectedItem().toString().trim());
            object.put("occupation", occupation.getText().toString());
            object.put("houseNo", house_no.getText().toString());
            object.put("locality", locatlity.getText().toString());
            object.put("streetArea", street.getText().toString());
            object.put("districts", Integer.toString(distId));
            object.put("mandals", Integer.toString(manId));
            object.put("village", Integer.toString(villageID));
            object.put("pincode", pincode.getText().toString());
            object.put("activities", Integer.toString(activityID));
            object.put("noOfHours", hours.getText().toString());
            object.put("photoPath", PHOTOPATH);
            object.put("requestType", "mobile");

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
            Log.e("sent_json", object.toString());

            Call<PaymentBean> call = apiInterface.SendDetails(gsonObject);
            call.enqueue(new Callback<PaymentBean>() {
                @Override
                public void onResponse(Call<PaymentBean> call, Response<PaymentBean> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {

                        GlobalDeclaration.Paymenturl = response.body().getPaymentGatewayUrl();
                        Toast.makeText(MembershipRegFormActivity.this, response.body().getStatusMsg(), Toast.LENGTH_LONG).show();
                        //GlobalDeclaration.encrpyt=response.body().getPaymentRequest().replace("|","!");
                        GlobalDeclaration.encrpyt = response.body().getPaymentRequest();

                        Log.d("responseurl", "onResponse: url" + response.body().getPaymentRequest());


                        Intent i = new Intent(MembershipRegFormActivity.this, WebViewPaymentActivity.class);
                        startActivity(i);
                        /*Log.e("Response","====="+response.toString());
                        final PrettyDialog dialog = new PrettyDialog(MembershipRegFormActivity.this);
                        dialog
                                .setTitle("Red cross")
                                .setMessage("Registration Completed Succe")
                                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        dialog.dismiss();
                                        startActivity(new Intent(MembershipRegFormActivity.this, TabLoginActivity.class));
                                        finish();
                                    }
                                });


                        dialog.show();*/


                    } else {
                        Toast.makeText(MembershipRegFormActivity.this, "else", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PaymentBean> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(MembershipRegFormActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MembershipRegFormActivity.this)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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

    //Validate fields
    protected boolean validateFields() {

        if (Full_name.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter your full name", Toast.LENGTH_LONG).show();
            return false;
        } else if (fatherHusbName.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter your Father or husband name", Toast.LENGTH_LONG).show();
            return false;
        } else if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MembershipRegFormActivity.this, "Select your gender", Toast.LENGTH_LONG).show();
            return false;
        } else if (DOB.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter your Date of Birth", Toast.LENGTH_LONG).show();
            return false;
        } else if (mob_num.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (mob_num.getText().toString().trim().length() < 10) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (!(mob_num.getText().toString().trim().startsWith("9") || mob_num.getText().toString().trim().startsWith("8") || mob_num.getText().toString().trim().startsWith("7") || mob_num.getText().toString().trim().startsWith("6") || mob_num.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (!EMailResult.matches(emailPattern)) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Email ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (EMailResult.length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Email ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (Bloodgroup.getSelectedItem().toString().trim().equals("Select Blood Group")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Blood group", Toast.LENGTH_LONG).show();
            return false;
        } else if (education.getSelectedItem().toString().trim().equals("Select Educational Qualification")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Educational Qualification", Toast.LENGTH_LONG).show();
            return false;
        } else if (occupation.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Occupation", Toast.LENGTH_LONG).show();
            return false;
        } else if (house_no.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter House No.", Toast.LENGTH_LONG).show();
            return false;
        } else if (locatlity.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter locatlity", Toast.LENGTH_LONG).show();
            return false;
        } else if (street.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Street/Area", Toast.LENGTH_LONG).show();
            return false;
        } else if (district.getSelectedItem().toString().trim().equals("Select District")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select District", Toast.LENGTH_LONG).show();
            return false;
        } else if (mandal.getSelectedItem().toString().trim().equals("Select Mandal")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Mandal", Toast.LENGTH_LONG).show();
            return false;
        } else if (village.getSelectedItem().toString().trim().equals("Select Town/Village")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Town/Village", Toast.LENGTH_LONG).show();
            return false;
        } else if (pincode.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Pincode", Toast.LENGTH_LONG).show();
            return false;
        } else if (pincode.getText().toString().length() < 6) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Pincode", Toast.LENGTH_LONG).show();
            return false;
        } else if (activities.getSelectedItem().toString().trim().equals("Select Activities interested")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Activities interested", Toast.LENGTH_LONG).show();
            return false;
        } else if (hours.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter number of hours", Toast.LENGTH_LONG).show();
            return false;
        } else if (Photo.getDrawable() == null) {
            Toast.makeText(MembershipRegFormActivity.this, "Choose photo", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MembershipRegFormActivity.this);
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
        /*Uri imageUri = FileProvider.getUriForFile(
                MembershipRegFormActivity.this,
                "in.gov.cgg.redcrossphase1.ui_citiguest.provider", //(use your app signature + ".provider" )
                imageFile);
        return imageUri;*/
        Uri imageUri = FileProvider.getUriForFile(
                MembershipRegFormActivity.this,//(use your app signature + ".provider" )
                BuildConfig.APPLICATION_ID + ".fileprovider",
                imageFile);
        return imageUri;
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

                Photo.setImageBitmap(bm);
                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
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
                Photo.setImageBitmap(bm);
                FilePath = picturePath;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

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

}
