package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentAldistrictBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;

public class AllVillageFragment extends Fragment {


    ProgressDialog pd;
    String value;
    private AllDistrictsViewModel allDistrictsViewModel;
    private FragmentAldistrictBinding binding;
    // private RecyclerView rv_district;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_aldistrict, container, false);


        GlobalDeclaration.home = false;

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading ,Please wait");
        pd.show();
        allDistrictsViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "alldistrict")).
                        get(AllDistrictsViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("Village wise");

        if (getArguments() != null) {
            value = getArguments().getString("mid");
        }

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(false);
                // code on swipe refresh
                allDistrictsViewModel.getAllVillages("VillageWise", "3", value).
                        observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
                            @Override
                            public void onChanged(@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
                                if (allDistrictList != null) {
                                    setDataforRV(allDistrictList);
                                    pd.dismiss();
                                }
                            }
                        });
            }
        });
        binding.refreshLayout.setColorSchemeColors(Color.RED);

        allDistrictsViewModel.getAllVillages("VillageWise", "3", value).
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
        binding.rvAlldistrictwise.setHasFixedSize(true);
        binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
        LevelAdapter adapter1 = new LevelAdapter(getActivity(), allDistrictList, "");
        binding.rvAlldistrictwise.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


    }
}
