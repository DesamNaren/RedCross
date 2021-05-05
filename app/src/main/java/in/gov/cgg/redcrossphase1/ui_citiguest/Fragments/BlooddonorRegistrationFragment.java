package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDistAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipMandalAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipvillageAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenDonarRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DistrictBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonorregResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class BlooddonorRegistrationFragment extends Fragment {
    private final List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private final List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private final List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();
    CustomProgressDialog progressDialog;
    Button btn_donorRegNext;
    ImageView iv_datepickerdateofBirth, iv_previouslyDonationDate;
    EditText datePicker_dateofBirth, et_previouslyDonationDate;
    EditText et_name, et_fathersName, et_occupation, et_email, et_mobileNumber, et_office, et_adress, et_pincode;
    Spinner spn_gender, spn_bloodgroups;
    Spinner spn_district, spn_mandal, spn_village;
    RadioGroup RG_willingtodonateblood;
    RadioButton radioButton1True, radioButton2False;
    String name, fratherName, mobileNumber, officeName, address, dob, previoslydonatedDate, noofpreviousDonations;
    TextView et_donatetype;
    LinearLayout ll_registrationForm;
    LinearLayout dist_spin_lay, Mandal_spin_lay, Village_apin_lay;
    String email = "";
    String pincode = "";
    String occupation = "";
    Spinner spn_education;
    Spinner spn_married;
    String mBloodGroupId;
    String mGenderId;
    String mEducationId;
    Integer distId = 0, manId = 0, villageID = 0;
    CitizenDonarRequest request;
    MembershipDistAdaptor adapter;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    List<String> mBloodGroupIdsList;
    List<String> mGenderIdsList;
    List<String> mEducationIdsList;
    List<String> mMarriedIdsList;
    String mMarriedId;
    EditText et_noofpreviousDonations;
    String WillingBldDonateStatus = "false";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    JsonObject jsonObject;
    RadioButton radioButton;
    LinearLayout ll_donorregForm;
    TextView tv_titledonorDetails, tv_titleAdressInfo, tv_titledonorInfo;
    View view1, view2, view3;
    int selectedThemeColor = -1;
    private boolean distValidation, mandalValid, villageValid;
    private int mYear, mMonth, mDay;
    private List<DistrictBean> districtBeanArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        final View root = inflater.inflate(R.layout.fragment_blooddonor_registration, container, false);

        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.donor_reg));
        progressDialog = new CustomProgressDialog(getActivity());
        dist_spin_lay = root.findViewById(R.id.dist_spin_lay);
        Mandal_spin_lay = root.findViewById(R.id.mandal_spin_lay);
        Village_apin_lay = root.findViewById(R.id.village_spin_lay);

        findViews(root);


        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
            setThemes();

        } catch (Exception e) {
            e.printStackTrace();

        }


        btn_donorRegNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ediitextvalues
                name = et_name.getText().toString();
                fratherName = et_fathersName.getText().toString();
                occupation = et_occupation.getText().toString();
                mobileNumber = et_mobileNumber.getText().toString();
                email = et_email.getText().toString();
                officeName = et_office.getText().toString();
                address = et_adress.getText().toString();
                pincode = et_pincode.getText().toString();

                dob = datePicker_dateofBirth.getText().toString();
                previoslydonatedDate = et_previouslyDonationDate.getText().toString();
                noofpreviousDonations = et_noofpreviousDonations.getText().toString();
                //static spinner values

                //Save blood donor data(registration)
                if (CheckInternet.isOnline(getActivity())) {
                    if (validateFields()) {
                        calldonorRegistarion();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }


            }
        });

        loadGenderSpinner();
        loadBloodGroupSpinner();
        loadEducationSpinner();
        loadMarriedstatusSpinner();

        RG_willingtodonateblood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                int selectedId = RG_willingtodonateblood.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = root.findViewById(selectedId);

               /* Toast.makeText(getActivity(),
                        radioButton.getText(), Toast.LENGTH_SHORT).show();
*/


                if (radioButton.getText().toString().equalsIgnoreCase("Yes")) {

                    WillingBldDonateStatus = getResources().getString(R.string.trueval);

                } else {
                    WillingBldDonateStatus = getResources().getString(R.string.falseval);

                }

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
                et_previouslyDonationDate.setText(format);

            }
        };

        //Datepicker and age calculation
        final DatePickerDialog.OnDateSetListener datePickerListenerDOB = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                datePicker_dateofBirth.setText(format);


            }
        };
        datePicker_dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                c.add(Calendar.YEAR, -18);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerListener = new DatePickerDialog(view.getContext(), datePickerListenerDOB, mYear, mMonth, mDay);
                datePickerListener.getDatePicker().setMaxDate(c.getTimeInMillis());
                c.add(Calendar.YEAR, -57);
                datePickerListener.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerListener.show();
            }
        });

        et_previouslyDonationDate.setOnClickListener(new View.OnClickListener() {
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
        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (MembersipDistResponseList.size() > 0) {
                    distId = MembersipDistResponseList.get(i).getDistrictID();
                    //   District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {
                        if (CheckInternet.isOnline(getActivity())) {

                            Mandal_spin_lay.setVisibility(View.VISIBLE);
                            callgetMandalsListRequest("" + distId);
                            distValidation = true;

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        spn_mandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        spn_village.setAdapter(villageadapter);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //mandal spinner
        spn_mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembershipMandalsResponseList.size() > 0) {
                    manId = MembershipMandalsResponseList.get(i).getMandalID();
                    //   Mandal_View.setText(MembershipMandalsResponseList.get(i).getMandalName());
                    if (distId != 0 && manId != 0) {

                        if (CheckInternet.isOnline(getActivity())) {
                            Village_apin_lay.setVisibility(View.VISIBLE);
                            callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
                            mandalValid = true;
                            Log.e("MANDALID", "====" + MembershipMandalsResponseList.get(i).getMandalID());

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        spn_village.setAdapter(villageadapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //village spinner
        spn_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        if (CheckInternet.isOnline(getActivity())) {

            callgetDistrictListRequest();

        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


        //    callgetMandalsListRequest();
        //    callgetVillagesListRequest();

        return root;
    }

    private void loadBloodGroupSpinner() {

        mBloodGroupIdsList = Arrays.asList(getResources().getStringArray(R.array.blood_groups));
        spn_bloodgroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                mBloodGroupId = mBloodGroupIdsList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadGenderSpinner() {

        mGenderIdsList = Arrays.asList(getResources().getStringArray(R.array.Gender));
        spn_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGenderId = mGenderIdsList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadEducationSpinner() {

        mEducationIdsList = Arrays.asList(getResources().getStringArray(R.array.Education));
        spn_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spn_education.getSelectedItem().toString().contains(getResources().getString(R.string.enter_edcation))) {
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

    private void loadMarriedstatusSpinner() {

        mMarriedIdsList = Arrays.asList(getResources().getStringArray(R.array.Married));
        spn_married.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mMarriedId = mMarriedIdsList.get(position);

/*
                if(mMarriedIdsList.get(position) = ""){
                    mMarriedId = "";
                } else {
                    mMarriedId = mMarriedIdsList.get(position);

                }*/


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
            progressDialog.setCancelable(false);
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
                        adapter = new MembershipDistAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        spn_district.setAdapter(adapter);

                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        spn_mandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        spn_village.setAdapter(villageadapter);
                    }

                }

                @Override
                public void onFailure(Call<List<MembersipDistResponse>> call, Throwable t) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    spn_village.setAdapter(villageadapter);
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<MembershipVillagesResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
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
                    mandaladapter = new MembershipMandalAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                    spn_mandal.setAdapter(mandaladapter);
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    MembersipVillagesResponseList.clear();
                    MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                    membershipVillagesResponse.setVillageID(0);
                    membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                    MembersipVillagesResponseList.add(membershipVillagesResponse);
                    villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    spn_village.setAdapter(villageadapter);
                }
            }

            @Override
            public void onFailure(Call<List<MembershipMandalsResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


    protected boolean validateFields() {

        if (et_name.getText().toString().trim().length() == 0) {

            et_name.setError(getResources().getString(R.string.enter_your_name));
            et_name.requestFocus();

            return false;
        } else if (et_fathersName.getText().toString().trim().length() == 0) {

            et_fathersName.setError(getResources().getString(R.string.enter_father_s_husband_s_name));
            et_fathersName.requestFocus();

            return false;

        } else if (datePicker_dateofBirth.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_date_of_birth), Toast.LENGTH_LONG).show();
            return false;
        } else if (spn_gender.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.select_gender), Toast.LENGTH_LONG).show();
            return false;
        } else if (et_mobileNumber.getText().toString().trim().length() == 0) {
            et_mobileNumber.setError(getResources().getString(R.string.enter_mobile_number));
            et_mobileNumber.requestFocus();

            return false;
        } else if (et_mobileNumber.getText().toString().trim().length() < 10) {
            et_mobileNumber.setError(getResources().getString(R.string.enter_validmno));
            et_mobileNumber.requestFocus();

            return false;
        } else if (!(et_mobileNumber.getText().toString().trim().startsWith("9") || et_mobileNumber.getText().toString().trim().startsWith("8") || et_mobileNumber.getText().toString().trim().startsWith("7") || et_mobileNumber.getText().toString().trim().startsWith("6") || et_mobileNumber.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_validmno), Toast.LENGTH_LONG).show();
            return false;

        } else if (et_office.getText().toString().trim().length() == 0) {
            et_office.setError(getResources().getString(R.string.enter_office_name));
            et_office.requestFocus();

            return false;
        } else if (et_adress.getText().toString().trim().length() == 0) {
            et_adress.setError(getResources().getString(R.string.enter_address));
            et_adress.requestFocus();

            return false;
        } else if (!distValidation) {
            Toast.makeText(getActivity(), getResources().getString(R.string.Select_District), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mandalValid) {
            Toast.makeText(getActivity(), getResources().getString(R.string.Select_Mandal), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!villageValid) {
            Toast.makeText(getActivity(), getResources().getString(R.string.Select_Village), Toast.LENGTH_SHORT).show();
            return false;
        } else if (spn_bloodgroups.getSelectedItemPosition() == 0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.select_blood_group), Toast.LENGTH_LONG).show();
            return false;
        }

//        else if (et_previouslyDonationDate.getText().toString().trim().length() == 0) {
//            Toast.makeText(getActivity(), "Please enter donation date ", Toast.LENGTH_LONG).show();
//
//            return false;
//        }

        else if (!radioButton1True.isChecked() && !radioButton2False.isChecked()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.willing_to_donate_blood), Toast.LENGTH_LONG).show();
            return false;
        } else if (et_noofpreviousDonations.getText().toString().trim().length() == 0) {

            et_noofpreviousDonations.setError(getResources().getString(R.string.no_prev_experience));
            et_noofpreviousDonations.requestFocus();

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

    private void calldonorRegistarion() {

        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        request = new CitizenDonarRequest();
        request.setName(name);
        request.setFatherName(fratherName);
        request.setDateOfBirth(dob);
        request.setGender(mGenderId);
        request.setOccupation(occupation);
        request.setPhoneNo(mobileNumber);
        request.setEmail(email);
        request.setOffice(officeName);
        request.setAddress(address);
        request.setDistricts("" + distId);
        request.setMandals("" + manId);
        request.setVillage("" + villageID);
        request.setPincode(pincode);

        if (mBloodGroupId.contains("--Select--")) {
            mBloodGroupId = "";
            request.setBloodGroup(mBloodGroupId);

        } else {
            request.setBloodGroup(mBloodGroupId);

        }


        if (mEducationId.contains("--Select--")) {
            mEducationId = "";
            request.setEducation(mEducationId);

        } else {
            request.setEducation(mEducationId);

        }
        if (mMarriedId.contains("--Select--")) {
            mMarriedId = "";
            request.setMarried(mMarriedId);
        } else {
            request.setMarried(mMarriedId);
        }


        request.setDonateType("");
        request.setPrevDonationDate(previoslydonatedDate);
        request.setWillingToDonateYearly(WillingBldDonateStatus);
        request.setNoOfPrevDonations(noofpreviousDonations);
        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.d("Donor", "================: " + json);
        Call<DonorregResponse> call = apiInterface.callCitizendonorRegistration(request);


        Log.d("Login", "callcitizenLoginRequest: " + call.request().url());

        call.enqueue(new Callback<DonorregResponse>() {
            @Override
            public void onResponse(Call<DonorregResponse> call, Response<DonorregResponse> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {

                        callsuccess();


                    } else if (response.body() != null && response.body().getStatus().contains("Failed")) {
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

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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
                    } else if (response.body() != null && response.body().getStatus().contains(getResources().getString(R.string.failed))) {
                        //Toast.makeText(getActivity(), "Response" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
//                        final PrettyDialog dialog = new PrettyDialog(getActivity());
//                        dialog
//                                .setTitle("Response")
//                                .setMessage("Registration Failed")
//                                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
//                                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
//                                    @Override
//                                    public void onClick() {
//                                        dialog.dismiss();
//                                    }
//                                });
//
//                        dialog.show();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<DonorregResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callsuccess() {

        /*final PrettyDialog dialog = new PrettyDialog(getActivity());
        dialog
                .setTitle("Registered Succesfully")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), CitiGuestMainActivity.class);
                        startActivity(i);


                    }
                });

        dialog.show();*/
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(getResources().getString(R.string.reg_successfully));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), CitiGuestMainActivity.class);
                        startActivity(i);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void callSuccessDailouge() {

//        final PrettyDialog dialog = new PrettyDialog(getActivity());
//        dialog
//                .setTitle("Registered Succesfully")
//                .setMessage("Do you want to Logout?")
//                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
//                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
//                    @Override
//                    public void onClick() {
//                        dialog.dismiss();
//                        FragmentManager fm = getFragmentManager();
//                        FragmentTransaction ft = fm.beginTransaction();
//                        BlooddonorRegistrationFragment llf = new BlooddonorRegistrationFragment();
//                        ft.replace(R.id.fragment_citizendashboard, llf);
//                        ft.commit();
//                    }
//                });
//
//               /* .addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
//                    @Override
//                    public void onClick() {
//                        dialog.dismiss();
//                        // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
//                    }
//                });*/
//
//        dialog.show();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(getResources().getString(R.string.reg_successfully));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        dialog.dismiss();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        BlooddonorRegistrationFragment llf = new BlooddonorRegistrationFragment();
                        ft.replace(R.id.fragment_citizendashboard, llf);
                        ft.commit();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void findViews(View root) {
        btn_donorRegNext = root.findViewById(R.id.btn_donorRegNext);
        //  iv_datepickerdateofBirth = root.findViewById(R.id.iv_datepickerdateofBirth);
        //  iv_previouslyDonationDate = root.findViewById(R.id.iv_previouslyDonationDate);
        et_previouslyDonationDate = root.findViewById(R.id.et_previouslyDonationDate);
        et_noofpreviousDonations = root.findViewById(R.id.et_noofpreviousDonations);


        et_name = root.findViewById(R.id.et_name);
        ll_registrationForm = root.findViewById(R.id.ll_registrationForm);
        et_fathersName = root.findViewById(R.id.et_fathersName);
        datePicker_dateofBirth = root.findViewById(R.id.datePicker_dateofBirth);
        et_occupation = root.findViewById(R.id.et_occupation);
        et_mobileNumber = root.findViewById(R.id.et_mobileNumber);
        et_email = root.findViewById(R.id.et_email);
        et_office = root.findViewById(R.id.et_office);
        et_adress = root.findViewById(R.id.et_adress);
        et_pincode = root.findViewById(R.id.et_pincode);
        et_donatetype = root.findViewById(R.id.et_donatetype);


        spn_gender = root.findViewById(R.id.spn_gender);
        spn_education = root.findViewById(R.id.spn_education);
        spn_married = root.findViewById(R.id.spn_married);
        spn_district = root.findViewById(R.id.spn_district);
        spn_mandal = root.findViewById(R.id.spn_mandal);
        spn_village = root.findViewById(R.id.spn_village);
        spn_bloodgroups = root.findViewById(R.id.spn_bloodgroups);

        RG_willingtodonateblood = root.findViewById(R.id.radioGroup_willingtodonateblood);
        radioButton1True = root.findViewById(R.id.radioButton1True);
        radioButton2False = root.findViewById(R.id.radioButton2False);

        ll_donorregForm = root.findViewById(R.id.ll_donorregForm);
        tv_titledonorDetails = root.findViewById(R.id.tv_titledonorDetails);
        tv_titleAdressInfo = root.findViewById(R.id.tv_titleAdressInfo);
        tv_titledonorInfo = root.findViewById(R.id.tv_titledonorInfo);


        view1 = root.findViewById(R.id.view1);
        view2 = root.findViewById(R.id.view2);
        view3 = root.findViewById(R.id.view3);


    }

    public void setThemes() {

        if (selectedThemeColor != -1) {
            if (selectedThemeColor == R.color.redcroosbg_1) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                //btn_donorRegNext.setBackgroundColor(selectedThemeColor);

            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else {
                ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_titleAdressInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_titledonorInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }

        } else {
            ll_registrationForm.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
            tv_titledonorDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_titleAdressInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_titledonorInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
            view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(final View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage(getResources().getString(R.string.exitmsg));
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
//                                    startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
                                    FragmentActivity activity = (FragmentActivity) v.getContext();
                                    Fragment frag = new DonateBloodMainFragment();
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                            frag).addToBackStack(null).commit();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                    return true;
                }
                return false;
            }
        });
    }


}
