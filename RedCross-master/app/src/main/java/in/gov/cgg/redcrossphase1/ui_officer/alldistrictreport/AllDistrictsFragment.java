package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentAldistrictBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.OfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;

import static android.content.Context.MODE_PRIVATE;

public class AllDistrictsFragment extends Fragment {


    ProgressDialog pd;
    private AllDistrictsViewModel allDistrictsViewModel;
    private FragmentAldistrictBinding binding;
    LevelAdapter adapter1;
    int selectedThemeColor = -1;
    private ArrayList byNameList = new ArrayList();
    private Set<String> byNameListSet = new LinkedHashSet<>();
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_aldistrict, container, false);

//        binding.searchView.setEllipsize(true);
//        binding.searchView.setHint("Search by Name");
//        binding.searchView.setMenuItem(binding.searchimage);

     /*   binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.setQueryHint("Search By Name");*/

        binding.customCount.llPicker.setVisibility(View.VISIBLE);
        binding.customCount.cvName.setVisibility(View.VISIBLE);

        GlobalDeclaration.home = false;

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                }
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvTotal.setTextColor(getResources().getColor(selectedThemeColor));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading ,Please wait");
        pd.show();
        allDistrictsViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(),
                        "alldistrict")).get(AllDistrictsViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("District wise");

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(false);
                // code on swipe refresh
                allDistrictsViewModel.getAllDistrcts("DistrictWise", "3").
                        observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
                            @Override
                            public void onChanged(@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
                                if (allDistrictList != null) {
                                    setDataforRV(allDistrictList);
                                    //setUpearchView(allDistrictList);
                                    pd.dismiss();
                                }
                            }
                        });
            }
        });
        binding.refreshLayout.setColorSchemeColors(Color.RED);

        allDistrictsViewModel.getAllDistrcts("DistrictWise", "3").
                observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
                    @Override
                    public void onChanged(@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
                        if (allDistrictList != null) {
                            setDataforRV(allDistrictList);
                            pd.dismiss();
                        }
                    }
                });


        return binding.getRoot();

    }


    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {
            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvTotalcount.setText(String.valueOf(total));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));

        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void setDataforRV(List<StatelevelDistrictViewCountResponse> allDistrictList) {

        if (allDistrictList.size() > 0) {

            binding.rvAlldistrictwise.setHasFixedSize(true);
            binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter1 = new LevelAdapter(getActivity(), allDistrictList, "d", selectedThemeColor);
            binding.rvAlldistrictwise.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

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
        inflater.inflate(R.menu.activity_searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        menu.findItem(R.id.logout_search).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.logout_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), OfficerMainActivity.class));
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
