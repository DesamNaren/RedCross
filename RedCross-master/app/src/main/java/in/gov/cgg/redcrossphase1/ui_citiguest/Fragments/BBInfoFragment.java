package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.gov.cgg.redcrossphase1.MapsActivity;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.BBAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.BDonorAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodDonorResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;
import in.gov.cgg.redcrossphase1.utils.AppConstants;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private SearchView searchView;
    private Button btn_blood_banks, btn_blood_donors;
    private GoogleMap googleMap;
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

        setHasOptionsMenu(false);

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
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("E_RAKTAKOSH_DATA", bbAdapter.getFilteredData());
                    bundle.putString("FROM_CLASS", "ALL");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        getActivity().registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));


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
                            callERaktakoshRequest(location.getLatitude(), location.getLongitude());
                        }
                    }
                }
            };

        } else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
        }


        spinner_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selVal = spinner_bg.getSelectedItem().toString();
                    if (bbAdapter != null) {
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


                        tv_total.setVisibility(View.GONE);
                        bbAdapter.getFilter().filter(selVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);


                    }
                } else {
                    if (bbAdapter != null) {
                        bbAdapter = new BBAdapter(getActivity(), mainList);
                        bb_rv.setAdapter(bbAdapter);
                        bbAdapter.notifyDataSetChanged();
                        sortData(mainList);
                        tv_total.setText("Total Records: " + mainList.size());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_bg_donor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String distVal = spinner_bg_dist.getSelectedItem().toString();
                String bgVal = spinner_bg_donor.getSelectedItem().toString();

                if (bgVal.contains("Select") && distVal.contains("Select")) {
                    if (bDonorAdapter != null) {
                        bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                        bb_rv.setAdapter(bDonorAdapter);
                        tv_total.setText("Total Records: " + bloodDonorResponseMainList.size());
                    }
                } else if (!bgVal.contains("Select") && distVal.contains("Select")) {
                    if (bDonorAdapter != null) {
                        bDonorAdapter.getFilter().filter(bgVal + "_" + distVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);
                    }
                } else if (bgVal.contains("Select") && !distVal.contains("Select")) {
                    if (bDonorAdapter != null) {
                        bDonorAdapter.getFilter().filter(bgVal + "_" + distVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);
                    }
                } else {
                    if (bDonorAdapter != null) {
                        bDonorAdapter.getFilter().filter(bgVal + "_" + distVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_bg_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String distVal = spinner_bg_dist.getSelectedItem().toString();
                String bgVal = spinner_bg_donor.getSelectedItem().toString();

                if (bgVal.contains("Select") && distVal.contains("Select")) {
                    if (bDonorAdapter != null) {
                        bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                        bb_rv.setAdapter(bDonorAdapter);
                        tv_total.setText("Total Records: " + bloodDonorResponseMainList.size());
                    }
                } else if (!bgVal.contains("Select") && distVal.contains("Select")) {
                    if (bDonorAdapter != null) {
                        bDonorAdapter.getFilter().filter(bgVal + "_" + distVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);
                    }
                } else if (bgVal.contains("Select") && !distVal.contains("Select")) {
                    if (bDonorAdapter != null) {
                        bDonorAdapter.getFilter().filter(bgVal + "_" + distVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);
                    }
                } else {
                    if (bDonorAdapter != null) {
                        bDonorAdapter.getFilter().filter(bgVal + "_" + distVal);
                        bbAdapter.notifyDataSetChanged();
                        bb_rv.smoothScrollToPosition(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_blood_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_bg.setSelection(0);

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
                        callERaktakoshRequest(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    } else {
                        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        btn_blood_donors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_bg_donor.setSelection(0);
                spinner_bg_dist.setSelection(0);


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
                        callBloodDonorsRequest(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    } else {
                        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        return root;
    }

    private void callERaktakoshRequest(final Double lat, final Double lng) {
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

                                    if (!TextUtils.isEmpty(list.get(x).getLat()) && !TextUtils.isEmpty(list.get(x).getLong())) {
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
                                    if (list.get(x).getDistance() == 0) {
                                        eRaktkoshResponseBeans.add(list.get(x));
                                    }
                                }

                                list.removeAll(eRaktkoshResponseBeans);
                                mainList.addAll(list);
                                tv_total.setVisibility(View.VISIBLE);
                                tv_total.setText("Total Records: " + mainList.size());
                                sortData(list);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            bb_rv.setHasFixedSize(true);
                            bb_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            bbAdapter = new BBAdapter(getActivity(), list);
                            bb_rv.setAdapter(bbAdapter);
                            bbAdapter.notifyDataSetChanged();
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

    private void callBloodDonorsRequest(final Double lat, final Double lng) {
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
                        iv_switch.setVisibility(View.GONE);
                        no_data_tv.setVisibility(View.GONE);

                        ArrayList<BloodDonorResponse> bloodDonorResponses = response.body();

                        if (bloodDonorResponses.size() > 0) {
                            bloodDonorResponseMainList.addAll(bloodDonorResponses);
                            bb_rv.setHasFixedSize(true);
                            bb_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponses);
                            bb_rv.setAdapter(bDonorAdapter);
                            tv_total.setText("Total Records: " + bloodDonorResponses.size());
//                            sortData(bloodDonorResponses);
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
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mGpsSwitchStateReceiver);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        inflater.inflate(R.menu.bb_search_menu, menu);

        MenuItem menuItem = menu.getItem(1);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search by blood bank name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (bbAdapter != null) {
                        bbAdapter.getFilter().filter(newText);
                        bbAdapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

}
