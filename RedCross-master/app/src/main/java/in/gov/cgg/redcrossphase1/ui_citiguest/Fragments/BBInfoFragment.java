package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.BBAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.BDonorAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodDonorResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.DonorsMapsActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.MapsActivity;
import in.gov.cgg.redcrossphase1.utils.AppConstants;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class BBInfoFragment extends LocBaseFragment {
    private CustomProgressDialog progressDialog;
    private RecyclerView bb_rv;
    private TextView no_data_tv;
    private TextView tv_total;
    private Button iv_switch;
    private BBAdapter bbAdapter;
    private BDonorAdapter bDonorAdapter;
    private int cnt = 0;
    private Spinner spinner_bg, spinner_bg_donor, spinner_bg_dist;
    private LinearLayout ll_donor;
    private ArrayList<eRaktkoshResponseBean> mainList;
    private ArrayList<BloodDonorResponse> bloodDonorResponseMainList;
    private androidx.appcompat.widget.SearchView searchView;
    private Button btn_blood_banks, btn_blood_donors;
    private int spinner_position = 0;
    private String fromType;

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private int selectedThemeColor = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bb, container, false);
        progressDialog = new CustomProgressDialog(getActivity());

        if (getActivity() != null)
            getActivity().setTitle("Blood Banks Info");

        setHasOptionsMenu(true);

        bbAdapter = new BBAdapter();
        bDonorAdapter = new BDonorAdapter();
        mainList = new ArrayList<>();
        bloodDonorResponseMainList = new ArrayList<>();
        bb_rv = root.findViewById(R.id.bb_rv);
        no_data_tv = root.findViewById(R.id.no_data_tv);
        iv_switch = root.findViewById(R.id.iv_switch);
        tv_total = root.findViewById(R.id.tv_total);
        spinner_bg = root.findViewById(R.id.spinner_bg);
        spinner_bg_donor = root.findViewById(R.id.spinner_bg_donor);
        spinner_bg_dist = root.findViewById(R.id.spinner_bg_dist);
        ll_donor = root.findViewById(R.id.ll_donor);
        btn_blood_banks = root.findViewById(R.id.btn_blood_banks);
        btn_blood_donors = root.findViewById(R.id.btn_blood_donors);

        iv_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    if (bbAdapter != null) {
                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("E_RAKTAKOSH_DATA", bbAdapter.getFilteredData());
                        bundle.putString("FROM_CLASS", "ALL");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), DonorsMapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("DONORS_DATA", bDonorAdapter.getFilteredData());
                        bundle.putString("FROM_CLASS", "ALL");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });

        getActivity().registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            setThemes();

        } catch (Exception e) {
            e.printStackTrace();
        }

        spinner_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    spinner_position = position;
                    if (position != 0) {
                        String selVal = spinner_bg.getSelectedItem().toString();
                        loadBBanksFilter(selVal);
                    } else {
                        if (bbAdapter != null) {
                            bb_rv.setVisibility(View.VISIBLE);
                            no_data_tv.setVisibility(View.GONE);

                            bbAdapter = new BBAdapter(getActivity(), mainList);
                            bb_rv.setAdapter(bbAdapter);
                            bbAdapter.notifyDataSetChanged();
                            tv_total.setVisibility(View.VISIBLE);
                            tv_total.setText("Total Records: " + mainList.size());

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_bg_donor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    spinner_position = position;
                    if (position != 0) {
                        String distVal = spinner_bg_dist.getSelectedItem().toString();
                        String bgVal = spinner_bg_donor.getSelectedItem().toString();

                        if (bgVal.contains("Select") && distVal.contains("Select")) {
                            loadAllDonorsFilterData();


                        } else if (!bgVal.contains("Select") && distVal.contains("Select")) {
                            loadBDonorsFilterData(bgVal, distVal);

                        } else if (bgVal.contains("Select") && !distVal.contains("Select")) {
                            loadBDonorsFilterData(bgVal, distVal);
                        }
                    } else {
                        if (bDonorAdapter != null) {
                            bb_rv.setVisibility(View.VISIBLE);
                            no_data_tv.setVisibility(View.GONE);
                            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                            bb_rv.setAdapter(bDonorAdapter);
                            bDonorAdapter.notifyDataSetChanged();
                            tv_total.setVisibility(View.VISIBLE);
                            tv_total.setText("Total Records: " + bloodDonorResponseMainList.size());
                            sortBDonorData(bloodDonorResponseMainList);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_bg_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String distVal = spinner_bg_dist.getSelectedItem().toString();
                    String bgVal = spinner_bg_donor.getSelectedItem().toString();

                    if (bgVal.contains("Select") && distVal.contains("Select")) {
                        loadAllDonorsFilterData();
                    } else if (!bgVal.contains("Select") && distVal.contains("Select")) {
                        loadBDonorsFilterData(bgVal, distVal);
                    } else if (bgVal.contains("Select") && !distVal.contains("Select")) {
                        loadBDonorsFilterData(bgVal, distVal);
                    } else {
                        loadBDonorsFilterData(bgVal, distVal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_blood_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bDonorAdapter = null;
                searchView.setQuery("", false);
                searchView.setIconified(true);
                spinner_bg.setSelection(spinner_position);

                ll_donor.setVisibility(View.GONE);
                spinner_bg.setVisibility(View.VISIBLE);
                tv_total.setVisibility(View.GONE);
                bb_rv.setAdapter(null);
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));

                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.black));

                if (mCurrentLocation != null) {
                    if (CheckInternet.isOnline(getActivity())) {
                        bbAdapter = new BBAdapter(getActivity(), mainList);
                        callERaktakoshRequest(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), spinner_position);
//                        callERaktakoshRequest(17.419703, 78.460390, spinner_position); //Rajbhavan
//                        callERaktakoshRequest(17.504685, 78.521757, spinner_position); //Rastrapathi Nilayam
                    } else {
                        if (mainList != null && mainList.size() > 0) {
                            spinner_bg.setSelection(0);
                            tv_total.setText("Total Records: " + mainList.size());
                            bb_rv.setVisibility(View.VISIBLE);
                            no_data_tv.setVisibility(View.GONE);

                            bbAdapter = new BBAdapter(getActivity(), mainList);
                            bb_rv.setAdapter(bbAdapter);
                            bbAdapter.notifyDataSetChanged();
                            tv_total.setVisibility(View.VISIBLE);
                            iv_switch.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();

                    }
                }
                setThemes();

            }
        });

        btn_blood_donors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbAdapter = null;
                spinner_bg_donor.setSelection(spinner_position);

                spinner_bg.setVisibility(View.GONE);
                ll_donor.setVisibility(View.VISIBLE);
                tv_total.setVisibility(View.GONE);
                iv_switch.setVisibility(View.GONE);
                bb_rv.setAdapter(null);
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));

                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.black));


                if (mCurrentLocation != null) {
                    if (CheckInternet.isOnline(getActivity())) {
                        callBloodDonorsRequest(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), spinner_position);
                    } else {
                        if (bloodDonorResponseMainList != null && bloodDonorResponseMainList.size() > 0) {
                            spinner_bg_donor.setSelection(0);
                            tv_total.setText("Total Records: " + bloodDonorResponseMainList.size());
                            bb_rv.setVisibility(View.VISIBLE);
                            no_data_tv.setVisibility(View.GONE);

                            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                            bb_rv.setAdapter(bDonorAdapter);
                            bDonorAdapter.notifyDataSetChanged();
                            tv_total.setVisibility(View.VISIBLE);
                            iv_switch.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                setThemes_1();

            }
        });


        if (getArguments() != null) {
            fromType = getArguments().getString("FROM_TYPE");
            if (fromType != null && fromType.equalsIgnoreCase("BLOOD_DONOR")) {
                callInitBloodDonor();
                setThemes_1();
            } else {
                callBloodBanks();
                setThemes();

            }
        } else {
            callBloodBanks();
        }

        return root;
    }

    private void setThemes() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_1));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));

            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_3));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_4));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_5));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_6));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_7));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.redcroosbg_8));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
            } else {
                btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
                btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.black));
                spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));

            }

        } else {
            btn_blood_banks.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
            btn_blood_banks.setTextColor(getResources().getColor(R.color.white));
            btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            btn_blood_donors.setTextColor(getResources().getColor(R.color.black));
            spinner_bg.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));

        }
    }

    private void setThemes_1() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_1));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));

            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_3));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_4));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_5));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_6));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_7));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.redcroosbg_8));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
            } else {
                btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
                btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_blood_banks.setTextColor(getResources().getColor(R.color.black));
                spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));

            }

        } else {
            btn_blood_donors.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
            btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
            btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            btn_blood_banks.setTextColor(getResources().getColor(R.color.black));
            spinner_bg_donor.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            iv_switch.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));


        }
    }


    private void callInitBloodDonor() {
        bbAdapter = null;
        spinner_bg.setVisibility(View.GONE);
        ll_donor.setVisibility(View.VISIBLE);

        btn_blood_donors.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
        btn_blood_banks.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));

        btn_blood_donors.setTextColor(getResources().getColor(R.color.white));
        btn_blood_banks.setTextColor(getResources().getColor(R.color.black));

        if (!fromType.equalsIgnoreCase("BLOOD_DONOR")) {
            setThemes_1();
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                if (mCurrentLocation != null) {
                    cnt++;
                    if (cnt == 1) {
                        Location location = mCurrentLocation;
                        callBloodDonorsRequest(location.getLatitude(), location.getLongitude(), spinner_position);
                    }
                }
            }
        };


    }

    private void callBloodBanks() {
        if (getActivity() != null) {
            if (CheckInternet.isOnline(getActivity())) {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                mLocationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        mCurrentLocation = locationResult.getLastLocation();
                        if (mCurrentLocation != null) {
                            cnt++;
                            if (cnt == 1) {
                                Location location = mCurrentLocation;
                                callERaktakoshRequest(location.getLatitude(), location.getLongitude(), spinner_position);
//                                callERaktakoshRequest(17.419703, 78.460390, spinner_position); //Rajbhavan
//                                callERaktakoshRequest(17.504685, 78.521757, spinner_position); //Rastrapathi Nilayam
                            }
                        }
                    }
                };

            } else {
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Context is null, Please try again", Toast.LENGTH_LONG).show();
        }
    }

    private void loadBBanksFilter(String selVal) {
        if (bbAdapter != null) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }

            bbAdapter = new BBAdapter(getActivity(), mainList);
            ArrayList<eRaktkoshResponseBean> arrayList = bbAdapter.getBBFilter(selVal);

            if (arrayList != null && arrayList.size() > 0) {
                bb_rv.setVisibility(View.VISIBLE);
                no_data_tv.setVisibility(View.GONE);
                tv_total.setVisibility(View.VISIBLE);
                tv_total.setText("Filtered Records: " + arrayList.size());
                bbAdapter = new BBAdapter(getActivity(), arrayList);
                bbAdapter.notifyDataSetChanged();
                bb_rv.setAdapter(bbAdapter);
                bb_rv.smoothScrollToPosition(0);

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } else {
                tv_total.setText("Filtered Records: " + 0);
                bb_rv.setVisibility(View.GONE);
                no_data_tv.setVisibility(View.VISIBLE);

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }

    }

    private void loadBDonorsFilterData(String bgVal, String distVal) {
        if (bDonorAdapter != null) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
            ArrayList<BloodDonorResponse> arrayList = bDonorAdapter.getBDonorFilter(bgVal + "_" + distVal);

            if (arrayList != null && arrayList.size() > 0) {
                bb_rv.setVisibility(View.VISIBLE);
                no_data_tv.setVisibility(View.GONE);
                tv_total.setVisibility(View.VISIBLE);
                tv_total.setText("Filtered Records: " + arrayList.size());
                bDonorAdapter = new BDonorAdapter(getActivity(), arrayList);
                bDonorAdapter.notifyDataSetChanged();
                bb_rv.setAdapter(bDonorAdapter);
                bb_rv.smoothScrollToPosition(0);

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            } else {
                tv_total.setText("Filtered Records: " + 0);
                bb_rv.setVisibility(View.GONE);
                no_data_tv.setVisibility(View.VISIBLE);

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }

    private void loadAllDonorsFilterData() {
        if (bDonorAdapter != null) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

            }, 2000);

            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
            bDonorAdapter.notifyDataSetChanged();
            bb_rv.setAdapter(bDonorAdapter);
            tv_total.setVisibility(View.VISIBLE);
            tv_total.setText("Total Records: " + bloodDonorResponseMainList.size());
        }
    }

    private void callERaktakoshRequest(final Double lat, final Double lng, final int bank_spinner_position) {
        try {

            mainList.clear();
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }

            ApiInterface apiInterface = ApiClient.getERaktakoshClient().create(ApiInterface.class);
            Call<ArrayList<eRaktkoshResponseBean>> call =
                    apiInterface.callERaktakoshInfo(AppConstants.STATE_ID, AppConstants.STATE_CODE, AppConstants.ERAKTAKOSH_KEY);
            call.enqueue(new Callback<ArrayList<eRaktkoshResponseBean>>() {
                @Override
                public void onResponse(Call<ArrayList<eRaktkoshResponseBean>> call, Response<ArrayList<eRaktkoshResponseBean>> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful() && response.body() != null) {
                        bb_rv.setVisibility(View.VISIBLE);
                        iv_switch.setVisibility(View.VISIBLE);

                        no_data_tv.setVisibility(View.GONE);
                        ArrayList<eRaktkoshResponseBean> list = response.body();
                        if (list.size() > 0) {
                            list.remove(0);


                            try {
                                for (int x = 0; x < list.size(); x++) {

                                    Location location = new Location("Cur_Location");
                                    location.setLatitude(lat);
                                    location.setLongitude(lng);

                                    if (!TextUtils.isEmpty(list.get(x).getLat()) &&
                                            !TextUtils.isEmpty(list.get(x).getLong())) {
                                        Location eLocation = new Location("e_Location");
                                        eLocation.setLatitude(Double.parseDouble(list.get(x).getLat()));
                                        eLocation.setLongitude(Double.parseDouble(list.get(x).getLong()));

                                        float distance = calcDistance(location, eLocation);
                                        distance = Float.valueOf(String.format("%.2f", distance));
                                        list.get(x).setDistance(distance);
                                    }
                                }


                                ArrayList<eRaktkoshResponseBean> eRaktkoshResponseBeans = new ArrayList<>();
                                for (int x = 0; x < list.size(); x++) {
                                    if ((list.get(x).getDistance() == 0) || (TextUtils.isEmpty(list.get(x).getName()) || list.get(x).getName().trim().equals("-"))) {
                                        eRaktkoshResponseBeans.add(list.get(x));
                                    }
                                }

                                list.removeAll(eRaktkoshResponseBeans);
                                mainList.addAll(list);
                                tv_total.setVisibility(View.VISIBLE);
                                tv_total.setText("Total Records: " + mainList.size());
                                sortData(mainList);

//                                ArrayList<eRaktkoshResponseBean> newArrayList = new ArrayList<>();
//                                for(int z=0;z<mainList.size();z++){
//                                    if(z<=14) {
//                                        newArrayList.add(mainList.get(z));
//                                    }
//                                }
//
//                                Gson gson = new Gson();
//                                String s = gson.toJson(newArrayList);
//                                Log.d("JSON_LIST", "onResponse: "+s);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            bb_rv.setHasFixedSize(true);
                            bb_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            bbAdapter = new BBAdapter(getActivity(), mainList);
                            bb_rv.setAdapter(bbAdapter);
                            bbAdapter.notifyDataSetChanged();

                            if (bank_spinner_position != 0) {
                                spinner_bg.setSelection(bank_spinner_position);
                                String selVal = spinner_bg.getSelectedItem().toString();
                                loadBBanksFilter(selVal);
                            }

                        }
                    } else {
                        bb_rv.setVisibility(View.GONE);
                        iv_switch.setVisibility(View.GONE);
                        no_data_tv.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<eRaktkoshResponseBean>> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        } catch (Exception e) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }

    }

    private void sortData(List<eRaktkoshResponseBean> eRaktkoshResponseBean) {

        Collections.sort(eRaktkoshResponseBean, new Comparator<eRaktkoshResponseBean>() {
            @Override
            public int compare(eRaktkoshResponseBean o1, eRaktkoshResponseBean o2) {

                String[] ar_lhs = String.valueOf(o1.getDistance()).split("\\.");
                String[] ar_rhs = String.valueOf(o2.getDistance()).split("\\.");

                int string_lhs = Integer.parseInt(ar_lhs[0]);
                int string_rhs = Integer.parseInt(ar_rhs[0]);

                /*String ar_lhs = String.valueOf(o1.getDistance()).replace(".","");
                String ar_rhs = String.valueOf(o2.getDistance()).replace(".","");

                int string_lhs = Integer.parseInt(ar_lhs);
                int string_rhs = Integer.parseInt(ar_rhs);*/

                return string_lhs - string_rhs;
            }
        });
    }

    private void sortBDonorData(List<BloodDonorResponse> bloodDonorResponses) {

        Collections.sort(bloodDonorResponses, new Comparator<BloodDonorResponse>() {
            @Override
            public int compare(BloodDonorResponse o1, BloodDonorResponse o2) {

                String[] ar_lhs = String.valueOf(o1.getDistance()).split("\\.");
                String[] ar_rhs = String.valueOf(o2.getDistance()).split("\\.");

                int string_lhs = Integer.parseInt(ar_lhs[0]);
                int string_rhs = Integer.parseInt(ar_rhs[0]);

                /*String ar_lhs = String.valueOf(o1.getDistance()).replace(".","");
                String ar_rhs = String.valueOf(o2.getDistance()).replace(".","");

                int string_lhs = Integer.parseInt(ar_lhs);
                int string_rhs = Integer.parseInt(ar_rhs);*/

                return string_lhs - string_rhs;
            }
        });
    }

    private void callBloodDonorsRequest(final Double lat, final Double lng, final int donor_spinner_position) {
        try {
            bloodDonorResponseMainList.clear();
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<BloodDonorResponse>> call =
                    apiInterface.getBloodDonorsResponse();
            call.enqueue(new Callback<ArrayList<BloodDonorResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<BloodDonorResponse>> call, Response<ArrayList<BloodDonorResponse>> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful() && response.body() != null) {
                        bb_rv.setVisibility(View.VISIBLE);
                        iv_switch.setVisibility(View.VISIBLE);
                        no_data_tv.setVisibility(View.GONE);

                        ArrayList<BloodDonorResponse> bloodDonorResponses = response.body();

                        if (bloodDonorResponses.size() > 0) {

                            try {
                                for (int x = 0; x < bloodDonorResponses.size(); x++) {

                                    Location location = new Location("Cur_Location");
                                    location.setLatitude(lat);
                                    location.setLongitude(lng);

                                    if (!TextUtils.isEmpty(bloodDonorResponses.get(x).getLatitude()) &&
                                            !TextUtils.isEmpty(bloodDonorResponses.get(x).getLongitude())
                                            && !bloodDonorResponses.get(x).getLatitude().trim().equals("null")
                                            && !bloodDonorResponses.get(x).getLongitude().trim().equals("null")) {
                                        Location dLocation = new Location("d_Location");
                                        dLocation.setLatitude(Double.parseDouble(bloodDonorResponses.get(x).getLatitude()));
                                        dLocation.setLongitude(Double.parseDouble(bloodDonorResponses.get(x).getLongitude()));

                                        float distance = calcDistance(location, dLocation);
                                        distance = Float.valueOf(String.format("%.2f", distance));
                                        bloodDonorResponses.get(x).setDistance(distance);
                                    }
                                }

                                ArrayList<BloodDonorResponse> bloodDonorResponseArrayList = new ArrayList<>();
                                for (int x = 0; x < bloodDonorResponses.size(); x++) {
                                    if (bloodDonorResponses.get(x).getDistance() == 0) {
                                        bloodDonorResponseArrayList.add(bloodDonorResponses.get(x));
                                    }
                                }

                                bloodDonorResponses.removeAll(bloodDonorResponseArrayList);
                                bloodDonorResponseMainList.addAll(bloodDonorResponses);
                                tv_total.setVisibility(View.VISIBLE);
                                tv_total.setText("Total Records: " + bloodDonorResponseMainList.size());
                                sortBDonorData(bloodDonorResponseMainList);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            bb_rv.setHasFixedSize(true);
                            bb_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                            bb_rv.setAdapter(bDonorAdapter);
                            bDonorAdapter.notifyDataSetChanged();

                            if (donor_spinner_position != 0) {
                                spinner_bg_donor.setSelection(donor_spinner_position);
                                String selVal = spinner_bg_donor.getSelectedItem().toString();
                                loadBDonorsFilterData(selVal, "Select");
                            }
                        }

                    } else {
                        bb_rv.setVisibility(View.GONE);
                        iv_switch.setVisibility(View.GONE);
                        no_data_tv.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<BloodDonorResponse>> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        } catch (Exception e) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }

    }

    private float calcDistance(Location crntLocation, Location eLocation) {
        return crntLocation.distanceTo(eLocation) / 1000; // in km
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.logout_search:
                // search action
                startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mGpsSwitchStateReceiver);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.activity_searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(searchView);
                    return false;
                }
            });
        }
    }


    private void search(androidx.appcompat.widget.SearchView searchView) {
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (bbAdapter != null) {
                        bbAdapter.getFilter().filter(newText);
                    } else {
                        bDonorAdapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }
}