package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentInstituioncountsBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.FinancialSpinnerAdaptor;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.InstitutionAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.FinYear;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;
import in.gov.cgg.redcrossphase1.viewmodels.AllDistrictsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class InstitutionCountsFrgament extends Fragment {
    int selectedThemeColor = -1;

    FragmentInstituioncountsBinding binding;
    private InstitutionAdapter adapter1;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private List<FinYear> years = new ArrayList<>();
    private String spn_year;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_instituioncounts, container, false);


        binding.tvNodata.setVisibility(View.VISIBLE);
        GlobalDeclaration.home = false;
        GlobalDeclaration.Selection_type = "JRC";

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1)
                    , -1);
            callThemesChanges();

        } catch (Exception e) {
            e.printStackTrace();
        }


        final AllDistrictsViewModel model = ViewModelProviders.of(this, new CustomDistricClass(getActivity(),
                "d")).get(AllDistrictsViewModel.class);

        (getActivity()).setTitle("Institution wise");

        model.getfinancialYears().
                observe(getActivity(), new Observer<List<FinYear>>() {
                    @Override
                    public void onChanged(@Nullable List<FinYear> alldaywisecounts) {
                        if (alldaywisecounts != null) {
                            FinancialSpinnerAdaptor spinneradapter = new FinancialSpinnerAdaptor(getActivity(),
                                    R.layout.listitems_layout,
                                    R.id.title, alldaywisecounts);
                            years = alldaywisecounts;
                            binding.spnFinancialyear.setAdapter(spinneradapter);
                            if (spn_year == null) {
                                spn_year = String.valueOf(alldaywisecounts.get(0).getFinYearId());
                            }
                        }
                    }
                });

        binding.spnFinancialyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spn_year = String.valueOf(years.get(position).getFinYearId());
                model.getDashboardCounts(GlobalDeclaration.districtId, spn_year)
                        .observe(Objects.requireNonNull(getActivity()), new Observer<DashboardCountResponse>() {

                            @Override
                            public void onChanged(@Nullable DashboardCountResponse dashboardCountResponse) {
                                if (dashboardCountResponse != null) {
                                    GlobalDeclaration.counts = dashboardCountResponse;
                                    setCountsForDashboard(dashboardCountResponse);
                                } else {
                                    binding.customCount.tvJrccount.setText("");
                                    binding.customCount.tvYrccount.setText("");
                                    binding.customCount.tvLmcount.setText("");
                                    binding.customCount.tvAllcount.setText("");
                                }
                            }
                        });

                model.getInstitutionCounts(spn_year).observe(getActivity(), new Observer<List<UserTypesList>>() {
                    @Override
                    public void onChanged(@Nullable List<UserTypesList> alldaywisecounts) {
                        if (alldaywisecounts != null) {
                            setDataforRV(alldaywisecounts);
                        } else {
                            binding.tvNodata.setText("No data available");
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
            }
        });


        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        } else {
            binding.customCount.tvJrccount.setText("");
            binding.customCount.tvYrccount.setText("");
            binding.customCount.tvLmcount.setText("");
            binding.customCount.tvAllcount.setText("");
        }

//        if (CheckInternet.isOnline(getActivity())) {
//
//            model.getInstitutionCounts(spn_year).observe(getActivity(), new Observer<List<UserTypesList>>() {
//                @Override
//                public void onChanged(@Nullable List<UserTypesList> alldaywisecounts) {
//                    if (alldaywisecounts != null) {
//                        setDataforRV(alldaywisecounts);
//                    } else {
//                        binding.tvNodata.setText("No data available");
//
//                    }
//                }
//            });
//        } else {
//            Toast.makeText(getActivity(), "Please check internet internet", Toast.LENGTH_SHORT).show();
//
//        }

        return binding.getRoot();

    }


    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {
            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));
            binding.customCount.tvAllcount.setText(String.valueOf(total));
        } else {
            binding.customCount.tvJrccount.setText("");
            binding.customCount.tvYrccount.setText("");
            binding.customCount.tvLmcount.setText("");
            binding.customCount.tvAllcount.setText("");
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void setDataforRV(List<UserTypesList> daywisecount) {

        //int r= sortDatesHere();
        int total;
        if (daywisecount.size() > 1) {

            binding.tvNodata.setVisibility(View.GONE);

            binding.rvDaywiselist.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);


            List<UserTypesList> newlist = new ArrayList<>();
            newlist.addAll(daywisecount);

            Collections.sort(newlist, new Comparator<UserTypesList>() {
                @Override
                public int compare(UserTypesList lhs, UserTypesList rhs) {
                    return lhs.getIname().compareTo(rhs.getIname());
                }
            });

            binding.rvDaywiselist.setHasFixedSize(true);
            binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter1 = new InstitutionAdapter(getActivity(), newlist, selectedThemeColor);
            binding.rvDaywiselist.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();


        } else {
            binding.tvNodata.setText("No data available");

            binding.rvDaywiselist.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
            binding.tvNodata.setText("No data available");

        }


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
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new NewOfficerHomeFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });
    }

    private void callThemesChanges() {
        if (selectedThemeColor != -1) {
//
//            binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//            binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));

            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
            } else {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            }
        } else {
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.bb_search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        menu.findItem(R.id.action_logout).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.action_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
            }
        });
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    if (adapter1 != null) {
                        adapter1.filter(newText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


}
