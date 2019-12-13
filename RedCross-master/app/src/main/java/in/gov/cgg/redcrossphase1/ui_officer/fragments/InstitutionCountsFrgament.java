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

import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentInstituioncountsBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.InstitutionAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.AllDistrictsViewModel;

import static android.content.Context.MODE_PRIVATE;
import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;

public class InstitutionCountsFrgament extends Fragment {
    int selectedThemeColor = -1;

    FragmentInstituioncountsBinding binding;
    InstitutionAdapter adapter1;
    private androidx.appcompat.widget.SearchView searchView;
    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_instituioncounts, container, false);


        binding.tvNodata.setVisibility(View.VISIBLE);
        GlobalDeclaration.home = false;
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            callThemesChanges();

        } catch (Exception e) {
            e.printStackTrace();

        }
        AllDistrictsViewModel model = ViewModelProviders.of(this, new CustomDistricClass(getActivity(),
                "alldistrict")).get(AllDistrictsViewModel.class);


        Objects.requireNonNull(getActivity()).setTitle("Institution wise");

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }


        model.getInstitutionCounts().observe(getActivity(), new Observer<List<UserTypesList>>() {
            @Override
            public void onChanged(@Nullable List<UserTypesList> alldaywisecounts) {
                if (alldaywisecounts != null) {
                    setDataforRV(alldaywisecounts);
                }
            }
        });


        return binding.getRoot();

    }


    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {
            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));
            binding.customCount.tvAllcount.setText(String.valueOf(total));
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
            binding.rvDaywiselist.setHasFixedSize(true);
            binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));

            InstitutionAdapter adapter1 = new InstitutionAdapter(getActivity(), daywisecount, selectedThemeColor);
            binding.rvDaywiselist.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();


        } else {
            binding.tvNodata.setText("No data available");

            binding.rvDaywiselist.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
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
                    if (!GlobalDeclaration.role.contains("D")) {
                        Fragment frag = new NewOfficerHomeFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                                frag).addToBackStack(null).commit();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        menu.findItem(R.id.logout_search).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.logout_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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

            queryTextListener1 = new SearchView.OnQueryTextListener() {
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
            searchView.setOnQueryTextListener(queryTextListener1);
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
        searchView.setOnQueryTextListener(queryTextListener1);
        return super.onOptionsItemSelected(item);
    }


    private void callThemesChanges() {
        if (selectedThemeColor != -1) {

            binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));

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
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
        }
    }

}
