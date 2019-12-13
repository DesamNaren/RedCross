package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class BlooddonorRegistrationFragment extends Fragment {
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
    private boolean distValidation, mandalValid, villageValid;
    JsonObject jsonObject;
    private int mYear, mMonth, mDay;
    private List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();
    private List<DistrictBean> districtBeanArrayList;
    RadioButton radioButton;
    LinearLayout ll_donorregForm;
    TextView tv_titledonorDetails, tv_titleAdressInfo, tv_titledonorInfo;
    View view1, view2, view3;
    int selectedThemeColor = -1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_blooddonor_registration, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Donor Regisration");
        progressDialog = new CustomProgressDialog(getActivity());

        findViews(root);


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
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

                    WillingBldDonateStatus = "true";

                } else {
                    WillingBldDonateStatus = "false";

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


                Calendar userAge = new GregorianCalendar(year, month, day);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(userAge)) {
                    Toast.makeText(getActivity(), "minimum age should be 18 years ", Toast.LENGTH_SHORT).show();
                    datePicker_dateofBirth.setText("");
                } else {
                    datePicker_dateofBirth.setText(format);

                }

            }
        };
        datePicker_dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), datePickerListenerDOB, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
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
                        callgetMandalsListRequest("" + distId);
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
        spn_mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembershipMandalsResponseList.size() > 0) {
                    manId = MembershipMandalsResponseList.get(i).getMandalID();
                    //   Mandal_View.setText(MembershipMandalsResponseList.get(i).getMandalName());
                    if (distId != 0 && manId != 0) {
                        callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
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

        callgetDistrictListRequest();
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


                mEducationId = mEducationIdsList.get(position);



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
                        spn_district.setAdapter(adapter);

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
                    villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    spn_village.setAdapter(villageadapter);
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
                    spn_mandal.setAdapter(mandaladapter);
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

        if (et_name.getText().toString().trim().length() == 0) {

            et_name.setError("enter your  name");
            et_name.requestFocus();

            return false;
        } else if (et_fathersName.getText().toString().trim().length() == 0) {

            et_fathersName.setError("enter  father or husband name");
            et_fathersName.requestFocus();

            return false;

        } else if (datePicker_dateofBirth.getText().toString().trim().length() == 0) {
            datePicker_dateofBirth.setError("enter your date of birth");
            datePicker_dateofBirth.requestFocus();


            return false;
        } else if (spn_gender.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "select gender", Toast.LENGTH_LONG).show();



            return false;
        } else if (et_mobileNumber.getText().toString().trim().length() == 0) {
            et_mobileNumber.setError("enter mobile number");
            et_mobileNumber.requestFocus();

            return false;
        } else if (!(et_mobileNumber.getText().toString().trim().startsWith("9") || et_mobileNumber.getText().toString().trim().startsWith("8") || et_mobileNumber.getText().toString().trim().startsWith("7") || et_mobileNumber.getText().toString().trim().startsWith("6") || et_mobileNumber.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(getActivity(), "Enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;

        } else if (et_office.getText().toString().trim().length() == 0) {
            et_office.setError("enter office name");
            et_office.requestFocus();

            return false;
        } else if (et_adress.getText().toString().trim().length() == 0) {
            et_adress.setError("enter address");
            et_adress.requestFocus();

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
        } else if (spn_bloodgroups.getSelectedItemPosition() == 0) {

            Toast.makeText(getActivity(), "select bloodgroup", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_previouslyDonationDate.getText().toString().trim().length() == 0) {
            et_previouslyDonationDate.setError("enter previous donation date");
            et_previouslyDonationDate.requestFocus();


            return false;
        } else if (!radioButton1True.isChecked() && !radioButton2False.isChecked()) {
            Toast.makeText(getActivity(), "please select willing to donate blood ", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_noofpreviousDonations.getText().toString().trim().length() == 0) {

            et_noofpreviousDonations.setError("enter no of  previous donation ");
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
                    if (response.body().getSaveStatus().equalsIgnoreCase("Success")) {

                        Intent i = new Intent(getActivity(), CitiGuestMainActivity.class);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<DonorregResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void findViews(View root) {
        btn_donorRegNext = root.findViewById(R.id.btn_donorRegNext);
        //  iv_datepickerdateofBirth = root.findViewById(R.id.iv_datepickerdateofBirth);
        //  iv_previouslyDonationDate = root.findViewById(R.id.iv_previouslyDonationDate);
        et_previouslyDonationDate = root.findViewById(R.id.et_previouslyDonationDate);
        et_noofpreviousDonations = root.findViewById(R.id.et_noofpreviousDonations);


        et_name = root.findViewById(R.id.et_name);
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
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(selectedThemeColor);
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));

            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                tv_titledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));

            } else {
                ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                tv_titledonorDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_titleAdressInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_titledonorInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));

            }

        } else {
            ll_donorregForm.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
            tv_titledonorDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_titleAdressInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_titledonorInfo.setTextColor(getResources().getColor(R.color.colorPrimary));
            view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn_donorRegNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }


    }
   /* public void apicallSucceuss() {
        final PrettyDialog dialog = new PrettyDialog(getActivity());
        dialog
                .setTitle("")
                .setMessage("Regitered successfully")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(),CitizendashboardFragment.)
                        startActivity(new Intent(getActivity(), CitizendashboardFragment.get));

                    }
                })
    }
                *//*.addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
                    }
                });
*//*
        dialog.show();
    }*/

}
