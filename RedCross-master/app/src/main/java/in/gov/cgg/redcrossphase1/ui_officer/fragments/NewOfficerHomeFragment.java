package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.graphics.RectF;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragemtHomeOfficerNewBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.DistrictViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.GenderwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.GovtPvtViewModel;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static android.content.Context.MODE_PRIVATE;
import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class NewOfficerHomeFragment extends Fragment {
    private final RectF onValueSelectedRectF = new RectF();

    int selectedThemeColor = -1;
    GovtPvtViewModel govtPvtViewModel;
    private DistrictViewModel districtViewModel;
    private AgewiseViewModel agewiseViewModel;
    private BloodwiseViewModel bloodwiseVm;
    private GenderwiseViewModel genderwiseViewModel;
    private String gender, bloodwise, age, top5, btm5;
    private Fragment fragment;
    private LineChart lineChart;
    private FragemtHomeOfficerNewBinding binding;
    private String D_TYPE;
    private String type;
    private HomeViewPagerAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragemt_home_officer_new, container, false);


        getActivity().setTitle("Dashboard");
        districtViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "district")).get(DistrictViewModel.class);

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            //Log.e("themecolor", String.valueOf(selectedThemeColor));
            callThemesChanges();

        } catch (Exception e) {
            e.printStackTrace();

        }
        if (GlobalDeclaration.role.contains("D")) {
            setupViewPagerAll(binding.viewpagerHome, "Mandals");
        } else {
            setupViewPagerAll(binding.viewpagerHome, "Districts");
        }

        binding.viewpagerHome.setOffscreenPageLimit(3);

        districtViewModel.getDashboardCounts(GlobalDeclaration.districtId)
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

        binding.tabsHome.setupWithViewPager(binding.viewpagerHome);


        GlobalDeclaration.home = true;
        GlobalDeclaration.Selection_type = "JRC";
        reload();


        binding.fabDaywise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DaywiseFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_officer, fragment);
                fragmentTransaction.commit();
            }
        });


        binding.customCount.llJrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        callThemesChangesJRC();
                        setupViewPagerJRC(binding.viewpagerHome);
                        GlobalDeclaration.Selection_type = "JRC";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llYrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        callThemesChangesYRC();
                        setupViewPagerYRC(binding.viewpagerHome);
                        GlobalDeclaration.Selection_type = "YRC";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llLm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        callThemesChangesMembership();
                        setupViewPagermMembeship(binding.viewpagerHome);
                        GlobalDeclaration.Selection_type = "Membership";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        callThemesChanges();
                        if (GlobalDeclaration.role.contains("D")) {
                            setupViewPagerAll(binding.viewpagerHome, "Mandals");
                        } else {
                            setupViewPagerAll(binding.viewpagerHome, "Districts");
                        }
                        GlobalDeclaration.Selection_type = "JRC";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });


        return binding.getRoot();

    }

    private void callThemesChangesMembership() {
        selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                MODE_PRIVATE).getInt("theme_color", -1);
        if (selectedThemeColor != -1) {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabselected));
            } else {
                binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));


        }
    }

    private void callThemesChangesYRC() {
        selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                MODE_PRIVATE).getInt("theme_color", -1);
        if (selectedThemeColor != -1) {

            binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabselected));
            } else {
                binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));


        }
    }

    private void callThemesChangesJRC() {
        selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                MODE_PRIVATE).getInt("theme_color", -1);
        if (selectedThemeColor != -1) {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabselected));
            } else {
                binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
                binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));

        }
    }

    private void callThemesChanges() {
        selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                MODE_PRIVATE).getInt("theme_color", -1);
        if (selectedThemeColor != -1) {

            binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
            binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));

            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabselected));
            } else {
                binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
                binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabselected));
                binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(colorPrimary));

            }
        } else {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabselected));
            binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(colorPrimary));

        }
    }

    private void reload() {

        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();

        //pager.setCurrentItem(0);
    }


    private void setupViewPagerAll(ViewPager viewPager_homer, String mandals) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NewAllDistrictsFragment(), mandals, type);
        adapter.addFragment(new GenderwiseFragment(), "Gender & Age", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new GovtPvtFragment(), "Govt vs Pvt", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setupViewPagerYRC(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        // adapter.addFragment(new NewAllDistrictsFragment(), "Districts", type);
        adapter.addFragment(new GenderwiseFragment(), "Gender & Age", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new GovtPvtFragment(), "Govt vs Pvt", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setupViewPagerJRC(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        // adapter.addFragment(new NewAllDistrictsFragment(), "Districts", type);
        adapter.addFragment(new OnlyGenderwiseFragment(), "Gender", type);
        adapter.addFragment(new AgewiseFragment(), "Age", type);
        adapter.addFragment(new GovtPvtFragment(), "GovtVsPvt", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setupViewPagermMembeship(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        // adapter.addFragment(new NewAllDistrictsFragment(), "Districts", type);
        adapter.addFragment(new OnlyGenderwiseFragment(), "Gender", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new AgewiseFragment(), "Age", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() +
                    dashboardCountResponse.getMs();
            binding.customCount.tvAllcount.setText(String.valueOf(total));
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));

        }
    }


    public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private FragmentManager mFragmentManager;

        HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title, String type) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    public static class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;


        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value) {
            return "" + (value);
        }

        @Override
        public String getBarLabel(BarEntry barEntry) {
            return "" + (barEntry);
        }
    }
}
