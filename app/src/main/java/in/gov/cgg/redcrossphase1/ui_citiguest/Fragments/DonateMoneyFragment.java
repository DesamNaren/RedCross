package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentDonatemoneyBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDistAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipMandalAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipvillageAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonateMoneyRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.DonateMoneyResponse;
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

public class DonateMoneyFragment extends Fragment {

    private final List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();
    private final List<MembershipMandalsResponse> MembershipMandalsResponseList = new ArrayList<>();
    private final List<MembershipVillagesResponse> MembersipVillagesResponseList = new ArrayList<>();
    FragmentDonatemoneyBinding binding;
    CustomProgressDialog progressDialog;
    String name, mobileNumber, address, email, pincode, amount, remarks;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    private MembershipDistAdaptor adapter;
    private MembershipMandalAdaptor mandaladapter;
    private MembershipvillageAdaptor villageadapter;
    private Integer distId;
    private boolean distValidation;
    private Integer manId;
    private boolean mandalValid;
    private Integer villageID;
    private boolean villageValid;
    private Integer donationdistId;
    private boolean donationdistValidation;
    private int selectedThemeColor = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_donatemoney, container, false);


        progressDialog = new CustomProgressDialog(getActivity());

        if (getActivity() != null) {
            getActivity().setTitle(getResources().getString(R.string.online_donation));
        }

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                binding.tvTitledonorDetails.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTitleAdressInfo.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvTitledonorInfo.setTextColor(getResources().getColor(selectedThemeColor));
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.ll.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.ll.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.ll.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.ll.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.ll.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.ll.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.ll.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.ll.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    binding.ll.setBackgroundResource(R.drawable.redcross6_bg);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            binding.ll.setBackgroundResource(R.drawable.redcross6_bg);

        }

        if (CheckInternet.isOnline(getActivity())) {

            callgetDistrictListRequest();

        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        //disrtrict spinner
        binding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (MembersipDistResponseList.size() > 0) {
                    distId = MembersipDistResponseList.get(i).getDistrictID();
                    //   District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {
                        if (CheckInternet.isOnline(getActivity())) {

                            binding.mandalSpinLay.setVisibility(View.VISIBLE);
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
                        binding.spnMandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        binding.spnVillage.setAdapter(villageadapter);

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

                        if (CheckInternet.isOnline(getActivity())) {
                            binding.villageSpinLay.setVisibility(View.VISIBLE);
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
                        binding.spnVillage.setAdapter(villageadapter);
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

        //donation district spinner
        binding.spnDonatefordistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (MembersipDistResponseList.size() > 0) {
                    donationdistId = MembersipDistResponseList.get(i).getDistrictID();
                    //   District_View.setText(MembersipDistResponseList.get(i).getDistrictName());
                    if (distId != 0) {
                        if (CheckInternet.isOnline(getActivity())) {
                            donationdistValidation = true;

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ediitextvalues
                name = binding.etName.getText().toString();
                mobileNumber = binding.etMobileNumber.getText().toString();
                email = binding.etEmail.getText().toString();
                address = binding.etAdress.getText().toString();
                pincode = binding.etPincode.getText().toString();
                amount = binding.etAmount.getText().toString();
                remarks = binding.etDonationrmarks.getText().toString();

                //Save blood donor data(registration)
                if (CheckInternet.isOnline(getActivity())) {
                    if (validateFields()) {
                        callDonateMoney();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }


            }
        });


        return binding.getRoot();
    }

    private void callDonateMoney() {

        DonateMoneyRequest request = new DonateMoneyRequest();
        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        try {
            request.setName(name);
            request.setPhoneNo(mobileNumber);
            request.setEmail(email);
            request.setAddress(address);
            request.setDistricts(String.valueOf(distId));
            request.setMandals(String.valueOf(manId));
            request.setVillage(String.valueOf(villageID));
            request.setPincode(pincode);
            request.setDonationDistrict(String.valueOf(donationdistId));
            request.setDonationAmount(String.valueOf(amount));
            request.setDonationAmountInwords("");
            request.setRemarks(remarks);
            request.setRequestType("mobile");


            Call<DonateMoneyResponse> call = apiInterface.saveMoneyDonationDetails(request);

            Log.e("money url", call.request().url().toString());

            Gson g = new Gson();

            String s = g.toJson(request);

            Log.e("json", s);

            call.enqueue(new Callback<DonateMoneyResponse>() {
                @Override
                public void onResponse(Call<DonateMoneyResponse> call, final Response<DonateMoneyResponse> response) {

                    progressDialog.dismiss();
                    if (response.body() != null && response.body().getStatus().contains("Success")) {

                        GlobalDeclaration.Paymenturl = response.body().getPaymentGatewayUrl();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setMessage(getResources().getString(R.string.success_submited));
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        GlobalDeclaration.encrpyt = response.body().getPaymentRequest();
                                        Log.d("responseurl", "onResponse: url" + response.body().getPaymentRequest());


                                        Fragment fragment = new DonateMoneyPaymentFragment();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        transaction.replace(R.id.nav_host_fragment, fragment, DonateMoneyPaymentFragment.class.getSimpleName());
                                        transaction.addToBackStack(null);
                                        transaction.commitAllowingStateLoss();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    } else if (response.body() != null && response.body().getStatus().contains("Failed")) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<DonateMoneyResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }


    }

    protected boolean validateFields() {

        if (binding.etName.getText().toString().trim().length() == 0) {

            binding.etName.setError(getResources().getString(R.string.enter_your_name));
            binding.etName.requestFocus();

            return false;
        } else if (binding.etMobileNumber.getText().toString().trim().length() == 0) {
            binding.etMobileNumber.setError(getResources().getString(R.string.enter_mobile_number));
            binding.etMobileNumber.requestFocus();

            return false;
        } else if (binding.etMobileNumber.getText().toString().trim().length() < 10) {
            binding.etMobileNumber.setError(getResources().getString(R.string.enter_validmno));
            binding.etMobileNumber.requestFocus();

            return false;
        } else if (!(binding.etMobileNumber.getText().toString().trim().startsWith("9") || binding.etMobileNumber.getText().toString().trim().startsWith("8") ||
                binding.etMobileNumber.getText().toString().trim().startsWith("7") ||
                binding.etMobileNumber.getText().toString().trim().startsWith("6") ||
                binding.etMobileNumber.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_validmno), Toast.LENGTH_LONG).show();
            return false;

        } else if (binding.etEmail.getText().toString().trim().length() == 0 &&
                !binding.etEmail.getText().toString().trim().matches(emailPattern)) {
            binding.etEmail.setError("Enter valid Email ID");
            binding.etEmail.requestFocus();
            return false;
        } else if (binding.etAdress.getText().toString().trim().length() == 0) {
            binding.etAdress.setError(getResources().getString(R.string.enter_address));
            binding.etAdress.requestFocus();
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
        } else if (binding.etPincode.getText().toString().trim().length() == 0) {
            binding.etAdress.setError(getResources().getString(R.string.enter_pincode));
            binding.etAdress.requestFocus();
            return false;
        } else if (binding.etPincode.getText().toString().trim().length() == 0) {
            binding.etAmount.setError(getResources().getString(R.string.enter_donation_amount));
            binding.etAmount.requestFocus();
            return false;
        } else if (!donationdistValidation) {
            Toast.makeText(getActivity(), getResources().getString(R.string.selet_district_whichstate), Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etDonationrmarks.getText().toString().trim().length() == 0) {
            binding.etDonationrmarks.setError(getResources().getString(R.string.enter_donation_remarks));
            binding.etDonationrmarks.requestFocus();
            return false;
        }
        return true;
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
                        adapter = new MembershipDistAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        binding.spnDistrict.setAdapter(adapter);

                        //donation district

                        adapter = new MembershipDistAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        binding.spnDonatefordistrict.setAdapter(adapter);

                    } else {
                        MembershipMandalsResponseList.clear();
                        MembershipMandalsResponse membershipmandResponse = new MembershipMandalsResponse();
                        membershipmandResponse.setMandalID(0);
                        membershipmandResponse.setMandalName(getResources().getString(R.string.Select_Mandal));
                        MembershipMandalsResponseList.add(membershipmandResponse);
                        mandaladapter = new MembershipMandalAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembershipMandalsResponseList);
                        binding.spnMandal.setAdapter(mandaladapter);
                        MembersipVillagesResponseList.clear();
                        MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                        membershipVillagesResponse.setVillageID(0);
                        membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                        MembersipVillagesResponseList.add(membershipVillagesResponse);
                        villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                        binding.spnVillage.setAdapter(villageadapter);
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
                    binding.spnVillage.setAdapter(villageadapter);
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
                    binding.spnMandal.setAdapter(mandaladapter);
                    //goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    MembersipVillagesResponseList.clear();
                    MembershipVillagesResponse membershipVillagesResponse = new MembershipVillagesResponse();
                    membershipVillagesResponse.setVillageID(0);
                    membershipVillagesResponse.setVillageName(getResources().getString(R.string.Select_Village));
                    MembersipVillagesResponseList.add(membershipVillagesResponse);
                    villageadapter = new MembershipvillageAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipVillagesResponseList);
                    binding.spnVillage.setAdapter(villageadapter);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                    builder1.setMessage(getResources().getString(R.string.exitmsg));
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
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

                    return true;
                }
                return false;
            }
        });
    }
}
