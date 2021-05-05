package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

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
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
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
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_citiguest.LocBaseActivity;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class NewMembershipRegFormFragment extends LocBaseActivity {
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 100;
    private final List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private final List<MemberActivitiesResponse> MembersipActivityResponseList = new ArrayList<>();
    private final List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private final List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();
    public Uri fileUri;
    Button Next;
    Uri mCropImageUri;
    EditText Full_name, fatherHusbName, DOB, mob_num, email, occupation, house_no, locatlity, street, pincode, hours;
    RadioGroup rg;
    Uri resultUri;
    TextView ageResult, spared_hours;
    ImageView Photo;
    String PHOTOPATH = "";
    Spinner Bloodgroup, education, district, mandal, village, activities;
    String Selected_gender = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    String EMailResult = "", Education = "", Occupation = "", Pincode = "", Hours = "";
    String FilePath;
    TextView personaldetails, switchView, professionalheeading, CommuDetails, OtherDetails;
    RelativeLayout headingWithHome;
    File imageFile;
    String selectedFilePath;
    ImageView home;
    LinearLayout ll_Mainlayout, MainLayout, dist_spin_lay, Mandal_spin_lay, Village_apin_lay;
    CustomProgressDialog progressDialog;
    MembershipDistAdaptor adapter;
    MembershipActivityAdaptor activityAdaptor;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    TextView HeadingOfForm;
    Toolbar toolbar;
    int selectedThemeColor = -1;
    Integer distId = 0, manId = 0, villageID = 0, activityID = 0;
    View view1, view2, view3, view4;
    Bitmap bm;
    String Image_name;
    private JsonObject gsonObject;
    private FragmentActivity c;
    /*Dialog dialog;
    String[] str = {"", "", "", "", ""};
    ArrayList itemsSelected = new ArrayList();*/

    public static String[] GetStringArray(ArrayList<String> arr) {

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // convert Object array to String array
        String[] str = Arrays.copyOf(objArr, objArr.length, String[].class);
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_reg_form);

        // Inflate the layout for this fragment
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        ll_Mainlayout = findViewById(R.id.ll_Mainlayout);
        progressDialog = new CustomProgressDialog(this);
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
        HeadingOfForm = findViewById(R.id.HeadingOfForm);
        home = findViewById(R.id.home);
        dist_spin_lay = findViewById(R.id.dist_spin_lay);
        Mandal_spin_lay = findViewById(R.id.mandal_spin_lay);
        Village_apin_lay = findViewById(R.id.village_spin_lay);
        spared_hours = findViewById(R.id.spared_hours);
        MainLayout = findViewById(R.id.MainLayout);
        switchView = findViewById(R.id.switchView);
        personaldetails = findViewById(R.id.personaldetails);
        professionalheeading = findViewById(R.id.professionalheeading);
        CommuDetails = findViewById(R.id.commuDetails);
        OtherDetails = findViewById(R.id.OtherDetails);
        headingWithHome = findViewById(R.id.headingWithHome);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        //Actvities_selection_id=findViewById(R.id.Actvities_selection_id);
        Next = findViewById(R.id.Next);


        try {

            selectedThemeColor = getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);


            if (selectedThemeColor != -1) {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(selectedThemeColor)));
                }


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    MainLayout.setBackgroundResource(R.drawable.redcross1_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    MainLayout.setBackgroundResource(R.drawable.redcross2_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    MainLayout.setBackgroundResource(R.drawable.redcross3_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    MainLayout.setBackgroundResource(R.drawable.redcross4_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    MainLayout.setBackgroundResource(R.drawable.redcross5_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    MainLayout.setBackgroundResource(R.drawable.redcross7_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    MainLayout.setBackgroundResource(R.drawable.redcross8_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    switchView.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else {
                    MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                    personaldetails.setTextColor(getResources().getColor(selectedThemeColor));
                    switchView.setTextColor(getResources().getColor(selectedThemeColor));
                    professionalheeading.setTextColor(getResources().getColor(selectedThemeColor));
                    CommuDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    OtherDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    headingWithHome.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    spared_hours.setTextColor(getResources().getColor(selectedThemeColor));
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        HeadingOfForm.setText("Application for " + GlobalDeclaration.SELECTEDtype);
        //enablePermissions();


        callgetActivitiesListRequest();
        callgetDistrictListRequest();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(NewMembershipRegFormFragment.this, CitiGuestMainActivity.class);
                startActivity(ii);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().length() == 0) {
                    EMailResult = "";
                } else {
                    EMailResult = email.getText().toString().trim();
                }
                if (email.getText().toString().length() == 0) {
                    EMailResult = "";
                } else {
                    EMailResult = email.getText().toString().trim();
                }
                if (email.getText().toString().length() == 0) {
                    EMailResult = "";
                } else {
                    EMailResult = email.getText().toString().trim();
                }
                if (email.getText().toString().length() == 0) {
                    EMailResult = "";
                } else {
                    EMailResult = email.getText().toString().trim();
                }

                if (education.getSelectedItem().toString().contains("Select Education")) {
                    Education = "";
                } else {
                    Education = education.getSelectedItem().toString().trim();
                }
                Occupation = occupation.getText().toString().trim();
                Pincode = pincode.getText().toString().trim();
                Hours = hours.getText().toString();
                if (CheckInternet.isOnline(NewMembershipRegFormFragment.this)) {
                    if (validateFields()) {
                        PhotoUpload();
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

                try {
//                    selectImage();
                    selectImageCameraandGalary();


                } catch (Exception e) {
                    Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
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
                    if (distId != 0) {
                        callgetMandalsListRequest("" + distId);
                        Mandal_spin_lay.setVisibility(View.VISIBLE);
                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        mandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        village.setAdapter(villageadapter);
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
                    if (distId != 0 && manId != 0) {
                        callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
                        Log.e("MANDALID", "====" + MembershipMandalsResponseList.get(i).getMandalID());
                        Village_apin_lay.setVisibility(View.VISIBLE);
                    } else {
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName("Select Town/Village");
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        village.setAdapter(villageadapter);
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ACTIVITIES SELECTION SPINNER
        activities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembersipActivityResponseList.size() > 0) {
                    activityID = MembersipActivityResponseList.get(i).getId();
                }
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
                c.add(Calendar.YEAR, -10);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                c.add(Calendar.YEAR, -60);
                c.add(Calendar.MONTH, +1);
                dateDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                dateDialog.show();
            }
        });

    }

    private void selectImageCameraandGalary() {
        Intent intent = CropImage.activity()
                .setAspectRatio(16, 16)
                .getIntent(this);

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }


    //SERVICE CALL FOR PHOTO UPLOAD

    /*
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


                if (CheckInternet.isOnline(this)) {
                    //updateLocationPresenter.UploadImageServiceCall(body);
                    calluploadPhotoMembAdd(PhotoBody);
                } else {
                    Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

        }
    */
    private void PhotoUpload() {
        //saveHomeNursingDetails();

        if (resultUri == null) {
            Toast.makeText(this, "Image can't be empty", Toast.LENGTH_SHORT).show();
        } else if (selectedFilePath != null) {

            File file = new File(selectedFilePath);
            Log.d("selectedFilePath", selectedFilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData("photoUpload", file.getName(), requestFile);
            //TypedFile file = new TypedFile("multipart/form-data", new File(path));
            progressDialog.show();

            if (CheckInternet.isOnline(this)) {

                calluploadPhotoMembAdd(PhotoBody);

            } else {
                Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void calluploadPhotoMembAdd(final MultipartBody.Part PhotoBody) {

        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PhotoBean> call = apiInterface.SendPhoto(PhotoBody);
        Log.i("member_URL", call.request().url().toString());
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
            }

            @Override
            public void onFailure(Call<PhotoBean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                //updateLocationInterface.SubmitData(null);
                t.printStackTrace();
            }
        });
    }
    //SERVICE CALL OF VILLAGES

    private void callgetVillagesListRequest(String MandID) {

        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MembershipVillagesResponse>> call = apiInterface.getVillagesForMemReg(MandID);
        Log.e("  url", call.request().url().toString());

        MembersipVillagesResponseList.clear();
        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
        membershipVillagesResponse.setVillageID(0);
        membershipVillagesResponse.setVillageName("Select Town/Village");
        MembersipVillagesResponseList.add(membershipVillagesResponse);

        call.enqueue(new Callback<List<MembershipVillagesResponse>>() {
            @Override
            public void onResponse(Call<List<MembershipVillagesResponse>> call, Response<List<MembershipVillagesResponse>> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("Activity ", "Response = " + response.body().toString());
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                    MembersipVillagesResponseList.addAll(response.body());
                    villageadapter = new MembershipvillageAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    village.setAdapter(villageadapter);
                } else {
                    Toast.makeText(NewMembershipRegFormFragment.this, "NULL", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<MembershipVillagesResponse>> call, Throwable t) {
                Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


    }

    //SERVICE CALL OF MANDALS

    private void callgetMandalsListRequest(String DistID) {

        progressDialog.show();
        progressDialog.setCancelable(false);
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
                    mandaladapter = new MembershipMandalAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                    mandal.setAdapter(mandaladapter);
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    MembersipVillagesResponseList.clear();
                    MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                    membershipVillagesResponse.setVillageID(0);
                    membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                    MembersipVillagesResponseList.add(membershipVillagesResponse);
                    villageadapter = new MembershipvillageAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    village.setAdapter(villageadapter);
                }
            }

            @Override
            public void onFailure(Call<List<MembershipMandalsResponse>> call, Throwable t) {
                Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    //SERVICE CALL OF DISTRICTS
    private void callgetDistrictListRequest() {
        try {

            progressDialog.show();
            progressDialog.setCancelable(false);
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
                        adapter = new MembershipDistAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        district.setAdapter(adapter);


                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        mandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        village.setAdapter(villageadapter);
                    }

                }

                @Override
                public void onFailure(Call<List<MembersipDistResponse>> call, Throwable t) {
                    Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
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
        progressDialog.setCancelable(false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject object = new JSONObject();
        try {

            object.put("membershipType", GlobalDeclaration.Selection_MEMbership_type);
            object.put("studentName", Full_name.getText().toString().trim());
            object.put("fatherName", fatherHusbName.getText().toString().trim());
            object.put("gender", Selected_gender);
            object.put("dateOfBirth", DOB.getText().toString().trim());
            object.put("studentContactNumber", mob_num.getText().toString());
            object.put("email", EMailResult);
            object.put("bloodGroup", Bloodgroup.getSelectedItem().toString().trim());
            object.put("className", Education);
            object.put("occupation", Occupation);
            object.put("houseNo", house_no.getText().toString());
            object.put("locality", locatlity.getText().toString());
            object.put("streetArea", street.getText().toString());
            object.put("districts", Integer.toString(distId));
            object.put("mandals", Integer.toString(manId));
            object.put("village", Integer.toString(villageID));
            object.put("pincode", Pincode);
            object.put("activities", Integer.toString(activityID));
            object.put("noOfHours", Hours);
            object.put("photoPath", PHOTOPATH);
            object.put("requestType", "mobile");

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
            Log.e("sent_json", object.toString());

            Call<PaymentBean> call = apiInterface.SendDetails(gsonObject);
            call.enqueue(new Callback<PaymentBean>() {
                @Override
                public void onResponse(Call<PaymentBean> call, final Response<PaymentBean> response) {

                    progressDialog.dismiss();
                    if (response.body() != null && response.body().getStatus().contains("Success")) {

                        GlobalDeclaration.Paymenturl = response.body().getPaymentGatewayUrl();
                        // Toast.makeText(this, response.body().getStatusMsg(), Toast.LENGTH_LONG).show();
                       /* final PrettyDialog dialog = new PrettyDialog(getActivity());
                        dialog
                                .setTitle("Success")
                                .setMessage("Successfully submitted")
                                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                                .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        dialog.dismiss();
                                        GlobalDeclaration.encrpyt = response.body().getPaymentRequest();
                                        Log.d("responseurl", "onResponse: url" + response.body().getPaymentRequest());


                                        Fragment fragment = new MembershipPaymentFragment();
                                        FragmentManager fragmentManager = this.getSupportFragmentManager();
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        transaction.replace(R.id.nav_host_fragment, fragment, MembershipPaymentFragment.class.getSimpleName());
                                        transaction.addToBackStack(null);
                                        transaction.commitAllowingStateLoss();
                                    }
                                });

                        dialog.show();*/
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(NewMembershipRegFormFragment.this);
                        builder1.setMessage("Successfully submitted");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        GlobalDeclaration.encrpyt = response.body().getPaymentRequest();
                                        Log.d("responseurl", "onResponse: url" + response.body().getPaymentRequest());

                                        startActivity(new Intent(NewMembershipRegFormFragment.this, MembershipPaymentFragment.class));
//                                        Fragment fragment = new MembershipPaymentFragment();
//                                        FragmentManager fragmentManager = getSupportFragmentManager();
//                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                                        transaction.replace(R.id.nav_host_fragment, fragment, MembershipPaymentFragment.class.getSimpleName());
//                                        transaction.addToBackStack(null);
//                                        transaction.commitAllowingStateLoss();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    } else if (response.body() != null && response.body().getStatus().contains("Failed")) {
                        //Toast.makeText(this, "Response" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        /*final PrettyDialog dialog = new PrettyDialog(getActivity());
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
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(NewMembershipRegFormFragment.this);
                        builder1.setMessage(getResources().getString(R.string.reg_failed));
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        dialog.dismiss();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PaymentBean> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private int calculateAge(long date) {
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
    private boolean validateFields() {

        if (Full_name.getText().toString().trim().length() == 0) {
            Full_name.setError("Enter your full name");
            Full_name.requestFocus();
            return false;
        } else if (fatherHusbName.getText().toString().trim().length() == 0) {
            fatherHusbName.setError("Enter your Father or husband name");
            fatherHusbName.requestFocus();
            return false;
        } else if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select your gender", Toast.LENGTH_LONG).show();
            return false;
        } else if (DOB.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter your Date of birth", Toast.LENGTH_LONG).show();
            return false;
        } else if (mob_num.getText().toString().trim().length() == 0) {
            mob_num.setError(getResources().getString(R.string.enter_mobile_number));
            mob_num.requestFocus();
            return false;
        } else if (mob_num.getText().toString().trim().length() < 10) {
            mob_num.setError(getResources().getString(R.string.enter_validmno));
            mob_num.requestFocus();
            return false;
        } else if (!(mob_num.getText().toString().trim().startsWith("9") || mob_num.getText().toString().trim().startsWith("8") || mob_num.getText().toString().trim().startsWith("7") || mob_num.getText().toString().trim().startsWith("6") || mob_num.getText().toString().trim().startsWith("5"))) {
            mob_num.setError(getResources().getString(R.string.enter_validmno));
            mob_num.requestFocus();
            return false;
        } else if (EMailResult.length() != 0 && !EMailResult.matches(emailPattern)) {
            email.setError("Enter valid Email ID");
            email.requestFocus();
            return false;
        } else if (Bloodgroup.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Blood group", Toast.LENGTH_LONG).show();
            return false;
        } else if (Photo.getDrawable() == null) {
            Toast.makeText(this, "Choose photo", Toast.LENGTH_LONG).show();
            return false;
        } else if (house_no.getText().toString().trim().length() == 0) {
            house_no.setError("Enter House No.");
            house_no.requestFocus();
            return false;
        } else if (locatlity.getText().toString().trim().length() == 0) {
            locatlity.setError("Enter locatlity");
            locatlity.requestFocus();
            return false;
        } else if (street.getText().toString().trim().length() == 0) {
            street.setError("Enter Street/Area");
            street.requestFocus();
            return false;
        } else if (district.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select District", Toast.LENGTH_LONG).show();
            return false;
        } else if (mandal.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.Select_Mandal), Toast.LENGTH_LONG).show();
            return false;
        } else if (village.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Town/Village", Toast.LENGTH_LONG).show();
            return false;
        } else if (pincode.getText().toString().length() != 0 && pincode.getText().toString().length() < 6) {
            pincode.setError("Enter valid Pincode");
            pincode.requestFocus();
            return false;
        }
        return true;
    }

    private void selectImage() {
        final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_from_gallery), "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewMembershipRegFormFragment.this);
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
                    } catch (Exception e) {
                        Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }


                } else if (options[item].equals(getResources().getString(R.string.choose_from_gallery))) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } catch (Exception e) {
                        Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //SERVICE CALL OF ACTIVITIES
    private void callgetActivitiesListRequest() {

        try {
            progressDialog.show();
            progressDialog.setCancelable(false);
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

                        /*ArrayList<String> a1 = new ArrayList<String>();
                        for (int i =0;i<MembersipActivityResponseList.size();i++)
                        {
                            a1.add(MembersipActivityResponseList.get(i).getActivityName());
                        }
                        System.out.println("ArrayList: " + a1);
                        str  = GetStringArray(a1);
                        System.out.print("String Array[]: "+ Arrays.toString(str));*/
                        activityAdaptor = new MembershipActivityAdaptor(NewMembershipRegFormFragment.this, R.layout.listitems_layout, R.id.title, MembersipActivityResponseList);
                        activities.setAdapter(activityAdaptor);

                    } else {
                        Toast.makeText(NewMembershipRegFormFragment.this, "NULL", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MemberActivitiesResponse>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(NewMembershipRegFormFragment.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        /*Uri imageUri = FileProvider.getUriForFile(
                this,
                "in.gov.cgg.redcrossphase1.ui_citiguest.provider", //(use your app signature + ".provider" )
                imageFile);
        return imageUri;*/
        Uri imageUri = FileProvider.getUriForFile(
                this,//(use your app signature + ".provider" )
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
                .format(new Date()) + ".png";*/

        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "1.jpg");


        } else {
            return null;
        }

        return mediaFile;
    }

/*    @Override
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
                Photo.setImageBitmap(bm);
                FilePath = picturePath;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }*/

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
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();


            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //  Uri resultUri = result.getUri();
                resultUri = result.getUri();
                mCropImageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                selectedFilePath = resultUri.getPath();
                Photo.setImageURI(resultUri);
//                Log.d("result_uri", String.valueOf(resultUri));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
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


    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(NewMembershipRegFormFragment.this);
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
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}







