package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;
import java.util.Collections;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentNewbloodbankBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.BBAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_citiguest.MapsActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.SortBloodBanks;
import in.gov.cgg.redcrossphase1.utils.AppConstants;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NewBloodBankFragment extends BBBaseFragment {

    private final BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
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
    public Location mCurrentLocation;
    int selectedThemeColor = -1;
    FragmentNewbloodbankBinding binding;
    CustomProgressDialog progressDialog;
    private BBAdapter bbAdapter;
    private int spinner_position = 0;
    private ArrayList<eRaktkoshResponseBean> mainList;
    private String fromType;
    private int cnt = 0;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_newbloodbank, container, false);

        progressDialog = new CustomProgressDialog(getActivity());

        if (getActivity() != null)
            getActivity().setTitle("Blood Banks Info");

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                binding.ivSwitch.setBackgroundColor(getResources().getColor(selectedThemeColor));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        getActivity().registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        mainList = new ArrayList<>();
        bbAdapter = new BBAdapter();
        setHasOptionsMenu(true);

        //searchView.setQuery("", false);
        //searchView.setIconified(true);
        binding.spinnerBg.setSelection(spinner_position);

        binding.tvTotal.setVisibility(View.GONE);
        binding.bbRv.setAdapter(null);

        callBloodBanks();

        binding.spinnerBg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    spinner_position = position;
                    if (position != 0) {
                        String selVal = binding.spinnerBg.getSelectedItem().toString();
                        loadBBanksFilter(selVal);

                    } else {
                        if (bbAdapter != null) {
                            if (mainList != null) {
                                binding.bbRv.setVisibility(View.VISIBLE);
                                binding.noDataTv.setVisibility(View.GONE);

                                bbAdapter = new BBAdapter(getActivity(), mainList);
                                binding.bbRv.setAdapter(bbAdapter);
                                bbAdapter.notifyDataSetChanged();
                                binding.tvTotal.setVisibility(View.VISIBLE);
                                binding.tvTotal.setText("Total Records: " + mainList.size());
                            } else {

                                callBloodBanks();

                            }

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

        binding.ivSwitch.setOnClickListener(new View.OnClickListener() {
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
                    }
                }
            }
        });
        return binding.getRoot();

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
                            }
                        }
                    }
                };

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.context_null, Toast.LENGTH_LONG).show();
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

            Log.e("bb url", "callERaktakoshRequest: " + call.request().url());

            call.enqueue(new Callback<ArrayList<eRaktkoshResponseBean>>() {
                @Override
                public void onResponse(Call<ArrayList<eRaktkoshResponseBean>> call, Response<ArrayList<eRaktkoshResponseBean>> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful() && response.body() != null) {
                        binding.bbRv.setVisibility(View.VISIBLE);

                        binding.noDataTv.setVisibility(View.GONE);
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
                                binding.tvTotal.setVisibility(View.VISIBLE);
                                binding.tvTotal.setText("Total Records: " + mainList.size());
                                Collections.sort(mainList, new SortBloodBanks());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            binding.bbRv.setHasFixedSize(true);
                            binding.bbRv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            bbAdapter = new BBAdapter(getActivity(), mainList);
                            binding.bbRv.setAdapter(bbAdapter);
                            bbAdapter.notifyDataSetChanged();

                            if (bank_spinner_position != 0) {
                                binding.spinnerBg.setSelection(bank_spinner_position);
                                String selVal = binding.spinnerBg.getSelectedItem().toString();
                                loadBBanksFilter(selVal);
                            }

                        }
                    } else {
                        binding.bbRv.setVisibility(View.GONE);
                        binding.noDataTv.setVisibility(View.VISIBLE);
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

    private float calcDistance(Location crntLocation, Location eLocation) {
        return crntLocation.distanceTo(eLocation) / 1000; // in km
    }

    private void loadBBanksFilter(String selVal) {
        if (bbAdapter != null) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }

            bbAdapter = new BBAdapter(getActivity(), mainList);
            ArrayList<eRaktkoshResponseBean> arrayList = bbAdapter.getBBFilter(selVal);

            if (arrayList != null && arrayList.size() > 0) {
                binding.bbRv.setVisibility(View.VISIBLE);
                binding.noDataTv.setVisibility(View.GONE);
                binding.tvTotal.setVisibility(View.VISIBLE);
                binding.tvTotal.setText("Filtered Records: " + arrayList.size());
                bbAdapter = new BBAdapter(getActivity(), arrayList);
                bbAdapter.notifyDataSetChanged();
                binding.bbRv.setAdapter(bbAdapter);
                binding.bbRv.smoothScrollToPosition(0);

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } else {
                binding.tvTotal.setText("Filtered Records: " + 0);
                binding.bbRv.setVisibility(View.GONE);
                binding.noDataTv.setVisibility(View.VISIBLE);

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }

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
        inflater.inflate(R.menu.activity_searchmenu_citi, menu);
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

}
