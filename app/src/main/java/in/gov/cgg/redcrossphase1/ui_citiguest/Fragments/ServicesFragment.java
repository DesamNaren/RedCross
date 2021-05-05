package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.ServeAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBeanMainBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ServicesFragment extends Fragment {

    ServeAdapter serveAdapter;
    RecyclerView rv;
    RelativeLayout rl_header;
    int selectedThemeColor = -1;
    ArrayList<ServeBean> servList;
    private CustomProgressDialog progressDialog;
    private ArrayList<ServeBean> mainList;
    private TextView noDataTV;
    private Spinner spinner_bg;
    private FragmentActivity c;
    private androidx.appcompat.widget.SearchView searchView;
    //private List<MembersipDistResponse> MembersipDistResponseList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        View root = inflater.inflate(R.layout.fragment_services, container, false);
        Objects.requireNonNull(getActivity()).setTitle("SERV");
        progressDialog = new CustomProgressDialog(getActivity());
        rv = root.findViewById(R.id.bb_rv);
        rl_header = root.findViewById(R.id.rl_header);
        noDataTV = root.findViewById(R.id.noDataTV);
        spinner_bg = root.findViewById(R.id.spinner_bg);
        // sortData(mainList);

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);

        } catch (Exception e) {
            e.printStackTrace();

        }
        c = getActivity();
        setHasOptionsMenu(true);

        if (CheckInternet.isOnline(getActivity())) {

            calladditionsCentersService("SERV Volunteer");
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


        spinner_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    String selVal = spinner_bg.getSelectedItem().toString();
                    if (serveAdapter != null) {
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


                        serveAdapter.getBBFilter(selVal);
                        serveAdapter.notifyDataSetChanged();
                        rv.smoothScrollToPosition(0);

                    }
                } else {
                    if (serveAdapter != null) {
                        serveAdapter = new ServeAdapter(getActivity(), mainList, selectedThemeColor);
                        rv.setAdapter(serveAdapter);
                        serveAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return root;
    }


    private void calladditionsCentersService(String type) {

        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiInterface apiServiceSession = ApiClient.getClient().create(ApiInterface.class);


        Call<ServeBeanMainBean> call = apiServiceSession.getadditionsCentersService(type);
        Log.e("apiServiceSession_url: ", "" + call.request().url());
        call.enqueue(new Callback<ServeBeanMainBean>() {
            @Override
            public void onResponse(Call<ServeBeanMainBean> call, Response<ServeBeanMainBean> response) {
                // hideProgressDialog(progressDiaLogss);
                progressDialog.dismiss();
                try {
                    ServeBeanMainBean body = response.body();

                    if (body != null) {
                        mainList = (ArrayList<ServeBean>) body.getAdditionscenters();
                        servList = new ArrayList<>();
                        for (int x = 0; x < mainList.size(); x++) {
                            if (mainList.get(x).getType().contains("SERV")) {
                                servList.add(mainList.get(x));
                            }
                        }

                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int x = 0; x < mainList.size(); x++) {
                            arrayList.add(mainList.get(x).getDistirctid());
                        }


                        LinkedHashSet<String> hashSet = new LinkedHashSet<String>(arrayList);
                        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);

                        if (arrayList.size() > 0) {

                            listWithoutDuplicates.add(0, "Select District");
                            ArrayAdapter dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listWithoutDuplicates);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_bg.setAdapter(dataAdapter);
                        }

                        if (servList.size() > 0) {

                            //filterIV.setVisibility(View.VISIBLE);
                            rv.setVisibility(View.VISIBLE);
                            noDataTV.setVisibility(View.GONE);
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rv.setLayoutManager(layoutManager);

                            serveAdapter = new ServeAdapter(getActivity(), servList, selectedThemeColor);
                            rv.setAdapter(serveAdapter);
                            serveAdapter.notifyDataSetChanged();


                        } else {
                            rv.setVisibility(View.GONE);
                            noDataTV.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //  showResponseAlert();
                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), getResources().getString(R.string.somethingwentwrong) + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.toString());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ServeBeanMainBean> call, Throwable t) {
                //hideProgressDialog(progressDiaLogss);
                progressDialog.dismiss();
                Log.e("Exp", t.toString());

            }
        });

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

    private void search(androidx.appcompat.widget.SearchView searchView) {
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (serveAdapter != null) {
                        serveAdapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }


/*
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
                        MembershipDistAdaptor adapter = new MembershipDistAdaptor(getActivity(), R.layout.listitems_layout, R.id.title, MembersipDistResponseList);
                        spinner_bg.setAdapter(adapter);

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
*/


}


