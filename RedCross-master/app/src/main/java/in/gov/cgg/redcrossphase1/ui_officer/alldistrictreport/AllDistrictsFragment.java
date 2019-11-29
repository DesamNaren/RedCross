package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;

public class AllDistrictsFragment extends Fragment implements SearchView.OnQueryTextListener {


    ProgressDialog pd;
    private AllDistrictsViewModel allDistrictsViewModel;
    private FragmentAldistrictBinding binding;
    LevelAdapter adapter1;
    private ArrayList byNameList = new ArrayList();
    private Set<String> byNameListSet = new LinkedHashSet<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_aldistrict, container, false);

//        binding.searchView.setEllipsize(true);
//        binding.searchView.setHint("Search by Name");
//        binding.searchView.setMenuItem(binding.searchimage);

        binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.setQueryHint("Search By Name");

        binding.customCount.llPicker.setVisibility(View.VISIBLE);

        GlobalDeclaration.home = false;

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
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


   /*     binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
//                        .show();
//                Toast.makeText(Main2Activity.this,query,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //  Toast.makeText(Main2Activity.this, newText, Toast.LENGTH_LONG).show();

                if (adapter1 != null) {
                    adapter1.filter(newText);
                }
                return true;
            }
        });



        binding.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                binding.searchView.setVisibility(View.VISIBLE);
                binding.searchView.setSuggestions(null);
                binding.searchView.setHint("Search by Name");
            }

            @Override
            public void onSearchViewClosed() {
                binding.searchView.setVisibility(View.GONE);
            }
        });*/


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
        binding.rvAlldistrictwise.setHasFixedSize(true);
        binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter1 = new LevelAdapter(getActivity(), allDistrictList, "d");
        binding.rvAlldistrictwise.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (adapter1 != null) {
            adapter1.filter(newText);
        }
        return true;
    }


}
