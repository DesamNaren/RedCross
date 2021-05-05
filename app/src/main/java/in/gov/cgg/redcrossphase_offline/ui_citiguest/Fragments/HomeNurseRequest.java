package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.databinding.HomeNurseRequestLayoutBinding;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiClient;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.HomeNurseReqResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.MembershipDistAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.MembershipMandalAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.MembershipvillageAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembershipMandalsResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembershipVillagesResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase_offline.utils.CheckInternet;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeNurseRequest extends Fragment implements View.OnClickListener {
    private final List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private final List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private final List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();
    HomeNurseRequestLayoutBinding binding;
    private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            try {
                binding.serviceStartDateRes.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                toCal.set(selectedYear, selectedMonth, selectedDay + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    //CALL MAIN SERVICE
    private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        // the callback received when the user "sets" the Date in the
        // DatePickerDialog
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            try {
                binding.serviceEndDateRes.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    MembershipDistAdaptor adapter;
    MembershipMandalAdaptor mandaladapter;
    MembershipvillageAdaptor villageadapter;
    CustomProgressDialog progressDialog;
    Integer distId = 0, manId = 0, villageID = 0;
    String EMailResult = "", Pincode = "";
    int selectedThemeColor = -1;
    String RELATION = "";
    DatePickerDialog dateDialogStart;
    Calendar cal;
    Calendar destCal;
    DatePickerDialog fromDatePickerDialog, toDatePickerDialog;
    private JsonObject gsonObject;
    private Calendar fromCal, toCal;
    private int from_min_day;
    private int from_min_month;
    private int from_min_year;

    //SERVICE CALL OF MANDALS

    //SERVICE CALL FOR DISTRICTS
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
                        binding.DistrictSpinRes.setAdapter(adapter);


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

    //SERVICE CALL FOR VILLAGES

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
                    binding.MandalSpinRes.setAdapter(mandaladapter);
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
                    binding.TownVillageSpinRes.setAdapter(villageadapter);
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

    //Validate fields
    protected boolean validateFields() {

        if (binding.ApplicantRes.getText().toString().trim().length() == 0) {
            binding.ApplicantRes.setError("Enter Applicant name");
            binding.ApplicantRes.requestFocus();
            return false;
        } else if (binding.onBehalfOffRes.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Select registering on behalf of", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.onBehalfOffRes.getSelectedItem().toString().contains("Others") && binding.OtherRelationRes.getText().toString().length() == 0) {
            binding.OtherRelationRes.setError("Enter relation");
            binding.OtherRelationRes.requestFocus();
            return false;
        } else if (binding.PatientRes.getText().toString().trim().length() == 0) {
            binding.PatientRes.setError("Enter Patient name");
            binding.PatientRes.requestFocus();
            return false;
        } else if (binding.PatientAgeRes.getText().toString().trim().length() == 0) {
            binding.PatientAgeRes.setError("Enter Patient Age");
            binding.PatientAgeRes.requestFocus();
            return false;
        } else if (binding.natureOfDisabilityRes.getText().toString().trim().length() == 0) {
            binding.natureOfDisabilityRes.setError("Enter nature of disability");
            binding.natureOfDisabilityRes.requestFocus();
            return false;
        } else if (binding.serviceStartDateRes.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Enter Start date", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.serviceEndDateRes.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Enter End date", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.MobNumRes.getText().toString().trim().length() == 0) {
            binding.MobNumRes.setError("Enter Mobile number");
            binding.MobNumRes.requestFocus();
            return false;
        } else if (binding.MobNumRes.getText().toString().trim().length() < 10) {
            binding.MobNumRes.setError("Enter valid Mobile number");
            binding.MobNumRes.requestFocus();
            return false;
        } else if (!(binding.MobNumRes.getText().toString().trim().startsWith("9") || binding.MobNumRes.getText().toString().trim().startsWith("8") || binding.MobNumRes.getText().toString().trim().startsWith("7") || binding.MobNumRes.getText().toString().trim().startsWith("6") || binding.MobNumRes.getText().toString().trim().startsWith("5"))) {
            binding.MobNumRes.setError("Enter valid Mobile number");
            binding.MobNumRes.requestFocus();
            return false;
        } else if (EMailResult.length() != 0 && !EMailResult.matches(emailPattern)) {
            binding.EmailRes.setError("Enter valid Email ID");
            binding.EmailRes.requestFocus();
            return false;

        } else if (binding.AddressRes.getText().toString().length() == 0) {
            binding.AddressRes.setError("Enter Address");
            binding.AddressRes.requestFocus();
            return false;
        } else if (binding.DistrictSpinRes.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Select District", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.MandalSpinRes.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Select Mandal", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.TownVillageSpinRes.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Select Village", Toast.LENGTH_LONG).show();
            return false;
        } else if (Pincode.length() != 0 && Pincode.length() < 6) {
            binding.PincodeRes.setError("Enter valid Pincode");
            binding.PincodeRes.requestFocus();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_nurse_request_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Home Nursing");
        progressDialog = new CustomProgressDialog(getActivity());


        fromCal = Calendar.getInstance();
        toCal = Calendar.getInstance();
        from_min_day = fromCal.get(Calendar.DAY_OF_MONTH);
        from_min_month = fromCal.get(Calendar.MONTH);
        from_min_year = fromCal.get(Calendar.YEAR);

        binding.serviceStartDateRes.setOnClickListener(this);
        binding.serviceEndDateRes.setOnClickListener(this);

        cal = Calendar.getInstance();

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross1_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross2_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross3_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross4_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross5_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross6_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross7_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.Mainlayout.setBackgroundResource(R.drawable.redcross8_bg);

                    binding.ApplicantDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.PatientDetails.setTextColor(getResources().getColor(selectedThemeColor));
                    binding.CantactDetails.setTextColor(getResources().getColor(selectedThemeColor));

                    binding.view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    binding.view3.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else {
                    binding.Mainlayout.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    binding.ApplicantDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.PatientDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.CantactDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    binding.view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    binding.view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }
            } else {
                binding.Mainlayout.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                binding.ApplicantDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.PatientDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.CantactDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        callgetDistrictListRequest();
        //Relation SPINNER SELECTION CODE

        binding.onBehalfOffRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.onBehalfOffRes.getSelectedItem().toString().contains("Others")) {
                    binding.otherrelationLay.setVisibility(View.VISIBLE);
                    RELATION = binding.OtherRelationRes.getText().toString().trim();
                } else {
                    binding.otherrelationLay.setVisibility(View.GONE);
                    RELATION = binding.onBehalfOffRes.getSelectedItem().toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //DIST SPINNER SELECTION CODE

        binding.DistrictSpinRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (MembersipDistResponseList.size() > 0) {
                    distId = MembersipDistResponseList.get(i).getDistrictID();
                    //District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {
                        callgetMandalsListRequest("" + distId);
                        binding.mandalSpinLayout.setVisibility(View.VISIBLE);
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

        //Mandal SPINNER SELECTION CODE

        binding.MandalSpinRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembershipMandalsResponseList.size() > 0) {
                    manId = MembershipMandalsResponseList.get(i).getMandalID();
                    //Mandal_View.setText(MembershipMandalsResponseList.get(i).getMandalName());
                    if (distId != 0 && manId != 0) {
                        callgetVillagesListRequest("" + MembershipMandalsResponseList.get(i).getMandalID());
                        Log.e("MANDALID", "====" + MembershipMandalsResponseList.get(i).getMandalID());
                        binding.villageSpinLayout.setVisibility(View.VISIBLE);
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

        //VILLAGE SPINNER SELECTION CODE
        binding.TownVillageSpinRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MembersipVillagesResponseList.size() > 0) {
                    villageID = MembersipVillagesResponseList.get(i).getVillageID();
                    //Village_View.setText(MembersipVillagesResponseList.get(i).getVillageName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    /*    //Datepicker for start Date
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
                //binding.serviceEndDateRes
                binding.serviceStartDateRes.setText(format);
                //ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };


        binding.serviceStartDateRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);
                dateDialogStart = new DatePickerDialog(view.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialogStart.getDatePicker().setMinDate(new Date().getTime());
                dateDialogStart.show();
            }
        });
        //Datepicker for end Date

        final DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                destCal = Calendar.getInstance();
                destCal.set(Calendar.YEAR, year);
                destCal.set(Calendar.MONTH, month);
               destCal.set(Calendar.DAY_OF_MONTH, day);
                destCal.add(Calendar.DAY_OF_MONTH, 1);
                String format = new SimpleDateFormat("dd/MM/yyyy").format(destCal.getTime());
                //binding.serviceEndDateRes
                binding.serviceEndDateRes.setText(format);
                //ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };
        binding.serviceEndDateRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), datePickerListener2, mYear, mMonth, mDay);
               // cal.add(Calendar.DATE,1);
                dateDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dateDialog.show();
            }
        });
