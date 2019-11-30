package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;

import android.app.ProgressDialog;
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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentDaywiseBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.OfficerMainActivity;

import static android.content.Context.MODE_PRIVATE;

public class DaywiseFragment extends Fragment {

    int selectedThemeColor = -1;
    ProgressDialog pd;
    private DaywiseViewModel daywiseViewModel;
    private FragmentDaywiseBinding binding;
    private List<DayWiseReportCountResponse> reveList = new ArrayList<>();

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
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
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

        //int r= sortDatesHere();

        Collections.reverse(daywisecount);
        binding.rvDaywiselist.setHasFixedSize(true);
        binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));
        DaywiseAdapter adapter1 = new DaywiseAdapter(getActivity(), daywisecount, selectedThemeColor);
        binding.rvDaywiselist.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


    }

    private int sortDatesHere() {
        List<String> values = new ArrayList<String>();
        values.add("30-03-2012");
        values.add("28-03-2013");
        values.add("31-03-2012");
        Collections.sort(values, new Comparator<String>() {

            @Override
            public int compare(String arg0, String arg1) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                int compareResult = 0;
                try {
                    Date arg0Date = format.parse(arg0);
                    Date arg1Date = format.parse(arg1);
                    compareResult = arg0Date.compareTo(arg1Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    compareResult = arg0.compareTo(arg1);
                    Log.e("sorted", String.valueOf(compareResult));
                }
                return compareResult;
            }
        });
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_backpress, menu);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setIcon(R.drawable.ic_home_white_48dp);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), OfficerMainActivity.class));
                return false;
            }
        });

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
        return super.onOptionsItemSelected(item);
    }
}
