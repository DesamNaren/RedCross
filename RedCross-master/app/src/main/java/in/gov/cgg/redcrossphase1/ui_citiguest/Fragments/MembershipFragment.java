package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDetailsAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDetailsSpinnerAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_Bean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_spinner_Bean;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MembershipFragment extends Fragment {
    RecyclerView recyclerView;
    MembershipDetailsAdaptor adapter;
    MembershipDetailsSpinnerAdaptor spinneradapter;
    LinearLayout ll_lifetime_membership;
    int selectedThemeColor = -1;
    LinearLayout Parent_layout, ll_LTM_types;
    Button Proceed;
    Spinner TypeSpinner;
    private FragmentActivity c;
    CustomProgressDialog progressDialog;
    private List<MembershipDetails_Bean> callgetMembershipTypesList = new ArrayList<>();
    private List<MembershipDetails_spinner_Bean> callgetMembershipTypesSpinner = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.membership_selection_fragment, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Membership");


        c = getActivity();
        findViews(root);

        progressDialog = new CustomProgressDialog(getActivity());
        Parent_layout = root.findViewById(R.id.Parent_layout);
        ll_LTM_types = root.findViewById(R.id.ll_LTM_types);
        Proceed = root.findViewById(R.id.Proceed_bt);
        TypeSpinner = root.findViewById(R.id.type_spinner);
        recyclerView = root.findViewById(R.id.rv_Membership_Types);
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TypeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(c, "Select Membership Type", Toast.LENGTH_SHORT).show();
                } else {

                    Fragment fragment = new MembershipRegFormFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment, MembershipRegFormFragment.class.getSimpleName());
                    transaction.addToBackStack(null);
                    transaction.commitAllowingStateLoss();
                }
            }
        });

        TypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (callgetMembershipTypesSpinner.size() > 0) {
                    GlobalDeclaration.SELECTEDtype = callgetMembershipTypesSpinner.get(i).getMembershipType();
                    GlobalDeclaration.Selection_MEMbership_type = callgetMembershipTypesSpinner.get(i).getId();
                }
                Log.e("ID", "===" + GlobalDeclaration.Selection_MEMbership_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross2_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        callgetMembershipTypesForList();
        callgetMembershipTypesForSpinner();

        return root;
    }

    private void findViews(View root) {
        ll_lifetime_membership = root.findViewById(R.id.ll_lifetime_membership);
    }

    private void callgetMembershipTypesForList() {

        try {
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            callgetMembershipTypesList.clear();

            Call<List<MembershipDetails_Bean>> call = apiInterface.getMembershipTypes();
            Log.e("  url", call.request().url().toString());

            call.enqueue(new Callback<List<MembershipDetails_Bean>>() {
                @Override
                public void onResponse(Call<List<MembershipDetails_Bean>> call, Response<List<MembershipDetails_Bean>> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        Log.d("Activity ", "Response = " + response.body());
                        callgetMembershipTypesList.addAll(response.body());

                        adapter = new MembershipDetailsAdaptor(callgetMembershipTypesList, c, selectedThemeColor);
                        recyclerView.setAdapter(adapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                }

                @Override
                public void onFailure(Call<List<MembershipDetails_Bean>> call, Throwable t) {

                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void callgetMembershipTypesForSpinner() {

        try {
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            callgetMembershipTypesSpinner.clear();
            MembershipDetails_spinner_Bean membership_spin_res = new MembershipDetails_spinner_Bean();
            membership_spin_res.setId(0);
            membership_spin_res.setMembershipType("Select Membership Type");
            callgetMembershipTypesSpinner.add(membership_spin_res);

            Call<List<MembershipDetails_spinner_Bean>> call = apiInterface.getMembershipTypesforSpin();
            Log.e("  url", call.request().url().toString());

            call.enqueue(new Callback<List<MembershipDetails_spinner_Bean>>() {
                @Override
                public void onResponse(Call<List<MembershipDetails_spinner_Bean>> call, Response<List<MembershipDetails_spinner_Bean>> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        Log.d("Activity ", "Response = " + response.body());
                        callgetMembershipTypesSpinner.addAll(response.body());
                        spinneradapter = new MembershipDetailsSpinnerAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, callgetMembershipTypesSpinner);
                        //spinneradapter = new MembershipDetailsSpinnerAdaptor(callgetMembershipTypesSpinner, c, selectedThemeColor);
                        TypeSpinner.setAdapter(spinneradapter);

                    }
                }

                @Override
                public void onFailure(Call<List<MembershipDetails_spinner_Bean>> call, Throwable t) {

                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}







