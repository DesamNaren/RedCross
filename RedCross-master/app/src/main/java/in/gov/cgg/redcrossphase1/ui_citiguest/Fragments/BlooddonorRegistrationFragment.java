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
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlooddonorRegistrationFragment extends Fragment {
    CustomProgressDialog progressDialog;
    Button btn_donorRegNext;
    ImageView iv_datepickerdateofBirth, iv_previouslyDonationDate;
    EditText datePicker_dateofBirth, et_previouslyDonationDate;
    EditText et_name, et_fathersName, et_occupation, et_mobileNumber, et_email, et_office, et_adress, et_pincode, et_donatetype;
    Spinner spn_gender, spn_education, spn_married, spn_bloodgroups;
    Spinner spn_district, spn_mandal, spn_village;
    RadioGroup RG_willingtodonateblood;
    RadioButton radioButton1True, radioButton2False;
    String name, fratherName, occupation, mobileNumber, email, officeName, address, pincode, donationType, dob, previoslydonatedDate, noofpreviousDonations;
    String gender, education, married, bloodgroups;
    Integer distId = 0, manId = 0, villageID = 0;
    CitizenDonarRequest request;
    MembershipDistAdaptor adapter;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    List<String> mBloodGroupIdsList;
    List<String> mGenderIdsList;
    List<String> mEducationIdsList;
    List<String> mMarriedIdsList;
    String mBloodGroupId;
    String mGenderId;
    String mEducationId;
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


        //radiobutton values
        // get selected radio button from radioGroup
        //   int selectedId = radioGroup_willingtodonateblood.getCheckedRadioButtonId();
        // Log.d("tag","selectId" + selectedId);


        // find the radiobutton by returned id
        //  radioButton1True = (RadioButton) root.findViewById(selectedId);
        //  radioButton2False = (RadioButton) root.findViewById(selectedId);
        /*Log.d("tag","radioButton1" + radioButton1True);
        Log.d("tag","radioButton1.getText()" + radioButton1True.getText());
        Log.d("tag","radioButton2" + radioButton2False);
        Log.d("tag","radioButton2.getText()" + radioButton2False.getText());*/

     /*   Toast.makeText(getActivity(),
                radioButton1True.getText(), Toast.LENGTH_SHORT).show();*/


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
                donationType = et_donatetype.getText().toString();
                dob = datePicker_dateofBirth.getText().toString();
                previoslydonatedDate = et_previouslyDonationDate.getText().toString();
                noofpreviousDonations = et_noofpreviousDonations.getText().toString();
                //static spinner values

                //Save blood donor data(registration)

                if (validateFields()) {
                    calldonorRegistarion();
                }



            }
        });

        loadGenderSpinner();
        loadBloodGroupSpinner();
        loadEducationSpinner();
        loadMarriedstatusSpinner();


        RG_willingtodonateblood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
               /* RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
*/
                int selectedId = RG_willingtodonateblood.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = root.findViewById(selectedId);

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


                Calendar userAge = new GregorianCalendar(year, month, day);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(userAge)) {
                    Toast.makeText(getActivity(), "age not valid ", Toast.LENGTH_SHORT).show();
                    datePicker_dateofBirth.setText("");
                    et_previouslyDonationDate.setText("");
                } else {
                    datePicker_dateofBirth.setText(format);
                    et_previouslyDonationDate.setText(format);
                    //  SHOW_ERROR_MESSAGE;
                }

                //  ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };
        iv_datepickerdateofBirth.setOnClickListener(new View.OnClickListener() {
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

        iv_previouslyDonationDate.setOnClickListener(new View.OnClickListener() {
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
            setFocus(et_name, "enter your  name");
            return false;
        } else if (et_fathersName.getText().toString().trim().length() == 0) {
            setFocus(et_fathersName, "enter  father or husband name");
            return false;

        } else if (datePicker_dateofBirth.getText().toString().trim().length() == 0) {
            setFocus(datePicker_dateofBirth, "enter your date of birth");
            return false;
        } else if (spn_gender.getSelectedItemPosition() == 0) {
            errorSpinner(spn_gender, "select gender");
            return false;
        } else if (spn_education.getSelectedItemPosition() == 0) {
            errorSpinner(spn_education, "select education");
            return false;
        } else if (et_occupation.getText().toString().trim().length() == 0) {
            setFocus(et_occupation, "enter occupation");
            return false;
        } else if (spn_married.getSelectedItemPosition() == 0) {
            errorSpinner(spn_married, "select marriage status");
            return false;
        } else if (et_mobileNumber.getText().toString().trim().length() == 0) {
            //  Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_LONG).show();
            setFocus(et_mobileNumber, "enter mobile number");
            return false;
        } else if (!(et_mobileNumber.getText().toString().trim().startsWith("9") || et_mobileNumber.getText().toString().trim().startsWith("8") || et_mobileNumber.getText().toString().trim().startsWith("7") || et_mobileNumber.getText().toString().trim().startsWith("6") || et_mobileNumber.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(getActivity(), "Enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;

        } else if (et_email.getText().toString().trim().length() == 0) {
            setFocus(et_email, "enter email");
            return false;

        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getActivity(), "Enter valid Email ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_office.getText().toString().trim().length() == 0) {
            setFocus(et_office, "enter office name");
            return false;
        } else if (et_adress.getText().toString().trim().length() == 0) {
            setFocus(et_adress, "enter address");
            return false;
        }
//        else if (spn_district.getSelectedItem().toString().contains("select")) {
//            errorSpinner(spn_district, "select district");
//            return false;
//        }

        else if (!distValidation) {
            Toast.makeText(getActivity(), "select district ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mandalValid) {
            Toast.makeText(getActivity(), "select mandal ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!villageValid) {
            Toast.makeText(getActivity(), "select village", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_pincode.getText().toString().trim().length() == 0) {
            setFocus(et_pincode, "enter pincode");
            return false;
        } else if (et_pincode.getText().toString().length() < 6) {
            Toast.makeText(getActivity(), "Enter valid Pincode", Toast.LENGTH_LONG).show();
            return false;
        } else if (spn_bloodgroups.getSelectedItemPosition() == 0) {
            errorSpinner(spn_bloodgroups, "select bloodgroup");
            return false;
        } else if (et_donatetype.getText().toString().trim().length() == 0) {
            setFocus(et_donatetype, "enter donation type");
            return false;
        } else if (et_previouslyDonationDate.getText().toString().trim().length() == 0) {
            setFocus(et_previouslyDonationDate, "Enter previous donation date");
            return false;
        } else if (et_noofpreviousDonations.getText().toString().trim().length() == 0) {
            setFocus(et_noofpreviousDonations, "enter previous donation ");
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
        request.setEducation(mEducationId);
        request.setOccupation(occupation);
        request.setMarried(mMarriedId);
        request.setPhoneNo(mobileNumber);
        request.setEmail(email);
        request.setOffice(officeName);
        request.setAddress(address);
        request.setDistricts("" + distId);
        request.setMandals("" + manId);
        request.setVillage("" + villageID);
        request.setPincode(pincode);
        request.setBloodGroup(mBloodGroupId);
        request.setDonateType(donationType);
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
        iv_datepickerdateofBirth = root.findViewById(R.id.iv_datepickerdateofBirth);
        iv_previouslyDonationDate = root.findViewById(R.id.iv_previouslyDonationDate);
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


    }


}
