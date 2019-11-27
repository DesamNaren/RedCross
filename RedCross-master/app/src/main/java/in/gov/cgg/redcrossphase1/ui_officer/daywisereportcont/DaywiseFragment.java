package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;

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
import in.gov.cgg.redcrossphase1.databinding.FragmentDaywiseBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;

public class DaywiseFragment extends Fragment {


    ProgressDialog pd;
    private DaywiseViewModel daywiseViewModel;
    private FragmentDaywiseBinding binding;
    // private RecyclerView rv_district;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_daywise, container, false);


        GlobalDeclaration.home = false;

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading ,Please wait");
        pd.show();
        daywiseViewModel =
                ViewModelProviders.of(this).get(DaywiseViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("Day wise");

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(false);
                // code on swipe refresh
                daywiseViewModel.getDaysCount("3", GlobalDeclaration.districtId, "11").
                        observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                            @Override
                            public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
                                if (alldaywisecounts != null) {
                                    setDataforRV(alldaywisecounts);
                                    pd.dismiss();

                                }
                            }
                        });
            }
        });
        binding.refreshLayout.setColorSchemeColors(Color.RED);


        daywiseViewModel.getDaysCount("3", GlobalDeclaration.districtId, "11").
                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                    @Override
                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
                        if (alldaywisecounts != null) {
                            setDataforRV(alldaywisecounts);
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

    private void setDataforRV(List<DayWiseReportCountResponse> daywisecount) {

        binding.rvDaywiselist.setHasFixedSize(true);
        binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));
        DaywiseAdapter adapter1 = new DaywiseAdapter(getActivity(), daywisecount);
        binding.rvDaywiselist.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


    }
}