*/

        //SERVICE CALL
        binding.Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckInternet.isOnline(getActivity())) {
                    EMailResult = binding.EmailRes.getText().toString().trim();
                    Pincode = binding.PincodeRes.getText().toString().trim();
                    if (validateFields()) {
                        callsubmitHomeNurseRequest();

                    } else {

                    }
                }
            }
        });

        return binding.getRoot();
    }

    private void callsubmitHomeNurseRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject object = new JSONObject();
        try {

            object.put("applicantName", binding.ApplicantRes.getText().toString());
            object.put("onBehalfOf", RELATION);
            object.put("patientName", binding.PatientRes.getText().toString());
            object.put("patientAge", binding.PatientAgeRes.getText().toString());
            object.put("natureOfDisability", binding.natureOfDisabilityRes.getText().toString().trim());
            object.put("serviceStartDate", binding.serviceStartDateRes.getText().toString());
            object.put("serviceEndDate", binding.serviceEndDateRes.getText().toString());
            object.put("phoneNo", binding.MobNumRes.getText().toString());
            object.put("email", EMailResult);
            object.put("address", binding.AddressRes.getText().toString());
            object.put("districts", Integer.toString(distId));
            object.put("mandals", Integer.toString(manId));
            object.put("village", Integer.toString(villageID));
            object.put("pincode", Pincode);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
            Log.e("sent_json", object.toString());

            Call<HomeNurseReqResponse> call = apiInterface.submitHomeNurseRequest(gsonObject);
            call.enqueue(new Callback<HomeNurseReqResponse>() {
                @Override
                public void onResponse(Call<HomeNurseReqResponse> call, final Response<HomeNurseReqResponse> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {

                        Log.d("response", "onResponse: url" + response.body().getSaveStatus());
                        if (response.body().getSaveStatus().contains("Success")) {
                            final PrettyDialog dialog = new PrettyDialog(getActivity());
                            dialog
                                    .setTitle("Success")
                                    .setMessage("Successfully generated Nurse ID")
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

                    } else {
                        final PrettyDialog dialog = new PrettyDialog(getActivity());
                        dialog
                                .setTitle("Failed")
                                .setMessage("Submission failed")
                                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<HomeNurseReqResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serviceStartDate_res:
//                if (!toDateET.getText().toString().equalsIgnoreCase("To Date")) {
//                    toDateET.setText("To Date");
//                }
                onCreateDialog(R.id.serviceStartDate_res);
                fromDatePickerDialog.show();
                break;
            case R.id.serviceEndDate_res:
                if (binding.serviceStartDateRes.getText().toString().length() != 0) {
                    onCreateDialog(R.id.serviceEndDate_res);
                    toDatePickerDialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select the start date first.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void onCreateDialog(int id) {

        switch (id) {
            case R.id.serviceStartDate_res:
                fromDatePickerDialog = new DatePickerDialog(getActivity(), fromDatePickerListener, from_min_year, from_min_month, from_min_day);
                Calendar cal1 = Calendar.getInstance();
                fromDatePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
                return;
            case R.id.serviceEndDate_res:
                toDatePickerDialog = new DatePickerDialog(getActivity(), toDatePickerListener, from_min_year, from_min_month, from_min_day);
                toDatePickerDialog.getDatePicker().setMinDate(toCal.getTimeInMillis());
        }
    }
}
