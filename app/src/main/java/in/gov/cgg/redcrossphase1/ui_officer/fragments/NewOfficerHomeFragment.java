package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

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

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragemtHomeOfficerNewBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.FinancialSpinnerAdaptor;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.FinYear;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.viewmodels.AllDistrictsViewModel;

import static android.content.Context.MODE_PRIVATE;
import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class NewOfficerHomeFragment extends Fragment {
    private final RectF onValueSelectedRectF = new RectF();

    int selectedThemeColor = -1;
    AllDistrictsViewModel govtPvtViewModel;
    private AllDistrictsViewModel districtViewModel;
    private Fragment fragment;
    private LineChart lineChart;
    private FragemtHomeOfficerNewBinding binding;
    private String D_TYPE;
    private String type;
    private HomeViewPagerAdapter adapter;
    private List<FinYear> years = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragemt_home_officer_new, container, false);


        getActivity().setTitle("Dashboard");

        districtViewModel =
                ViewModelProviders.of(getActivity(), new CustomDistricClass(getActivity(), "d")).get(AllDistrictsViewModel.class);


        binding.customCount.cvpick.setVisibility(View.VISIBLE);

        districtViewModel.getfinancialYears().
                observe(getViewLifecycleOwner(), new Observer<List<FinYear>>() {
                    @Override
                    public void onChanged(@Nullable List<FinYear> alldaywisecounts) {
                        if (alldaywisecounts != null) {
                            FinancialSpinnerAdaptor spinneradapter = new FinancialSpinnerAdaptor(getActivity(),
                                    R.layout.listitems_layout,
                                    R.id.title, alldaywisecounts);
                            years = alldaywisecounts;
                            binding.customCount.spnFinancialyear.setAdapter(spinneradapter);
                            if (GlobalDeclaration.spn_year == null) {
                                GlobalDeclaration.spn_year = String.valueOf(alldaywisecounts.get(0).getFinYearId());
                            }


                        }
                    }
                });

        binding.customCount.spnFinancialyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                GlobalDeclaration.spn_year = String.valueOf(years.get(position).getFinYearId());

                districtViewModel.getDashboardCounts(GlobalDeclaration.districtId, GlobalDeclaration.spn_year)
                        .observe(getViewLifecycleOwner(), new Observer<DashboardCountResponse>() {

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

                reload();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
            }
        });


        if (GlobalDeclaration.Selection_type.equalsIgnoreCase("Membership")) {
            try {
                selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                        MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
                //Log.e("themecolor", String.valueOf(selectedThemeColor));
                callThemesChangesMembership();
                if (GlobalDeclaration.role.contains("D")) {
                    temp(binding.viewpagerHome);
                } else {
                    setupViewPagermMembeship(binding.viewpagerHome, "Districts");
                }
                GlobalDeclaration.Selection_type = "Membership";
                reload();
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            GlobalDeclaration.Selection_type = "";

            try {
                selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                        MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
                callThemesChanges();
                if (GlobalDeclaration.role.contains("D")) {
                    setupViewPagerAll(binding.viewpagerHome, "Mandals");
                } else {
                    setupViewPagerAll(binding.viewpagerHome, "Districts");
                }
                GlobalDeclaration.Selection_type = "";
                reload();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }


        binding.viewpagerHome.setOffscreenPageLimit(3);
//        if (CheckInternet.isOnline(getActivity())) {
//
//            districtViewModel.getDashboardCounts(GlobalDeclaration.districtId)
//                    .observe(Objects.requireNonNull(getActivity()), new Observer<DashboardCountResponse>() {
//
//                        @Override
//                        public void onChanged(@Nullable DashboardCountResponse dashboardCountResponse) {
//                            if (dashboardCountResponse != null) {
//                                GlobalDeclaration.counts = dashboardCountResponse;
//                                setCountsForDashboard(dashboardCountResponse);
//                            } else {
//                                binding.customCount.tvJrccount.setText("");
//                                binding.customCount.tvYrccount.setText("");
//                                binding.customCount.tvLmcount.setText("");
//                                binding.customCount.tvAllcount.setText("");
//                            }
//                        }
//                    });
//        } else {
//            Toast.makeText(getActivity(), "Please check internet internet", Toast.LENGTH_SHORT).show();
//
//        }

        binding.tabsHome.setupWithViewPager(binding.viewpagerHome);


        GlobalDeclaration.home = true;
//        reload();


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
                        if (GlobalDeclaration.role.contains("D")) {
                            temp(binding.viewpagerHome);
                        } else {
                            setupViewPagermMembeship(binding.viewpagerHome, "Districts");
                        }
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
                        GlobalDeclaration.Selection_type = "";
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
        selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
        if (selectedThemeColor != -1) {
            binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
            binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

            binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
            binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

            binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

            binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
            binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));

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
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme8_seleetedbg));
            } else {
                binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
                binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

                binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
                binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

                binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
                binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
            binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

            binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
            binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

            binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));


        }
    }

    private void callThemesChangesYRC() {
        selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
        if (selectedThemeColor != -1) {

            binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
            binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

            binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
            binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

            binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));

            binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));

            binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));


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
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else {
                binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
                binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

                binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
                binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

                binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));

                binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
                binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
            binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

            binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
            binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

            binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));

            binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));


        }
    }

    private void callThemesChangesJRC() {
        selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
        if (selectedThemeColor != -1) {

            binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));

            binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
            binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

            binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

            binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
            binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));

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
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else {
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));

                binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
                binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

                binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
                binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

                binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
                binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            }
        } else {

            binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));

            binding.customCount.tvAllcount.setTextColor(Color.parseColor("#000000"));
            binding.customCount.tvAllname.setTextColor(Color.parseColor("#000000"));

            binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

            binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));

        }
    }

    private void callThemesChanges() {
        selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
        if (selectedThemeColor != -1) {

            binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(white));

            binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
            binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

            binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

            binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
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
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else {

                binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
                binding.customCount.tvAllname.setTextColor(getResources().getColor(white));

                binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
                binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

                binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
                binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

                binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
                binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(colorPrimary));

            }
        } else {

            binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(white));

            binding.customCount.tvJrccount.setTextColor(Color.parseColor("#16508D"));
            binding.customCount.tvJrcnme.setTextColor(Color.parseColor("#16508D"));

            binding.customCount.tvYrccount.setTextColor(Color.parseColor("#d1395c"));
            binding.customCount.tvYrcnme.setTextColor(Color.parseColor("#d1395c"));

            binding.customCount.tvLmcount.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.tvLmname.setTextColor(Color.parseColor("#07584E"));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
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

    private void setupViewPagermMembeship(ViewPager viewPager_homer, String mandals) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MemberDistrictFragment(), mandals, type);
        adapter.addFragment(new OnlyGenderwiseFragment(), "Gender", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new AgewiseFragment(), "Age", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void temp(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
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

}