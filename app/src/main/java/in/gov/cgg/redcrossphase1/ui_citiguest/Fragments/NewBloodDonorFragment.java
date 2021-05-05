package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentNewblooddonorBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.BDonorAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodDonorResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.DonorsMapsActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_citiguest.SortBloodDonors;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NewBloodDonorFragment extends BBBaseFragment {

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
    int selectedThemeColor = -1;
    FragmentNewblooddonorBinding binding;
    CustomProgressDialog progressDialog;
    private int spinner_position = 0;
    private BDonorAdapter bDonorAdapter;
    private ArrayList<BloodDonorResponse> bloodDonorResponseMainList;
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
                inflater, R.layout.fragment_newblooddonor, container, false);


        progressDialog = new CustomProgressDialog(getActivity());

        bloodDonorResponseMainList = new ArrayList<>();
        bDonorAdapter = new BDonorAdapter();

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                binding.ivSwitch.setBackgroundColor(getResources().getColor(selectedThemeColor));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        callInitBloodDonor();


        binding.spinnerBgDonor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    spinner_position = position;
                    if (position != 0) {
                        String distVal = binding.spinnerBgDist.getSelectedItem().toString();
                        String bgVal = binding.spinnerBgDonor.getSelectedItem().toString();

                        if (bgVal.contains("Select") && distVal.contains("Select")) {
                            loadAllDonorsFilterData();


                        } else if (!bgVal.contains("Select") && distVal.contains("Select")) {
                            loadBDonorsFilterData(bgVal, distVal);

                        } else if (bgVal.contains("Select") && !distVal.contains("Select")) {
                            loadBDonorsFilterData(bgVal, distVal);
                        }
                    } else {
                        if (bDonorAdapter != null) {
                            binding.bbRv.setVisibility(View.VISIBLE);
                            binding.noDataTv.setVisibility(View.GONE);
                            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                            binding.bbRv.setAdapter(bDonorAdapter);
                            bDonorAdapter.notifyDataSetChanged();
                            binding.tvTotal.setVisibility(View.VISIBLE);
                            binding.tvTotal.setText("Total Records: " + bloodDonorResponseMainList.size());
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

        binding.spinnerBgDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String distVal = binding.spinnerBgDist.getSelectedItem().toString();
                    String bgVal = binding.spinnerBgDonor.getSelectedItem().toString();

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

        binding.ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    if (bDonorAdapter != null) {
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
        return binding.getRoot();

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
            binding.bbRv.setAdapter(bDonorAdapter);
            binding.tvTotal.setVisibility(View.VISIBLE);
            binding.tvTotal.setText("Total Records: " + bloodDonorResponseMainList.size());
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
                binding.bbRv.setVisibility(View.VISIBLE);
                binding.noDataTv.setVisibility(View.GONE);
                binding.tvTotal.setVisibility(View.VISIBLE);
                binding.tvTotal.setText("Filtered Records: " + arrayList.size());
                bDonorAdapter = new BDonorAdapter(getActivity(), arrayList);
                bDonorAdapter.notifyDataSetChanged();
                binding.bbRv.setAdapter(bDonorAdapter);
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

    private void callInitBloodDonor() {
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

    private void callBloodDonorsRequest(final Double lat, final Double lng, final int donor_spinner_position) {
        try {
            bloodDonorResponseMainList.clear();
            progressDialog.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<BloodDonorResponse>> call =
                    apiInterface.getBloodDonorsResponse();
            Log.e("blooddonorsurl", call.request().url().toString());

            call.enqueue(new Callback<ArrayList<BloodDonorResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<BloodDonorResponse>> call, Response<ArrayList<BloodDonorResponse>> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful() && response.body() != null) {
                        binding.bbRv.setVisibility(View.VISIBLE);
                        binding.ivSwitch.setVisibility(View.VISIBLE);
                        binding.noDataTv.setVisibility(View.GONE);

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
                                binding.tvTotal.setVisibility(View.VISIBLE);
                                binding.tvTotal.setText("Total Records: " + bloodDonorResponseMainList.size());
//                                sortBDonorData(bloodDonorResponseMainList);
                                Collections.sort(bloodDonorResponseMainList, new SortBloodDonors());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            binding.bbRv.setHasFixedSize(true);
                            binding.bbRv.setLayoutManager(new LinearLayoutManager(getActivity()));

                            bDonorAdapter = new BDonorAdapter(getActivity(), bloodDonorResponseMainList);
                            binding.bbRv.setAdapter(bDonorAdapter);
                            bDonorAdapter.notifyDataSetChanged();

                            if (donor_spinner_position != 0) {
                                binding.spinnerBgDonor.setSelection(donor_spinner_position);
                                String selVal = binding.spinnerBgDonor.getSelectedItem().toString();
                                loadBDonorsFilterData(selVal, "Select");
                            }
                        } else {
                            binding.bbRv.setVisibility(View.GONE);
                            binding.ivSwitch.setVisibility(View.GONE);
                            binding.noDataTv.setVisibility(View.VISIBLE);
                        }

                    } else {
                        binding.bbRv.setVisibility(View.GONE);
                        binding.ivSwitch.setVisibility(View.GONE);
                        binding.noDataTv.setVisibility(View.VISIBLE);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getActivity()).unregisterReceiver(mGpsSwitchStateReceiver);
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
                    if (bDonorAdapter != null) {
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
